package com.bramerlabs.molecular.molecule.bond;

import com.bramerlabs.engine.objects.RenderObject;
import com.bramerlabs.molecular.molecule.atom.Atom;

public class Bond {

    // the atoms this bond connects
    private Atom left, right;

    // the order of the bond
    private int order = 1;
    public static final int
        SINGLE = 1,
        DOUBLE = 2,
        TRIPLE = 3;

    // makes a single bond
    public Bond(Atom left, Atom right) {
        this.left = left;
        this.right = right;
    }

    // makes a variable order bond
    public Bond(Atom left, Atom right, int order) {
        this.left = left;
        this.right = right;
        this.order = order;
    }

    public Atom getLeft() {
        return this.left;
    }

    public Atom getRight() {
        return this.right;
    }

    public void setLeft(Atom left) {
       this.left = left;
    }

    public void setRight(Atom right) {
        this.right = right;
    }

    // attempts to add an atom to a non-connected side
    public boolean addAtom(Atom atom) {
        if (left == null) {
            left = atom;
            return true;
        } else if (right == null) {
            right = atom;
            return true;
        } else {
            return false;
        }
    }

    public static RenderObject generateRenderObject(int ID) {
        return null;
    }

}
