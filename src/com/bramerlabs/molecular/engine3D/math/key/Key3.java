package com.bramerlabs.molecular.engine3D.math.key;

import java.util.Objects;

public class Key3 {

    /**
     * the values of this key
     */
    public int x, y, z;

    /**
     * default constructor
     * @param x - the x value
     * @param y - the y value
     * @param z - the z value
     */
    public Key3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * constructor from int array
     * @param i - the int array
     */
    public Key3(int[] i) {
        this.x = i.length > 0 ? i[0] : 0;
        this.y = i.length > 1 ? i[1] : 0;
        this.z = i.length > 2 ? i[2] : 0;
    }

    /**
     * getter method
     * @return - the x value of this key
     */
    public int getX() {
        return x;
    }

    /**
     * sets the x value of this key
     * @param x - the new x value
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * getter method
     * @return - the y value of this key
     */
    public int getY() {
        return y;
    }

    /**
     * sets the y value of this key
     * @param y - the new y value
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * getter method
     * @return - the z value of this key
     */
    public int getZ() {
        return z;
    }

    /**
     * sets the z value of this key
     * @param z - the new z value of this key
     */
    public void setZ(int z) {
        this.z = z;
    }

    /**
     * determines if this key is equal to another object
     * @param o - the other object
     * @return - true if the other object is a key with the same x, y value of these objects
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key3)) return false;
        Key3 key3 = (Key3) o;
        return x == key3.x &&
                y == key3.y &&
                z == key3.z;
    }

    /**
     * generates a hash code of this key
     * @return - the key's hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    /**
     * converts this key to a string
     * @return - the string representation of this key in the form 'k(x, y, z)'
     */
    @Override
    public String toString() {
        return "k(" + x + ", " + y + ", " + z + ")";
    }
}

