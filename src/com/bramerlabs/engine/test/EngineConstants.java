package com.bramerlabs.engine.test;

import com.bramerlabs.engine.math.vector.Vector3f;

import java.awt.*;

public class EngineConstants {

    /** the application name */
    public static final String APPLICATION_NAME = "Engine";

    /** the window background color */
    public static final Vector3f BACKGROUND_COLOR = new Vector3f(new Color(204, 232, 220)).scale(1.0f/255.0f);
    public static final float
            BACKGROUND_R = BACKGROUND_COLOR.x, // background red component
            BACKGROUND_G = BACKGROUND_COLOR.y, // background green component
            BACKGROUND_B = BACKGROUND_COLOR.z; // background blue component

}
