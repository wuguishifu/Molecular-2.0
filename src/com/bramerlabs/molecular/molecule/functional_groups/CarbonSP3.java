package com.bramerlabs.molecular.molecule.functional_groups;

import com.bramerlabs.engine.math.matrix.Matrix4f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.atom.AtomicData;
import com.bramerlabs.molecular.molecule.atom.common.Carbon;
import com.bramerlabs.molecular.molecule.atom.common.Hydrogen;
import com.bramerlabs.molecular.molecule.bond.Bond;

public class CarbonSP3 extends FunctionalGroup {

    private static final float R2O3 = 0.866025f;
    private static final Vector3f[] HYDROGEN_POSITIONS = new Vector3f[]{
            new Vector3f(-1, -1, -1),
            new Vector3f(1, 1, -1),
            new Vector3f(1, -1, 1),
            new Vector3f(-1, 1, 1)
    };

    public CarbonSP3() {
        super();
    }

    public CarbonSP3(Bond connected) {
        super(connected);
    }

    @Override
    public void populate() {
        // populate the carbon atom
        atoms = new Atom[1];
        atoms[0] = new Carbon(new Vector3f(0, 0, 0));

        // populate the bonds
        bonds = new Bond[4];
        bonds[0] = new Bond(atoms[0], Vector3f.normalize(HYDROGEN_POSITIONS[0], Bond.DEFAULT_BOND_LENGTH));
        bonds[1] = new Bond(atoms[0], Vector3f.normalize(HYDROGEN_POSITIONS[1], Bond.DEFAULT_BOND_LENGTH));
        bonds[2] = new Bond(atoms[0], Vector3f.normalize(HYDROGEN_POSITIONS[2], Bond.DEFAULT_BOND_LENGTH));
        bonds[3] = new Bond(atoms[0], Vector3f.normalize(HYDROGEN_POSITIONS[3], Bond.DEFAULT_BOND_LENGTH));
    }

    public void populate(Bond bond) {
        Vector3f nonEmptyPosition = bond.getvLeft();
        if (nonEmptyPosition == null) {
            nonEmptyPosition = bond.getvRight();
        }
        Vector3f bondNormal = Vector3f.normalize(Vector3f.subtract(bond.getEmptyPosition(), nonEmptyPosition));

        // populate the carbon atom
        atoms = new Atom[1];
        atoms[0] = new Carbon(bond.getEmptyPosition());
        Vector3f emptyPosition = bond.getEmptyPosition();
        if (emptyPosition == null) {
            return;
        }

        // define the initial bond locations before any normalized rotation
        Vector3f[] bondLocations = {
            Vector3f.normalize(HYDROGEN_POSITIONS[1], Bond.DEFAULT_BOND_LENGTH),
            Vector3f.normalize(HYDROGEN_POSITIONS[2], Bond.DEFAULT_BOND_LENGTH),
            Vector3f.normalize(HYDROGEN_POSITIONS[3], Bond.DEFAULT_BOND_LENGTH)
        };

        // rotate each bond
        float angle = Vector3f.angleBetween(bond.getEmptyPosition(), HYDROGEN_POSITIONS[0]);
        Vector3f axis = Vector3f.cross(bond.getEmptyPosition(), bondNormal);
        for (Vector3f v : bondLocations) {
            v.rotate(angle, axis);
        }

        bonds = new Bond[3];
        for (int i = 0; i < 3; i++) {
            bonds[i] = new Bond(atoms[0], bondLocations[i]);
        }

        for (Bond b : bonds) {
            b.recalculateModelComponents();
        }
    }

    public void update() {
        Vector3f rotationAxis = Vector3f.normalize(Vector3f.subtract(connectedTo.getvLeft(), connectedTo.getvRight()));
        float rotationAngle = 0.3f;

        Vector3f v1 = Vector3f.subtract(atoms[1].getPosition(), atoms[0].getPosition());
        Vector3f v2 = Vector3f.subtract(atoms[2].getPosition(), atoms[0].getPosition());
        Vector3f v3 = Vector3f.subtract(atoms[3].getPosition(), atoms[0].getPosition());

        v1 = Vector3f.rotate(v1, rotationAngle, rotationAxis);
        v2 = Vector3f.rotate(v2, rotationAngle, rotationAxis);
        v3 = Vector3f.rotate(v3, rotationAngle, rotationAxis);

        v1 = Vector3f.add(v1, atoms[0].getPosition());
        v2 = Vector3f.add(v2, atoms[0].getPosition());
        v3 = Vector3f.add(v3, atoms[0].getPosition());

        atoms[1].moveTo(v1);
        atoms[2].moveTo(v2);
        atoms[3].moveTo(v3);

        for (Bond bond : bonds) {
            bond.recalculateModelComponents();
        }

    }
}
