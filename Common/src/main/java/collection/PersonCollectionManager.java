package collection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import data.Person;
import exceptions.NoSuchIdException;
import json.LocalDateTimeDeserializer;
import json.LocalDateTimeSerializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.TreeSet;

public class PersonCollectionManager implements CollectionManager<Person>{
    private TreeSet<Person> treeMap;
    private final LocalDateTime initDate;
    private final HashSet<Long> uniqueIds;
    private HashSet<String> uniquePassportID;
    /**
     * Constructor, set start values
     */
    public PersonCollectionManager() {
        treeMap = new TreeSet<>();
        initDate = LocalDateTime.now();
        uniqueIds = new HashSet<>();
        uniquePassportID = new HashSet<>();
    }
    public long generateID(){
        if(treeMap.isEmpty())return 1;
        else {
            Long id = (long)treeMap.size()+ 1;
            if(uniqueIds.contains(id)){
                while (uniqueIds.contains(id))id+=1;
            }
            uniqueIds.add(id);
            return id;
        }
    }
    /**
     * Add element to collection
     */
    public TreeSet<Person> getTreeMap(){
        return treeMap;
    }
    public void add(Person person){
        person.setId(generateID());
        uniquePassportID.add(person.getPassportID());
        treeMap.add(person);
        System.out.println("added element");
        System.out.println(person.toString());
    }
    /**
     * Get information about collection
     * @return Information
     */
    public String getInfo(){
        return "Collection size: " + Integer.toString(treeMap.size()) + ", initialization date and time: " + initDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    /**
     * Give info about is this ID used
     * @param ID ID
     * @return is it used or not
     */
    public boolean checkID(Long ID){
        for (Person person: treeMap)
        {
            if (person.getId() == ID) return true;
        }
        return false;
    }
    /**
     * Delete element by ID
     * @param id ID
     */
    public boolean removeByID(Long id){
        for(Person person : treeMap){
            if (person.getId() == id){
                treeMap.remove(person);
                uniqueIds.remove(id);
                System.out.println("element #"+Long.toString(id)+" successfully deleted");
                return true;
            }
        }
        return false;
    }
    public Person getByID(Long id){
        Optional<Person> worker = treeMap.stream()
                .filter(w -> w.getId() == id)
                .findFirst();
        if(!worker.isPresent()){
            throw new NoSuchIdException(1);
        }
        return worker.get();
    }
    public void removeID(Long id){
        Optional<Person> person = treeMap.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
        if(!person.isPresent()){
            throw new NoSuchIdException(2);
        }
        treeMap.remove(person.get());
        uniqueIds.remove(id);
    }

    /**
     * Delete element by ID
     * @param id ID
     */
    public void updateID(Long id, Person newPerson){
        long ID = 1;
        for (Person person1 : treeMap){
            if (newPerson.getId() == id){
                newPerson.setId(id);
                person1.setId(ID);
            }
            ID += 1;
        }
    }
    public boolean updateIdBoolean(Long id, Person newPerson){
        long ID = 1;
        for (Person person1 : treeMap){
            if (newPerson.getId() == id){
                newPerson.setId(id);
                person1.setId(ID);
                return true;
            }
            ID += 1;
        }
        return false;
    }
    public void updateById(Long id, Person newPerson){
        long ID = 1;
        for (Person person1 : treeMap){
            if (newPerson.getId() == id){
                newPerson.setId(id);
                person1.setId(ID);
            }
            ID += 1;
        }
    }
    public int getSize(){
        return treeMap.size();
    }
    public void clear(){
        treeMap.clear();
        uniqueIds.clear();
    }
    public String printContainsName(String string){
        LinkedList<Person> list = new LinkedList<>();
        for (Person person : treeMap){
            if (person.getName().contains(string)){
                list.add(person);
            }
        }
        if (list.isEmpty()) return ("none of elements have name" + string);
        else{
            return ("contains name: " + string);
        }
    }
    public boolean deserializeCollection(String json){
        boolean success = true;
        if (json == null || json.equals("")){
            treeMap =  new TreeSet<Person>();
        }
        else {
            Type collectionType = new TypeToken<TreeSet<Person>>(){}.getType();
            Gson builder = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).create();
            treeMap = builder.fromJson(json.trim(),collectionType);
            return success;
        }
        success = false;
        System.err.println("wrong json data");
        return success;
    }
    public void deserializeVoidCollection(String json){
        boolean success = true;
        if (json == null || json.equals("")){
            treeMap =  new TreeSet<Person>();
        }
        else {
            Type collectionType = new TypeToken<TreeSet<Person>>(){}.getType();
            Gson builder = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).create();
            treeMap = builder.fromJson(json.trim(),collectionType);
        }
    }
    public String serializeCollection(){
        if (treeMap == null || treeMap.isEmpty()) return "";
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .create();
        String json = gson.toJson(treeMap);
        return json;
    }
    public void removeElement(Person person){
        long id = person.getId();
        uniqueIds.remove(person.getId());
        treeMap.remove(person);
        System.out.println("element #"+Long.toString(id)+" successfully deleted");
    }
    public boolean addIfMax(Person person){
        if (treeMap.stream()
                .max(Person::compareTo)
                .filter(w->w.compareTo(person)==1)
                .isPresent())
        {
            return false;
        }
        add(person);
        return true;
    }
    public void addIfMaxVoid(Person person){
        if (treeMap.stream()
                .max(Person::compareTo)
                .filter(w->w.compareTo(person)==1)
                .isPresent())
        {
        }
        add(person);
    }
    protected void addWithoutIdGeneration(Person worker){
        uniqueIds.add(worker.getId());
        treeMap.add(worker);
    }

}
