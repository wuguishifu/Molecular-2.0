package com.bramerlabs.molecular.molecule.default_molecules;

import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.atom.AtomicData;
import com.bramerlabs.molecular.molecule.atom.common.Carbon;
import com.bramerlabs.molecular.molecule.atom.common.Hydrogen;
import com.bramerlabs.molecular.molecule.bond.Bond;

import java.util.ArrayList;

public class Benzene extends Molecule {

    private final static float[][] positions = new float[][]{
            {0, 0, 1}, {-0.866025f, 0, 0.5f}, {-0.866025f, 0, -0.5f},
            {0, 0, -1}, {0.866025f, 0, -0.5f}, {0.866025f, 0, 0.5f}
    };

    // C-H
    protected static float CHBond = AtomicData.getVDWRadius(AtomicData.CARBON) +
            AtomicData.getVDWRadius(AtomicData.HYDROGEN) +
            AtomicData.getCovalentBondLength(AtomicData.CARBON, AtomicData.HYDROGEN, 1);

    // C-C
    protected static float CCBond = AtomicData.getVDWRadius(AtomicData.CARBON) +
            AtomicData.getVDWRadius(AtomicData.CARBON) +
            AtomicData.getCovalentBondLength(AtomicData.CARBON, AtomicData.CARBON, 1);

    // C=O
    protected static float COBond = AtomicData.getVDWRadius(AtomicData.OXYGEN) +
            AtomicData.getVDWRadius(AtomicData.CARBON) +
            AtomicData.getCovalentBondLength(AtomicData.CARBON, AtomicData.OXYGEN, 2);

    public Benzene(Vector3f position) {
        super(position, new ArrayList<>(), new ArrayList<>());

        // carbon atoms
        for (float[] p : positions) {
            this.addAtom(new Carbon(Vector3f.add(Vector3f.normalize(new Vector3f(p), CCBond), position)));
        }
        this.addBond(new Bond(this.getAtoms().get(1), this.getAtoms().get(0), Bond.SINGLE)); // 0
        this.addBond(new Bond(this.getAtoms().get(2), this.getAtoms().get(1), Bond.DOUBLE)); // 1
        this.addBond(new Bond(this.getAtoms().get(3), this.getAtoms().get(2), Bond.SINGLE)); // 2
        this.addBond(new Bond(this.getAtoms().get(4), this.getAtoms().get(3), Bond.DOUBLE)); // 3
        this.addBond(new Bond(this.getAtoms().get(5), this.getAtoms().get(4), Bond.SINGLE)); // 4
        this.addBond(new Bond(this.getAtoms().get(0), this.getAtoms().get(5), Bond.DOUBLE)); // 5
        this.getAtoms().get(1).addBond(this.getBonds().get(0));
        this.getAtoms().get(0).addBond(this.getBonds().get(0));
        this.getAtoms().get(2).addBond(this.getBonds().get(1));
        this.getAtoms().get(1).addBond(this.getBonds().get(1));
        this.getAtoms().get(3).addBond(this.getBonds().get(2));
        this.getAtoms().get(2).addBond(this.getBonds().get(2));
        this.getAtoms().get(4).addBond(this.getBonds().get(3));
        this.getAtoms().get(3).addBond(this.getBonds().get(3));
        this.getAtoms().get(5).addBond(this.getBonds().get(4));
        this.getAtoms().get(4).addBond(this.getBonds().get(4));
        this.getAtoms().get(0).addBond(this.getBonds().get(5));
        this.getAtoms().get(5).addBond(this.getBonds().get(5));

        // hydrogen atoms
        for (float[] p : positions) {
            this.addAtom(new Hydrogen(Vector3f.add(Vector3f.normalize(new Vector3f(p), CCBond + CHBond), position)));
        }
        this.addBond(new Bond(this.getAtoms().get(0), this.getAtoms().get(6), Bond.SINGLE)); // 6
        this.addBond(new Bond(this.getAtoms().get(1), this.getAtoms().get(7), Bond.SINGLE)); // 7
        this.addBond(new Bond(this.getAtoms().get(2), this.getAtoms().get(8), Bond.SINGLE)); // 8
        this.addBond(new Bond(this.getAtoms().get(3), this.getAtoms().get(9), Bond.SINGLE)); // 9
        this.addBond(new Bond(this.getAtoms().get(4), this.getAtoms().get(10), Bond.SINGLE)); // 10
        this.addBond(new Bond(this.getAtoms().get(5), this.getAtoms().get(11), Bond.SINGLE)); // 11
        this.getAtoms().get(0).addBond(this.getBonds().get(6));
        this.getAtoms().get(6).addBond(this.getBonds().get(6));
        this.getAtoms().get(1).addBond(this.getBonds().get(7));
        this.getAtoms().get(7).addBond(this.getBonds().get(7));
        this.getAtoms().get(2).addBond(this.getBonds().get(8));
        this.getAtoms().get(8).addBond(this.getBonds().get(8));
        this.getAtoms().get(3).addBond(this.getBonds().get(9));
        this.getAtoms().get(9).addBond(this.getBonds().get(9));
        this.getAtoms().get(4).addBond(this.getBonds().get(10));
        this.getAtoms().get(10).addBond(this.getBonds().get(10));
        this.getAtoms().get(11).addBond(this.getBonds().get(11));
        this.getAtoms().get(5).addBond(this.getBonds().get(11));

    }

}
