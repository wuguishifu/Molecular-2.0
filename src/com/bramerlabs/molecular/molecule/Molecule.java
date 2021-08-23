package com.bramerlabs.molecular.molecule;

import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.bond.Bond;

import java.util.ArrayList;

public class Molecule {

    public ArrayList<Atom> atoms;
    public ArrayList<Bond> bonds;

    public static int currentID = 0;

    public Molecule(ArrayList<Atom> atoms, ArrayList<Bond> bonds) {
        this.atoms = atoms;
        this.bonds = bonds;
    }

    public ArrayList<Atom> getAtoms() {
        return this.atoms;
    }

    public ArrayList<Bond> getBonds() {
        return this.bonds;
    }

    public void add(Atom atom) {
        this.atoms.add(atom);
    }

    public void add(Bond bond) {
        this.bonds.add(bond);
    }

    public void remove(Atom atom) {
        this.atoms.remove(atom);
    }

    public void remove(Bond bond) {
        this.bonds.remove(bond);
    }

    public void destroy() {
        for (Bond bond : bonds) {
            bond.destroy();
        }
    }

}
