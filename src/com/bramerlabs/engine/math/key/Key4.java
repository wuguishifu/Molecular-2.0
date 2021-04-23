package com.bramerlabs.engine.math.key;

import java.util.Objects;

public class Key4 {

    /**
     * the values of this key
     */
    public int x, y, z, w;

    /**
     * default constructor
     * @param x - the x value of this key
     * @param y - the y value of this key
     * @param z - the z value of this key
     * @param w - the w value of this key
     */
    public Key4(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * constructor from int array
     * @param i - the int array
     */
    public Key4(int[] i) {
        this.x = i.length > 0 ? i[0] : 0;
        this.y = i.length > 1 ? i[1] : 0;
        this.z = i.length > 2 ? i[2] : 0;
        this.w = i.length > 3 ? i[3] : 0;
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
     * getter method
     * @return - the w value of this key
     */
    public int getW() {
        return this.w;
    }

    /**
     * sets the w value of this key
     * @param w - the new w value
     */
    public void setW(int w) {
        this.w = w;
    }

    /**
     * determines if this key is equal to another object
     * @param o - the other object
     * @return - true if the other object is a key with the same x, y value of these objects
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key4)) return false;
        Key4 key4 = (Key4) o;
        return x == key4.x &&
                y == key4.y &&
                z == key4.z &&
                w == key4.w;
    }

    /**
     * generates a hash code of this key
     * @return - the key's hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }

    /**
     * converts this key to a string
     * @return - the string representation of this key in the form 'k(x, y, z, w)'
     */
    @Override
    public String toString() {
        return "k(" + x + ", " + y + ", " + z + ", " + w + ")";
    }
}
