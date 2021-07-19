package com.bramerlabs.molecular.main.molecule;

import com.bramerlabs.molecular.main.molecule.bond.Bond;
import com.bramerlabs.molecular.main.molecule.node.Node;

import java.util.ArrayList;

public class Molecule {

    protected ArrayList<Node> nodes;
    protected ArrayList<Bond> bonds;

    public Molecule() {
        this.nodes = new ArrayList<>();
        this.bonds = new ArrayList<>();
    }

    public void add(Node node) {
        this.nodes.add(node);
    }

    public void add(Bond bond) {
        this.bonds.add(bond);
    }

    public ArrayList<Node> getNodes() {
        return this.nodes;
    }

    public void removeNode(Node node) {
        this.nodes.remove(node);
    }

    public ArrayList<Bond> getBonds() {
        return this.bonds;
    }

    public void removeBond(Bond bond) {
        this.bonds.remove(bond);
    }
}