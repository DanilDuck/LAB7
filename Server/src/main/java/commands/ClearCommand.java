package commands;

import collection.CollectionManager;
import data.Person;
import exceptions.EmptyCollectionException;
import exceptions.InvalidDataException;

public class ClearCommand extends CommandImpl{
    private CollectionManager<Person> collectionManager;
    public ClearCommand(CollectionManager<Person> cm){
        super("clear",CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() throws InvalidDataException {
        if(collectionManager.getTreeMap().isEmpty()) throw new EmptyCollectionException();
        collectionManager.clear();
        return "collection cleared";
    }

}
