package commands;

import collection.CollectionManager;
import data.Person;
import exceptions.EmptyCollectionException;
import exceptions.MissedCommandArgumentException;

import java.util.List;

public class FilterContainsNameCommand extends CommandImpl{
    private CollectionManager<Person> collectionManager;
    public FilterContainsNameCommand(CollectionManager<Person> cm){
        super("filter_contains_name",CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute(){
        String s = getStringArg();
        if (s == null || s.equals("")){
            return "none of elements have name which starts with ";
        } else{
            if (collectionManager.getTreeMap().isEmpty()) throw new EmptyCollectionException();
            return (collectionManager.printContainsName(s));
        }
    }
}
