package com.bramerlabs.molecular.chem_components;

import java.util.ArrayList;

public class AtomContainer extends ChemObject {

    private final ArrayList<Atom> atoms;
    private final ArrayList<Bond> bonds;

    public AtomContainer(ArrayList<Atom> atoms, ArrayList<Bond> bonds) {
        super(new Property[]{});
        this.atoms = atoms;
        this.bonds = bonds;
    }

    public AtomContainer() {
        super(new Property[]{});
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

    public int getAtomCount() {
        return this.atoms.size();
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

    public int getBondCount() {
        return this.bonds.size();
    }

    public int getDegree(Atom atom) {
        int degree = 0;
        for (Bond bond : bonds) {
            if (bond.getAtoms()[0].equals(atom) || bond.getAtoms()[1].equals(atom)) {
                degree++;
            }
        }
        return degree;
    }

}
