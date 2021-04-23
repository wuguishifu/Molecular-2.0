package com.bramerlabs.engine.math.noise;

import java.util.ArrayList;
import java.util.Random;

public class Noise {

    private long seed;
    private int[] p; //doubled permutation to avoid overflow

    public Noise(long seed) {
        this.seed = seed;
        generateHashMap();
    }

    /**
     * Generates a seeded random hash map
     */
    private void generateHashMap() {
        Random random = new Random(seed);
        ArrayList<Integer> numbers = new ArrayList<>();
        p = new int[512];
        for (int i = 0; i < 256; i++) { //creates an ordered array of all numbers [0,255]
            numbers.add(i);
        }

        //Perlin hash lookup table
        ArrayList<Integer> permutation = shuffle(numbers, random);

        for (int i = 0; i < 256; i++) { //doubles permutation to avoid overflow
            p[i] = p[i+256] = permutation.get(i);

        }


    }

    /**
     * shuffles an array list
     * @param ints - the array list to be shuffled
     * @param random - the random number generator used to procedurally shuffle
     * @return the new shuffled arraylist
     */
    private ArrayList<Integer> shuffle(ArrayList<Integer> ints, Random random) {
        ArrayList<Integer> result = new ArrayList<>();
        int size = ints.size();
        for (int i = 0; i < size; i++) {
            int index = (int)(random.nextDouble()*(256-i));
            result.add(ints.remove(index));
        }
        return result;
    }

    /**
     * fade function as defined by Ken Perlin. Approximates coordinate values towards integral values.
     * the result smooths output values.
     * @param t - the value to be smoothed
     * @return the smoothed value according to Perlin's equation 6t^5 - 15t^4 + 10t^3
     */
    private static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    /**
     * Linear Interpolation
     * @param a - the first dot product
     * @param b - the second dot product
     * @param t - the faded value used to make the linear interpolation
     * @return the linearly interpolated value
     */
    private static double lerp(double a, double b, double t) {
        return a + t * (b - a);
    }

    /**
     * my java version of Ken Perlin's original gradient function, this may or may not be slower than the alternative written below
     * the goal of this function is to calculate the dot product of a vector and one of 12 R^3 unit vectors
     * update: the new grad function below kenGrad has been confirmed more efficient, but I will leave the original one
     * as a way of paying homage to Ken Perlin.
     *
     * Plus, I like the way it looks and wish it was efficient
     * @param hash - the hash function value
     * @param x - the x coordinate
     * @param y - the y coordinate
     * @param z - the z coordinate
     * @return the dot product of the vector represented by <x, y, z> and one of the 12 R^3 unit vectors
     */
    static double grad(int hash, double x, double y, double z) {
        int h = hash & 15;                      // CONVERT LO 4 BITS OF HASH CODE
        double u = h<8 ? x : y,                 // INTO 12 GRADIENT DIRECTIONS.
                v = h<4 ? y : h==12||h==14 ? x : z;
        return ((h&1) == 0 ? u : -u) + ((h&2) == 0 ? v : -v);
    }

    /**
     * an updated, more efficient version of the now obsolete kenGrad function
     * the goal of this function is to calculate the dot product of a vector and one of 12 R^3 unit vectors
     *
     * the 12 R^3 unit vectors expressed in component notation:
     * i+j, -i+j, i-j, -i-j,
     * i+k, -i+k, i-k, -i-k,
     * j+k, -j+k, j-k, -j-k
     *
     * @param hash - the hash function value
     * @param x - the x coordinate
     * @param y - the y coordinate
     * @param z - the z coordinate
     * @return the dot product of the vector represented by <x, y, z> and one of the 12 R^3 unit vectors.
     */
    private static double badGrad(int hash, double x, double y, double z) {
        switch (hash & 0xF) {
            case 0x0: return  x + y;
            case 0x1: return -x + y;
            case 0x2: return  x - y;
            case 0x3: return -x - y;
            case 0x4: return  x + z;
            case 0x5: return -x + z;
            case 0x6: return  x - z;
            case 0x7: return -x - z;
            case 0x8: return  y + z;
            case 0x9:
            case 0xD:
                return -y + z;
            case 0xA: return  y - z;
            case 0xB:
            case 0xF:
                return -y - z;
            case 0xC: return  y + x;
            case 0xE: return  y - x;
            default: return 0; //never occurs
        }
    }

    public double perlin(double x, double y, double z, double f) {
        x *= f;
        y *= f;
        z *= f;

        int X = (int)Math.floor(x) & 255,                  // FIND UNIT CUBE THAT
                Y = (int)Math.floor(y) & 255,                  // CONTAINS POINT.
                Z = (int)Math.floor(z) & 255;
        x -= Math.floor(x);                                // FIND RELATIVE X,Y,Z
        y -= Math.floor(y);                                // OF POINT IN CUBE.
        z -= Math.floor(z);
        double u = fade(x),                                // COMPUTE FADE CURVES
                v = fade(y),                                // FOR EACH OF X,Y,Z.
                w = fade(z);
        int A = p[X  ]+Y, AA = p[A]+Z, AB = p[A+1]+Z,      // HASH COORDINATES OF
                B = p[X+1]+Y, BA = p[B]+Z, BB = p[B+1]+Z;      // THE 8 CUBE CORNERS,

        double result = lerp(w, lerp(v, lerp(u, grad(p[AA  ], x  , y  , z   ),  // AND ADD
                grad(p[BA  ], x-1, y  , z   )), // BLENDED
                lerp(u, grad(p[AB  ], x  , y-1, z   ),  // RESULTS
                        grad(p[BB  ], x-1, y-1, z   ))),// FROM  8
                lerp(v, lerp(u, grad(p[AA+1], x  , y  , z-1 ),  // CORNERS
                        grad(p[BA+1], x-1, y  , z-1 )), // OF CUBE
                        lerp(u, grad(p[AB+1], x  , y-1, z-1 ),
                                grad(p[BB+1], x-1, y-1, z-1 ))));
        return Math.abs(result)>=1 ? 0.999999999 : result;
    }

    /**
     * perlin function
     * @param x - x position
     * @param y - y position
     * @param z - z position
     * @param frequency - a NON INTEGER interval for perlin calculation
     * @return a perlin value at (x, y, z)
     */
    public double perlin(double x, double y, double z, double frequency, boolean b) {
        x *= frequency;
        y *= frequency;
        z *= frequency;
        //vector creation
        int xi = (int)x % 255; //make sure the coordinate values are <= 255
        int yi = (int)y % 255;
        int zi = (int)z % 255;
        if (xi < 0) { //make sure the coordinate values are >= 255
            xi += 255;
        }
        if (yi < 0) {
            yi += 255;
        }
        if (zi < 0) {
            zi += 255;
        }
        double xf = x - (int)x; //finding the location of the coordinate inside its unit cube
        double yf = y - (int)y; //this is essentially finding the decimal location in the 1x1x1 unit cube
        double zf = z - (int)z; //i.e., n %= 1.0

        //fade functions
        double u = fade(xf); //using Perlin's fade equation to smooth out our decimal coordinates
        double v = fade(yf);
        double w = fade(zf);

        //Perlin hash function
        int aaa, aab, aba, abb, baa, bab, bba, bbb;
        aaa = p[p[p[ xi   ]+  yi  ]+  zi  ]; //hash function that perlin noise uses
        aab = p[p[p[ xi   ]+  yi  ]+  zi+1]; //each b in the three digit variables represents a location where
        aba = p[p[p[ xi   ]+  yi+1]+  zi  ]; //the value is incremented by 1
        abb = p[p[p[ xi   ]+  yi+1]+  zi+1];
        baa = p[p[p[ xi+1 ]+  yi  ]+  zi  ];
        bab = p[p[p[ xi+1 ]+  yi  ]+  zi+1];
        bba = p[p[p[ xi+1 ]+  yi+1]+  zi  ];
        bbb = p[p[p[ xi+1 ]+  yi+1]+  zi+1];
        //System.out.println(aaa + ", " + aab + ", " + aba + ", " + abb + ", " + baa + ", " + bab + ", " + bba + ", " + bbb);

        //linear interpolation
        double x1, x2, y1, y2;
        x1 = lerp(grad(aaa, xf, yf, zf), grad(baa, xf-1, yf, zf), u);
        x2 = lerp(grad(aba, xf, yf-1, zf), grad(bba, xf-1, yf-1, zf), u);
        y1 = lerp(x1, x2, v);
        x1 = lerp(grad(aab, xf, yf, zf-1), grad(bab, xf-1, yf, zf-1), u);
        x2 = lerp(grad(abb, xf, yf-1, zf-1), grad(bbb, xf-1, yf-1, zf-1), u);
        y2 = lerp(x1, x2, v);
        //System.out.println(lerp(y1, y2, w));
        return lerp(y1, y2, w);
    }

    /**
     * stacks multiple octaves of Perlin Noise for more natural look. Not sure if this will be implemented yet,
     * this is more of a finishing touch than an absolute necessity at the moment.
     * @param x - x position
     * @param y - y position
     * @param z - z position
     * @param octaves - amount of octaves
     * @param persistence - strength of the octaves
     * @param frequency - how often noise is calculated
     * @param amplitude - how large noise is calculated
     * @return a new value for perlin with multiple octaves taken into account
     */
    public double octavePerlin(double x, double y, double z, int octaves, double persistence, double frequency, double amplitude) {
        double total = 0;
        double maxValue = 0;  // Used for normalizing result to 0.0 - 1.0
        for(int i=0;i<octaves;i++) {
            total += perlin(x * frequency, y * frequency, z, frequency) * amplitude;

            maxValue += amplitude;

            amplitude *= persistence;
            frequency *= 2;
        }

        return total/maxValue;

    }
}
