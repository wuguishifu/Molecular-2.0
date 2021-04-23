package com.bramerlabs.engine.math.collision;

public class RectangularHitbox {

    // the positions of opposite maxima corners
    public float xMin, xMax, yMin, yMax, zMin, zMax;

    // hitbox flags - use multiple using bitwise or: IGNORE_X_DIRECTION | IGNORE_Y_DIRECTION
    public static final int IGNORE_FLAGS = 0;
    public static final int IGNORE_X_DIRECTION = 2;
    public static final int IGNORE_Y_DIRECTION = 4;
    public static final int IGNORE_Z_DIRECTION = 8;
    public static final int IGNORE_X_DIRECTION_POSITIVE = 16;
    public static final int IGNORE_X_DIRECTION_NEGATIVE = 32;
    public static final int IGNORE_Y_DIRECTION_POSITIVE = 64;
    public static final int IGNORE_Y_DIRECTION_NEGATIVE = 128;
    public static final int IGNORE_Z_DIRECTION_POSITIVE = 256;
    public static final int IGNORE_Z_DIRECTION_NEGATIVE = 512;

    // flags used for getting values
    public static final int X_MIN = 0;
    public static final int X_MAX = 1;
    public static final int Y_MIN = 2;
    public static final int Y_MAX = 3;
    public static final int Z_MIN = 4;
    public static final int Z_MAX = 5;
    public static final int NUM_VAL = 6;

    /**
     * default constructor for specified maxima corners
     * @param xMin - the minimum x value of this hitbox
     * @param xMax - the maximum x value of this hitbox
     * @param yMin - the minimum y value of this hitbox
     * @param yMax - the maximum y value of this hitbox
     * @param zMin - the minimum z value of this hitbox
     * @param zMax - the maximum z value of this hitbox
     */
    public RectangularHitbox(float xMin, float xMax, float yMin, float yMax, float zMin, float zMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.zMin = zMin;
        this.zMax = zMax;
    }

    /**
     * sets the values of this hitbox
     * @param xMin - the minimum x value of this hitbox
     * @param xMax - the maximum x value of this hitbox
     * @param yMin - the minimum y value of this hitbox
     * @param yMax - the maximum y value of this hitbox
     * @param zMin - the minimum z value of this hitbox
     * @param zMax - the maximum z value of this hitbox
     */
    public void setValues(float xMin, float xMax, float yMin, float yMax, float zMin, float zMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.zMin = zMin;
        this.zMax = zMax;
    }

    /**
     * checks if two hitboxes are intersecting
     * @param other - the other hitbox
     * @return - true if the two hitboxes intersect
     */
    public boolean intersects(RectangularHitbox other) {
        return (other.xMax > xMin && other.xMin < xMax) &&
                (other.yMax > yMin && other.yMin < yMax) &&
                (other.zMax > zMin && other.zMin < zMax);
    }

    /**
     * unfinished method - deprecated for now
     * @param other - the other hitbox
     * @param flags - flags regarding hitbox detection
     * @return - true if the two hitboxes intersect
     */
    public boolean intersects(RectangularHitbox other, int flags) {
        if (flags == IGNORE_FLAGS) {
            return intersects(other);
        } else {
            return false;
        }
    }

    /**
     * getter method
     * @param v - which value to get
     * @return - the value
     */
    public float get(int v) {
        switch (v) {
            case X_MIN: return xMin;
            case X_MAX: return xMax;
            case Y_MIN: return yMin;
            case Y_MAX: return yMax;
            case Z_MIN: return zMin;
            case Z_MAX: return zMax;
            default: return -1;
        }
    }

    /**
     * getter method
     * @return - the min x value of the hitbox
     */
    public float getXMin() {
        return xMin;
    }

    /**
     * getter method
     * @return - the max x value of the hitbox
     */
    public float getXMax() {
        return xMax;
    }

    /**
     * getter method
     * @return - the min y value of the hitbox
     */
    public float getYMin() {
        return yMin;
    }

    /**
     * getter method
     * @return - the max y value of the hitbox
     */
    public float getYMax() {
        return yMax;
    }

    /**
     * getter method
     * @return - the min z value of the hitbox
     */
    public float getZMin() {
        return zMin;
    }

    /**
     * getter method
     * @return - the max z value of the hitbox
     */
    public float getZMax() {
        return zMax;
    }
}
