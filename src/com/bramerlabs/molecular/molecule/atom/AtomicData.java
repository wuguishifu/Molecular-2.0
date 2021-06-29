package com.bramerlabs.molecular.molecule.atom;

import com.bramerlabs.engine.math.vector.Vector3f;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AtomicData {

    private static boolean hasBeenInitialized = false;

    // atomic numbers
    public static final int
            HYDROGEN = 1, HELIUM = 2, LITHIUM = 3, BERYLLIUM =  4, BORON = 5, CARBON = 6, NITROGEN = 7, OXYGEN = 8,
            FLUORINE = 9, NEON = 10, SODIUM = 11, MAGNESIUM = 12, ALUMINUM = 13, SILICON = 14, PHOSPHORUS = 15,
            SULFUR = 16, CHLORINE = 17, ARGON = 18, POTASSIUM = 19, CALCIUM = 20, SCANDIUM = 21, TITANIUM = 22,
            VANADIUM = 23, CHROMIUM = 24, MANGANESE = 25, IRON = 26, COBALT = 27, NICKEL = 28, COPPER = 29, ZINC = 30,
            GALLIUM = 31, GERMANIUM = 32, ARSENIC = 33, SELENIUM = 34, BROMINE = 35, KRYPTON = 36, RUBIDIUM = 37,
            STRONTIUM = 38, YTTRIUM = 39, ZIRCONIUM = 40, NIOBIUM = 41, MOLYBDENUM = 42, TECHNETIUM = 43,
            RUTHENIUM = 44, RHODIUM = 45, PALLADIUM = 46, SILVER = 47, CADMIUM = 48, INDIUM = 49, TIN = 50,
            ANTIMONY = 51, TELLURIUM = 52, IODINE = 53, XENON = 54, CESIUM = 55, BARIUM = 56, LANTHANUM = 57,
            CERIUM = 58, PRASEODYMIUM = 59, NEODYMIUM = 60, PROMETHIUM = 61, SAMARIUM = 62, EUROPIUM = 63,
            GADOLINIUM = 64, TERBIUM = 65, DYSPROSIUM = 66, HOLMIUM = 67, ERBIUM = 68, THULIUM = 69, YTTERBIUM = 70,
            LUTETIUM = 71, HAFNIUM = 72, TANTALUM = 73, TUNGSTEN = 74, RHENIUM = 75, OSMIUM = 76, IRIDIUM = 77,
            PLATINUM = 78, GOLD = 79, MERCURY = 80, THALLIUM = 81, LEAD = 82, BISMUTH = 83, POLONIUM = 84,
            ASTATINE = 85, RADON = 86, FRANCIUM = 87, RADIUM = 88, ACTINIUM = 89, THORIUM = 90, PROTACTINIUM = 91,
            URANIUM = 92, NEPTUNIUM = 93, PLUTONIUM = 94, AMERICIUM = 95, CURIUM = 96, BERKELIUM = 97, CALIFORNIUM = 98,
            EINSTEINIUM = 99, FERMIUM = 100, MENDELEVIUM = 101, NOBELIUM = 102, LAWRENCIUM = 103, RUTHERFORDIUM = 104,
            DUBNIUM = 105, SEABORGIUM = 106, BOHRIUM = 107, HASSIUM = 108, MEITNERIUM = 109, DARMSTADTIUM = 110,
            ROENTGENIUM = 111, COPERNICIUM = 112, NIHONIUM = 113, FLEROVIUM = 114, MOSCOVIUM = 115, LIVERMORIUM = 116,
            TENNESSINE = 117, OGANESSON = 118;

    // the abbreviations of the atom names
    final static String[] atomicAbbreviations = new String[]{
            "H", "He", "Li", "Be", "B", "C", "N", "O", "F", "Ne", "Na", "Mg", "Al", "Si", "P", "S", "Cl", "Ar", "K",
            "Ca", "Sc", "Ti", "V", "Cr", "Mn", "Fe", "Co", "Ni", "Cu", "Zn", "Ga", "Ge", "As", "Se", "Br", "Kr", "Rb",
            "Sr", "Y", "Zr", "Nb", "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In", "Sn", "Sb", "Te", "I", "Xe", "Cs",
            "Ba", "La", "Ce", "Pr", "Nd", "Pm", "Sm", "Eu", "Gd", "Tb", "Dy", "Ho", "Er", "Tm", "Yb", "Lu", "Hf", "Ta",
            "W", "Re", "Os", "Ir", "Pt", "Au", "Hg", "Tl", "Pb", "Bi", "Po", "At", "Rn", "Fr", "Ra", "Ac", "Th", "Pa",
            "U", "Np", "Pu", "Am", "Cm", "Bk", "Cf", "Es", "Fm", "Md", "No", "Lr", "Rf", "Db", "Sg", "Bh", "Hs", "Mt",
            "Ds", "Rg", "Cn", "Nh", "Fl", "Mc", "Lv", "Ts", "Og"
    };

    // the CPK color of each atom
    final static String[] atomicCPKHexColors = new String[]{
            "FFFFFF", "D9FFFF", "CC80FF", "C2FF00", "FFB5B5", "909090", "3050F8", "FF0D0D", "90E050", "B3E3F5",
            "AB5CF2", "8AFF00", "BFA6A6", "F0C8A0", "FF8000", "FFFF30", "1FF01F", "80D1E3", "8F40D4", "3DFF00",
            "E6E6E6", "BFC2C7", "A6A6AB", "8A99C7", "9C7AC7", "E06633", "F090A0", "50D050", "C88033", "7D80B0",
            "C28F8F", "668F8F", "BD80E3", "FFA100", "A62929", "5CB8D1", "7A2EB0", "00FF00", "94FFFF", "94E0E0",
            "73C2C9", "54B5B5", "3B9E9E", "248F8F", "0A7D8C", "006985", "C0C0C0", "FFD98F", "A67573", "668080",
            "9E63B5", "D47A00", "940094", "429EB0", "57178F", "00C900", "70D4FF", "FFFFC7", "D9FFC7", "C7FFC7",
            "A3FFC7", "8FFFC7", "61FFC7", "45FFC7", "30FFC7", "1FFFC7", "00FF9C", "00E675", "00D452", "00BF38",
            "00AB24", "4DC2FF", "4DA6FF", "2194D6", "267DAB", "266696", "175487", "D0D0E0", "FFD123", "B8B8D0",
            "A6544D", "575961", "9E4FB5", "AB5C00", "754F45", "428296", "420066", "007D00", "70ABFA", "00BAFF",
            "00A1FF", "008FFF", "0080FF", "006BFF", "545CF2", "785CE3", "8A4FE3", "A136D4", "B31FD4", "B31FBA",
            "B30DA6", "BD0D87", "C70066", "CC0059", "D1004F", "D90045", "E00038", "E6002E", "EB0026", "BFC2C7",
            "BFC2C7", "BFC2C7", "BFC2C7", "BFC2C7", "BFC2C7", "BFC2C7", "BFC2C7", "BFC2C7"
    };

    // the covalent radii of each atom
    public static ArrayList<int[]> covalentRadii = new ArrayList<>();

    // the Van der Waals radii of each atom
    public static ArrayList<Float> VDWRadii = new ArrayList<>();

    public static final HashMap<String, Integer> atomicNumberMap = new HashMap<>();
    public static final HashMap<Integer, String> elementNameMap = new HashMap<>();

    public static String getElementName(int atomicNumber) {
        if (atomicNumber < 1 || atomicNumber > 118) {
            return "null";
        }
        if (!hasBeenInitialized) {
            init();
        }
        return elementNameMap.get(atomicNumber);
    }

    public static int getAtomicNumber(String elementName) {
        if (!hasBeenInitialized) {
            init();
        }
        return atomicNumberMap.getOrDefault(elementName, CARBON);
    }

    public static float getVDWRadius(int atomicNumber) {
        if (!hasBeenInitialized) {
            init();
        }
        if (atomicNumber < 1 || atomicNumber > 118) {
            return 0;
        } else {
            return VDWRadii.get(atomicNumber - 1) == 0 ? 0.5f : VDWRadii.get(atomicNumber - 1); // if the VDW radius is 0, return 2.0f
        }
    }

    public static float getCovalentRadius(int atomicNumber, int bondOrder) {
        if (!hasBeenInitialized) {
            init();
        }
        if (bondOrder == 1) {
            if (covalentRadii.get(atomicNumber - 1)[0] == 0) {
                return covalentRadii.get(atomicNumber - 1)[1] / 100.0f;
            } else {
                return covalentRadii.get(atomicNumber - 1)[0] / 100.0f;
            }
        } else if (bondOrder == 2) {
            return covalentRadii.get(atomicNumber - 1)[2] / 100.0f;
        } else if (bondOrder == 3) {
            return covalentRadii.get(atomicNumber - 1)[3] / 100.0f;
        }
        return 0;
    }

    public static String getAtomAbbrName(int atomicNumber) {
        try {
            return atomicAbbreviations[atomicNumber - 1];
        } catch (IndexOutOfBoundsException e) {
            return "Null";
        }
    }

    public static float getCovalentBondLength(int a1, int a2, int bondOrder) {
        if (!hasBeenInitialized) {
            init();
        }

        if ((a1 > 118 || a1 < 1) || (a2 > 118 || a2 < 1) || (bondOrder < 1 || bondOrder > 3)) {
            return 0;
        }

        // get the covalent bond radii
        float a1r = AtomicData.getCovalentRadius(a1, bondOrder);
        float a2r = AtomicData.getCovalentRadius(a2, bondOrder);

        if (a1r == 0 || a2r == 0) {
            return 0;
        }

        // add them together
        return (a1r + a2r);
    }

    public static Vector3f getCPKColor(int atomicNumber) {
        try {
            Color CPKColor = Color.decode("#" + atomicCPKHexColors[atomicNumber - 1]);
            return Vector3f.divide(new Vector3f(CPKColor.getRed(), CPKColor.getGreen(), CPKColor.getBlue()), new Vector3f(255));
        } catch (IndexOutOfBoundsException e) {
            return new Vector3f(0);
        }
    }

    public static void init() {
        initElementNames();
        compileCovalentRadii();
        compileVDWRadii();
        hasBeenInitialized = true;
    }

    private static void compileVDWRadii() {
        try {
            Scanner input = new Scanner(new File("res/atomic_data/Atom Van der Waals Radii"));
            for (int i = 0; i < 118; i++) {
                VDWRadii.add(input.nextFloat());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void compileCovalentRadii() {
        try {
            Scanner input = new Scanner(new File("res/atomic_data/Atom Covalent Radii"));
            for (int i = 0; i < 118; i++) {
                covalentRadii.add(new int[]{input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt()});
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void initElementNames() {
        File file = new File("res/atomic_data/Atomic Names");
        try {
            Scanner input = new Scanner(file);
            for (int i = 0; i < 118; i++) {
                String element = input.nextLine();
                atomicNumberMap.put(element, i + 1);
                elementNameMap.put(i + 1, element);
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
