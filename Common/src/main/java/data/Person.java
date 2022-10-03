package data;

import auth.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Person class
 */
public class Person implements Collectionable, Serializable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float height; //Поле может быть null, Значение поля должно быть больше 0
    private String passportID; //Длина строки должна быть не меньше 10, Значение этого поля должно быть уникальным, Строка не может быть пустой, Поле может быть null
    private Color eyeColor; //Поле может быть null
    private Country nationality; //Поле может быть null
    private Location location; //Поле может быть null

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    String userLogin;
    /**
     * constructor, just set fields
     * @param name
     * @param coordinates
     * @param height
     * @param passportID
     * @param eyeColor
     * @param nationality
     * @param location
     */
    public Person(String name, Coordinates coordinates, Float height, String passportID, Color eyeColor, Country nationality, Location location){
        creationDate = LocalDateTime.now();
        this.name = name;
        this.coordinates = coordinates;
        this.height = height;
        this.passportID =passportID;
        this.eyeColor = eyeColor;
        this.nationality = nationality;
        this.location = location;
    }
    /**
     * @return String
     */
    @Override
    public String toString() {
        String s = "";
        s += "{\n";
        s += "  \"id\" : " + Long.toString(id) + ",\n";
        s += "  \"name\" : " + "\"" + name + "\"" + ",\n";
        s += "  \"coordinates\" : " + coordinates.toString() + ",\n";
        s += "  \"creationDate\" : " + "\"" +  creationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +"\"" + ",\n";
        s += "  \"height\" : " + "\"" +  height +"\"" + ",\n";
        s += "  \"passportID\" : " + "\"" +  passportID +"\"" + ",\n";
        s += "  \"eyeColor\" : " + "\"" +  eyeColor +"\"" + ",\n";
        s += "  \"nationality\" : " + "\"" + nationality +"\"" + ",\n";
        s += "  \"location : " + "x :" + location.getX()+" y: "+ location.getY()+" z:"+ location.getZ() +"\"" + "\n";
        if (userLogin != null) s += "  \"userLogin\" : " + userLogin + "\n";
        s += "}";
        return s;
    }

    public void setId(long id) {
        this.id = id;
    }
    /**
     * @return float
     */
    public float getHeight() {
        return height;
    }
    /**
     * @return String
     */
    public String getPassportID() {
        return passportID;
    }
    /**
     * @return Color
     */
    public Color getEyeColor() {
        return eyeColor;
    }
    /**
     * @return Country
     */
    public Country getNationality() {
        return nationality;
    }
    /**
     * @return Location
     */
    public Location getLocation() {
        return location;
    }
    public Coordinates getCoordinates(){
        return coordinates;
    }
    /**
     * @return long
     */
    public long getId(){
        return id;
    }
    /**
     * @return String
     */
    public String getName() {
        return name;
    }
    /**
     * @param person
     * @return int
     */

    public void setUser(User usr) {
        userLogin = usr.getLogin();
    }
    public int compareTo(Collectionable person){
        return Float.compare(this.id, person.getId());
    }
    public void setCreationDateTime(LocalDateTime dateTime){
        creationDate = dateTime;
    }
}

