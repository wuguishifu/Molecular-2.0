package com.bramerlabs.molecular.molecule;

import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.molecule.components.atom.Atom;
import com.bramerlabs.molecular.molecule.components.bond.Bond;

import java.util.ArrayList;
import java.util.HashMap;

public class Molecule {

    public HashMap<Integer, Atom> atoms;
    public HashMap<Integer, Bond> bonds;

    public BondMap bondMap;

    public Molecule(HashMap<Integer, Atom> atoms, HashMap<Integer, Bond> bonds) {
        this.atoms = atoms;
        this.bonds = bonds;
        this.bondMap = new BondMap();
        for (Atom atom : atoms.values()) {
            this.bondMap.add(atom.ID);
        }
        for (Bond bond : bonds.values()) {
            this.addBond(atoms.get(bond.atomID1), atoms.get(bond.atomID2), bond.order);
        }
    }

    public Molecule() {
        this.atoms = new HashMap<>();
        this.bonds = new HashMap<>();
        this.bondMap = new BondMap();
    }

    public boolean toggleSelection(int ID) {
        Atom atom = atoms.get(ID);
        if (atom != null) {
            atom.toggleSelected();
            return true;
        }
        Bond bond = bonds.get(ID);
        if (bond != null) {
            bond.toggleSelected();
            return true;
        }
        return false;
    }

    public boolean singleSelection(int ID) {
        unselect();
        Atom atom = atoms.get(ID);
        if (atom != null) {
            atom.selected = true;
            return true;
        }
        Bond bond = bonds.get(ID);
        if (bond != null) {
            bond.selected = true;
            return true;
        }
        return false;
    }

    public void unselect() {
        for (Atom atom : atoms.values()) {
            atom.selected = false;
        }
        for (Bond bond : bonds.values()) {
            bond.selected = false;
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

    public Atom getAtom(int ID) {
        return atoms.getOrDefault(ID, null);
    }

    public Bond getBond(int ID) {
        return bonds.getOrDefault(ID, null);
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

    public void addBond(Atom a1, Atom a2, int order) {
        Bond bond = new Bond(a1, a2, order);
        bonds.put(bond.ID, bond);
        bondMap.add(a1.ID, a2.ID, order);
    }

    public ArrayList<Atom> getConnectedAtoms(Atom a1) {
        int id = a1.ID;
        ArrayList<Integer> ids = bondMap.getConnected(id);
        ArrayList<Atom> atoms = new ArrayList<>();
        for (int i : ids) {
            atoms.add(this.atoms.get(i));
        }
        return atoms;
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

    public void remove(int ID) {
        if (isAtomID(ID)) {
            this.removeAtom(ID);
        } else {
            this.bonds.remove(ID);
        }
    }

    public void removeAtom(int id1) {
        this.atoms.remove(id1);
        ArrayList<Integer> connected = this.bondMap.removeAtom(id1);
        for (int id2 : connected) {
            this.bondMap.removeBond(id1, id2);
        }
    }
}
