package com.bramerlabs.engine.objects.shapes.shapes_2d;

import com.bramerlabs.engine.math.vector.Vector3f;

import java.util.ArrayList;

public class Circle {

    // position of the focus of the circle
    private Vector3f position;

    // radius of the circle
    private float radius;

    // vector normal to this circle
    private Vector3f normal;

    // the number of triangles used to make this circle
    private int numVertices;

    // list of triangles used in the mesh of this circle
    ArrayList<Vector3f> vertices = new ArrayList<>();

    // the golden ratio
    private static final float phi = 1.6180339f;

    public Circle(Vector3f position, float radius, Vector3f normal, int numVertices) {
        this.position = position;
        this.radius = radius;
        this.normal = normal;
        this.numVertices = numVertices;

        generateVertices();
    }

    private void generateVertices() {
        // generate two orthogonal vectors, v1 and v2, on the plane described by the normal vector
        // take some random vector v0 non-parallel to the normal vector
        Vector3f v0 = new Vector3f(1, 0, 1);
        if (Vector3f.cross(this.normal, v0).equals(new Vector3f(0, 0, 0), 0.00001f)) {
            v0 = new Vector3f(0, 1, 1);
        }

        // generate the new normal vectors
        Vector3f v1 = Vector3f.normalize(Vector3f.cross(this.normal, v0), this.radius);
        Vector3f v2 = Vector3f.normalize(Vector3f.cross(this.normal, v1), this.radius);

        // determine the change in t corresponding to the number of vertices required
        float dt = ((float) Math.PI * 2) / numVertices;

        // generate the vertices using the parametric equation:
        // r(t) = c + rcos(t)*i + rsin(t)*j, where i, j are v1, v2, and r is radius
        // r(t) for 0 <= t <= 2pi
        for (int i = 0; i < numVertices; i++) {
            Vector3f v = Vector3f.scale(v1, (float) (this.radius * Math.cos(i * dt)));
            v = Vector3f.add(v, Vector3f.scale(v2, (float) (this.radius * Math.sin(i * dt))));
            v = Vector3f.normalize(v, this.radius);
            v = Vector3f.add(v, this.position);
            this.vertices.add(v);
        }
    }

    public ArrayList<Vector3f> getVertices() {
        return this.vertices;
    }

}
