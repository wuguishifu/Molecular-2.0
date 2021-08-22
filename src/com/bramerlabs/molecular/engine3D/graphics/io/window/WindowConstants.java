package com.bramerlabs.molecular.engine3D.graphics.io.window;

import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;

import java.awt.*;

public class WindowConstants {

    public String name;
    public Vector3f backgroundColor;

    public WindowConstants(String name, Color backgroundColor) {
        this.name = name;
        this.backgroundColor = new Vector3f(backgroundColor);
    }

}
