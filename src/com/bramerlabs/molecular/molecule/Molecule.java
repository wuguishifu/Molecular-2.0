package com.bramerlabs.molecular.molecule;

import com.bramerlabs.molecular.molecule.components.atom.Atom;
import com.bramerlabs.molecular.molecule.components.bond.Bond;

import java.util.HashMap;

public class Molecule {

    public HashMap<Integer, Atom> atoms;
    public HashMap<Integer, Bond> bonds;

    public Molecule(HashMap<Integer, Atom> atoms, HashMap<Integer, Bond> bonds) {
        this.atoms = atoms;
        this.bonds = bonds;
    }

    public void toggleSelection(int ID) {
        Atom atom = atoms.get(ID);
        if (atom != null) {
            atom.toggleSelected();
        }
        Bond bond = bonds.get(ID);
        if (bond != null) {
            bond.toggleSelected();
        }
    }

    public HashMap<Integer, Atom> getAtoms() {
        return this.atoms;
    }

    public HashMap<Integer, Bond> getBonds() {
        return this.bonds;
    }

    public void add(int ID, Atom atom) {
        this.atoms.put(ID, atom);
    }

    public void add(int ID, Bond bond) {
        this.bonds.put(ID, bond);
    }

    public void remove(int ID) {
        this.atoms.remove(ID);
        this.bonds.remove(ID);
    }

    public void destroy() {
        for (Bond bond : bonds.values()) {
            bond.destroy();
        }
    }
}
