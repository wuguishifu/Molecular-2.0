package com.bramerlabs.molecular.molecule.groups;

import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.components.atom.Atom;
import com.bramerlabs.molecular.molecule.components.bond.Bond;

import java.util.ArrayList;

public class FunctionalGroup {

    public static final int
    TETRAHEDRAL = 0,
    TRIGONAL = 1,
    BENT = 2;

    public static void addGroup(Molecule molecule, int group, Vector3f position, Vector3f normal, float rotation) {
        switch (group) {
            case TETRAHEDRAL:
                addTetrahedral(molecule, position, normal, rotation);
            case TRIGONAL:
                addTrigonal(molecule, position, normal, rotation);
            case BENT:
                addBent(molecule, position, normal, rotation);
            default:
                break;
        }
    }

    private static void addTetrahedral(Molecule molecule, Vector3f position, Vector3f normal, float rotation) {
        ArrayList<Atom> atoms = new ArrayList<>();
        ArrayList<Bond> bonds = new ArrayList<>();

        Vector3f[] positions = new Vector3f[]{
                Vector3f.normalize(-1, -1, -1),
                Vector3f.normalize( 1,  1, -1),
                Vector3f.normalize( 1, -1,  1),
                Vector3f.normalize(-1,  1,  1)
        };

        Vector3f axis = Vector3f.normalize(Vector3f.cross(positions[0], normal));
        float angle = Vector3f.angleBetween(positions[0], normal);

        float CC_1 = Bond.DataCompiler.getAdjustedLength(Atom.C, Atom.C, 1);
        atoms.add(new Atom(Vector3f.add(Vector3f.normalize(normal, CC_1), position), new Atom.Data(Atom.C, 0)));

        for (int i = 1; i < positions.length; i++) {
            Vector3f p = Vector3f.normalize(positions[i], CC_1);
            p = Vector3f.rotate(p, axis, angle + 180);
            p = Vector3f.rotate(p, Vector3f.normalize(normal), rotation);
            p = Vector3f.add(p, atoms.get(0).position);
            atoms.add(new Atom(p, new Atom.Data(Atom.C, 0)));
        }

        for (int i = 1; i < atoms.size(); i++) {
//            bonds.add(new Bond(atoms.get(0).position, atoms.get(i).position, 1));
        }

        for (Atom atom : atoms) {
            molecule.add(atom);
        }

        for (Bond bond : bonds) {
//            molecule.add(bond);
        }

//        molecule.add(new Bond(new Vector3f(0, 0, 0), atoms.get(0).position, 1));

    }

    private static void addTrigonal(Molecule molecule, Vector3f position, Vector3f normal, float rotation) {

    }

    private static void addBent(Molecule molecule, Vector3f position, Vector3f normal, float rotation) {

    }

}
