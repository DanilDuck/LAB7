package commands;

import collection.CollectionManager;
import data.Person;
import exceptions.EmptyCollectionException;

import java.time.LocalDate;
import java.util.*;

public class PrintAscendingCommand extends CommandImpl {
    private CollectionManager<Person> collectionManager;

    public PrintAscendingCommand(CollectionManager<Person> cm) {
        super("print_ascending", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() {
        if (collectionManager.getTreeMap().isEmpty()) return ("collection is empty");
        else {
            StringBuilder s = new StringBuilder(" ");
            TreeSet<Person> someTree = collectionManager.getTreeMap();
            PriorityQueue<Person> sortedSet = new PriorityQueue<>(new Comparator<Person>() {
                @Override
                public int compare(Person o1, Person o2) {
                    return (int) (o2.getHeight() - o1.getHeight());
                }
            });
            sortedSet.addAll(someTree);
            for (Person person : sortedSet) {
                s.append(person.toString());
            }
            return s.toString();
        }
    }
}