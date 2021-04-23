package com.bramerlabs.engine.math.matrix;

import com.bramerlabs.engine.math.vector.Vector3f;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.Arrays;

public class Matrix3f {

    /**
     * the square dimensions of this matrix
     */
    public static final int SIZE = 3;
    public static final int WIDTH = SIZE;
    public static final int HEIGHT = SIZE;
    public static final int NUM_ELEMENTS = SIZE * SIZE;

    /**
     * the elements of this matrix
     */
    private float[] elements = new float[SIZE * SIZE];

    /**
     *
     */
    public Matrix3f() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.set(i, j, 0);
            }
        }
    }

    /**
     * sets a value of the matrix
     * @param x - the column (x, y) cartesian coordinates
     * @param y - the row
     * @param value - the value to be set
     */
    public void set(int x, int y, float value) {
        elements[y * SIZE + x] = value;
    }

    /**
     * creates an identity matrix
     * @return - the 3I identity matrix
     */
    public static Matrix3f identity() {
        Matrix3f result = new Matrix3f();
        for (int i = 0; i < SIZE; i++) {
            result.set(i, i, 1);
        }
        return result;
    }

    /**
     * creates a scalar identity matrix
     * @param scaleFactor - the scale factor
     * @return - the n3I identity matrix
     */
    public static Matrix3f identity(float scaleFactor) {
        Matrix3f result = new Matrix3f();
        for (int i = 0; i < SIZE; i++) {
            result.set(i, i, scaleFactor);
        }
        return result;
    }

    /**
     * creates a scale matrix by some group of scalars
     * @param scalar - a vector with all the scalars to scale the matrix by
     * @return - a matrix representing the scale operations specified by the scalar vector
     */
    public static Matrix3f scale(Vector3f scalar) {
        Matrix3f result = Matrix3f.identity();

        result.set(0, 0, scalar.getX());
        result.set(1, 1, scalar.getY());
        result.set(2, 2, scalar.getZ());

        return result;
    }

    /**
     * multiplies two matrices together
     * @param matrix - matrix 1
     * @param other - matrix 2
     * @return - a new Matrix, A x B
     */
    public static Matrix4f multiply(Matrix3f matrix, Matrix3f other) {
        Matrix4f result = Matrix4f.identity();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                result.set(i, j, matrix.get(i, 0) * other.get(0, j) +
                        matrix.get(i, 1) * other.get(1, j) +
                        matrix.get(i, 2) * other.get(2, j));
            }
        }

        return result;
    }

    /**
     * gets the value at a certain (x, y) position
     * @param x - the column
     * @param y - the row
     * @return - the value at (x, y) starting at the top left
     */
    public float get(int x, int y) {
        return elements[y * SIZE + x];
    }

    /**
     * getter method
     * @return - the elements of this matrix
     */
    public float[] getAll() {
        return elements;
    }

    /**
     * converts the matrix to a float buffer
     * @param value - the matrix to be converted
     * @return - a float buffer object
     */
    public static FloatBuffer toFloatBuffer(Matrix3f value) {
        FloatBuffer matrix = MemoryUtil.memAllocFloat(SIZE * SIZE);
        matrix.put(value.getAll()).flip();
        return matrix;
    }

    /**
     * determines if two matrices are exactly identical
     * @param o - the other object
     * @return - true if both objects are matrices that are exactly equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Matrix3f)) return false;
        Matrix3f matrix3f = (Matrix3f) o;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (matrix3f.elements[j * SIZE + i] != this.elements[j * SIZE + i]) { // check that all elements of this matrix match all the elements of the other matrix
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * determines if two matrices are almost identical, with an error maximum of epsilon
     * @param o - the other object
     * @param epsilon - the max error
     * @return - true if both objects are matrices that are nearly equal
     */
    public boolean equals(Object o, float epsilon) {
        if (this == o) return true;
        if (!(o instanceof Matrix3f)) return false;
        Matrix3f other = (Matrix3f) o;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (Math.abs(this.elements[j * SIZE + i] - other.elements[j * SIZE + i]) > epsilon) { // check if the absolute error between both matrix values is less than epsilon
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * generates a hashcode based on the elements of this matrix
     * @return - a unique hashcode
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }

    /**
     * generates a string version of this matrix
     * @return - a string
     */
    @Override
    public String toString() {
        return  "|" + get(0, 0) + "  " + get(0, 1) + "  " + get(0, 2) + "|\n" +
                "|" + get(1, 0) + "  " + get(1, 1) + "  " + get(1, 2) + "|\n" +
                "|" + get(2, 0) + "  " + get(2, 1) + "  " + get(2, 2) + "|\n";
    }

}
