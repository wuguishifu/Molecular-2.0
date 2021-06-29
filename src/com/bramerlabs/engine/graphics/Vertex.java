package com.bramerlabs.engine.graphics;

import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;

public class Vertex {

    // the position of this vertex
    private Vector3f position;

    // vectors normal, tangent, and bitangent to this vertex
    private Vector3f normal, tangent, bitangent;

    // if this vertex has a tangent or bitangent assigned to it
    private boolean hasTangent;
    private boolean hasBitangent;

    // the texture coordinate of this vertex
    private Vector2f textureCoord;

    // color of this vertex
    private Vector4f color;

    /**
     * default constructor
     * @param position - the position of this vertex
     * @param textureCoord - the texture coordinate of this vertex
     * @param normal - a vector normal to this vertex
     * @param tangent - a vector tangent to this vertex
     * @param bitangent - a vector bitangent to this vertex
     */
    public Vertex(Vector3f position, Vector2f textureCoord, Vector3f normal, Vector3f tangent, Vector3f bitangent) {
        this.position = position;
        this.textureCoord = textureCoord;
        this.normal = normal;
        this.tangent = tangent;
        this.bitangent = bitangent;

        // default color, unused for texture vertices
        this.color = new Vector4f(1, 1, 1, 1);

        this.hasTangent = true;
        this.hasBitangent = true;
    }

    /**
     * constructor for position, texture coord, and normal
     * @param position - the position of this vertex
     * @param textureCoord - the texture coordinate of this vertex
     * @param normal - a vector normal to this vertex
     */
    public Vertex(Vector3f position, Vector2f textureCoord, Vector3f normal) {
        this.position = position;
        this.textureCoord = textureCoord;
        this.normal = normal;

        // default color, unused for texture vertices
        this.color = new Vector4f(1, 1, 1, 1);

        this.hasTangent = false;
        this.hasBitangent = false;
    }

    /**
     * constructor for position, color, and normal
     * @param position - the position of this vertex
     * @param color - the color of this vertex
     * @param normal - the vector normal to this vertex
     */
    public Vertex(Vector3f position, Vector4f color, Vector3f normal) {
        this.position = position;
        this.color = color;
        this.normal = normal;

        // default tangent and bitangent, unused for color vertices
        this.tangent = new Vector3f(0);
        this.bitangent = new Vector3f(0);
        this.textureCoord = new Vector2f(0);

        this.hasTangent = true;
        this.hasBitangent = true;
    }

    /**
     * sets the tangent vector
     * @param tangent - the new tangent vector
     */
    public void setTangent(Vector3f tangent) {
        this.tangent = tangent;
    }

    /**
     * sets the bitangent vector
     * @param bitangent - the new bitangent vector
     */
    public void setBitangent(Vector3f bitangent) {
        this.bitangent = bitangent;
    }

    /**
     * getter method
     * @return - if this vertex has a tangent vector
     */
    public boolean hasTangent() {
        return hasTangent;
    }

    /**
     * getter method
     * @return - if this vertex has a bitangent vector
     */
    public boolean hasBitangent() {
        return this.hasBitangent;
    }

    /**
     * getter method
     * @return - the position of this vertex
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * getter method
     * @return - the vector normal to this vertex
     */
    public Vector3f getNormal() {
        return normal;
    }

    /**
     * getter method
     * @return - the vector tangent to this vertex
     */
    public Vector3f getTangent() {
        return tangent;
    }

    /**
     * getter method
     * @return - the vector bitangent to this vertex
     */
    public Vector3f getBitangent() {
        return bitangent;
    }

    /**
     * getter method
     * @return - the texture coord of this vertex
     */
    public Vector2f getTextureCoord() {
        return textureCoord;
    }

    /**
     * getter method
     * @return - the color of this vertex
     */
    public Vector4f getColor() {
        return this.color;
    }
}
