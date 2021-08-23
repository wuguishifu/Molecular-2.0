package com.bramerlabs.molecular.engine3D.objects;

import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;

import java.util.ArrayList;

public class Circle {

    ArrayList<Vector3f> vertices = new ArrayList<>();

    public Circle(Vector3f position, float radius, Vector3f normal, int numVertices) {
        Vector3f v0 = new Vector3f(1, 0, 1);
        if (Vector3f.cross(normal, v0).equals(new Vector3f(0, 0, 0), 0.000001f)) {
            v0 = new Vector3f(0, 1, 1);
        }

        Vector3f v1 = Vector3f.normalize(Vector3f.cross(normal, v0), radius);
        Vector3f v2 = Vector3f.normalize(Vector3f.cross(normal, v1), radius);

        float dt = ((float) Math.PI * 2) / numVertices;

        for (int i = 0; i < numVertices; i++) {
            Vector3f v = Vector3f.scale(v1, (float) (radius * Math.cos(i * dt)));
            v = Vector3f.add(v, Vector3f.scale(v2, (float) (radius * Math.sin(i * dt))));
            v = Vector3f.normalize(v, radius);
            v = Vector3f.add(v, position);
            vertices.add(v);
        }
    }

    public ArrayList<Vector3f> getVertices() {
        return this.vertices;
    }

}
