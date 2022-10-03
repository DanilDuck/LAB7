package database;

import auth.User;
import auth.UserManager;
import collection.PersonCollectionManager;
import data.*;
import exceptions.CollectionException;
import exceptions.DatabaseException;
import exceptions.InvalidDataException;
import exceptions.InvalidEnumException;
import log.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class PersonDatabaseManager extends PersonCollectionManager {
    //language=SQL
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss");
    private final static String INSERT_WORKER_QUERY = "INSERT INTO PERSONS (name, coordinates_x, coordinates_y, creation_date, height, passportid, eyecolor, nationality, location_x, location_y, location_z, user_login,id)" +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,DEFAULT) RETURNING id; ";
    private final DatabaseHandler databaseHandler;
    private final UserManager userManager;

    public PersonDatabaseManager(DatabaseHandler c, UserManager userManager) throws DatabaseException {
        super();
        databaseHandler = c;
        this.userManager = userManager;
        create();
    }

    private void create()throws DatabaseException {
        //language=SQL
        String create = "CREATE TABLE IF NOT EXISTS PERSONS (" +
                "id SERIAL PRIMARY KEY CHECK ( id > 0 )," +
                "name TEXT NOT NULL CHECK (name <> '')," +
                "coordinates_x INT NOT NULL," +
                "coordinates_y FLOAT NOT NULL," +
                "creation_date TEXT NOT NULL," +
                "height FLOAT NOT NULL ," +
                "passportid TEXT NOT NULL," +
                "eyecolor TEXT NOT NULL," +
                "nationality TEXT NOT NULL," +
                "location_x INT NOT NULL," +
                "location_y REAL NOT NULL," +
                "location_z INT NOT NULL," +
                "user_login TEXT NOT NULL REFERENCES USERS(login)"+
                ");";

        try (PreparedStatement createStatement = databaseHandler.getPreparedStatement(create)) {
            createStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("cannot create PERSONS database");
        }
    }

    @Override
    public long generateID() {
        try (PreparedStatement statement = databaseHandler.getPreparedStatement("SELECT nextval('id')")) {
            ResultSet r = statement.executeQuery();
            r.next();
            return r.getInt(1);
        } catch (SQLException e) {
            return 1;
        }
    }

    private void setPerson(PreparedStatement statement, Person person) throws SQLException {
        statement.setString(1, person.getName());
        statement.setInt(2, person.getCoordinates().getX());
        statement.setFloat(3, person.getCoordinates().getY());
        statement.setString(4, formatter.format(person.getCreationDate()));
        statement.setFloat(5, person.getHeight());
        statement.setString(6, person.getPassportID());
        statement.setString(7, person.getEyeColor().toString());
        statement.setString(8, person.getNationality().toString());
        statement.setInt(9, person.getLocation().getX());
        statement.setDouble(10, person.getLocation().getY());
        statement.setInt(11, person.getLocation().getZ());
        statement.setString(12, person.getUserLogin());
    }
    @Override
    public void updateById(Long id, Person person) {
        databaseHandler.setCommitMode();
        databaseHandler.setSavepoint();
        //language=SQL
        String sql = "UPDATE PERSONS SET " +
                "name=?," +
                "coordinates_x=?," +
                "coordinates_y=?," +
                "creation_date=?," +
                "height=?," +
                "passportid=?," +
                "eyecolor=?," +
                "nationality=?," +
                "location_x=?," +
                "location_y=?," +
                "location_z=?,"+
                "user_login=?" +
                "WHERE id=?";
        try (PreparedStatement statement = databaseHandler.getPreparedStatement(sql)) {
            setPerson(statement, person);
            statement.setLong(13, id);
            statement.execute();
            databaseHandler.commit();
        } catch (SQLException e) {
            databaseHandler.rollback();
            throw new DatabaseException("cannot update person #" + id + " in database");
        } finally {
            databaseHandler.setNormalMode();
        }
        super.updateById(id, person);
    }


    private Person getPerson(ResultSet resultSet) throws SQLException, InvalidDataException {
        Coordinates coordinates = new Coordinates(resultSet.getInt("coordinates_x"), resultSet.getLong("coordinates_y"));
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");

        java.time.LocalDateTime creationDate = LocalDateTime.parse(resultSet.getString("creation_date") , formatter);
        float height = resultSet.getFloat("height");
        Color eyecolor;
        Country country;
        try {
            eyecolor = Color.valueOf(resultSet.getString("eyecolor"));
            country = Country.valueOf(resultSet.getString("nationality"));
        } catch (IllegalArgumentException e) {
            throw new InvalidEnumException();
        }
        String passportId = resultSet.getString("passportid");
        Location location = new Location(resultSet.getInt("location_x"), resultSet.getDouble("location_y"), resultSet.getInt("location_z"));
        Person worker = new Person(name, coordinates, height, passportId,eyecolor,country,location);
        worker.setCreationDateTime(creationDate);
        worker.setId(id);
        worker.setUserLogin(resultSet.getString("user_login"));
        if (!userManager.isPresent(worker.getUserLogin())) throw new DatabaseException("no user found");
        return worker;
    }

    @Override
    public void add(Person person) {
        databaseHandler.setCommitMode();
        databaseHandler.setSavepoint();
        try (PreparedStatement statement = databaseHandler.getPreparedStatement(INSERT_WORKER_QUERY, true)) {
            setPerson(statement, person);
            if (statement.executeUpdate() == 0) throw new DatabaseException();
            ResultSet resultSet = statement.getGeneratedKeys();

            if (!resultSet.next()) throw new DatabaseException();
            person.setId(resultSet.getInt(resultSet.findColumn("id")));

            databaseHandler.commit();
        } catch (SQLException | DatabaseException e) {
            databaseHandler.rollback();
            throw new DatabaseException("cannot add to database");
        } finally {
            databaseHandler.setNormalMode();
        }
        super.addWithoutIdGeneration(person);
    }

    @Override
    public void removeID(Long id) {
        //language=SQL
        String query = "DELETE FROM PERSONS WHERE id = ?;";
        try (PreparedStatement statement = databaseHandler.getPreparedStatement(query)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("cannot remove from database");
        }
        super.removeID(id);
    }

    @Override
    public void addIfMaxVoid(Person person) {
        //language=SQL
        String getMaxQuery = "SELECT MAX(id) FROM PERSONS";

        if (getTreeMap().isEmpty()) {
            add(person);
            return;
        }
        databaseHandler.setCommitMode();
        databaseHandler.setSavepoint();
        try (Statement getStatement = databaseHandler.getStatement();
             PreparedStatement insertStatement = databaseHandler.getPreparedStatement(INSERT_WORKER_QUERY)) {

            ResultSet resultSet = getStatement.executeQuery(getMaxQuery);
            if (!resultSet.next()) throw new DatabaseException("unable to add");

            long maxId = resultSet.getLong(1);
            if (person.getId() < maxId)
                throw new DatabaseException("unable to add, max id is " + maxId + " current id is " + person.getId());

            setPerson(insertStatement, person);

            person.setId(resultSet.getInt("id"));
            databaseHandler.commit();
        } catch (SQLException e) {
            databaseHandler.rollback();
            throw new DatabaseException("cannot add due to internal error");
        } finally {
            databaseHandler.setNormalMode();
        }
        super.addWithoutIdGeneration(person);
    }


    public void clear(User user) {
        databaseHandler.setCommitMode();
        databaseHandler.setSavepoint();
        Set<Integer> ids = new HashSet<>();
        try (PreparedStatement statement = databaseHandler.getPreparedStatement("DELETE FROM PERSONS WHERE user_login=? RETURNING id")) {
            statement.setString(1, user.getLogin());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                ids.add(id);
            }
        } catch (SQLException | CollectionException e) {
            databaseHandler.rollback();
            deserializeVoidCollection("");
            throw new DatabaseException("can't clear database");
        } finally {
            databaseHandler.setNormalMode();
        }
        clear();
    }

    @Override
    public void deserializeVoidCollection(String ignored) {
        if (!getTreeMap().isEmpty()) super.clear();
        //language=SQL
        String query = "SELECT * FROM PERSONS";
        try (PreparedStatement selectAllStatement = databaseHandler.getPreparedStatement(query)) {
            ResultSet resultSet = selectAllStatement.executeQuery();
            int damagedElements = 0;
            while (resultSet.next()) {
                try {
                    Person person = getPerson(resultSet);
                    super.addWithoutIdGeneration(person);
                } catch (InvalidDataException | SQLException e) {
                    damagedElements += 1;
                }
            }
            if (super.getTreeMap().isEmpty()) throw new DatabaseException("nothing to load");
            if (damagedElements == 0) Log.logger.info("collection successfully loaded");
            else Log.logger.warn(damagedElements + " elements are damaged");
        } catch (SQLException e) {
            throw new DatabaseException("cannot load");
        }

    }
}
