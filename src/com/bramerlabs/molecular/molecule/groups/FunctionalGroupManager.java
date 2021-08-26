package com.bramerlabs.molecular.molecule.groups;

import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.components.atom.Atom;

import java.util.HashMap;

public class FunctionalGroupManager {

    public static HashMap<Group, Vector3f> connections;
    public static HashMap<Group, Vector3f[]> bondPositions;

    static {
        connections = new HashMap<>();
        bondPositions = new HashMap<>();

        connections.put(Group.TETRAHEDRAL, FunctionalGroup.Tetrahedral.connection);
        connections.put(Group.TRIGONAL, FunctionalGroup.Trigonal.connection);
        connections.put(Group.LINEAR, FunctionalGroup.Linear.connection);
        connections.put(Group.BENT, FunctionalGroup.Bent.connection);

        bondPositions.put(Group.TETRAHEDRAL, FunctionalGroup.Tetrahedral.bondPositions);
        bondPositions.put(Group.TRIGONAL, FunctionalGroup.Trigonal.bondPositions);
        bondPositions.put(Group.LINEAR, FunctionalGroup.Linear.bondPositions);
        bondPositions.put(Group.BENT, FunctionalGroup.Bent.bondPositions);
    }

    public static void addGroup(Molecule molecule, Group type, Atom connected, Vector3f normal, float rotation) {
        FunctionalGroup.addGroup(molecule, connected, normal, rotation, connections.get(type), bondPositions.get(type));
    }

    public static void createGroup(Molecule molecule, Group type, Vector3f normal, float rotation) {
        FunctionalGroup.createGroup(molecule, normal, rotation, connections.get(type), bondPositions.get(type));
    }

    public static void createAndReplace(Molecule molecule, Group type, Atom connected, Vector3f normal, float rotation) {
        FunctionalGroup.createAndReplace(molecule, connected, normal, rotation, connections.get(type), bondPositions.get(type));
    }

    public enum Group {
        TETRAHEDRAL, TRIGONAL, LINEAR, BENT
    }

}
