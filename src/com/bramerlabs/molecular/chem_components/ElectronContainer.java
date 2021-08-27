package com.bramerlabs.molecular.chem_components;

public class ElectronContainer extends ChemObject {

    private int electronCount;

    public ElectronContainer(int electronCount) {
        super(new Property[]{
                new Property("electronCount", String.valueOf(electronCount))
        });
        this.electronCount = electronCount;
    }

    public int getElectronCount() {
        return electronCount;
    }

    public void setElectronCount(int electronCount) {
        this.electronCount = electronCount;
    }
}
