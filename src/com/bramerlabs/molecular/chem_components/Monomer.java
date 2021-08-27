package com.bramerlabs.molecular.chem_components;

public class Monomer extends Substructure {

    private String monomerType;

    public Monomer(String subStructureName) {
        super(subStructureName);
    }

    public Monomer() {
        super();
    }

    public String getMonomerType() {
        return monomerType;
    }

    public void setMonomerType(String monomerType) {
        this.monomerType = monomerType;
    }
}
