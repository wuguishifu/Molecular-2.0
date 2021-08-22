package com.bramerlabs.molecular.engine3D.graphics.mesh;

import com.bramerlabs.molecular.engine3D.graphics.vertex.Vertex;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract class Mesh {

    protected Vertex[] vertices;
    protected int[] indices;

    protected int
    vao, // vertex array object
    pbo, // position buffer
    nbo, // normal buffer
    tan, // tangent buffer
    bbo, // bitangent buffer
    tbo, // texture coord buffer
    cbo, // color buffer
    ibo; // index array buffer

    public static final int
    POSITION = 0,
    NORMAL = 1,
    COLOR = 2,
    TANGENT = 2,
    BITANGENT = 3,
    TEXTURE_COORD = 4;

    public Mesh(Vertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public abstract void create();

    protected void makePositionBuffer() {
        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        float[] positionData = new float[vertices.length * 3];
        for (int i = 0; i < vertices.length; i++) {
            positionData[3 * i    ] = vertices[i].getPosition().x;
            positionData[3 * i + 1] = vertices[i].getPosition().y;
            positionData[3 * i + 2] = vertices[i].getPosition().z;
        }
        positionBuffer.put(positionData).flip();
        pbo = storeData(positionBuffer, POSITION, 3);
    }

    protected void makeNormalBuffer() {
        FloatBuffer normalBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        float[] normalData = new float[vertices.length * 3];
        for (int i = 0; i < vertices.length; i++) {
            normalData[3 * i    ] = vertices[i].getNormal().x;
            normalData[3 * i + 1] = vertices[i].getNormal().y;
            normalData[3 * i + 2] = vertices[i].getNormal().z;
        }
        normalBuffer.put(normalData).flip();
        nbo = storeData(normalBuffer, NORMAL, 3);
    }

    protected static int storeData(FloatBuffer buffer, int index, int size) {
        int bufferID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        return bufferID;
    }

    protected void makeIndexBuffer() {
        IntBuffer indexBuffer = MemoryUtil.memAllocInt(indices.length);
        indexBuffer.put(indices).flip();
        ibo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void destroy() {
        GL15.glDeleteBuffers(pbo);
        GL15.glDeleteBuffers(tbo);
        GL15.glDeleteBuffers(ibo);
        GL15.glDeleteBuffers(tan);
        GL15.glDeleteBuffers(bbo);
        GL15.glDeleteBuffers(cbo);
        GL30.glDeleteVertexArrays(vao);
    }

    public Vertex[] getVertices() {
        return this.vertices;
    }

    public int[] getIndices() {
        return this.indices;
    }

    public int getVao() {
        return vao;
    }

    public int getPbo() {
        return pbo;
    }

    public int getNbo() {
        return nbo;
    }

    public int getTan() {
        return tan;
    }

    public int getBbo() {
        return bbo;
    }

    public int getTbo() {
        return tbo;
    }

    public int getCbo() {
        return cbo;
    }

    public int getIbo() {
        return ibo;
    }
}
