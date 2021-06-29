package com.bramerlabs.molecular.molecule;

import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.objects.RenderObject;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.bond.Bond;

import java.util.ArrayList;
import java.util.HashMap;

public class Molecule {

    private final Vector3f position;

    private final ArrayList<Atom> atoms;
    private final ArrayList<Bond> bonds;

    public Molecule(Vector3f position, ArrayList<Atom> atoms, ArrayList<Bond> bonds) {
        this.atoms = atoms;
        this.bonds = bonds;
        this.position = position;
    }

    public ArrayList<Atom> getAtoms() {
        return this.atoms;
    }

    public ArrayList<Bond> getBonds() {
        return this.bonds;
    }

    public void addAtom(Atom atom) {
        atoms.add(atom);
    }

    public void addBond(Bond bond) {
        bonds.add(bond);
    }

    public void removeAtom(Atom atom) {
        this.atoms.remove(atom);
    }

    public void removeBond(Bond bond) {
        this.bonds.remove(bond);
    }

    public void moveAtom(Atom atom, Vector3f position) {
        atom.moveTo(position);
    }

    public HashMap<Integer, RenderObject> atomObjects = new HashMap<>();
    public HashMap<Integer, RenderObject> bondObjects = new HashMap<>();

    public RenderObject getAtomObject(int atomicNumber) {
        if (atomObjects.containsKey(atomicNumber)) {
            return atomObjects.get(atomicNumber);
        } else {
            RenderObject object = Atom.generateRenderObject(atomicNumber);
            atomObjects.put(atomicNumber, object);
            return object;
        }
    }

    public RenderObject[] getBondObject(int order) {
        switch (order) {
            default:
                return Bond.SINGLE_BOND;
            case Bond.DOUBLE:
                return Bond.DOUBLE_BOND;
            case Bond.TRIPLE:
                return Bond.TRIPLE_BOND;
        }
    }

    public void destroy() {
        atomObjects.forEach((K, V) -> V.destroy());
        bondObjects.forEach((K, V) -> V.destroy());
    }

}
