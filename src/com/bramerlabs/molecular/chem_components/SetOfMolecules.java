package com.bramerlabs.molecular.chem_components;

import java.util.ArrayList;
import java.util.Arrays;

public class SetOfMolecules extends ChemObject {

    private final ArrayList<Molecule> molecules;

    public SetOfMolecules(ArrayList<Molecule> molecules, ArrayList<Property> properties) {
        super(properties);
        this.molecules = molecules;
    }

    public SetOfMolecules(Molecule[] molecules, Property[] properties) {
        super(properties);
        this.molecules = (ArrayList<Molecule>) Arrays.asList(molecules);
    }

    public ArrayList<Molecule> getMolecules() {
        return this.molecules;
    }

    public Molecule getMolecule(int index) {
        return this.molecules.get(index);
    }

    public void addMolecule(Molecule molecule) {
        this.molecules.add(molecule);
    }

    public boolean removeMolecule(Molecule molecule) {
        return this.molecules.remove(molecule);
    }

    public int getMoleculeCount() {
        return this.molecules.size();
    }

}
