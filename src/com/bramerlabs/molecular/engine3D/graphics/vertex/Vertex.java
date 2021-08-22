package com.bramerlabs.molecular.engine3D.graphics.vertex;

import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;

public class Vertex {

    protected Vector3f position, normal;

    public Vertex(Vector3f position, Vector3f normal) {
        this.position = position;
        this.normal = normal;
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public Vector3f getNormal() {
        return this.normal;
    }

}