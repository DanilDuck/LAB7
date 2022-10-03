package commands;

import collection.CollectionManager;
import data.Person;
import exceptions.EmptyCollectionException;

import java.util.LinkedList;

public class HistoryCommand extends CommandImpl {
    private LinkedList<String> his;
    private CollectionManager<Person> collectionManager;
    public HistoryCommand(CollectionManager<Person> cm, LinkedList<String> history){
        super("history",CommandType.NORMAL);
        collectionManager = cm;
        his = history;
    }
    @Override
    public String execute(){
        StringBuilder stringBuilder = new StringBuilder(new String(""));
        for (String string: his){
            stringBuilder.append(string).append("\n");
        }
        return  stringBuilder.toString();
    }
}
