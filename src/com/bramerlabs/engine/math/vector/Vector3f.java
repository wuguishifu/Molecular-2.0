package com.bramerlabs.engine.math.vector;

import com.bramerlabs.engine.math.matrix.Matrix4f;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Objects;

public class Vector3f {

    /**
     * the x, y, and z components of this vector
     */
    public float x, y, z;

    /**
     * unit vectors in the e1, e2, and e3 directions
     */
    public static final Vector3f e1 = new Vector3f(1, 0, 0);
    public static final Vector3f e2 = new Vector3f(0, 1, 0);
    public static final Vector3f e3 = new Vector3f(0, 0, 1);

    /**
     * default constructor
     * @param x - the x component
     * @param y - the y component
     * @param z - the z component
     */
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * constructor for 1 specified value
     * @param val - the value for all three components to be set to
     */
    public Vector3f(float val) {
        this.x = val;
        this.y = val;
        this.z = val;
    }

    /**
     * constructor for duplicating a vector
     * @param v - the vector to duplicate
     */
    public Vector3f(Vector3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    /**
     * constructor for a vector from a 2-float vector and a float z component
     * @param v - the 2-float vector
     * @param z - the z component
     */
    public Vector3f(Vector2f v, float z) {
        this.x = v.x;
        this.y = v.y;
        this.z = z;
    }

    /**
     * constructor from a float array
     * @param v - a float array containing values in form [x, y, z]
     * method will automatically set the position based off of available variables.
     * No minimum or maximum necessary supplied variables, if more than 3 are supplied the vector
     * will be constructed out of the first 3.
     */
    public Vector3f(float[] v) {
        this.x = v.length > 0 ? v[0] : 0;
        this.y = v.length > 1 ? v[1] : 0;
        this.z = v.length > 2 ? v[2] : 0;
    }

    /**
     * constructor from a java awt color
     * makes a vector in the form (r, g, b)
     * @param c - the color
     */
    public Vector3f(Color c) {
        this.x = c.getRed();
        this.y = c.getGreen();
        this.z = c.getBlue();
    }

    /**
     * sets each component of this vector
     * @param x - the new x component
     * @param y - the new y component
     * @param z - the new z component
     */
    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * sets the x component
     * @param x - the new x value
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * sets the y component
     * @param y - the new y value
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * sets the z component
     * @param z - the new z value
     */
    public void setZ(float z) {
        this.z = z;
    }

    /**
     * adds values to each component to this vector
     * @param dx - the change in x component
     * @param dy - the change in y component
     * @param dz - the change in z component
     * @return - this vector
     */
    public Vector3f add(float dx, float dy, float dz) {
        this.x += dx;
        this.y += dy;
        this.z += dz;
        return this;
    }

    /**
     * adds a vector to this vector
     * @param v - the vector to be added to this vector
     * @return - this vector
     */
    public Vector3f add(Vector3f v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        return this;
    }

    /**
     * adds values to each component of a vector
     * @param v - the vector to add to
     * @param dx - the change in x component
     * @param dy - the change in y component
     * @param dz - the change in z component
     * @return - a new vector, v + (dx, dy, dz)
     */
    public static Vector3f add(Vector3f v, float dx, float dy, float dz) {
        return new Vector3f(v.x + dx, v.y + dy, v.z + dz);
    }

    /**
     * adds two vectors together
     * @param v - vector 1
     * @param u - vector 2
     * @return - a new vector, the sum of v and u
     */
    public static Vector3f add(Vector3f v, Vector3f u) {
        return new Vector3f(v.x + u.x, v.y + u.y, v.z + u.z);
    }

    /**
     * scales each component in this vector by a scale factor
     * @param scaleFactor - the scale factor
     * @return - this vector
     */
    public Vector3f scale(float scaleFactor) {
        this.x *= scaleFactor;
        this.y *= scaleFactor;
        this.z *= scaleFactor;
        return this;
    }

    /**
     * scales each component in a vector by a scale factor
     * @param v - the vector to scale
     * @param scaleFactor - the scale factor
     * @return - a new vector, the scalar scale of v by scaleFactor
     */
    public static Vector3f scale(Vector3f v, float scaleFactor) {
        return new Vector3f(v.x * scaleFactor, v.y * scaleFactor, v.z * scaleFactor);
    }

    /**
     * subtracts from each component of this vector
     * @param dx - the change in x component
     * @param dy - the change in y component
     * @param dz - the change in z component
     * @return - this vector
     */
    public Vector3f subtract(float dx, float dy, float dz) {
        this.x -= dx;
        this.y -= dy;
        this.z -= dz;
        return this;
    }

    /**
     * subtracts each component of this vector by a component of another vector
     * @param v - the other vector
     * @return - this vector
     */
    public Vector3f subtract(Vector3f v) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
        return this;
    }

    /**
     * subtracts from each component in a vector
     * @param v - the vector
     * @param dx - the change in x component
     * @param dy - the change in y component
     * @param dz - the change in z component
     * @return - a new vector, v - (dx, dy, dz)
     */
    public static Vector3f subtract(Vector3f v, float dx, float dy, float dz) {
        return new Vector3f(v.x - dx, v.y - dy, v.z - dz);
    }

    /**
     * subtracts two vectors
     * @param v - vector 1
     * @param u - vector 2
     * @return - a new vector, v - u
     */
    public static Vector3f subtract(Vector3f v, Vector3f u) {
        return new Vector3f(v.x - u.x, v.y - u.y, v.z - u.z);
    }

    /**
     * multiplies the components of this vector by values
     * @param mx - the multiplication factor of the x component
     * @param my - the multiplication factor of the y component
     * @param mz - the multiplication factor of the z component
     * @return - this vector
     */
    public Vector3f multiply(float mx, float my, float mz) {
        this.x *= mx;
        this.y *= my;
        this.z *= mz;
        return this;
    }

    /**
     * multiplies the components of this vector by the components of another vector
     * @param v - the other vector
     * @return - this vector
     */
    public Vector3f multiply(Vector3f v) {
        this.x *= v.x;
        this.y *= v.y;
        this.z *= v.z;
        return this;
    }

    /**
     * multiplies the components of a vector by values
     * @param v - the vector
     * @param mx - the multiplication factor of the x component
     * @param my - the multiplication factor of the y component
     * @param mz - the multiplication factor of the z component
     * @return - a new vector
     */
    public static Vector3f multiply(Vector3f v, float mx, float my, float mz) {
        return new Vector3f(v.x * mx, v.y * my, v.z * mz);
    }

    /**
     * multiplies the components of one vector by the components of another vector
     * @param v - the first vector
     * @param u - the second vector
     * @return - a vector where the values are the straight multiplication of v and u
     */
    public static Vector3f multiply(Vector3f v, Vector3f u) {
        return new Vector3f(v.x * u.x, v.y * u.y, v.z * u.z);
    }

    /**
     * divides the components of this vector by values
     * @param mx - the division factor of the x component
     * @param my - the division factor of the y component
     * @param mz - the division factor of the z component
     * @return - this vector
     */
    public Vector3f divide(float mx, float my, float mz) {
        this.x /= mx;
        this.y /= my;
        this.z /= mz;
        return this;
    }

    /**
     * divides the components of this vector
     * @param d - the division factor for each component
     * @return - this vector
     */
    public Vector3f divide(float d) {
        this.x /= d;
        this.y /= d;
        this.z /= d;
        return this;
    }

    /**
     * divides the components of a vector
     * @param v - the vector
     * @param d - the division factor
     * @return - the new vector, v/d
     */
    public static Vector3f divide(Vector3f v, float d) {
        return Vector3f.divide(new Vector3f(v), new Vector3f(d));
    }

    /**
     * divides the components of this vector by the components of another vector
     * @param v - the other vector
     * @return - this vector
     */
    public Vector3f divide(Vector3f v) {
        this.x /= v.x;
        this.y /= v.y;
        this.z /= v.z;
        return this;
    }

    /**
     * divides the components of a vector by values
     * @param v - the vector
     * @param mx - the division factor of the x component
     * @param my - the division factor of the y component
     * @param mz - the division factor of the z component
     * @return - a new vector
     */
    public static Vector3f divide(Vector3f v, float mx, float my, float mz) {
        return new Vector3f(v.x / mx, v.y / my, v.z / mz);
    }

    /**
     * divides the components of one vector by the components of another vector
     * @param v - the first vector
     * @param u - the second vector
     * @return - a new vector
     */
    public static Vector3f divide(Vector3f v, Vector3f u) {
        return new Vector3f(v.x / u.x, v.y / u.y, v.z / u.z);
    }

    /**
     * dots this vector with another vector
     * @param v - the other vector
     * @return - the dot product of this vector and the other vector
     */
    public float dot(Vector3f v) {
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }

    /**
     * dots two vectors together
     * @param v - the first vector
     * @param u - the second vector
     * @return - the dot product v dot u
     */
    public static float dot(Vector3f v, Vector3f u) {
        return v.x * u.x + v.y * u.y + v.z * u.z;
    }

    /**
     * computes the cross product of two vectors
     * @param v - vector 1
     * @param u - vector 2
     * @return - the cross product of v and u (v x u)
     */
    public static Vector3f cross(Vector3f v, Vector3f u) {
        float x = v.y * u.z - v.z * u.y;
        float y = v.z * u.x - v.x * u.z;
        float z = v.x * u.y - v.y * u.x;
        return new Vector3f(x, y, z);
    }

    /**
     * computes the length of this vector
     * @return - the length of this vector
     */
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * computes the length of a vector
     * @param v - the vector
     * @return - the length of vector v
     */
    public static float length(Vector3f v) {
        return (float) Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
    }

    /**
     * normalizes this vector
     * @return - this vector
     */
    public Vector3f normalize() {
        this.x /= this.length();
        this.y /= this.length();
        this.z /= this.length();
        return this;
    }

    /**
     * normalizes this vector to a specific length
     * @param l - the new length
     * @return - this vector
     */
    public Vector3f normalize(float l) {
        this.x *= l / this.length();
        this.y *= l / this.length();
        this.z *= l / this.length();
        return this;
    }

    /**
     * normalizes a vector
     * @param v - the other vector
     * @return - a new vector with a length of 1 in the same direction of v
     */
    public static Vector3f normalize(Vector3f v) {
        return Vector3f.divide(v, new Vector3f(length(v)));
    }

    /**
     * normalizes a vector to a specific length
     * @param v - the vector
     * @param l - the length
     * @return - a new vector with length l in the same direction of v
     */
    public static Vector3f normalize(Vector3f v, float l) {
        return Vector3f.normalize(v).scale(l);
    }

    /**
     * determines the length between two vectors
     * @param v - vector 1
     * @param u - vector 2
     * @return - the length between vector 1 and vector 2
     */
    public static float distance(Vector3f v, Vector3f u) {
        return length(multiply(subtract(u, v), subtract(u, v)));
    }

    /**
     * determines the midpoint of the two vectors
     * @param v - vector 1
     * @param u - vector 2
     * @return - a vector representing the midpoint of the two vectors
     */
    public static Vector3f midpoint(Vector3f v, Vector3f u) {
        return new Vector3f((v.x + u.x) / 2, (v.y + u.y) / 2, (v.z + u.z) / 2);
    }

    /**
     * determines the angle between two vectors
     * @param v - vector 1
     * @param u - vector 2
     * @return - the angle between
     */
    public static float angleBetween(Vector3f v, Vector3f u) {
        return (float) Math.acos(Vector3f.dot(v, u) * quickInverseSqrt(v) * quickInverseSqrt(u));
    }

    /**
     * quickly determines the inverse magnitude of a vector using the Fast Inverse Square Root formula
     * @param v - the vector
     * @return - the magnitude
     */
    public static float quickInverseSqrt(Vector3f v) {
        float val = v.x * v.x + v.y * v.y + v.z * v.z;
        float xHalf = 0.5f * val;
        int i = Float.floatToIntBits(val);
        i = 0x5f3759df - (i >> 1);
        val = Float.intBitsToFloat(i);
        val *= (1.5f - xHalf * val * val);
        return val;
    }

    /**
     * rotates this vector around an axis
     * @param angle - the angle to rotate
     * @param axis - the axis
     */
    public void rotate(float angle, Vector3f axis) {
        Matrix4f rotationMatrix = Matrix4f.rotate(angle, axis);
        Vector4f result = Matrix4f.multiply(rotationMatrix, new Vector4f(this, 1.0f));
        this.set(result.x, result.y, result.z);
    }

    /**
     * rotates a vector around an axis
     * @param v - the vector to rotate
     * @param angle - the angle to rotate it by
     * @param axis - the axis to rotate it around
     * @return - the new rotated vector
     */
    public static Vector3f rotate(Vector3f v, float angle, Vector3f axis) {
        Matrix4f rotationMatrix = Matrix4f.rotate(angle, axis);
        Vector4f result = Matrix4f.multiply(rotationMatrix, new Vector4f(v, 1.0f));
        return result.xyz();
    }

    /**
     * maximizes all the values of this vector to be over a specific value
     * @param min - the minimum value
     */
    public void maximize(float min) {
        this.x = Math.max(min, this.x);
        this.y = Math.max(min, this.y);
        this.z = Math.max(min, this.z);
    }

    /**
     * minimizes all the values of this vector to be under a specific value
     * @param max - the maximum value
     */
    public void minimize(float max) {
        this.x = Math.min(max, this.x);
        this.y = Math.min(max, this.y);
        this.z = Math.min(max, this.z);
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
     * @return - the z component o f this vector
     */
    public float getZ() {
        return this.z;
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
     * getter method
     * @return - the k component of this vector
     */
    public float getK() {
        return this.z;
    }

    /**
     * getter method
     * @return - a 2 float vector of some of the components in this vector
     */
    public Vector2f xy() {
        return new Vector2f(x, y);
    }

    /**
     * getter method
     * @return - a 2 float vector of some of the components in this vector
     */
    public Vector2f yz() {
        return new Vector2f(y, z);
    }

    /**
     * getter method
     * @return - a 2 float vector of some of the components in this vector
     */
    public Vector2f xz() {
        return new Vector2f(x, z);
    }

    /**
     * converts this 3-float vector to a float array
     * @return - the float array
     */
    public float[] toFloatArray() {
        return new float[]{x, y, z};
    }

    /**
     * determines if two vectors are exactly identical
     * @param o - the other object
     * @return - true if this and o are both vectors that are exactly equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector3f)) return false;
        Vector3f other = (Vector3f) o;
        return this.x == other.x && this.y == other.y && this.z == other.z;
    }

    /**
     * determines if two vectors are nearly identical
     * @param o - the other object
     * @param epsilon - the max error
     * @return - true if this and o are both vectors and the absolute error for each component is less than epsilon
     */
    public boolean equals(Object o, float epsilon) {
        if (this == o) return true;
        if (!(o instanceof Vector3f)) return false;
        Vector3f other = (Vector3f) o;
        if (x - other.x > epsilon) {
            return false;
        } else if (y - other.y > epsilon) {
            return false;
        } else return !(z - other.z > epsilon);
    }

    /**
     * creates a hashcode of this vector
     * @return - the hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
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
        return "(" + xS + ", " + yS + ", " + zS + ")";
    }
}