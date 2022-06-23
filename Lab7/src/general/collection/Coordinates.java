package general.collection;

import java.io.Serializable;

/**
 * Coordinates - the class that includes coordinates of the lab.
 */
public class Coordinates implements Serializable {
    private int x; //Максимальное значение поля: 802
    private float y;

    /**
     * Constructor of the class.
     * @param x coordinate.
     */
    public Coordinates(int x){
        this.x = x;
    }

    /**
     * Constructor of the class.
     * @param x coordinate.
     * @param y coordinate.
     */
    public Coordinates(int x, float y){
        this.x = x;
        this.y = y;
    }


    /**
     * @return X coordinate of the lab.
     */
    public int getX(){
        return x;
    }

    /**
     * @return Y coordinate of the lab.
     */
    public float getY(){
        return y;
    }

    /**
     * @param x X coordinate of the lab.
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * @param y Y coordinate of the lab.
     */
    public void setY(float y){
        this.y = y;
    }

    /**
     * @return Format output info of the coordinates.
     */
    public String toString() {
        String info = "";
        info += "\n X: " + x;
        info += "\n Y: " + y;
        return info;
    }

    /**
     * @param obj The object to equal to.
     * @return The equals return true if objects are equal to each other.
     */
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Coordinates) {
            Coordinates coordinatesObj = (Coordinates) obj;
            return (x == coordinatesObj.getX() && y == coordinatesObj.getY());
        }
        return false;
    }
}
