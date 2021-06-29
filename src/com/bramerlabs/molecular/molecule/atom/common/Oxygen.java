package com.bramerlabs.molecular.molecule.atom.common;

import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.atom.AtomicData;

public class Oxygen extends Atom {

    // Van der Waals atomic radius of carbon
    public final static float ATOMIC_RADIUS = AtomicData.getVDWRadius(AtomicData.OXYGEN);

    // atomic number
    private final static int ATOMIC_NUMBER = AtomicData.OXYGEN;

    public Oxygen(Vector3f position) {
        super(ATOMIC_NUMBER, position);
    }

}
