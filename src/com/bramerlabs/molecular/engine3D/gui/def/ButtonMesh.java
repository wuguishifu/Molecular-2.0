package com.bramerlabs.molecular.engine3D.gui.def;

import com.bramerlabs.molecular.engine3D.gui.GuiMesh;
import com.bramerlabs.molecular.engine3D.gui.GuiVertex;
import com.bramerlabs.molecular.engine3D.math.vector.Vector2f;

public class ButtonMesh extends GuiMesh {

    public ButtonMesh(Vector2f position, Vector2f size) {
        super(
                new GuiVertex[]{
                        new GuiVertex(position.x, position.y, 0, 0),
                        new GuiVertex(position.x + size.x, position.y, 1, 0),
                        new GuiVertex(position.x + size.x, position.y + size.y, 1, 1),
                        new GuiVertex(position.x, position.y + size.y, 0, 1)
                },
                new int[]{
                        0, 1, 2,
                        0, 2, 3
                },
                null
        );
    }

}
