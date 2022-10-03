package commands;

import collection.CollectionManager;
import data.Person;
import exceptions.EmptyCollectionException;

import java.util.List;

public class PrintFieldAscendingEyeColor extends CommandImpl{
    private CollectionManager<Person> collectionManager;
    public PrintFieldAscendingEyeColor(CollectionManager<Person> cm){
        super("print_field_ascending_eye_color",CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute(){
        if (collectionManager.getTreeMap().isEmpty()) throw new EmptyCollectionException();
        StringBuilder s = new StringBuilder();
        for (Person p: collectionManager.getTreeMap()) {
            s.append("EyeColor: "+ p.getEyeColor()+" Id:"+ p.getId()+"\n");
        }
        return s.toString();
    }
}
