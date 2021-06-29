package com.bramerlabs.engine.math.vector;

import java.text.DecimalFormat;
import java.util.Objects;

public class Vector4f {

    /**
     * the x, y, z, and w component of this vector
     */
    public float x, y, z, w;

    /**
     * unit vectors in the e1, e2, e3, and e4 directions
     */
    public static final Vector4f e1 = new Vector4f(1, 0, 0, 0);
    public static final Vector4f e2 = new Vector4f(0, 1, 0, 0);
    public static final Vector4f e3 = new Vector4f(0, 0, 1, 0);
    public static final Vector4f e4 = new Vector4f(0, 0, 0, 1);

    /**
     * default constructor
     * @param x - the x component
     * @param y - the y component
     * @param z - the z component
     * @param w - the w component
     */
    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * constructor for 1 specified value
     * @param val - the value for all four components to be set to
     */
    public Vector4f(float val) {
        this.x = val;
        this.y = val;
        this.z = val;
        this.w = val;
    }

    /**
     * constructor for duplicating a vector
     * @param v - the vector to duplicate
     */
    public Vector4f(Vector4f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }

    /**
     * constructor for a vector from a 3-float vector and a float w component
     * @param v - the 3-float vector
     * @param w - the w component
     */
    public Vector4f(Vector3f v, float w) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
    }

    /**
     * constructor from a float array
     * @param v - a float array containing values in form [x, y, z, w]
     * method will automatically set the position based off of available variables.
     * No minimum or maximum necessary supplied variables, if more than 4 are supplied the vector
     * will be constructed out of the first 4.
     */
    public Vector4f(float[] v) {
        x = v.length > 0 ? v[0] : 0;
        y = v.length > 1 ? v[1] : 0;
        z = v.length > 2 ? v[2] : 0;
        w = v.length > 3 ? v[3] : 0;
    }

    /**
     * sets each component of this vector
     * @param x - the x component
     * @param y - the y component
     * @param z - the z component
     * @param w - the w component
     */
    public void set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * sets the x component of this vector
     * @param x - the new x value
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * sets the y component of this vector
     * @param y - the new y value
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * sets the z component of this vector
     * @param z - the new z value
     */
    public void setZ(float z) {
        this.z = z;
    }

    /**
     * sets the w component of this vector
     * @param w - the new w value
     */
    public void setW(float w) {
        this.w = w;
    }

    /**
     * adds values to each component to this vector
     * @param dx - the change in x component
     * @param dy - the change in y component
     * @param dz - the change in z component
     * @param dw - the change in w component
     * @return - this vector
     */
    public Vector4f add(float dx, float dy, float dz, float dw) {
        this.x += dx;
        this.y += dy;
        this.z += dz;
        this.w += dw;
        return this;
    }

    /**
     * adds a vector to this vector
     * @param v - the vector to be added to this vector
     * @return - this vector
     */
    public Vector4f add(Vector4f v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        this.w += v.w;
        return this;
    }

    /**
     * adds values to each component of a vector
     * @param v - the vector to add to
     * @param dx - the change in x component
     * @param dy - the change in y component
     * @param dz - the change in z component
     * @param dw - the change in w component
     * @return - a new vector, v + (dx, dy, dz)
     */
    public static Vector4f add(Vector4f v, float dx, float dy, float dz, float dw) {
        return new Vector4f(v.x + dx, v.y + dy, v.z + dz, v.w + dw);
    }

    /**
     * adds two vectors together
     * @param v - vector 1
     * @param u - vector 2
     * @return - a new vector, the sum of v and u
     */
    public static Vector4f add(Vector4f v, Vector4f u) {
        return new Vector4f(v.x + u.x, v.y + u.y, v.z + u.z, v.w + u.w);
    }

    /**
     * subtracts values to each component to this vector
     * @param dx - the change in x component
     * @param dy - the change in y component
     * @param dz - the change in z component
     * @param dw - the change in w component
     * @return - this vector
     */
    public Vector4f subtract(float dx, float dy, float dz, float dw) {
        this.x -= dx;
        this.y -= dy;
        this.z -= dz;
        this.w -= dw;
        return this;
    }

    /**
     * subtracts a vector to this vector
     * @param v - the vector to be added to this vector
     * @return - this vector
     */
    public Vector4f subtract(Vector4f v) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
        this.w -= v.w;
        return this;
    }

    /**
     * subtracts values to each component of a vector
     * @param v - the vector to add to
     * @param dx - the change in x component
     * @param dy - the change in y component
     * @param dz - the change in z component
     * @param dw - the change in w component
     * @return - a new vector, v + (dx, dy, dz)
     */
    public static Vector4f subtract(Vector4f v, float dx, float dy, float dz, float dw) {
        return new Vector4f(v.x - dx, v.y - dy, v.z - dz, v.w - dw);
    }

    /**
     * subtracts two vectors together
     * @param v - vector 1
     * @param u - vector 2
     * @return - a new vector, the sum of v and u
     */
    public static Vector4f subtract(Vector4f v, Vector4f u) {
        return new Vector4f(v.x - u.x, v.y - u.y, v.z - u.z, v.w - u.w);
    }

    /**
     * multiplies the components of this vector by values
     * @param mx - the multiplication factor of the x component
     * @param my - the multiplication factor of the y component
     * @param mz - the multiplication factor of the z component
     * @param mw - the multiplication factor of the w component
     * @return - this vector
     */
    public Vector4f multiply(float mx, float my, float mz, float mw) {
        this.x *= mx;
        this.y *= my;
        this.z *= mz;
        this.w *= mw;
        return this;
    }

    /**
     * multiplies the components of this vector by the components of another vector
     * @param v - the other vector
     * @return - this vector
     */
    public Vector4f multiply(Vector4f v) {
        this.x *= v.x;
        this.y *= v.y;
        this.z *= v.z;
        this.w *= v.w;
        return this;
    }

    /**
     * multiplies the components of a vector by values
     * @param v - the vector
     * @param mx - the multiplication factor of the x component
     * @param my - the multiplication factor of the y component
     * @param mz - the multiplication factor of the z component
     * @param mw - the multiplication factor of the w component
     * @return - a new vector
     */
    public static Vector4f multiply(Vector4f v, float mx, float my, float mz, float mw) {
        return new Vector4f(v.x * mx, v.y * my, v.z * mz, v.w * mw);
    }

    /**
     * multiplies the components of one vector by the components of another vector
     * @param v - the first vector
     * @param u - the second vector
     * @return - a vector where the values are the straight multiplication of v and u
     */
    public static Vector4f multiply(Vector4f v, Vector4f u) {
        return new Vector4f(v.x * u.x, v.y * u.y, v.z * u.z, v.w * u.w);
    }

    /**
     * divides the components of this vector by values
     * @param mx - the division factor of the x component
     * @param my - the division factor of the y component
     * @param mz - the division factor of the z component
     * @param mw - the division factor of the w component
     * @return - this vector
     */
    public Vector4f divide(float mx, float my, float mz, float mw) {
        this.x /= mx;
        this.y /= my;
        this.z /= mz;
        this.w /= mw;
        return this;
    }

    /**
     * divides the components of this vector by the components of another vector
     * @param v - the other vector
     * @return - this vector
     */
    public Vector4f divide(Vector4f v) {
        this.x /= v.x;
        this.y /= v.y;
        this.z /= v.z;
        this.w /= v.w;
        return this;
    }

    /**
     * divides the components of a vector by values
     * @param v - the vector
     * @param mx - the division factor of the x component
     * @param my - the division factor of the y component
     * @param mz - the division factor of the z component
     * @param mw - the division factor of the w component
     * @return - a new vector
     */
    public static Vector4f divide(Vector4f v, float mx, float my, float mz, float mw) {
        return new Vector4f(v.x / mx, v.y / my, v.z / mz, v.w / mw);
    }

    /**
     * divides the components of one vector by the components of another vector
     * @param v - the first vector
     * @param u - the second vector
     * @return - a vector where the values are the straight multiplication of v and u
     */
    public static Vector4f divide(Vector4f v, Vector4f u) {
        return new Vector4f(v.x / u.x, v.y / u.y, v.z / u.z, v.w / u.w);
    }

    /**
     * dots this vector with another vector
     * @param v - the other vector
     * @return - the dot product of this vector and the other vector
     */
    public float dot(Vector4f v) {
        return this.x * v.x + this.y * v.y + this.z * v.z + this.w * v.w;
    }

    /**
     * dots two vectors together
     * @param v - the first vector
     * @param u - the second vector
     * @return - the dot product u dot v
     */
    public static float dot(Vector4f v, Vector4f u) {
        return v.x * u.x + v.y * u.y + v.z * u.z + v.w * u.w;
    }

    /**
     * computes the length of this vector
     * @return - the length
     */
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    /**
     * computes the length of a vector
     * @param v - the vector
     * @return - the length of vector v
     */
    public static float length(Vector4f v) {
        return (float) Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z + v.w * v.w);
    }

    /**
     * normalizes this vector
     * @return - this vector
     */
    public Vector4f normalize() {
        this.x /= this.length();
        this.y /= this.length();
        this.z /= this.length();
        this.w /= this.length();
        return this;
    }

    /**
     * normalizes this vector to a specific length
     * @param l - the new length of this vector
     * @return - this vector
     */
    public Vector4f normalize(float l) {
        this.x *= l / this.length();
        this.y *= l / this.length();
        this.z *= l / this.length();
        this.w *= l / this.length();
        return this;
    }

    /**
     * normalizes a vector
     * @param v - the vector
     * @return - a new vector with length 1 in the same direction of v
     */
    public static Vector4f normalize(Vector4f v) {
        return new Vector4f(
                v.x /= v.length(),
                v.y /= v.length(),
                v.z /= v.length(),
                v.w /= v.length()
        );
    }

    /**
     * normalizes a vector to a specific length
     * @param v - the vector
     * @param l - the new length
     * @return - a new vector with length 1 in the same direction of v
     */
    public static Vector4f normalize(Vector4f v, float l) {
        return new Vector4f(
                v.x *= l / v.length(),
                v.y *= l / v.length(),
                v.z *= l / v.length(),
                v.w *= l / v.length()
        );
    }

    /**
     * getter method
     * @return - the x value of this vector
     */
    public float getX() {
        return x;
    }

    /**
     * getter method
     * @return - the y value of this vector
     */
    public float getY() {
        return y;
    }

    /**
     * getter method
     * @return - the z value of this vector
     */
    public float getZ() {
        return z;
    }

    /**
     * getter method
     * @return - the w value of this vector
     */
    public float getW() {
        return w;
    }

    /**
     * getter method
     * @return - the i value of this vector
     */
    public float getI() {
        return x;
    }

    /**
     * getter method
     * @return - the j value of this vector
     */
    public float getJ() {
        return y;
    }

    /**
     * getter method
     * @return - the k value of this vector
     */
    public float getK() {
        return z;
    }

    /**
     * getter method
     * @return - the l value of this vector
     */
    public float getL() {
        return w;
    }

    /**
     * getter method
     * @return - the first 3 values in a 3-float vector
     */
    public Vector3f xyz() {
        return new Vector3f(x, y, z);
    }

    /**
     * converts this 4-float vector to a float array
     * @return - the float array
     */
    public float[] toFloatArray() {
        return new float[]{x, y, z, w};
    }

    /**
     * determines if two vectors are exactly identical
     * @param o - the other object
     * @return - true if this and o are both vectors that are exactly equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector4f)) return false;
        Vector4f other = (Vector4f) o;
        return this.x == other.x && this.y == other.y && this.z == other.z && this.w == other.w;
    }

    /**
     * determines if two vectors are nearly identical
     * @param o - the other object
     * @param epsilon - the max error
     * @return - true if this and o are both vectors and the absolute error for each component is less than epsilon
     */
    public boolean equals(Object o, float epsilon) {
        if (this == o) return true;
        if (!(o instanceof Vector4f)) return false;
        Vector4f other = (Vector4f) o;
        if (x - other.x > epsilon) {
            return false;
        } else if (y - other.y > epsilon) {
            return false;
        } else if (z - other.z > epsilon) {
            return false;
        } else return !(w - other.w > epsilon);
    }

    /**
     * creates a hashcode of this vector
     * @return - the hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }

    /**
     * converts this vector to a string
     * @return - the string value of this vector
     */
    @Override
    public String toString() {
        DecimalFormat df2 = new DecimalFormat("#,###,###,#00.00");
        String xS = df2.format(this.x);
        String yS = df2.format(this.y);
        String zS = df2.format(this.z);
        String wS = df2.format(this.w);
        return "(" + xS + ", " + yS + ", " + zS + ", " + wS + ")";
    }
}