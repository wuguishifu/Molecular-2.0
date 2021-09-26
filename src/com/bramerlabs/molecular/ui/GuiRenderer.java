package com.bramerlabs.molecular.ui;

import com.bramerlabs.molecular.engine3D.graphics.Shader;

public class GuiRenderer {

    private Shader shader;

    public GuiRenderer() {
        this.shader = new Shader("shaders/gui/vertex.glsl", "shaders/gui/fragment.glsl");
        this.shader.create();
    }

    public void renderMesh() {

    }

}
