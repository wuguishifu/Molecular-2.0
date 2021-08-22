package com.bramerlabs.molecular.engine3D.objects;

import com.bramerlabs.molecular.engine3D.graphics.mesh.Mesh;
import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;

public class RenderObject {

    private Vector3f position, rotation, scale;
    private Mesh mesh;

    public RenderObject(Mesh mesh, Vector3f position, Vector3f rotation, Vector3f scale) {
        this.mesh = mesh;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public void createMesh() {
        mesh.create();
    }

    public void destroy() {
        mesh.destroy();
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public Vector3f getRotation() {
        return this.rotation;
    }

    public Vector3f getScale() {
        return this.scale;
    }

    public Mesh getMesh() {
        return this.mesh;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }
}
