package com.bramerlabs.molecular.chem_components;

public class Isotope extends Element {

    private float naturalAbundance;
    private float exactMass;

    public Isotope(float naturalAbundance, float exactMass, int mass, int number, String symbol) {
        super(mass, number, symbol);
        this.naturalAbundance = naturalAbundance;
        this.exactMass = exactMass;

        this.setProperty(new Property("naturalAbundance", String.valueOf(naturalAbundance)));
        this.setProperty(new Property("exactMass", String.valueOf(exactMass)));
    }

    public Isotope(int mass, int number, String symbol) {
        super(mass, number, symbol);
    }

    public float getNaturalAbundance() {
        return naturalAbundance;
    }

    public void setNaturalAbundance(float naturalAbundance) {
        this.naturalAbundance = naturalAbundance;
    }

    public float getExactMass() {
        return exactMass;
    }

    public void setExactMass(float exactMass) {
        this.exactMass = exactMass;
    }
}
