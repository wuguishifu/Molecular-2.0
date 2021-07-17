package old.molecule.atom.common;

import com.bramerlabs.engine.math.vector.Vector3f;
import old.molecule.atom.Atom;
import old.molecule.atom.AtomicData;

public class Hydrogen extends Atom {

    // Van der Waals atomic radius of carbon
    public final static float ATOMIC_RADIUS = AtomicData.getVDWRadius(AtomicData.HYDROGEN);

    // atomic number
    private final static int ATOMIC_NUMBER = AtomicData.HYDROGEN;

    public Hydrogen(Vector3f position) {
        super(ATOMIC_NUMBER, position);
    }

}
