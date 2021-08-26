package com.bramerlabs.molecular.molecule.groups;

import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.components.atom.Atom;
import com.bramerlabs.molecular.molecule.components.bond.Bond;

import java.util.ArrayList;

public class FunctionalGroup {

    public static void addGroup(Molecule molecule, Atom connected, Vector3f n, float rotation,
                                Vector3f connection, Vector3f[] bondPositions) {
        Vector3f normal = Vector3f.normalize(n);

        Vector3f axis = Vector3f.normalize(Vector3f.cross(connection, normal));
        float angle = Vector3f.angleBetween(connection, normal);

        float CC_1 = Bond.DataCompiler.getAdjustedLength(Atom.C, Atom.C, 1);
        float CH_1 = Bond.DataCompiler.getAdjustedLength(Atom.C, Atom.H, 1);

        Atom centerAtom = new Atom(Vector3f.add(Vector3f.normalize(normal, CC_1), connected.position),
                new Atom.Data(Atom.C, 0));
        molecule.add(centerAtom);
        molecule.addBond(centerAtom, connected, 1);

        for (Vector3f v : bondPositions) {
            Vector3f p = Vector3f.normalize(v, CH_1);
            p = Vector3f.rotate(p, axis, angle + 180);
            p = Vector3f.rotate(p, normal, rotation);
            p = Vector3f.add(p, centerAtom.position);
            Atom atom = new Atom(p, new Atom.Data(Atom.H, 0));
            molecule.add(atom);
            molecule.addBond(centerAtom, atom, 1);
        }

    }

    public static void createGroup(Molecule molecule, Vector3f n, float rotation,
                                   Vector3f connection, Vector3f[] bondPositions) {
        Vector3f normal = Vector3f.normalize(n);

        Vector3f axis = Vector3f.normalize(Vector3f.cross(connection, normal));
        float angle = Vector3f.angleBetween(connection, normal);

        float CH_1 = Bond.DataCompiler.getAdjustedLength(Atom.C, Atom.H, 1);

        Atom centerAtom = new Atom(new Vector3f(0, 0, 0), new Atom.Data(Atom.C, 0));
        molecule.add(centerAtom);

        for (Vector3f v : bondPositions) {
            Vector3f p = Vector3f.normalize(v, CH_1);
            p = Vector3f.rotate(p, axis, angle);
            p = Vector3f.rotate(p, normal, rotation);
            Atom atom = new Atom(p, new Atom.Data(Atom.H, 0));
            molecule.add(atom);
            molecule.addBond(centerAtom, atom, 1);
        }

        Vector3f p = Vector3f.normalize(connection, CH_1);
        p = Vector3f.rotate(p, axis, angle);
        p = Vector3f.rotate(p, normal, rotation);
        Atom atom = new Atom(p, new Atom.Data(Atom.H, 0));
        molecule.add(atom);
        molecule.addBond(centerAtom, atom, 1);

    }

    public static void createAndReplace(Molecule molecule, Atom connected, Vector3f n, float rotation,
                                        Vector3f connection, Vector3f[] bondPositions) {
        ArrayList<Atom> connectedAtoms = molecule.getConnectedAtoms(connected);
        if (connectedAtoms.size() > 1) {
            System.err.println("Tried to replace a non-single bonded atom!");
            return;
        }

        Vector3f normal = Vector3f.normalize(n);
        Atom connectionAtom = molecule.getConnectedAtoms(connected).get(0);

        Vector3f axis = Vector3f.normalize(Vector3f.cross(connection, normal));
        float angle = Vector3f.angleBetween(connection, normal);

        float CH_1 = Bond.DataCompiler.getAdjustedLength(Atom.C, Atom.H, 1);

        Atom centerAtom = new Atom(connected.position, new Atom.Data(Atom.C, 0));
        molecule.add(centerAtom);
        molecule.addBond(connectionAtom, centerAtom, 1);

        for (Vector3f v : bondPositions) {
            Vector3f p = Vector3f.normalize(v, CH_1);
            p = Vector3f.rotate(p, axis, angle + 180);
            p = Vector3f.rotate(p, normal, rotation);
            p = Vector3f.add(p, centerAtom.position);
            Atom atom = new Atom(p, new Atom.Data(Atom.H, 0));
            molecule.add(atom);
            molecule.addBond(centerAtom, atom, 1);
        }
    }

    public static class Tetrahedral {
        public static final Vector3f connection = Vector3f.normalize(-1, -1, -1);
        public static final Vector3f[] bondPositions = new Vector3f[]{
                Vector3f.normalize(1, 1, -1),
                Vector3f.normalize(1, -1, 1),
                Vector3f.normalize(-1, 1, 1)
        };
    }

    public static class Trigonal {
        public static final Vector3f connection = Vector3f.normalize(Vector3f.fromAngle(0));
        public static final Vector3f[] bondPositions = new Vector3f[]{
                Vector3f.normalize(Vector3f.fromAngle(120)),
                Vector3f.normalize(Vector3f.fromAngle(240))
        };
    }

    public static class Linear {
        public static final Vector3f connection = Vector3f.normalize(Vector3f.fromAngle(180));
        public static final Vector3f[] bondPositions = new Vector3f[]{
                Vector3f.fromAngle(0)
        };
    }

    public static class Bent {
        public static final Vector3f connection = Vector3f.normalize(Vector3f.fromAngle(180));
        public static final Vector3f[] bondPositions = new Vector3f[]{
                Vector3f.fromAngle(60)
        };
    }

}