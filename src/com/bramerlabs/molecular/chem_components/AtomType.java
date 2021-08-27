package com.bramerlabs.molecular.chem_components;

public class AtomType extends Isotope {

    float maxOrder;

    public AtomType(float maxOrder, float naturalAbundance, float exactMass, int mass, int number, String symbol) {
        super(naturalAbundance, exactMass, mass, number, symbol);
        this.maxOrder = maxOrder;

        this.setProperty(new Property("maxOrder", String.valueOf(maxOrder)));
    }

    public AtomType(int mass, int number, String symbol) {
        super(mass, number, symbol);
    }

    public float getMaxOrder() {
        return maxOrder;
    }

    public void setMaxOrder(float maxOrder) {
        this.maxOrder = maxOrder;
    }
}
