package com.bramerlabs.engine.objects.shapes.shapes_2d;

import com.bramerlabs.engine.math.vector.Vector3f;

public class Triangle {

    // the vertices of this triangle
    private Vector3f v1, v2, v3;

    // a vector normal to this triangle
    private Vector3f normal;

    /**
     * default constructor
     * @param v1 - the first vertex
     * @param v2 - the second vertex
     * @param v3 - the third vertex
     */
    public Triangle(Vector3f v1, Vector3f v2, Vector3f v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    /**
     * constructs a triangle with 3 vertices and a normal vector
     * @param v1 - the first vertex
     * @param v2 - the second vertex
     * @param v3 - the third vertex
     * @param normal - the normal vector
     */
    public Triangle(Vector3f v1, Vector3f v2, Vector3f v3, Vector3f normal) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.normal = normal;
    }

    /**
     * moves each vector by a certain amount
     * @param dP - the delta position
     */
    public void move(Vector3f dP) {
        this.v1 = Vector3f.add(v1, dP);
        this.v2 = Vector3f.add(v2, dP);
        this.v3 = Vector3f.add(v3, dP);
    }

    /**
     * sets the normal vector of this triangle
     * @param normal - the new normal vector
     */
    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }

    /**
     * @return - the first vertex of this triangle
     */
    public Vector3f getV1() {
        return v1;
    }

    /**
     * @return - the second vertex of this triangle
     */
    public Vector3f getV2() {
        return v2;
    }

    /**
     * @return - the third vertex of this triangle
     */
    public Vector3f getV3() {
        return v3;
    }

    /**
     * getter method
     * @return - the vector normal to this triangle
     */
    public Vector3f getNormal() {
        return normal;
    }
}
