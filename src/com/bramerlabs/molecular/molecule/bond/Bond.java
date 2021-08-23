package com.bramerlabs.molecular.molecule.bond;

import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector4f;
import com.bramerlabs.molecular.engine3D.objects.Cylinder;
import com.bramerlabs.molecular.molecule.Molecule;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Bond {

    public Vector3f p1, p2;
    public ArrayList<Cylinder> cylinders;
    public int order;

    public int ID;

    public Bond(Vector3f p1, Vector3f p2, int order) {
        this.p1 = p1;
        this.p2 = p2;
        this.order = order;
        cylinders = new ArrayList<>();
        generateCylinders();
        this.ID = Molecule.currentID;
        Molecule.currentID++;
    }

    public void generateCylinders() {

        Vector4f color = new Vector4f(0.3f, 0.3f, 0.3f, 1.0f);
        float radius = 0.2f;

        Vector3f normal = new Vector3f(0);
        if (order != 1) {
            // find the direction of the bond
            Vector3f bondDirection = Vector3f.subtract(p1, p2);

            // generate a vector normal to the bond direction
            Vector3f v0 = new Vector3f(0, 1, 0);
            if (Vector3f.cross(bondDirection, v0).equals(Vector3f.zero)) {
                System.out.println(bondDirection + ", " + v0 + ", " + Vector3f.cross(bondDirection, v0));
                v0 = new Vector3f(0, 0, 1);
            }
            normal = Vector3f.normalize(Vector3f.cross(bondDirection, v0), 0.8f);
        }

        switch (order) {
            case 2:
                normal.scale(0.5f);
                cylinders.add(Cylinder.makeCylinder(Vector3f.add(p1, normal), Vector3f.add(p2, normal), color, radius));
                cylinders.add(Cylinder.makeCylinder(Vector3f.subtract(p1, normal), Vector3f.subtract(p2, normal), color, radius));
                break;
            case 3:
                cylinders.add(Cylinder.makeCylinder(Vector3f.add(p1, normal), Vector3f.add(p2, normal), color, radius));
                cylinders.add(Cylinder.makeCylinder(Vector3f.subtract(p1, normal), Vector3f.subtract(p2, normal), color, radius));
            case 1:
                cylinders.add(Cylinder.makeCylinder(p1, p2, color, radius));
                break;
            default:
                cylinders.add(Cylinder.makeCylinder(p1, p2, color, radius));
        }

        for (Cylinder cylinder : cylinders) {
            cylinder.createMesh();
        }
    }

    public void destroy() {

    }

    public static class DataCompiler {

        public static HashMap<Integer, float[]> covalentRadii = new HashMap<>();

        public static void init() {
            parse();
        }

        public static float getLength(int atomicNumber1, int atomicNumber2, int order) {
            if (order >= 4 || order < 1) {
                order = 1;
            }
            return covalentRadii.get(atomicNumber1)[order - 1] + covalentRadii.get(atomicNumber2)[order - 1];
        }

        public static void parse() {
            try (Scanner input = new Scanner(new File("res/atomic_data/bond_data.txt"))) {
                for (int i = 0; i < 118; i++) {
                    String line = input.nextLine();
                    String[] c = line.split(" ");
                    float[] item = new float[]{
                            Float.parseFloat(c[0]),
                            Float.parseFloat(c[1]),
                            Float.parseFloat(c[2]),
                            Float.parseFloat(c[3])};
                    covalentRadii.put(i + 1, item);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
