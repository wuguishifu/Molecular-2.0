package com.bramerlabs.molecular.molecule.groups.inorganic;

import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.molecule.components.atom.Atom;
import com.bramerlabs.molecular.molecule.components.bond.Bond;
import com.bramerlabs.molecular.molecule.groups.FunctionalGroup;

public class Tetrahedral extends FunctionalGroup {

    protected final static Vector3f[] atomPositions = new Vector3f[]{
            new Vector3f(-1, -1, -1),
            new Vector3f(1, -1, 1),
            new Vector3f(1, 1, -1),
            new Vector3f(-1, 1, 1)
    };

    public Tetrahedral() {
        super(atomPositions);
    }

    public Tetrahedral(Vector3f position, Vector3f normal) {
        super(atomPositions, position, normal);
    }

    @Override
    public void generateAtoms() {
        atoms.add(new Atom(new Vector3f(0, 0, 0), new Atom.Data(Atom.C, 0)));
        for (int i = 0; i < atomPositions.length; i++) {
            atoms.add(new Atom(Vector3f.scale(atomPositions[i], 3), new Atom.Data(Atom.C, 0)));
        }
    }

    @Override
    public void generateBonds() {
        for (int i = 1; i < atoms.size(); i++) {
            bonds.add(new Bond(atoms.get(0).position, atoms.get(i).position, 1));
        }
    }
}
