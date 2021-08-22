package com.bramerlabs.molecular.molecule.default_molecules;

import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;

import java.util.ArrayList;

public class Benzene extends Molecule {

    private final static Vector3f[] positions = {
            Vector3f.fromAngle(0),
            Vector3f.fromAngle(60),
            Vector3f.fromAngle(120),
            Vector3f.fromAngle(180),
            Vector3f.fromAngle(240),
            Vector3f.fromAngle(300)
    };

    public Benzene() {
        super(new ArrayList<>(), new ArrayList<>());

        for (Vector3f p : positions) {
            this.add(new Atom(Vector3f.normalize(p, 2), new Atom.Data(6, 0)));
        }

    }

}
