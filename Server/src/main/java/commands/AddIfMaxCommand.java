package commands;

import collection.CollectionManager;
import data.Person;
import exceptions.CommandException;

public class AddIfMaxCommand extends CommandImpl{
    private CollectionManager<Person> collectionManager;
    public AddIfMaxCommand(CollectionManager<Person> cm){
        super("add_if_max",CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute(){
        boolean success = collectionManager.addIfMax(getPersonArg());
        if (success) return ("Added element: " + getPersonArg().toString());
        else throw new CommandException("cannot add");
    }

}
