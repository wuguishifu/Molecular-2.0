package com.bramerlabs.engine.math.color;

import com.bramerlabs.engine.math.vector.Vector3f;

import java.awt.*;

public class ColorFader3C {

    /**
     * the 3 color fade values
     */
    private Vector3f c1, c2, c3;

    // slopes
    private float mr1, mr2;
    private float mg1, mg2;
    private float mb1, mb2;

    // offsets
    private float br1, br2;
    private float bg1, bg2;
    private float bb1, bb2;

    private float x1 = 0.0f;
    private float x2;
    private float x3 = 1.0f;

    public ColorFader3C(Color c_1, Color c_2, Color c_3, float xChange) {
        x2 = xChange;

        this.c1 = Vector3f.divide(new Vector3f(c_1), new Vector3f(255));
        this.c2 = Vector3f.divide(new Vector3f(c_2), new Vector3f(255));
        this.c3 = Vector3f.divide(new Vector3f(c_3), new Vector3f(255));

        // calculate the slopes
        this.mr1 = (c2.getX() - c1.getX())/(x2-x1);
        this.mr2 = (c3.getX() - c2.getX())/(x3-x2);
        this.mg1 = (c2.getY() - c1.getY())/(x2-x1);
        this.mg2 = (c3.getY() - c2.getY())/(x3-x2);
        this.mb1 = (c2.getZ() - c1.getZ())/(x2-x1);
        this.mb2 = (c3.getZ() - c2.getZ())/(x3-x2);

        // calculate the offsets
        br1 = c1.getX();
        br2 = c2.getX();
        bg1 = c1.getY();
        bg2 = c2.getY();
        bb1 = c1.getZ();
        bb2 = c2.getZ();
    }

    /**
     * default constructor
     * @param c1 - color 1
     * @param c2 - color 2
     * @param c3 - color 3
     */
    public ColorFader3C(Vector3f c1, Vector3f c2, Vector3f c3) {
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;

        // calculate the slopes
        this.mr1 = (c2.getX() - c1.getX())/(x2-x1);
        this.mr2 = (c3.getX() - c2.getX())/(x3-x2);
        this.mg1 = (c2.getY() - c1.getY())/(x2-x1);
        this.mg2 = (c3.getY() - c2.getY())/(x3-x2);
        this.mb1 = (c2.getZ() - c1.getZ())/(x2-x1);
        this.mb2 = (c3.getZ() - c2.getZ())/(x3-x2);

        // calculate the offsets
        br1 = c1.getX();
        br2 = c2.getX();
        bg1 = c1.getY();
        bg2 = c2.getY();
        bb1 = c1.getZ();
        bb2 = c2.getZ();
    }

    public Vector3f getColor(float x) {

        float r, g, b;

        // calculate the components
        if (x < x2) {
            r = mr1 * x + br1;
            g = mg1 * x + bg1;
            b = mb1 * x + bb1;
        } else {
            r = mr2 * x + br2;
            g = mg2 * x + bg2;
            b = mb2 * x + bb2;
        }

        return new Vector3f(r, g, b);
    }

}
