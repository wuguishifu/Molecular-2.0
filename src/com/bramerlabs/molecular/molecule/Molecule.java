package com.bramerlabs.molecular.molecule;

import com.bramerlabs.molecular.engine3D.math.map.BiHashMap;
import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.molecule.components.atom.Atom;
import com.bramerlabs.molecular.molecule.components.bond.Bond;

import java.util.HashMap;

public class Molecule {

    public HashMap<Integer, Atom> atoms;
    public HashMap<Integer, Bond> bonds;
    public BiHashMap<Integer, Integer, Integer> bondMap;

    public Molecule(HashMap<Integer, Atom> atoms, HashMap<Integer, Bond> bonds) {
        this.atoms = atoms;
        this.bonds = bonds;
        bondMap = new BiHashMap<>();
    }

    public void toggleSelection(int ID) {
        Atom atom = atoms.get(ID);
        if (atom != null) {
            atom.toggleSelected();
        }
        Bond bond = bonds.get(ID);
        if (bond != null) {
            bond.toggleSelected();
        }
    }

    public Vector3f getCenter() {
        int i = 0;
        float x = 0, y = 0, z = 0;
        for (Atom atom : atoms.values()) {
            x += atom.position.x;
            y += atom.position.y;
            z += atom.position.z;
            i ++;
        }
        x /= i;
        y /= i;
        z /= i;
        return new Vector3f(x, y, z);
    }

    public boolean isAtomID(int ID) {
        for (Integer key : atoms.keySet()) {
            if (key == ID) {
                return true;
            }
        }
        return false;
    }

    public boolean isBondID(int ID) {
        for (Integer key : atoms.keySet()) {
            if (key == ID) {
                return true;
            }
        }
        return false;
    }

    public HashMap<Integer, Atom> getAtoms() {
        return this.atoms;
    }

    public HashMap<Integer, Bond> getBonds() {
        return this.bonds;
    }

    public void add(Atom atom) {
        this.atoms.put(atom.ID, atom);
    }

    public void add(Bond bond) {
        this.bonds.put(bond.ID, bond);
    }

    public void remove(int ID) {
        this.atoms.remove(ID);
        this.bonds.remove(ID);
    }
}
