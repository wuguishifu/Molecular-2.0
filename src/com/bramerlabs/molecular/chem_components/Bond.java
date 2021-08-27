package com.bramerlabs.molecular.chem_components;

import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;

public class Bond extends ElectronContainer {

    private Atom[] atoms = new Atom[2];
    private float length;
    int order;

    public Bond(Atom[] atoms, int order, int electronCount) {
        super(electronCount);
        this.atoms = atoms;
        this.order = order;
    }

    public Bond(Atom a1, Atom a2, int order, int electronCount) {
        super(electronCount);
        this.atoms[0] = a1;
        this.atoms[1] = a2;
        this.order = order;
    }

    public Atom[] getAtoms() {
        return atoms;
    }

    public void setAtoms(Atom[] atoms) {
        this.atoms = atoms;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Vector3f getCenter() {
        return Vector3f.midpoint(atoms[0].getPosition(), atoms[1].getPosition());
    }
}
