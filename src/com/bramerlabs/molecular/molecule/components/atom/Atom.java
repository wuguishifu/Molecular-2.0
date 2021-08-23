package com.bramerlabs.molecular.molecule.components.atom;

import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.engine3D.objects.IcoSphere;
import com.bramerlabs.molecular.molecule.components.Component;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class Atom extends Component {

    public Vector3f position;
    public Data data;

    public static IcoSphere sphere;

    public Atom(Vector3f position, Data data) {
        super();
        this.position = position;
        this.data = data;
    }

    public static class Data {
        public int atomicNumber;
        public int charge;
        public Data(int atomicNumber, int charge) {
            this.atomicNumber = atomicNumber;
            this.charge = charge;
        }
    }

    public static class DataCompiler {
        private static final HashMap<Integer, Item> atomData = new HashMap<>();

        public static String getName(int atomicNumber) {
            return atomData.get(atomicNumber).name;
        }

        public static String getAbbreviation(int atomicNumber) {
            return atomData.get(atomicNumber).abbreviation;
        }

        public static Color getColor(int atomicNumber) {
            return atomData.get(atomicNumber).CPKColor;
        }

        public static float getRadius(int atomicNumber) {
            return atomData.get(atomicNumber).VDWRadius;
        }

        public static void init() {
            parse();
        }

        private static void parse() {
            try (Scanner input = new Scanner(new File("res/atomic_data/atom_data.txt"))) {
                Item item = null;
                while (input.hasNextLine()) {
                    String nextLine = input.nextLine();
                    if (nextLine.equals("<atom")) {
                        item = new Item();
                    } else if (nextLine.equals("/>")) {
                        assert item != null;
                        atomData.put(item.id, item);
                    } else {
                        String[] components = nextLine.split("=");
                        switch (components[0]) {
                            case "\tid":
                                assert item != null;
                                item.id = Integer.parseInt(components[1]);
                                break;
                            case "\tname":
                                assert item != null;
                                item.name = components[1];
                                break;
                            case "\tabbreviation":
                                assert item != null;
                                item.abbreviation = components[1];
                                break;
                            case "\tcpk_color":
                                assert item != null;
                                item.CPKColor = Color.decode("#" + components[1]);
                                break;
                            case "\tvdw_radius":
                                assert item != null;
                                item.VDWRadius = Float.parseFloat(components[1]);
                                break;
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        private static class Item {
            private int id;
            private String name;
            private String abbreviation;
            private Color CPKColor;
            private float VDWRadius;
            @Override
            public String toString() {
                return "<atom>" + "id=" + id + ", name=" + name + ", abbr=" + abbreviation +
                        ", cpk=" + CPKColor + ", vdw=" + VDWRadius + "</atom>";
            }
        }
    }
}