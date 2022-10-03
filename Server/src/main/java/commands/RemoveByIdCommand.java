package commands;

import auth.User;
import collection.CollectionManager;
import data.Person;
import exceptions.*;

public class RemoveByIdCommand extends CommandImpl{
    private CollectionManager<Person> collectionManager;
    public RemoveByIdCommand(CollectionManager<Person> cm){
        super("remove_by_id",CommandType.NORMAL);
        collectionManager = cm;
    }


    @Override
    public String execute() throws InvalidDataException, AuthException {
        User user = getArgument().getUser();
        if (collectionManager.getTreeMap().isEmpty()) throw new EmptyCollectionException();
        if (!hasStringArg()) throw new MissedCommandArgumentException();
        Long id = Long.parseLong(getStringArg());
        if (!collectionManager.checkID(id))
            throw new InvalidCommandArgumentException("no such id #" + id);
        String owner = collectionManager.getByID(id).getUserLogin();
        String workerCreatorLogin = user.getLogin();

        if (workerCreatorLogin == null || !workerCreatorLogin.equals(owner))
            throw new AuthException("you dont have permission, element was created by " + owner);
        collectionManager.removeID(id);
        return "element #" + id + " removed";
    }

}
