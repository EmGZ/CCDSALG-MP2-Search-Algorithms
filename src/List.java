/**
    MCO2 Searching S21 Group 4
    Cabungcal, Mary Joselle
    Ladrido, Eryl Gabriel
    Rejano, Hans Martin
    Uy, Gleezell Vina
 */
/**
 * This is used for the chaining collision
 * @author - Gleezell
 */

import java.util.ArrayList;

public class List {
    private ArrayList<Integer> intArray;
    private ArrayList<Long> longArray;

    public List () {
        intArray = new ArrayList<Integer>();
        longArray = new ArrayList<Long>();
    }

    public void addInt (int num) {
        this.intArray.add(num);
    }

    public void addLong (long num) {
        this.longArray.add(num);
    }

    public int getIntElement (int index) {
        return this.intArray.get(index);
    }

    public long getLongElement (int index) {
        return this.longArray.get(index);
    }

    public ArrayList<Integer> getIntArray () {
        return this.intArray;
    }

    public ArrayList<Long> getLongArray () {
        return this.longArray;
    }

    public int getIntSize () {
        return this.intArray.size();
    }

    public int getLongSize () {
        return this.longArray.size();
    }

}
