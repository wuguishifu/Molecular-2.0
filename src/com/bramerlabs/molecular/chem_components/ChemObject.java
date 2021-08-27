package com.bramerlabs.molecular.chem_components;

import java.util.ArrayList;
import java.util.Arrays;

public class ChemObject {

    private static int curID;
    public int ID;

    private final ArrayList<Property> properties;
    private ChemObjectListener listener;

    public ChemObject(ArrayList<Property> properties) {
        this.properties = properties;

        this.ID = curID;
        curID++;
    }

    public ChemObject(Property[] properties) {
        this.properties = new ArrayList<>();
        this.properties.addAll(Arrays.asList(properties));

        this.ID = curID;
        curID++;
    }

    public void setProperty(Property property) {
        this.properties.add(property);
    }

    public String getProperty(String description) {
        for (Property property : properties) {
            if (description.equals(property.description)) {
                return property.property;
            }
        }
        return null;
    }

    public void addListener(ChemObjectListener listener) {
        this.listener = listener;
    }

    public ChemObjectListener getListener() {
        return this.listener;
    }

    public ChemObjectListener removeListener() {
        ChemObjectListener listener = this.listener;
        this.listener = null;
        return listener;
    }

    public static class Property {
        public String description;
        public String property;
        public Property(String description, String property) {
            this.description = description;
            this.property = property;
        }
    }

}
