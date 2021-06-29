package com.bramerlabs.engine.math.vector;

import java.text.DecimalFormat;
import java.util.Objects;

public class Vector2f {

    /**
     * the x and y components of this vector
     */
    public float x, y;

    public static final Vector2f one = new Vector2f(1, 1);
    public static final Vector2f zero = new Vector2f(0, 0);

    /**
     * unit vectors in the e1 and e2 direction
     */
    public static final Vector2f e1 = new Vector2f(1, 0);
    public static final Vector2f e2 = new Vector2f(0, 1);

    /**
     * default constructor
     * @param x - the x component
     * @param y - the y component
     */
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * constructor for 1 specified value
     * @param val - the value for both components to be set to
     */
    public Vector2f(float val) {
        this.x = val;
        this.y = val;
    }

    /**
     * constructor for duplicating a vector
     * @param v - the vector to duplicate
     */
    public Vector2f(Vector2f v) {
        this.x = v.x;
        this.y = v.y;
    }

    /**
     * constructs a 2-float vector from a float array
     * @param v - the float array
     */
    public Vector2f(float[] v) {
        x = v.length > 0 ? v[0] : 0;
        y = v.length > 1 ? v[1] : 0;
    }

    /**
     * generates a random vector with all components between 0 and a max value
     * @param max - the max value
     * @return - the new vector
     */
    public static Vector2f random(float max) {
        return new Vector2f((float) Math.random() * max, (float) Math.random() * max);
    }

    /**
     * generates a random vector with all components between 0 and 1
     * @return - the new vector
     */
    public static Vector2f random() {
        return new Vector2f((float) Math.random(), (float) Math.random());
    }

    /**
     * generates a random vector with all components between 0 and a max value
     * @param maxX - the max x value
     * @param maxY - the max y value
     * @return - the new vector
     */
    public static Vector2f random(float maxX, float maxY) {
        return new Vector2f((float) Math.random() * maxX, (float) Math.random() * maxY);
    }

    /**
     * sets the components of this vector
     * @param x - the new x component
     * @param y - the new y component
     */
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * sets the x component of this vector
     * @param x - the new x component
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * sets the y component of this vector
     * @param y - the new y component
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * adds components to this vector
     * @param dx - the change in x component
     * @param dy - the change in y component
     * @return - this vector
     */
    public Vector2f add(float dx, float dy) {
        this.x += dx;
        this.y += dy;
        return this;
    }

    /**
     * adds a vector to this vector
     * @param v - the vector to add to this vector
     * @return - this vector
     */
    public Vector2f add(Vector2f v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    /**
     * adds to the components of a vector
     * @param v - the vector to add to
     * @param dx - the change in x component
     * @param dy - the change in y component
     * @return - a new vector, v + (dx, dy)
     */
    public static Vector2f add(Vector2f v, float dx, float dy) {
        return new Vector2f(v.x + dx, v.y + dy);
    }

    /**
     * adds two vectors together
     * @param v - vector 1
     * @param u - vector 2
     * @return - a new vector, the sum of v and u
     */
    public static Vector2f add(Vector2f v, Vector2f u) {
        return new Vector2f(v.x + u.x, v.y + u.y);
    }

    /**
     * scales the entire vector by a certain factor
     * @param scaleFactor - the factor to scale each component by
     * @return - this vector
     */
    public Vector2f scale(float scaleFactor) {
        this.x *= scaleFactor;
        this.y *= scaleFactor;
        return this;
    }

    /**
     * scales an entire vector by a certain factor
     * @param v - the vector to scale
     * @param scaleFactor - the factor to scale each component by
     * @return - a new scaled vector
     */
    public static Vector2f scale(Vector2f v, float scaleFactor) {
        return new Vector2f(v.x * scaleFactor, v.y * scaleFactor);
    }

    /**
     * subtracts components from this vector
     * @param dx - the amount to subtract from x
     * @param dy - the amount to subtract from y
     * @return - this vector
     */
    public Vector2f subtract(float dx, float dy) {
        this.x -= dx;
        this.y -= dy;
        return this;
    }

    /**
     * subtracts components of this vector by components of another vector
     * @param v - the other vector
     * @return - this vector
     */
    public Vector2f subtract(Vector2f v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    /**
     * subtracts components from a vector
     * @param v - the vector
     * @param dx - the change in x position
     * @param dy - the change in y position
     * @return - a new vector, the difference v - (dx, dy)
     */
    public static Vector2f subtract(Vector2f v, float dx, float dy) {
        return new Vector2f(v.x - dx, v.y - dy);
    }

    /**
     * subtracts two vectors
     * @param v - vector 1
     * @param u - vector 2
     * @return - a new vector, the difference v - u
     */
    public static Vector2f subtract(Vector2f v, Vector2f u) {
        return new Vector2f(v.x - u.x, v.y - u.y);
    }

    /**
     * multiplies the components of this vector by certain values
     * @param mx - the multiplication factor of the x component
     * @param my - the multiplication factor of the y component
     * @return - this vector
     */
    public Vector2f multiply(float mx, float my) {
        this.x *= mx;
        this.y *= my;
        return this;
    }

    /**
     * multiplies the components of this vector by the components of another vector
     * @param v - the other vector
     * @return - this vector
     */
    public Vector2f multiply(Vector2f v) {
        this.x *= v.x;
        this.y *= v.y;
        return this;
    }

    /**
     * multiplies the components of a vector by certain values
     * @param v - vector to multiply components by
     * @param mx - the multiplication factor of the x component
     * @param my - the multiplication factor of the y component
     * @return - a new vector, (v.x * mx, v.y * my)
     */
    public static Vector2f multiply(Vector2f v, float mx, float my) {
        return new Vector2f(v.x * mx, v.y * my);
    }

    /**
     * straight multiplies values of two vectors
     * @param v - vector 1
     * @param u - vector 2
     * @return - a new vector, where the x and y components are the straight multiplications
     *           of the x and y components of v and u
     */
    public static Vector2f multiply(Vector2f v, Vector2f u) {
        return new Vector2f(v.x * u.x, v.y * u.y);
    }

    /**
     * divides the components of this vector by certain values
     * @param mx - the division factor of the x component
     * @param my - the division factor of the y component
     * @return - this vector
     */
    public Vector2f divide(float mx, float my) {
        this.x /= mx;
        this.y /= my;
        return this;
    }

    /**
     * divides the components of this vector by the components of another vector
     * @param v - the other vector
     * @return - this vector
     */
    public Vector2f divide(Vector2f v) {
        this.x /= v.x;
        this.y /= v.y;
        return this;
    }

    /**
     * divides the components of a vector by a value
     * @param v - the vector
     * @param m - the value
     * @return - the new vector, (v.x / m, v.y / m)
     */
    public static Vector2f divide(Vector2f v, float m) {
        return new Vector2f(v.x / m, v.y / m);
    }

    /**
     * divides the components of a vector by certain values
     * @param v - the vector
     * @param mx - the division factor of the x component
     * @param my - the division factor of the y component
     * @return - the new vector, (v.x / mx, v.y / my)
     */
    public static Vector2f divide(Vector2f v, float mx, float my) {
        return new Vector2f(v.x / mx, v.y / my);
    }

    /**
     * divides the components of one vector by the components of another vector
     * @param v - the first vector
     * @param u - the second vector
     * @return - a new vector where the components are the straight divisions of the components of v and u
     */
    public static Vector2f divide(Vector2f v, Vector2f u) {
        return new Vector2f(v.x / u.x, v.y / u.y);
    }

    /**
     * dots this vector with another vector
     * @param v - the other vector
     * @return - the dot product of this vector and the other vector
     */
    public float dot(Vector2f v) {
        return x * v.x + y * v.y;
    }

    /**
     * computes the dot product of two vectors
     * @param v - the first vector
     * @param u - the second vector
     * @return - the dot product of u and v
     */
    public static float dot(Vector2f v, Vector2f u) {
        return v.x * u.x + v.y * u.y;
    }

    /**
     * computes the length of this vector
     * @return - the length of this vector
     */
    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * computes the length of a vector
     * @param v - the vector
     * @return - the length of v
     */
    public static float length(Vector2f v) {
        return (float) Math.sqrt(v.x * v.x + v.y * v.y);
    }

    public static float angleBetween(Vector2f v, Vector2f u) {
        return (float) Math.acos(Vector2f.dot(Vector2f.normalize(v), Vector2f.normalize(u)) / (Vector2f.length(v) * Vector2f.length(u)));
    }

    /**
     * normalizes this vector
     * @return - this vector
     */
    public Vector2f normalize() {
        this.x /= this.length();
        this.y /= this.length();
        return this;
    }

    /**
     * normalizes this vector to a specific length
     * @param l - the new length of this vector
     * @return - this vector
     */
    public Vector2f normalize(float l) {
        this.x *= l / this.length();
        this.y *= l / this.length();
        return this;
    }

    /**
     * normalizes a vector
     * @param v - the vector to be normalized
     * @return - a new unit vector in the direction of v
     */
    public static Vector2f normalize(Vector2f v) {
        return Vector2f.divide(v, new Vector2f(length(v)));
    }

    /**
     * normalizes a vector to a specific length
     * @param v - the vector to normalize
     * @param l - the new length
     * @return - a new vector in the direction of v with a length of l
     */
    public static Vector2f normalize(Vector2f v, float l) {
        return Vector2f.normalize(v).scale(l);
    }

    /**
     * computes the distance between two vectors
     * @param v - the first vector
     * @param u - the second vector
     * @return - the distance between v and u
     */
    public static float distance(Vector2f v, Vector2f u) {
        return Vector2f.length(Vector2f.subtract(v, u));
    }

    /**
     * getter method
     * @return - the x component of this vector
     */
    public float getX() {
        return this.x;
    }

    /**
     * getter method
     * @return - the y component of this vector
     */
    public float getY() {
        return this.y;
    }

    /**
     * getter method
     * @return - the i component of this vector
     */
    public float getI() {
        return this.x;
    }

    /**
     * getter method
     * @return - the j component of this vector
     */
    public float getJ() {
        return this.y;
    }

    /**
     * converts this 2-float vector to a float array
     * @return - the float array
     */
    public float[] toFloatArray() {
        return new float[]{x, y};
    }

    /**
     * determines the center point of 2 vectors
     * @param v - the first vector
     * @param u - the second vector
     * @return - a vector at the center point of the two vectors
     */
    public static Vector2f center(Vector2f v, Vector2f u) {
        return new Vector2f((v.x + u.x) / 2, (v.y + u.y) / 2);
    }

    /**
     * calculates the determinant of 2 vectors
     * @param v - the first vector
     * @param u - the second vector
     * @return - the determinant of the two vectors
     */
    public static float det(Vector2f v, Vector2f u) {
        return v.x * u.y - v.y * u.x;
    }

    /**
     * determines if this vector is equal to another object o
     * @param o - the other object
     * @return - true if this and o are both vectors with the same quantities
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector2f)) return false;
        Vector2f vector2f = (Vector2f) o;
        return this.x == vector2f.x && this.y == vector2f.y;
    }

    /**
     * generates a hashcode of this vector
     * @return - the hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
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
        return "(" + xS + ", " + yS + ")";
    }
}