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

        // carbons
        atoms.add(new Atom(Vector3f.normalize(positions[0], CC_1), new Atom.Data(Atom.C, 0)));
        atoms.add(new Atom(Vector3f.normalize(positions[1], CC_2), new Atom.Data(Atom.C, 0)));
        atoms.add(new Atom(Vector3f.normalize(positions[2], CC_1), new Atom.Data(Atom.C, 0)));
        atoms.add(new Atom(Vector3f.normalize(positions[3], CC_2), new Atom.Data(Atom.C, 0)));
        atoms.add(new Atom(Vector3f.normalize(positions[4], CC_1), new Atom.Data(Atom.C, 0)));
        atoms.add(new Atom(Vector3f.normalize(positions[5], CC_2), new Atom.Data(Atom.C, 0)));

        this.addBond(atoms.get(0), atoms.get(1), 1);
        this.addBond(atoms.get(1), atoms.get(2), 2);
        this.addBond(atoms.get(2), atoms.get(3), 1);
        this.addBond(atoms.get(3), atoms.get(4), 2);
        this.addBond(atoms.get(4), atoms.get(5), 1);
        this.addBond(atoms.get(5), atoms.get(0), 2);

        // hydrogens
        atoms.add(new Atom(Vector3f.normalize(positions[1], CC_2 + CH_1), new Atom.Data(Atom.H, 0)));
        atoms.add(new Atom(Vector3f.normalize(positions[2], CC_1 + CH_1), new Atom.Data(Atom.H, 0)));
        atoms.add(new Atom(Vector3f.normalize(positions[3], CC_2 + CH_1), new Atom.Data(Atom.H, 0)));
        atoms.add(new Atom(Vector3f.normalize(positions[4], CC_1 + CH_1), new Atom.Data(Atom.H, 0)));
        atoms.add(new Atom(Vector3f.normalize(positions[5], CC_2 + CH_1), new Atom.Data(Atom.H, 0)));

        this.addBond(atoms.get(1), atoms.get(6), 1);
        this.addBond(atoms.get(2), atoms.get(7), 1);
        this.addBond(atoms.get(3), atoms.get(8), 1);
        this.addBond(atoms.get(4), atoms.get(9), 1);
        this.addBond(atoms.get(5), atoms.get(10), 1);

        // cyanide group
        atoms.add(new Atom(Vector3f.normalize(positions[0], CC_1 + CC_1), new Atom.Data(Atom.C, 0)));
        atoms.add(new Atom(Vector3f.normalize(positions[0], CC_1 + CC_1 + CN_3), new Atom.Data(Atom.N, 0)));

        this.addBond(atoms.get(0), atoms.get(11), 1);
        this.addBond(atoms.get(11), atoms.get(12), 3);

        for (Atom atom : atoms) {
            this.add(atom);
        }
    }

}
