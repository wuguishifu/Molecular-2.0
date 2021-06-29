package com.bramerlabs.molecular.molecule.default_molecules;

import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.atom.AtomicData;
import com.bramerlabs.molecular.molecule.bond.Bond;

import java.util.ArrayList;

public class Benzene extends Molecule {

    private final static float[][] positions = new float[][]{
            {0, 0, 1}, {-0.866025f, 0, 0.5f}, {-0.866025f, 0, -0.5f},
            {0, 0, -1}, {0.866025f, 0, -0.5f}, {0.866025f, 0, 0.5f}
    };

    public Benzene(Vector3f position) {
        super(position, new ArrayList<>(), new ArrayList<>());

        // C-H
        float CHBond = AtomicData.getVDWRadius(AtomicData.CARBON) +
                AtomicData.getVDWRadius(AtomicData.HYDROGEN) +
                AtomicData.getCovalentBondLength(AtomicData.CARBON, AtomicData.HYDROGEN, 1);

        // C-C
        float CCBond = AtomicData.getVDWRadius(AtomicData.CARBON) +
                AtomicData.getVDWRadius(AtomicData.CARBON) +
                AtomicData.getCovalentBondLength(AtomicData.CARBON, AtomicData.CARBON, 1);

        // C=O
        float COBond = AtomicData.getVDWRadius(AtomicData.OXYGEN) +
                AtomicData.getVDWRadius(AtomicData.CARBON) +
                AtomicData.getCovalentBondLength(AtomicData.CARBON, AtomicData.OXYGEN, 2);

        // carbon atoms
        for (float[] p : positions) {
            this.addAtom(new Atom(AtomicData.CARBON,
                    Vector3f.add(Vector3f.normalize(new Vector3f(p), CCBond), position)));
        }
        this.addBond(new Bond(this.getAtoms().get(1), this.getAtoms().get(0), Bond.SINGLE));
        this.addBond(new Bond(this.getAtoms().get(2), this.getAtoms().get(1), Bond.DOUBLE));
        this.addBond(new Bond(this.getAtoms().get(3), this.getAtoms().get(2), Bond.SINGLE));
        this.addBond(new Bond(this.getAtoms().get(4), this.getAtoms().get(3), Bond.DOUBLE));
        this.addBond(new Bond(this.getAtoms().get(5), this.getAtoms().get(4), Bond.SINGLE));
        this.addBond(new Bond(this.getAtoms().get(0), this.getAtoms().get(5), Bond.DOUBLE));

        // hydrogen atoms
        for (float[] p : positions) {
            this.addAtom(new Atom(AtomicData.HYDROGEN,
                    Vector3f.add(Vector3f.normalize(new Vector3f(p), CCBond + CHBond), position)));
        }
        this.addBond(new Bond(this.getAtoms().get(0), this.getAtoms().get(6), 1));
        this.addBond(new Bond(this.getAtoms().get(1), this.getAtoms().get(7), 1));
        this.addBond(new Bond(this.getAtoms().get(2), this.getAtoms().get(8), 1));
        this.addBond(new Bond(this.getAtoms().get(3), this.getAtoms().get(9), 1));
        this.addBond(new Bond(this.getAtoms().get(4), this.getAtoms().get(10), 1));
        this.addBond(new Bond(this.getAtoms().get(5), this.getAtoms().get(11), 1));

    }

}
