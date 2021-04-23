package com.bramerlabs.engine.math.noise;

import java.util.Random;

// Just like in perlin noise you will in general combine several octaves of noise
// to create fractal noise (which gives you terrain like features).
// Note that 3D terrain heights are created by 2D noise.

public class SimplexNoise {

    private SimplexNoiseOctave[] octaves;
    private float[] frequencys;
    private float[] amplitudes;

    private float persistence;

    public SimplexNoise(float largestFeature, float persistence, int seed) {
        this.persistence = persistence;

        // recieves a number (eg 128) and calculates what power of 2 it is (eg 2^7)
        // Math.ceil(7) = 7 = numberOfOctaves
        int numberOfOctaves = (int)Math.ceil(Math.log10(largestFeature)/Math.log10(2));

        octaves = new SimplexNoiseOctave[numberOfOctaves];
        frequencys = new float[numberOfOctaves];
        amplitudes = new float[numberOfOctaves];

        Random rnd = new Random(seed);

        for(int i=0; i<numberOfOctaves; i++) {
            octaves[i] = new SimplexNoiseOctave(rnd.nextInt());
            frequencys[i] = (float) Math.pow(2,i);
            amplitudes[i] = (float) Math.pow(this.persistence, octaves.length-i);
        }
    }

    public double getNoise2D(float x, float y) {
        double result = 0;
        for(int i=0;i<octaves.length;i++) {

            result = result + octaves[i].noise(x/frequencys[i], y/frequencys[i])* amplitudes[i];
        }

        return result;
    }

    public double getNoise3D(float x, float y, float z) {
        double result=0;
        for(int i=0;i<octaves.length;i++) {
            double frequency = Math.pow(2,i);
            double amplitude = Math.pow(persistence,octaves.length - i);

            result = result + octaves[i].noise(x/frequency, y/frequency,z/frequency)* amplitude;
        }

        return result;
    }
}