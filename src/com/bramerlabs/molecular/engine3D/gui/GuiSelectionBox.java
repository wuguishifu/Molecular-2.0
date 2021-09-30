package com.bramerlabs.molecular.engine3D.gui;

import com.bramerlabs.molecular.engine3D.math.vector.Vector2f;

public class GuiSelectionBox {

    private Vector2f position, size;

    public GuiSelectionBox(Vector2f position, Vector2f size) {
        this.position = position;
        this.size = size;
    }

    public boolean inBounds(float x, float y) {
        return x >= position.x && x <= position.x + size.x && y >= position.y && y <= position.y + size.y;
    }

}
