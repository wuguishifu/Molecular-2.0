package com.bramerlabs.molecular.engine3D.math.key;

import java.util.Objects;

public class Key2 {

    /**
     * the values of this key
     */
    public int x, y;

    /**
     * default constructor
     * @param x - the x value
     * @param y - the y value
     */
    public Key2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * constructor from int array
     * @param i - the int array
     */
    public Key2(int[] i) {
        this.x = i.length > 0 ? i[0] : 0;
        this.x = i.length > 1 ? i[1] : 0;
    }

    /**
     * sets the x value
     * @param x - the x value
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * sets the y value
     * @param y - the y value
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * getter method
     * @return - the x value
     */
    public int getX() {
        return this.x;
    }

    /**
     * getter method
     * @return - the y value
     */
    public int getY() {
        return this.y;
    }

    /**
     * converts this to a int array
     * @return - the int array
     */
    public int[] asIntArray() {
        return new int[]{x, y};
    }

    /**
     * determines if this key is equal to another object
     * @param o - the other object
     * @return - true if the other object is a key with the same x, y value of these objects
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key2)) return false;
        Key2 key2 = (Key2) o;
        return key2.x == x &&
                key2.y == y;
    }

    /**
     * generates a hash code of this key
     * @return - the key's hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * converts this key to a string
     * @return - the string representation of this key in the form 'k(x, y)'
     */
    @Override
    public String toString() {
        return "k(" + x + ", " + y + ")";
    }
}
