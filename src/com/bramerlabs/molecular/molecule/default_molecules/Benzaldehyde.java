package com.bramerlabs.molecular.molecule.default_molecules;

import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.atom.common.Carbon;
import com.bramerlabs.molecular.molecule.atom.common.Hydrogen;
import com.bramerlabs.molecular.molecule.atom.common.Oxygen;
import com.bramerlabs.molecular.molecule.bond.Bond;

public class Benzaldehyde extends Benzene {

    public Benzaldehyde(Vector3f position) {
        super(position);

        // add constituent and remove extra hydrogen
        Atom subHydrogen = this.getAtoms().get(9);
        Atom subCarbon = this.getAtoms().get(3);
        for (Bond bond : subHydrogen.getConnectedBonds()) {
            this.removeBond(bond);
        }
        removeAtom(subHydrogen);

        Atom c1 = new Carbon(Vector3f.add(Vector3f.normalize(new Vector3f(0, 0, -1), CCBond), subCarbon.getPosition()));
        Atom o1 = new Oxygen(Vector3f.add(Vector3f.normalize(new Vector3f(-0.866025f, 0, -0.5f), COBond), c1.getPosition()));
        Atom h1 = new Hydrogen(Vector3f.add(Vector3f.normalize(new Vector3f(0.866025f, 0, -0.5f), CHBond), c1.getPosition()));

        this.addAtom(c1);
        this.addAtom(o1);
        this.addAtom(h1);

        this.addBond(new Bond(c1, o1, Bond.DOUBLE));
        this.addBond(new Bond(c1, h1, Bond.SINGLE));
        this.addBond(new Bond(subCarbon, c1, Bond.SINGLE));

    }

}
