package com.bramerlabs.molecular.molecule.groups;

import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.molecule.components.atom.Atom;
import com.bramerlabs.molecular.molecule.components.bond.Bond;

import java.util.ArrayList;

public abstract class FunctionalGroup {

    protected ArrayList<Atom> atoms;
    protected ArrayList<Bond> bonds;

    protected Vector3f[] atomPositions;

    public FunctionalGroup(Vector3f[] atomPositions) {
        this.atomPositions = atomPositions;
        this.atoms = new ArrayList<>();
        this.bonds = new ArrayList<>();
        this.generateAtoms();
        this.generateBonds();
    }

    public FunctionalGroup(Vector3f[] atomPositions, Vector3f position, Vector3f normal) {
        this.atomPositions = atomPositions;
        this.atoms = new ArrayList<>();
        this.bonds = new ArrayList<>();
        this.generateAtoms(position, normal);
        this.generateBonds();
    }

    public ArrayList<Atom> getAtoms() {
        return this.atoms;
    }

    public ArrayList<Bond> getBonds() {
        return this.bonds;
    }

    public abstract void generateAtoms();

    public void generateAtoms(Vector3f position, Vector3f normal) {
        generateAtoms();

        Vector3f axis = Vector3f.normalize(Vector3f.cross(normal, atomPositions[0]));
        System.out.println(normal + ", " + atomPositions[0]);
        System.out.println(axis);
        float angle = Vector3f.angleBetween(normal, atomPositions[0]);
        System.out.println(angle);

        for (Atom atom : atoms) {
            atom.position = Vector3f.rotate(atom.position, axis, angle);
            atom.position = Vector3f.add(atom.position, position);
        }

    }

    public abstract void generateBonds();

}
