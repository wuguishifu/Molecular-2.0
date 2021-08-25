package com.bramerlabs.molecular.molecule.components.bond;

import com.bramerlabs.molecular.engine3D.math.matrix.Matrix4f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.engine3D.objects.Cylinder;
import com.bramerlabs.molecular.molecule.components.Component;
import com.bramerlabs.molecular.molecule.components.atom.Atom;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Bond extends Component {

    public Vector3f p1, p2;
    public int atomID1, atomID2;
    public int order;

    public Vector3f axial;

    public Vector3f position, scale;
    public Vector3f rotationAxis;
    public float rotationAngle;

    public static Cylinder cylinder;

    public Matrix4f[] modelMatrices;
    public Matrix4f[] highlightModelMatrices;

    public Bond(Atom a1, Atom a2, int order) {
        this.atomID1 = a1.ID;
        this.atomID2 = a2.ID;
        this.p1 = a1.position;
        this.p2 = a2.position;
        this.order = order;
        this.generateModelMatrices();
    }

    private void generateModelVectors() {
        this.position = p1;
        axial = Vector3f.normalize(Vector3f.subtract(p2, p1));

        float length = Vector3f.distance(p1, p2);
        this.scale = new Vector3f(length, 1, 1);

        Vector3f cross = Vector3f.cross(axial, Vector3f.e1);
        if (cross.equals(Vector3f.zero)) {
            if (Vector3f.normalize(axial).equals(Vector3f.e1)) {
                this.rotationAxis = Vector3f.zero;
                this.rotationAngle = 0;
            } else {
                this.rotationAxis = Vector3f.e2;
                this.rotationAngle = 180;
            }
        } else {
            this.rotationAxis = Vector3f.normalize(Vector3f.cross(axial, Vector3f.e1));
            this.rotationAngle = Vector3f.angleBetween(axial, Vector3f.e1);
        }
    }

    public void generateModelMatrices() {
        generateModelVectors();

        Vector3f normal = null;
        if (order == 2 || order == 3) {
            normal = Vector3f.cross(Vector3f.e2, axial);
            if (normal.equals(Vector3f.zero)) {
                normal = Vector3f.cross(Vector3f.e3, axial);
            }
            normal = Vector3f.normalize(normal, 0.8f);
        }

        ArrayList<Matrix4f> models = new ArrayList<>();
        ArrayList<Matrix4f> highlightModels = new ArrayList<>();
        switch (order) {
            case 2:
                normal = Vector3f.scale(normal, 0.5f);
                models.add(Matrix4f.transform(Vector3f.add(position, normal), new Vector3f[]{rotationAxis},
                        new float[]{rotationAngle}, scale));
                models.add(Matrix4f.transform(Vector3f.subtract(position, normal), new Vector3f[]{rotationAxis},
                        new float[]{rotationAngle}, scale));
                highlightModels.add(Matrix4f.transform(Vector3f.add(position, normal), new Vector3f[]{rotationAxis},
                        new float[]{rotationAngle}, Vector3f.add(scale, 0, 0.5f, 0.5f)));
                highlightModels.add(Matrix4f.transform(Vector3f.subtract(position, normal), new Vector3f[]{rotationAxis},
                        new float[]{rotationAngle}, Vector3f.add(scale, 0, 0.5f, 0.5f)));
                break;
            case 3:
                models.add(Matrix4f.transform(Vector3f.add(position, normal), new Vector3f[]{rotationAxis},
                        new float[]{rotationAngle}, scale));
                models.add(Matrix4f.transform(Vector3f.subtract(position, normal), new Vector3f[]{rotationAxis},
                        new float[]{rotationAngle}, scale));
                highlightModels.add(Matrix4f.transform(Vector3f.add(position, normal), new Vector3f[]{rotationAxis},
                        new float[]{rotationAngle}, Vector3f.add(scale, 0, 0.5f, 0.5f)));
                highlightModels.add(Matrix4f.transform(Vector3f.subtract(position, normal), new Vector3f[]{rotationAxis},
                        new float[]{rotationAngle}, Vector3f.add(scale, 0, 0.5f, 0.5f)));
            case 1:
                models.add(Matrix4f.transform(position, new Vector3f[]{rotationAxis},
                        new float[]{rotationAngle}, scale));
                highlightModels.add(Matrix4f.transform(position, new Vector3f[]{rotationAxis},
                        new float[]{rotationAngle}, Vector3f.add(scale, 0, 0.5f, 0.5f)));
                break;
            default:
                models.add(Matrix4f.transform(position, new Vector3f[]{rotationAxis},
                        new float[]{rotationAngle}, scale));
                highlightModels.add(Matrix4f.transform(position, new Vector3f[]{rotationAxis},
                        new float[]{rotationAngle}, Vector3f.add(scale, 0, 0.5f, 0.5f)));
                break;
        }

        this.modelMatrices = models.toArray(Matrix4f[]::new);
        this.highlightModelMatrices = highlightModels.toArray(Matrix4f[]::new);
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

        public static float getAdjustedLength(int atomicNumber1, int atomicNumber2, int order) {
            if (order >= 4 || order < 1) {
                order = 1;
            }
            return (covalentRadii.get(atomicNumber1)[order - 1] + covalentRadii.get(atomicNumber2)[order - 1]) / 100 +
                    Atom.DataCompiler.getRadius(atomicNumber1) + Atom.DataCompiler.getRadius(atomicNumber2);
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
