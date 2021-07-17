package old.molecule.default_molecules;

import com.bramerlabs.engine.math.vector.Vector3f;
import old.molecule.atom.Atom;
import old.molecule.atom.common.Carbon;
import old.molecule.atom.common.Hydrogen;
import old.molecule.atom.common.Oxygen;
import old.molecule.bond.Bond;

public class Benzaldehyde extends Benzene {

    public Benzaldehyde(Vector3f position) {
        super(position);

        // add constituent and remove extra hydrogen
        Atom subHydrogen = this.getAtoms().get(9);
        Atom subCarbon = this.getAtoms().get(3);
        for (Bond bond : subHydrogen.getConnectedBonds()) {
            this.remove(bond);
        }
        remove(subHydrogen);

        Atom c1 = new Carbon(Vector3f.add(Vector3f.normalize(new Vector3f(0, 0, -1), CCBond), subCarbon.getPosition()));
        Atom o1 = new Oxygen(Vector3f.add(Vector3f.normalize(new Vector3f(-0.866025f, 0, -0.5f), COBond), c1.getPosition()));
        Atom h1 = new Hydrogen(Vector3f.add(Vector3f.normalize(new Vector3f(0.866025f, 0, -0.5f), CHBond), c1.getPosition()));

        this.add(c1);
        this.add(o1);
        this.add(h1);

        this.add(new Bond(c1, o1, Bond.DOUBLE));
        this.add(new Bond(c1, h1, Bond.SINGLE));
        this.add(new Bond(subCarbon, c1, Bond.SINGLE));

    }

}
