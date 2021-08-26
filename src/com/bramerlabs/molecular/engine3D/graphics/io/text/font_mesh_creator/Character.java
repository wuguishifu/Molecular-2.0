package com.bramerlabs.molecular.engine3D.graphics.io.text.font_mesh_creator;

public class Character {

    // the ID of the character
    public int id;

    // the texture coords
    private double xTexCoord, yTexCoord;
    private double xMaxTexCoord, yMaxTexCoord;

    // the offset values
    private double xOffset, yOffset;

    // the size
    private double sizeX, sizeY;

    // how much to advance by to space the characters correctly
    private double xAdvance;

    /**
     * constructs a character
     * @param id - the ASCII value of the character
     * @param xTexCoord - the x texture coordinate for the top left corner of the character in the texture atlas
     * @param yTexCoord - the y texture coordinate for the top left corner of the character in the texture atlas
     * @param xTexSize - the width of the character in the texture atlas
     * @param yTexSize - the height of the character in the texture atlas
     * @param xOffset - the x distance from the cursor to the left edge of the character's quad
     * @param yOffset - the y distance from the cursor to the top edge of the character's quad
     * @param sizeX - the width of the character's quad in screen space
     * @param sizeY - the height of the character's quad in screen space
     * @param xAdvance - how far in pixels the cursor should advance after adding this character
     */
    protected Character(int id, double xTexCoord, double yTexCoord, double xTexSize, double yTexSize,
                        double xOffset, double yOffset, double sizeX, double sizeY, double xAdvance) {
        this.id = id;
        this.xTexCoord = xTexCoord;
        this.yTexCoord = yTexCoord;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.xMaxTexCoord = xTexSize + xTexCoord;
        this.yMaxTexCoord = yTexSize + yTexCoord;
        this.xAdvance = xAdvance;
    }

    /**
     * getter method
     * @return - the ASCII value of this character
     */
    protected int getID() {
        return this.id;
    }

    /**
     * getter method
     * @return - the x texture coord
     */
    protected double getXTexCoord() {
        return this.xTexCoord;
    }

    /**
     * getter method
     * @return - the y texture coord
     */
    protected double getYTexCoord() {
        return this.yTexCoord;
    }

    /**
     * getter method
     * @return - the max x texture coord
     */
    public double getXMaxTexCoord() {
        return xMaxTexCoord;
    }

    /**
     * getter method
     * @return - the max y texture coord
     */
    public double getYMaxTexCoord() {
        return yMaxTexCoord;
    }

    /**
     * getter method
     * @return - the x offset
     */
    public double getXOffset() {
        return xOffset;
    }

    /**
     * getter method
     * @return - the y offset
     */
    public double getYOffset() {
        return yOffset;
    }

    /**
     * getter method
     * @return - the width of the character
     */
    public double getSizeX() {
        return sizeX;
    }

    /**
     * getter method
     * @return - the height of the character
     */
    public double getSizeY() {
        return sizeY;
    }

    /**
     * getter method
     * @return - the amount the cursor should advance
     */
    public double getXAdvance() {
        return xAdvance;
    }
}
