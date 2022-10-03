package commands;

import collection.CollectionManager;
import data.Person;
import exceptions.EmptyCollectionException;

import java.util.Iterator;
import java.util.TreeSet;

public class ShowCommand extends CommandImpl{
    private final CollectionManager<Person> collectionManager;
    String s = " ";
    public ShowCommand(CollectionManager<Person> cm){
        super("show",CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute(){
        if (collectionManager.getTreeMap().isEmpty()) throw new EmptyCollectionException();
        TreeSet<Person> treeSet= collectionManager.getTreeMap();
        Iterator<Person> iterator= treeSet.iterator();
        StringBuilder stringBuilder =new StringBuilder();
        while (iterator.hasNext()){
            stringBuilder.append(iterator.next());
        }
        return stringBuilder.toString();
    }

}

