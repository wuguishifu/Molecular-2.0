package com.bramerlabs.molecular.engine3D.math.complex;

public class Complex {

    public double re, im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public static Complex add(Complex c1, Complex c2) {
        return new Complex(c1.re + c2.re, c1.im + c2.im);
    }

    public static Complex subtract(Complex c1, Complex c2) {
        return new Complex(c1.re - c2.re, c1.im - c2.im);
    }

    public static Complex sqrt(double v) {
        if (v < 0) {
            return new Complex(0, Math.sqrt(v));
        } else {
            return new Complex(Math.sqrt(v), 0);
        }
    }

}
