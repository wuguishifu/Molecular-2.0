package com.bramerlabs.molecular.molecule;

import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import com.bramerlabs.engine.objects.shapes.shapes_3d.Sphere;

public class Atom {

    // the identity of the atom
    private int atomicNumber;

    // the location of the atom
    private Vector3f position;

    // the sphere used to render this atom
    private Sphere renderSphere;

    public Atom(int atomicNumber, Vector3f position) {
        this.atomicNumber = atomicNumber;
        this.position = position;
        this.renderSphere = Sphere.getInstance(position, new Vector4f(0.5f, 0.5f, 0.5f, 1.0f), 1.0f);
        this.renderSphere.createMesh();
    }

    public int getAtomicNumber() {
        return this.atomicNumber;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Sphere getRenderSphere() {
        return this.renderSphere;
    }
}