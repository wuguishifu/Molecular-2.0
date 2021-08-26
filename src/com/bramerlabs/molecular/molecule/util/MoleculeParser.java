package com.bramerlabs.molecular.molecule.util;

import com.bramerlabs.molecular.molecule.BondMap;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.components.atom.Atom;

import java.util.ArrayList;
import java.util.HashMap;

public class MoleculeParser {

    /**
     * convert molecule into a tree naively (does not support rings)
     * @param molecule the molecule to parse
     * @return the head node
     */
    public static Node parseMoleculeNaive(Molecule molecule) {

        HashMap<Integer, Atom> atoms = molecule.getAtoms();
        BondMap map = molecule.bondMap;

        ArrayList<Integer> connectedIDs = map.getConnected(atoms.get(0).ID);
        return addNodes(atoms, atoms.get(0).ID, connectedIDs, map);
    }

    public static Node addNodes(HashMap<Integer, Atom> atoms, int ID, ArrayList<Integer> connectedIDs, BondMap map) {
        if (connectedIDs == null) {
            return new Node(atoms.get(ID));
        }

        ArrayList<Node> nodes = new ArrayList<>();
        for (int id : connectedIDs) {
            ArrayList<Integer> connected = map.getConnected(id);
            if (connected != null) {
                connected.remove(Integer.valueOf(ID));
            }
            nodes.add(addNodes(atoms, id, connected, map));
        }
        return new Node(atoms.get(ID), nodes);
    }

    public static int longestChain(Molecule molecule) {
        Node node = parseMoleculeNaive(molecule);
        return countBFSDepth(node);
    }

    public static int countBFSDepth(Node node) {
        if (node.connections == null) {
            return 0;
        }
        int longest = 0;
        for (Node n : node.connections) {
            int diameter = countBFSDepth(n);
            longest = Math.max(longest, diameter);
        }
        return longest;
    }

    public static class Node {

        public Atom atom;
        public ArrayList<Node> connections = null;

        public Node(Atom atom, ArrayList<Node> connections) {
            this.atom = atom;
            this.connections = connections;
        }

        public Node(Atom atom) {
            this.atom = atom;
        }

    }

}
