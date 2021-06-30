package com.bramerlabs.molecular.molecule;

import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.objects.RenderObject;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.bond.Bond;
import com.bramerlabs.molecular.molecule.functional_groups.FunctionalGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Molecule {

    private final Vector3f position;

    private final ArrayList<Atom> atoms;
    private final ArrayList<Bond> bonds;

    private ArrayList<FunctionalGroup> functionalGroups = new ArrayList<>();

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

    public void add(Atom atom) {
        atoms.add(atom);
    }

    public void add(Bond bond) {
        bonds.add(bond);
    }

    public void remove(Atom atom) {
        this.atoms.remove(atom);
    }

    public void remove(Bond bond) {
        this.bonds.remove(bond);
    }

    public void add(Atom[] atoms) {
        this.atoms.addAll(Arrays.asList(atoms));
    }

    public void add(Bond[] bonds) {
        this.bonds.addAll(Arrays.asList(bonds));
    }

    public void add(FunctionalGroup fg) {
        this.add(fg.getAtoms());
        this.add(fg.getBonds());
    }

    public void add(FunctionalGroup[] fgs) {
        for (FunctionalGroup fg : fgs) {
            functionalGroups.add(fg);
            this.add(fg);
        }
    }

    public void update() {
        for (FunctionalGroup fg : functionalGroups) {
            fg.update();
        }
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

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Atom a : atoms) {
            result.append(a).append("\n");
        }
        for (Bond b : bonds) {
            result.append(b).append("\n");
        }
        return result.toString();
    }

}
