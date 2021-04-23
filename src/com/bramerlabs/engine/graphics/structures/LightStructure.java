package com.bramerlabs.engine.graphics.structures;

import com.bramerlabs.engine.math.vector.Vector3f;

public class LightStructure {

    /**
     * the structure attributes
     */
    private final Vector3f lightPosition;
    private final Vector3f lightColor;
    private final float lightLevel;

    /**
     * default constructor
     * @param lightPosition - the position of the light source
     * @param lightColor - the color of the light
     * @param lightLevel - the light level
     */
    public LightStructure(Vector3f lightPosition, Vector3f lightColor, float lightLevel) {
        this.lightPosition = lightPosition;
        this.lightColor = lightColor;
        this.lightLevel = lightLevel;
    }

    /**
     * getter method
     * @return - the position of the light source
     */
    public Vector3f getLightPosition() {
        return lightPosition;
    }

    /**
     * getter method
     * @return - the color of the light
     */
    public Vector3f getLightColor() {
        return lightColor;
    }

    /**
     * getter method
     * @return - the light level
     */
    public float getLightLevel() {
        return lightLevel;
    }
}
