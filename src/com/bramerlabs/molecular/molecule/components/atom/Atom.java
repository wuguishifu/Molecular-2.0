package com.bramerlabs.molecular.molecule.components.atom;

import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector4f;
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

    static {
        sphere = new IcoSphere(new Vector3f(0, 0, 0), new Vector4f(0, 0, 0, 0), 1.0f);
        sphere.createMesh();
    }

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

        static {parse();}

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

    public static int
            HYDROGEN = 1,       HELIUM = 2,         LITHIUM = 3,        BERYLLIUM =  4,         BORON = 5,
            CARBON = 6,         NITROGEN = 7,       OXYGEN = 8,         FLUORINE = 9,           NEON = 10,
            SODIUM = 11,        MAGNESIUM = 12,     ALUMINUM = 13,      SILICON = 14,           PHOSPHORUS = 15,
            SULFUR = 16,        CHLORINE = 17,      ARGON = 18,         POTASSIUM = 19,         CALCIUM = 20,
            SCANDIUM = 21,      TITANIUM = 22,      VANADIUM = 23,      CHROMIUM = 24,          MANGANESE = 25,
            IRON = 26,          COBALT = 27,        NICKEL = 28,        COPPER = 29,            ZINC = 30,
            GALLIUM = 31,       GERMANIUM = 32,     ARSENIC = 33,       SELENIUM = 34,          BROMINE = 35,
            KRYPTON = 36,       RUBIDIUM = 37,      STRONTIUM = 38,     YTTRIUM = 39,           ZIRCONIUM = 40,
            NIOBIUM = 41,       MOLYBDENUM = 42,    TECHNETIUM = 43,    RUTHENIUM = 44,         RHODIUM = 45,
            PALLADIUM = 46,     SILVER = 47,        CADMIUM = 48,       INDIUM = 49,            TIN = 50,
            ANTIMONY = 51,      TELLURIUM = 52,     IODINE = 53,        XENON = 54,             CESIUM = 55,
            BARIUM = 56,        LANTHANUM = 57,     CERIUM = 58,        PRASEODYMIUM = 59,      NEODYMIUM = 60,
            PROMETHIUM = 61,    SAMARIUM = 62,      EUROPIUM = 63,      GADOLINIUM = 64,        TERBIUM = 65,
            DYSPROSIUM = 66,    HOLMIUM = 67,       ERBIUM = 68,        THULIUM = 69,           YTTERBIUM = 70,
            LUTETIUM = 71,      HAFNIUM = 72,       TANTALUM = 73,      TUNGSTEN = 74,          RHENIUM = 75,
            OSMIUM = 76,        IRIDIUM = 77,       PLATINUM = 78,      GOLD = 79,              MERCURY = 80,
            THALLIUM = 81,      LEAD = 82,          BISMUTH = 83,       POLONIUM = 84,          ASTATINE = 85,
            RADON = 86,         FRANCIUM = 87,      RADIUM = 88,        ACTINIUM = 89,          THORIUM = 90,
            PROTACTINIUM = 91,  URANIUM = 92,       NEPTUNIUM = 93,     PLUTONIUM = 94,         AMERICIUM = 95,
            CURIUM = 96,        BERKELIUM = 97,     CALIFORNIUM = 98,   EINSTEINIUM = 99,       FERMIUM = 100,
            MENDELEVIUM = 101,  NOBELIUM = 102,     LAWRENCIUM = 103,   RUTHERFORDIUM = 104,    DUBNIUM = 105,
            SEABORGIUM = 106,   BOHRIUM = 107,      HASSIUM = 108,      MEITNERIUM = 109,       DARMSTADTIUM = 110,
            ROENTGENIUM = 111,  COPERNICIUM = 112,  NIHONIUM = 113,     FLEROVIUM = 114,        MOSCOVIUM = 115,
            LIVERMORIUM = 116,  TENNESSINE = 117,   OGANESSON = 118;

    public static int
            H = 1,      He = 2,     Li = 3,     Be = 4,     B = 5,      C = 6,      N = 7,      O = 8,      F = 9,
            Ne = 10,    Na = 11,    Mg = 12,    Al = 13,    Si = 14,    P = 15,     S = 16,     Cl = 17,    Ar = 18,
            K = 19,     Ca = 20,    Sc = 21,    Ti = 22,    V = 23,     Cr = 24,    Mn = 25,    Fe = 26,    Co = 27,
            Ni = 28,    Cu = 29,    Zn = 30,    Ga = 31,    Ge = 32,    As = 33,    Se = 34,    Br = 35,    Kr = 36,
            Rb = 37,    Sr = 38,    Y = 39,     Zr = 40,    Nb = 41,    Mo = 42,    Tc = 43,    Ru = 44,    Rh = 45,
            Pd = 46,    Ag = 47,    Cd = 48,    In = 49,    Sn = 50,    Sb = 51,    Te = 52,    I = 53,     Xe = 54,
            Cs = 55,    Ba = 56,    La = 57,    Ce = 58,    Pr = 59,    Nd = 60,    Pm = 61,    Sm = 62,    Eu = 63,
            Gd = 64,    Tb = 65,    Dy = 66,    Ho = 67,    Er = 68,    Tm = 69,    Yb = 70,    Lu = 71,    Hf = 72,
            Ta = 73,    W = 74,     Re = 75,    Os = 76,    Ir = 77,    Pt = 78,    Au = 79,    Hg = 80,    Tl = 81,
            Pb = 82,    Bi = 83,    Po = 84,    At = 85,    Rn = 86,    Fr = 87,    Ra = 88,    Ac = 89,    Th = 90,
            Pa = 91,    U = 92,     Np = 93,    Pu = 94,    Am = 95,    Cm = 96,    Bk = 97,    Cf = 98,    Es = 99,
            Fm = 100,   Md = 101,   No = 102,   Lr = 103,   Rf = 104,   Db = 105,   Sg = 106,   Bh = 107,   Hs = 108,
            Mt = 109,   Ds = 110,   Rg = 111,   Cn = 112,   Nh = 113,   Fl = 114,   Mc = 115,   Lv = 116,   Ts = 117,
            Og = 118;
}