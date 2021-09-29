package com.bramerlabs.molecular.engine3D.gui;

import com.bramerlabs.molecular.engine3D.graphics.vertex.Vertex;
import com.bramerlabs.molecular.engine3D.math.vector.Vector2f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;

public class GuiVertex extends Vertex {

    protected Vector2f textureCoord;

    public GuiVertex(Vector3f position, Vector2f textureCoord) {
        super(position, new Vector3f(1, 0, 0));
        this.textureCoord = textureCoord;
    }

    public GuiVertex(float px, float py, float tx, float ty) {
        super(new Vector3f(px, py, 0), new Vector3f(1, 0, 0));
        this.textureCoord = new Vector2f(tx, ty);
    }

    public Vector2f getTextureCoord() {
        return this.textureCoord;
    }

}
