package com.bramerlabs.molecular.molecule.groups;

import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.components.atom.Atom;

public abstract class FunctionalGroup {

    public static final int
    TETRAHEDRAL = 0;

    public static void addGroup(Molecule molecule, int type, Atom connected, Vector3f normal, float rotation) {
        switch (type) {
            case TETRAHEDRAL:
                Tetrahedral.addGroup(molecule, connected, normal, rotation);
                break;
        }
    }

    public static void createGroup(Molecule molecule, int type, Vector3f normal, float rotation) {
        switch (type) {
            case TETRAHEDRAL:
                Tetrahedral.createGroup(molecule, normal, rotation);
                break;
        }
    }

    public static void createAndReplace(Molecule molecule, int type, Atom connected, Vector3f normal, float rotation) {
        switch (type) {
            case TETRAHEDRAL:
                Tetrahedral.createAndReplace(molecule, connected, normal, rotation);
                break;
        }
    }

}
