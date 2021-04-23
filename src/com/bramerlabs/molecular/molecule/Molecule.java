package com.bramerlabs.molecular.molecule;

import java.util.ArrayList;

public class Molecule {

    private ArrayList<Atom> atoms;
    private ArrayList<Bond> bonds;

    public Molecule(ArrayList<Atom> atoms, ArrayList<Bond> bonds) {
        this.atoms = atoms;
        this.bonds = bonds;
    }

    public Molecule() {
        this.atoms = new ArrayList<>();
        this.bonds = new ArrayList<>();
    }

    public ArrayList<Atom> getAtoms() {
        return this.atoms;
    }

    public void addAtom(Atom atom) {
        this.atoms.add(atom);
    }

    public void removeAtom(Atom atom) {
        this.atoms.remove(atom);
    }

    public ArrayList<Bond> getBonds() {
        return this.bonds;
    }

    public void addBond(Bond bond) {
        this.bonds.add(bond);
    }

    public void removeBond(Bond bond) {
        this.bonds.remove(bond);
    }

}