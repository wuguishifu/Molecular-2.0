package com.bramerlabs.molecular.chem_components;

import java.util.ArrayList;

public class Polymer extends Molecule {

    private final ArrayList<Monomer> monomers;

    public Polymer() {
        super();
        monomers = new ArrayList<>();
    }

    public Polymer(ArrayList<Monomer> monomers) {
        super();
        this.monomers = monomers;
    }

    public int getMonomerCount() {
        return monomers.size();
    }

    public Monomer getMonomer(int index) {
        return this.monomers.get(index);
    }
}
