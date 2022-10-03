package commands;

import collection.CollectionManager;
import data.Person;
import exceptions.EmptyCollectionException;
import exceptions.InvalidDataException;

public class RemoveGreaterCommand extends CommandImpl{
    private CollectionManager<Person> collectionManager;
    public RemoveGreaterCommand(CollectionManager<Person> cm){
        super("remove_greater",CommandType.NORMAL);
        collectionManager = cm;
    }


    @Override
    public String execute()  {
        if(collectionManager.getTreeMap().isEmpty()) throw new EmptyCollectionException();
        long id = Long.parseLong(getStringArg());
        for (Person person: collectionManager.getTreeMap()) {
            if(person.getId()>id){
                collectionManager.removeElement(person);
            }
        }
        return "elements removed";
    }
}
