package com.bramerlabs.molecular.engine3D.graphics.vertex;

import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector4f;

public class ColorVertex extends Vertex {

    protected Vector4f color;

    public ColorVertex(Vector3f position, Vector3f normal, Vector4f color) {
        super(position, normal);
        this.color = color;
    }

    public ColorVertex(float px, float py, float pz, float nx, float ny, float nz, float r, float g, float b, float a) {
        super(new Vector3f(px, py, pz), new Vector3f(nx, ny, nz));
        this.color = new Vector4f(r, g, b, a);
    }

    public ColorVertex(float px, float py, float pz, float nx, float ny, float nz, Vector4f color) {
        super(new Vector3f(px, py, pz), new Vector3f(nx, ny, nz));
        this.color = color;
    }

    public Vector4f getColor() {
        return this.color;
    }

}
