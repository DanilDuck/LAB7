package commands;

import auth.User;
import collection.CollectionManager;
import data.Person;
import exceptions.*;

public class UpdateCommand extends CommandImpl{
    private CollectionManager<Person> collectionManager;
    public UpdateCommand(CollectionManager<Person> cm){
        super("update",CommandType.NORMAL);
        collectionManager = cm;
    }
    @Override
    public String execute() throws InvalidDataException, AuthException {
        User user = getArgument().getUser();
        if (collectionManager.getTreeMap().isEmpty()) throw new EmptyCollectionException();
        if (!hasStringArg() || !hasWorkerArg()) throw new MissedCommandArgumentException();
        Long id = Long.parseLong(getStringArg());
        if (!collectionManager.checkID((long)id)) throw new InvalidCommandArgumentException("no such id #" + getStringArg());

        String owner = collectionManager.getByID(id).getUserLogin();
        String workerCreatorLogin = user.getLogin();

        if (workerCreatorLogin == null || !workerCreatorLogin.equals(owner))
            throw new AuthException("you dont have permission, element was created by " + owner);
        collectionManager.updateById(id, getPersonArg());
        return "element #" + id + " updated";
    }
}
