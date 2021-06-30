package com.bramerlabs.molecular.molecule.functional_groups;

import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.bond.Bond;

public abstract class FunctionalGroup {

    protected Atom[] atoms;
    protected Bond[] bonds;

    protected Bond connectedTo;

    public FunctionalGroup() {
        populate();
    }

    public FunctionalGroup(Bond bond) {
        this.connectedTo = bond;
        populate(bond);
    }

    public abstract void populate();
    public abstract void populate(Bond bond);

    public Atom[] getAtoms() {
        return atoms;
    }

    public Bond[] getBonds() {
        return bonds;
    }

    public abstract void update();
}
