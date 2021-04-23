package com.bramerlabs.engine.graphics.structures;

public class MaterialStructure {

    /**
     * the attributes of this structured material
     */
    private final int reflectiveness;
    private final float specularStrength;
    private final float roughness;

    /**
     * default constructor for specified values
     * @param reflectiveness - the reflectiveness of this material
     * @param specularStrength - the specular strength of this material
     * @param roughness - the roughness of this material
     */
    public MaterialStructure(int reflectiveness, float specularStrength, float roughness) {
        this.reflectiveness = reflectiveness;
        this.specularStrength = specularStrength;
        this.roughness = roughness;
    }

    /**
     * getter method
     * @return - the reflectiveness of this material
     */
    public int getReflectiveness() {
        return this.reflectiveness;
    }

    /**
     * getter method
     * @return - the specular strength of this material
     */
    public float getSpecularStrength() {
        return this.specularStrength;
    }

    /**
     * getter method
     * @return - the roughness of this material
     */
    public float getRoughness() {
        return this.roughness;
    }

}
