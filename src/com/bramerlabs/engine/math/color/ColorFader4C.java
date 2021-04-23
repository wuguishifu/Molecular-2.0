package com.bramerlabs.engine.math.color;

import com.bramerlabs.engine.math.vector.Vector3f;

import java.awt.*;

public class ColorFader4C {

    private float[] c1, c2, c3, c4;

    // slopes
    private float mr1, mr2, mr3;
    private float mg1, mg2, mg3;
    private float mb1, mb2, mb3;

    // offsets
    private float br1, br2, br3;
    private float bg1, bg2, bg3;
    private float bb1, bb2, bb3;

    // dx values
    private float x1 = -0.5f, x2 = 0.0f, x3 = 0.5f, x4 = 1.0f;

    /**
     * default constructor
     * @param c_1 - color 1
     * @param c_2 - color 2
     * @param c_3 - color 3
     * @param c_4 - color 4
     */
    public ColorFader4C(Color c_1, Color c_2, Color c_3, Color c_4, float x2, float x3) {
        this.x2 = x2;
        this.x3 = x3;

        c1 = new float[]{c_1.getRed(), c_1.getGreen(), c_1.getBlue()};
        c2 = new float[]{c_2.getRed(), c_2.getGreen(), c_2.getBlue()};
        c3 = new float[]{c_3.getRed(), c_3.getGreen(), c_3.getBlue()};
        c4 = new float[]{c_4.getRed(), c_4.getGreen(), c_4.getBlue()};

        // calculate the slopes (9)
        this.mr1 = (c2[0] - c1[0]) / (x2 - x1); // reds
        this.mr2 = (c3[0] - c2[0]) / (x3 - x2);
        this.mr3 = (c4[0] - c3[0]) / (x4 - x3);
        this.mg1 = (c2[1] - c1[1]) / (x2 - x1); // greens
        this.mg2 = (c3[1] - c2[1]) / (x3 - x2);
        this.mg3 = (c4[1] - c3[1]) / (x4 - x3);
        this.mb1 = (c2[2] - c1[2]) / (x2 - x1); // blues
        this.mb2 = (c3[2] - c2[2]) / (x3 - x2);
        this.mb3 = (c4[2] - c3[2]) / (x4 - x3);

        // calculate the offsets
        this.br1 = c1[0]; // reds
        this.br2 = c2[0];
        this.br3 = c3[0];
        this.bg1 = c1[1]; // greens
        this.bg2 = c2[1];
        this.bg3 = c3[1];
        this.bb1 = c1[2]; // blues
        this.bb2 = c2[2];
        this.bb3 = c3[2];
    }

    /**
     * default constructor
     * @param c_1 - color 1
     * @param c_2 - color 2
     * @param c_3 - color 3
     * @param c_4 - color 4
     */
    public ColorFader4C(Color c_1, Color c_2, Color c_3, Color c_4) {
        c1 = new float[]{c_1.getRed(), c_1.getGreen(), c_1.getBlue()};
        c2 = new float[]{c_2.getRed(), c_2.getGreen(), c_2.getBlue()};
        c3 = new float[]{c_3.getRed(), c_3.getGreen(), c_3.getBlue()};
        c4 = new float[]{c_4.getRed(), c_4.getGreen(), c_4.getBlue()};

        // calculate the slopes (9)
        this.mr1 = (c2[0] - c1[0]) / (x2 - x1); // reds
        this.mr2 = (c3[0] - c2[0]) / (x3 - x2);
        this.mr3 = (c4[0] - c3[0]) / (x4 - x3);
        this.mg1 = (c2[1] - c1[1]) / (x2 - x1); // greens
        this.mg2 = (c3[1] - c2[1]) / (x3 - x2);
        this.mg3 = (c4[1] - c3[1]) / (x4 - x3);
        this.mb1 = (c2[2] - c1[2]) / (x2 - x1); // blues
        this.mb2 = (c3[2] - c2[2]) / (x3 - x2);
        this.mb3 = (c4[2] - c3[2]) / (x4 - x3);

        // calculate the offsets
        this.br1 = c1[0]; // reds
        this.br2 = c2[0];
        this.br3 = c3[0];
        this.bg1 = c1[1]; // greens
        this.bg2 = c2[1];
        this.bg3 = c3[1];
        this.bb1 = c1[2]; // blues
        this.bb2 = c2[2];
        this.bb3 = c3[2];
    }

    /**
     * gets a color
     * @param x - the x value
     * @return - the color
     */
    public Vector3f getColor(float x) {
        float r, g, b;

        // calculate the components
        if (x < x2) {
            r = mr1 * x + br1;
            g = mg1 * x + bg1;
            b = mb1 * x + bb1;
        } else if (x < x3) {
            r = mr2 * x + br2;
            g = mg2 * x + bg2;
            b = mb2 * x + bb2;
        } else {
            r = mr3 * x + br3;
            g = mg3 * x + bg3;
            b = mb3 * x + bb3;
        }

        return Vector3f.divide(new Vector3f(r, g, b), new Vector3f(255));

    }

}
