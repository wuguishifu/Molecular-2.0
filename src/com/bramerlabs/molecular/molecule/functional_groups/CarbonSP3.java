package com.bramerlabs.molecular.molecule.functional_groups;

import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.atom.common.Carbon;
import com.bramerlabs.molecular.molecule.bond.Bond;

public class CarbonSP3 extends FunctionalGroup {

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
        bonds[0] = new Bond(atoms[0], Vector3f.normalize(HYDROGEN_POSITIONS[0], Bond.DEFAULT_BOND_LENGTH), 1);
        bonds[1] = new Bond(atoms[0], Vector3f.normalize(HYDROGEN_POSITIONS[1], Bond.DEFAULT_BOND_LENGTH), 1);
        bonds[2] = new Bond(atoms[0], Vector3f.normalize(HYDROGEN_POSITIONS[2], Bond.DEFAULT_BOND_LENGTH), 1);
        bonds[3] = new Bond(atoms[0], Vector3f.normalize(HYDROGEN_POSITIONS[3], Bond.DEFAULT_BOND_LENGTH), 1);
    }

    public void populate_(Bond bond) {
        Vector3f emptyPos = bond.getEmptyPosition();
        Vector3f atomPos = bond.getLeft() != null ? bond.getLeft().getPosition() : bond.getRight().getPosition();
        Vector3f bondNormal = Vector3f.normalize(Vector3f.subtract(atomPos, emptyPos));
        Vector3f carbonPos = bond.getEmptyPosition();
        atoms = new Atom[1];
        atoms[0] = new Carbon(carbonPos);

        Vector3f[] bondPositions = new Vector3f[3];
        System.arraycopy(HYDROGEN_POSITIONS, 1, bondPositions, 0, 3);
        float angle = Vector3f.angleBetween(HYDROGEN_POSITIONS[0], bondNormal);
        Vector3f axis = Vector3f.cross(HYDROGEN_POSITIONS[0], bondNormal);

        for (Vector3f v : bondPositions) {
            v.rotate(angle, axis);
        }

        bonds = new Bond[3];
        for (int i = 0; i < 3; i++) {
            Vector3f bondPosition = Vector3f.add(emptyPos, Vector3f.normalize(bondPositions[i], Bond.DEFAULT_BOND_LENGTH));
            bonds[i] = new Bond(atoms[0], bondPosition, 1);
            bonds[i].recalculateModelComponents();
        }
    }

    public void populate(Bond bond) {
        Vector3f emptyPos = bond.getEmptyPosition();
        Vector3f atomPos = bond.getLeft() != null ? bond.getLeft().getPosition() : bond.getRight().getPosition();
        Vector3f bondNormal = Vector3f.normalize(Vector3f.subtract(atomPos, emptyPos));
    }

    public void update() {
        Vector3f rotationAxis = Vector3f.normalize(Vector3f.subtract(connectedTo.getvLeft(), connectedTo.getvRight()));
        float rotationAngle = 0.3f;

        Vector3f v0 = bonds[0].getEmptyPosition();
        Vector3f v1 = bonds[1].getEmptyPosition();
        Vector3f v2 = bonds[2].getEmptyPosition();

        v0 = Vector3f.rotate(v0, rotationAngle, rotationAxis);
        v1 = Vector3f.rotate(v1, rotationAngle, rotationAxis);
        v2 = Vector3f.rotate(v2, rotationAngle, rotationAxis);

        v0 = Vector3f.add(v0, atoms[0].getPosition());
        v1 = Vector3f.add(v1, atoms[0].getPosition());
        v2 = Vector3f.add(v2, atoms[0].getPosition());

        bonds[0].setEmptyPos(v0);
        bonds[1].setEmptyPos(v1);
        bonds[2].setEmptyPos(v2);

        for (Bond bond : bonds) {
            bond.recalculateModelComponents();
        }

    }
}
