package com.bramerlabs.molecular.main.molecule.bond;

import com.bramerlabs.molecular.main.molecule.node.Node;

public class Bond {

    private Node n1, n2;

    public Bond(Node n1, Node n2) {
        this.n1 = n1;
        this.n2 = n2;
    }

    public Node getNode1() {
        return this.n1;
    }

    public Node getNode2() {
        return this.n2;
    }

}
