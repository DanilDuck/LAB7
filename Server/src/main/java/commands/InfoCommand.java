package commands;

import collection.CollectionManager;
import data.Person;
import exceptions.InvalidDataException;

public class InfoCommand extends CommandImpl{
    private CollectionManager<Person> collectionManager;
    public InfoCommand(CollectionManager<Person> cm){
        super("info",CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() throws InvalidDataException {
        return collectionManager.getInfo();
    }

}
