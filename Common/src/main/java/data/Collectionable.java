package data;

public interface Collectionable extends Comparable<Collectionable>{
    public long getId();
    /**
     * sets id, useful for replacing object in collection
     * @param id
     */
    public void setId(long id);


    public String getName();

    /**
     * compairs two objects
     */
    public int compareTo(Collectionable person);

    float getHeight();
}