package com.bramerlabs.molecular.molecule.components;

public class Component {

    public static int currentID = 0;
    public int ID;
    public boolean selected = false;

    public Component() {
        this.ID = currentID;
        currentID++;
    }

    public void toggleSelected() {
        this.selected = !selected;
    }

}
