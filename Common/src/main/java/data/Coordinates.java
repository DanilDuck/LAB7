package data;

import java.io.Serializable;

public class Coordinates implements Serializable {
    public Coordinates(int x, float y){
        this.x = x;
        this.y = y;
    }
    private int x;
    private Float y; //Поле не может быть null
    @Override
    public String toString(){
        String s = "";
        s += "{\"x\" : " + Integer.toString(x) + ", ";
        s += "\"y\" : " + Float.toString(y) + "}";
        return s;
    }
    /**
     * @return x coord
     */
    public int getX() {
        return x;
    }
    /**
     * @return y coord
     */
    public Float getY() {
        return y;
    }
}

