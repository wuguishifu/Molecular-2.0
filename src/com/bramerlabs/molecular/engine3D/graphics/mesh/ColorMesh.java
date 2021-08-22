package com.bramerlabs.molecular.engine3D.graphics.mesh;

import com.bramerlabs.molecular.engine3D.graphics.vertex.ColorVertex;
import org.lwjgl.opengl.GL46;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class ColorMesh extends Mesh {

    public ColorMesh(ColorVertex[] vertices, int[] indices) {
        super(vertices, indices);
    }

    @Override
    public void create() {
        vao = GL46.glGenVertexArrays();
        GL46.glBindVertexArray(vao);
        makePositionBuffer();
        makeNormalBuffer();
        makeColorBuffer();
        makeIndexBuffer();
    }

    public void makeColorBuffer() {
        FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(vertices.length * 4);
        float[] colorData = new float[vertices.length * 4];
        for (int i = 0; i < vertices.length; i++) {
            ColorVertex vertex = (ColorVertex) vertices[i];
            colorData[4 * i    ] = vertex.getColor().x;
            colorData[4 * i + 1] = vertex.getColor().y;
            colorData[4 * i + 2] = vertex.getColor().z;
            colorData[4 * i + 3] = vertex.getColor().w;
        }
        colorBuffer.put(colorData).flip();
        cbo = storeData(colorBuffer, COLOR, 4);
    }

}
