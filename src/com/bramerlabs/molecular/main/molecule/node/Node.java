package com.bramerlabs.molecular.main.molecule.node;

import com.bramerlabs.engine.math.vector.Vector3f;

import java.util.ArrayList;

public class Node {

    private Vector3f position;
    private ArrayList<Node> connected;

    public Node(Vector3f position, ArrayList<Node> connected) {
        this.position = position;
        this.connected = connected;
    }

    public ArrayList<Node> getConnected() {
        return this.connected;
    }

    public Vector3f getPosition() {
        return this.position;
    }

}
