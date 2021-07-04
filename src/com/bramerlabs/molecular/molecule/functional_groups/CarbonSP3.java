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
        // populate the atoms
        atoms = new Atom[5];
        atoms[0] = new Carbon(new Vector3f(0, 0, 0));
        atoms[1] = new Hydrogen(Vector3f.normalize(HYDROGEN_POSITIONS[0], Bond.DEFAULT_BOND_LENGTH));
        atoms[2] = new Hydrogen(Vector3f.normalize(HYDROGEN_POSITIONS[1], Bond.DEFAULT_BOND_LENGTH));
        atoms[3] = new Hydrogen(Vector3f.normalize(HYDROGEN_POSITIONS[2], Bond.DEFAULT_BOND_LENGTH));
        atoms[4] = new Hydrogen(Vector3f.normalize(HYDROGEN_POSITIONS[3], Bond.DEFAULT_BOND_LENGTH));

        // populate the bonds
        bonds = new Bond[4];
        bonds[0] = new Bond(atoms[0], atoms[1]);
        bonds[1] = new Bond(atoms[0], atoms[2]);
        bonds[2] = new Bond(atoms[0], atoms[3]);
        bonds[3] = new Bond(atoms[0], atoms[4]);
    }

    public void populate(Bond bond) {
        atoms = new Atom[4];
        atoms[0] = new Carbon(new Vector3f(0, 0, 0));
        atoms[1] = new Hydrogen(Vector3f.normalize(HYDROGEN_POSITIONS[0], Bond.DEFAULT_BOND_LENGTH));
        atoms[2] = new Hydrogen(Vector3f.normalize(HYDROGEN_POSITIONS[1], Bond.DEFAULT_BOND_LENGTH));
        atoms[3] = new Hydrogen(Vector3f.normalize(HYDROGEN_POSITIONS[2], Bond.DEFAULT_BOND_LENGTH));

        int position = 2;

        // alignment rotation
        Vector3f normal = Vector3f.subtract(bond.getvLeft(), bond.getvRight());
        Vector3f binorm = HYDROGEN_POSITIONS[3];
        if (Vector3f.normalize(normal).equals(Vector3f.subtract(new Vector3f(0, 0, 0), Vector3f.normalize(binorm)))) {
            binorm = HYDROGEN_POSITIONS[2];
            position = 3;
            atoms[3] = new Hydrogen(Vector3f.normalize(HYDROGEN_POSITIONS[3], Bond.DEFAULT_BOND_LENGTH));
        }
        Vector3f rotationAxis = Vector3f.normalize(Vector3f.cross(binorm, normal));
        float rotationAngle = (float) Math.toDegrees(Math.acos(Vector3f.dot(binorm, normal)
                * Vector3f.quickInverseSqrt(normal) * Vector3f.quickInverseSqrt(binorm)));

        Vector3f v1 = Vector3f.rotate(atoms[1].getPosition(), rotationAngle, rotationAxis);
        Vector3f v2 = Vector3f.rotate(atoms[2].getPosition(), rotationAngle, rotationAxis);
        Vector3f v3 = Vector3f.rotate(atoms[3].getPosition(), rotationAngle, rotationAxis);

//        // normalize rotation
//        binorm = Vector3f.rotate(HYDROGEN_POSITIONS[position], rotationAngle, rotationAxis);
//        Vector3f bitan = new Vector3f(0, 1, 0);
//        normal = Vector3f.subtract(normal, Vector3f.normalize(bitan, Vector3f.dot(normal,  bitan) * 2));
//        rotationAxis = Vector3f.normalize(Vector3f.cross(binorm, normal));
//        rotationAngle = (float) Math.toDegrees(Math.acos(Vector3f.dot(binorm, normal)
//                * Vector3f.quickInverseSqrt(normal) * Vector3f.quickInverseSqrt(binorm)));
//
//        v1 = Vector3f.rotate(v1, rotationAngle, rotationAxis);
//        v2 = Vector3f.rotate(v2, rotationAngle, rotationAxis);
//        v3 = Vector3f.rotate(v3, rotationAngle, rotationAxis);

        // translation
        Vector3f v0 = bond.getEmptyPosition();
        v1 = Vector3f.add(v1, v0);
        v2 = Vector3f.add(v2, v0);
        v3 = Vector3f.add(v3, v0);

        atoms[0].moveTo(v0);
        atoms[1].moveTo(v1);
        atoms[2].moveTo(v2);
        atoms[3].moveTo(v3);

        bonds = new Bond[3];
        bonds[0] = new Bond(atoms[0], atoms[1]);
        bonds[1] = new Bond(atoms[0], atoms[2]);
        bonds[2] = new Bond(atoms[0], atoms[3]);

        bond.setEmptyAtom(atoms[0]);
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
