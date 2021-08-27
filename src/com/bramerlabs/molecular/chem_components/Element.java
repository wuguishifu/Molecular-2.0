package com.bramerlabs.molecular.chem_components;

public class Element extends ChemObject {

    private int mass;
    private int number;
    private String symbol;

    public Element(int mass, int number, String symbol) {
        super(new Property[]{
                new Property("mass", String.valueOf(mass)),
                new Property("number", String.valueOf(number)),
                new Property("symbol", symbol)
        });
        this.mass = mass;
        this.number = number;
        this.symbol = symbol;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
