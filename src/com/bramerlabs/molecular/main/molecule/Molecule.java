package com.bramerlabs.molecular.main.molecule;

import com.bramerlabs.molecular.main.molecule.node.Node;

import java.util.ArrayList;

public class Molecule {

    ArrayList<Node> nodes;

    public Molecule() {
        this.nodes = new ArrayList<>();
    }

    public void add(Node node) {
        this.nodes.add(node);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void removeNode(Node node) {
        nodes.remove(node);
    }

}
