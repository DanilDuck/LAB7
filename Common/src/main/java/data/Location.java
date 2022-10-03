package data;

import java.io.Serializable;

public class Location  implements Serializable{
    private Integer x; //Поле не может быть null
    private double y;
    private int z;
    public Location(int x, double y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    /**
     * @return z coord Location
     */
    public int getZ(){
        return z;
    }
    /**
     * @return y coord Location
     */
    public double getY(){
        return y;
    }
    /**
     * @return x coord Location
     */
    public int getX(){
        return x;
    }

}
