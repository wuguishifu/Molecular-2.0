package com.bramerlabs.molecular.molecule.atom;

import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import com.bramerlabs.engine.objects.RenderObject;
import com.bramerlabs.engine.objects.shapes.shapes_3d.Sphere;
import com.bramerlabs.molecular.molecule.bond.Bond;

import java.util.ArrayList;

public class Atom {

    private Vector3f position;
    private final int atomicNumber;

    private ArrayList<Bond> connectedBonds;

    public Atom(int atomicNumber, Vector3f position) {
        this.atomicNumber = atomicNumber;
        this.position = position;
    }

    public Atom(String element, Vector3f position) {
        this.atomicNumber = AtomicData.getAtomicNumber(element);
        this.position = position;
    }

    public void addBond(Bond bond) {
        this.connectedBonds.add(bond);
    }

    public ArrayList<Bond> getConnectedBonds() {
        return this.connectedBonds;
    }

    public int getAtomicNumber() {
        return this.atomicNumber;
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public void moveTo(Vector3f position) {
        this.position = position;
        // bond movement to be implemented later
    }

    public static RenderObject generateRenderObject(int atomicNumber) {
        float radius = AtomicData.getVDWRadius(atomicNumber);
        Vector4f color = new Vector4f(AtomicData.getCPKColor(atomicNumber), 1.0f);
        Sphere sphere = Sphere.getInstance(new Vector3f(0, 0, 0), color, radius);
        sphere.createMesh();
        return sphere;
    }

}