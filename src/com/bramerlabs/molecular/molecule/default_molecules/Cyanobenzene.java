package com.bramerlabs.molecular.molecule.default_molecules;

import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.components.atom.Atom;
import com.bramerlabs.molecular.molecule.components.bond.Bond;

import java.util.ArrayList;
import java.util.HashMap;

public class Cyanobenzene extends Molecule {

    private final static Vector3f[] positions = {
            Vector3f.fromAngle(0),
            Vector3f.fromAngle(60),
            Vector3f.fromAngle(120),
            Vector3f.fromAngle(180),
            Vector3f.fromAngle(240),
            Vector3f.fromAngle(300)
    };

    public Cyanobenzene() {
        super(new HashMap<>(), new HashMap<>());

        float CC_1 = Bond.DataCompiler.getAdjustedLength(Atom.C, Atom.C, 1);
        float CC_2 = Bond.DataCompiler.getAdjustedLength(Atom.C, Atom.C, 2);
        float CH_1 = Bond.DataCompiler.getAdjustedLength(Atom.C, Atom.H, 1);
        float CN_3 = Bond.DataCompiler.getAdjustedLength(Atom.C, Atom.N, 3);

        ArrayList<Atom> atoms = new ArrayList<>();
        ArrayList<Bond> bonds = new ArrayList<>();

        // carbons
        atoms.add(new Atom(Vector3f.normalize(positions[0], CC_1), new Atom.Data(Atom.C, 0)));
        atoms.add(new Atom(Vector3f.normalize(positions[1], CC_2), new Atom.Data(Atom.C, 0)));
        atoms.add(new Atom(Vector3f.normalize(positions[2], CC_1), new Atom.Data(Atom.C, 0)));
        atoms.add(new Atom(Vector3f.normalize(positions[3], CC_2), new Atom.Data(Atom.C, 0)));
        atoms.add(new Atom(Vector3f.normalize(positions[4], CC_1), new Atom.Data(Atom.C, 0)));
        atoms.add(new Atom(Vector3f.normalize(positions[5], CC_2), new Atom.Data(Atom.C, 0)));

        bonds.add(new Bond(atoms.get(0).position, atoms.get(1).position, 1));
        bonds.add(new Bond(atoms.get(1).position, atoms.get(2).position, 2));
        bonds.add(new Bond(atoms.get(2).position, atoms.get(3).position, 1));
        bonds.add(new Bond(atoms.get(3).position, atoms.get(4).position, 2));
        bonds.add(new Bond(atoms.get(4).position, atoms.get(5).position, 1));
        bonds.add(new Bond(atoms.get(5).position, atoms.get(0).position, 2));

        // hydrogens
        atoms.add(new Atom(Vector3f.normalize(positions[1], CC_2 + CH_1), new Atom.Data(Atom.H, 0)));
        atoms.add(new Atom(Vector3f.normalize(positions[2], CC_1 + CH_1), new Atom.Data(Atom.H, 0)));
        atoms.add(new Atom(Vector3f.normalize(positions[3], CC_2 + CH_1), new Atom.Data(Atom.H, 0)));
        atoms.add(new Atom(Vector3f.normalize(positions[4], CC_1 + CH_1), new Atom.Data(Atom.H, 0)));
        atoms.add(new Atom(Vector3f.normalize(positions[5], CC_2 + CH_1), new Atom.Data(Atom.H, 0)));

        bonds.add(new Bond(atoms.get(1).position, atoms.get(6).position, 1));
        bonds.add(new Bond(atoms.get(2).position, atoms.get(7).position, 1));
        bonds.add(new Bond(atoms.get(3).position, atoms.get(8).position, 1));
        bonds.add(new Bond(atoms.get(4).position, atoms.get(9).position, 1));
        bonds.add(new Bond(atoms.get(5).position, atoms.get(10).position, 1));

        // cyanide group
        atoms.add(new Atom(Vector3f.normalize(positions[0], CC_1 + CC_1), new Atom.Data(Atom.C, 0)));
        atoms.add(new Atom(Vector3f.normalize(positions[0], CC_1 + CC_1 + CN_3), new Atom.Data(Atom.N, 0)));

        bonds.add(new Bond(atoms.get(0).position, atoms.get(11).position, 1));
        bonds.add(new Bond(atoms.get(11).position, atoms.get(12).position, 3));

        for (Atom atom : atoms) {
            this.add(atom);
        }

        for (Bond bond : bonds) {
            this.add(bond);
        }

    }

}
