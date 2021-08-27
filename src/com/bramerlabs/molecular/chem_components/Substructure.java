package com.bramerlabs.molecular.chem_components;

public class Substructure extends AtomContainer {

    private String subStructureName;

    public Substructure(String subStructureName) {
        super();
        this.subStructureName = subStructureName;
    }

    public Substructure() {
        super();
    }

    public String getSubStructureName() {
        return subStructureName;
    }

    public void setSubStructureName(String subStructureName) {
        this.subStructureName = subStructureName;
    }
}
