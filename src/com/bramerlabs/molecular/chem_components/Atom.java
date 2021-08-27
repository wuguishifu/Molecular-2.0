package com.bramerlabs.molecular.chem_components;

import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;

public class Atom extends AtomType {

    private float charge;
    private int hydrogenCount;
    private Vector3f position;

    public Atom(float charge, int hydrogenCount, Vector3f position, float maxOrder, float naturalAbundance,
                float exactMass, int mass, int number, String symbol) {
        super(maxOrder, naturalAbundance, exactMass, mass, number, symbol);
        this.charge = charge;
        this.hydrogenCount = hydrogenCount;
        this.position = position;

        this.setProperty(new Property("charge", String.valueOf(charge)));
        this.setProperty(new Property("hydrogenCount", String.valueOf(hydrogenCount)));
        this.setProperty(new Property("position", String.valueOf(position)));
    }

    public Atom(int mass, int number, String symbol) {
        super(mass, number, symbol);
    }

    public float getCharge() {
        return charge;
    }

    public void setCharge(float charge) {
        this.charge = charge;
    }

    public int getHydrogenCount() {
        return hydrogenCount;
    }

    public void setHydrogenCount(int hydrogenCount) {
        this.hydrogenCount = hydrogenCount;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }
}
