package com.bramerlabs.molecular.chem_components;

import java.util.ArrayList;

public class Molecule extends AtomContainer {

    private String autonomName;
    private String title;
    private String casRN;
    private String beilsteinRN;

    public Molecule(ArrayList<Atom> atoms, ArrayList<Bond> bonds) {
        super(atoms, bonds);
    }

    public Molecule() {
        super();
    }

    public String getAutonomName() {
        return autonomName;
    }

    public void setAutonomName(String autonomName) {
        this.autonomName = autonomName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCasRN() {
        return casRN;
    }

    public void setCasRN(String casRN) {
        this.casRN = casRN;
    }

    public String getBeilsteinRN() {
        return beilsteinRN;
    }

    public void setBeilsteinRN(String beilsteinRN) {
        this.beilsteinRN = beilsteinRN;
    }
}
