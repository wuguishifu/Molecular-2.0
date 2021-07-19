package com.bramerlabs.molecular.main.molecule.default_molecules;

import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.molecular.main.molecule.Molecule;
import com.bramerlabs.molecular.main.molecule.bond.Bond;
import com.bramerlabs.molecular.main.molecule.node.Node;
import com.bramerlabs.molecular.main.molecule.node.atom.default_atoms.Carbon;

public class Benzene extends Molecule {

    public Benzene() {
        super();
        this.generateAtoms();
    }

    public void generateAtoms() {
        float angle = (float) Math.toRadians(60);
        for (int i = 0; i < 6; i++) {
            Vector2f planePosition = new Vector2f((float) Math.cos(angle * i), (float) Math.sin(angle * i));
            Vector3f atomPosition = Vector3f.normalize(new Vector3f(planePosition.x, 0, planePosition.y), 2);
            this.add(new Node(atomPosition, new Carbon()));
        }
        this.add(new Bond(nodes.get(0), nodes.get(1)));
        this.add(new Bond(nodes.get(1), nodes.get(2)));
        this.add(new Bond(nodes.get(2), nodes.get(3)));
        this.add(new Bond(nodes.get(3), nodes.get(4)));
        this.add(new Bond(nodes.get(4), nodes.get(5)));
        this.add(new Bond(nodes.get(5), nodes.get(0)));
    }

}
