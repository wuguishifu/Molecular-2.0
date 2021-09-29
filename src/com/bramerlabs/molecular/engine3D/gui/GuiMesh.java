package com.bramerlabs.molecular.engine3D.gui;

import com.bramerlabs.molecular.engine3D.graphics.mesh.Mesh;
import org.lwjgl.opengl.GL46;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class GuiMesh extends Mesh {

    public GuiMesh(GuiVertex[] vertices, int[] indices) {
        super(vertices, indices);
    }

    public boolean inBounds(float mouseX, float mouseY) {
        return false;
    }

    @Override
    public void create() {
        vao = GL46.glGenVertexArrays();
        GL46.glBindVertexArray(vao);
        makePositionBuffer();
        makeTextureCoordBuffer();
        makeIndexBuffer();
    }

    public void makeTextureCoordBuffer() {
        FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(vertices.length * 2);
        float[] textureData = new float[vertices.length * 2];
        for (int i = 0; i < vertices.length; i++) {
            GuiVertex vertex = (GuiVertex) vertices[i];
            textureData[2 * i    ] = vertex.getTextureCoord().x;
            textureData[2 * i + 1] = vertex.getTextureCoord().y;
        }
        textureBuffer.put(textureData).flip();
        tbo = storeData(textureBuffer, TEXTURE_COORD, 2);
    }
}
