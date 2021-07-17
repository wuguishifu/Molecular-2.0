package com.bramerlabs.molecular.main.molecule.node;

import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.molecular.main.molecule.node.atom.Atom;

import java.util.ArrayList;

public class Node {

    private Vector3f position;
    private Atom atom;

    public Node(Vector3f position, Atom atom) {
        this.position = position;
        this.atom = atom;
    }

    public Node(Vector3f position) {
        this.position = position;
        this.atom = null;
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public Atom getAtom() {
        return this.atom;
    }

    public void set(Vector3f position) {
        this.position = position;
    }

    public void set(Atom atom) {
        this.atom = atom;
    }

    @Override
    public String toString() {
        return "Node at " + position + (atom != null ? atom : "");
    }

}
