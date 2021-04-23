package com.bramerlabs.engine.graphics;

import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.math.vector.Vector3f;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class Mesh {

    // the vertices and indices of this mesh
    private Vertex[] vertices;
    private int[] indices;

    // the material this mesh is made of
    private Material material;

    // the vertex attribute objects
    private int
        vao, // vertex array
        pbo, // position buffer
        nbo, // normal buffer
        tan, // tangent buffer
        bbo, // bitangent buffer
        tbo, // texture buffer
        ibo, // index buffer
        cbo; // color buffer

    /**
     * default constructor
     * @param vertices - the vertices of this mesh
     * @param indices - the indices of this mesh
     * @param material - the material this mesh is made of
     */
    public Mesh(Vertex[] vertices, int[] indices, Material material) {
        this.vertices = vertices;
        this.indices = indices;
        this.material = material;
    }

    /**
     * constructor for specified list of vertices
     * @param vertices - the list of vertices
     */
    public Mesh(ArrayList<Vertex> vertices) {
        this.vertices = new Vertex[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            this.vertices[i] = vertices.get(i);
        }

        this.indices = new int[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            this.indices[i] = i;
        }
    }

    /**
     * creates the mesh
     */
    public void create() {
        // generate and bind the vertex array
        vao = GL46.glGenVertexArrays();
        GL46.glBindVertexArray(vao);

        // create the buffers
        makePositionBuffer(); // position
        makeNormalBuffer();   // normals
        makeTextureBuffer();  // texture coords
        makeTangentBuffer();  // tangents and bitangents
        makeColorBuffer();

        // make the draw order buffer
        makeIndexBuffer();
    }

    /**
     * helper method to create the position buffer object
     */
    private void makePositionBuffer() {
        // preallocate memory
        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);

        // create a new temp array to store position data
        float[] positionData = new float[vertices.length * 3];

        // add all the position data to the temp array
        for (int i = 0; i < vertices.length; i++) {
            positionData[i * 3    ] = vertices[i].getPosition().getX();
            positionData[i * 3 + 1] = vertices[i].getPosition().getY();
            positionData[i * 3 + 2] = vertices[i].getPosition().getZ();
        }

        // flip the data to make it handleable by OpenGL
        positionBuffer.put(positionData).flip();

        // store the position data in the position buffer object
        pbo = storeData(positionBuffer, 0, 3);
    }

    /**
     * helper method to create a buffer object for the vector normal to the surface at every vertex
     */
    public void makeNormalBuffer() {
        FloatBuffer normalBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        float[] normalData = new float[vertices.length * 3];
        for (int i = 0; i < vertices.length; i++) {
            normalData[i * 3] = vertices[i].getNormal().getX();
            normalData[i * 3 + 1] = vertices[i].getNormal().getY();
            normalData[i * 3 + 2] = vertices[i].getNormal().getZ();
        }
        normalBuffer.put(normalData).flip();

        nbo = storeData(normalBuffer, 2, 3);
    }

    /**
     * helper method to create the texture buffer object
     */
    private void makeTextureBuffer() {
        // preallocate memory
        FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(vertices.length * 2);

        // create a new temp array to store texture coord data
        float[] textureData = new float[vertices.length * 2];

        // add all the texture coord data to the temp array
        for (int i = 0; i < vertices.length; i++) {
            textureData[i * 2    ] = vertices[i].getTextureCoord().getX();
            textureData[i * 2 + 1] = vertices[i].getTextureCoord().getY();
        }

        // flip the data to make it handleable by OpenGL
        textureBuffer.put(textureData).flip();

        // store the texture coord data in the texture buffer object
        tbo = storeData(textureBuffer, 1, 2);
    }

    /**
     * creates tangent buffer
     */
    private void makeTangentBuffer() {

        // iterate over all triangles
        for (int i = 0; i < indices.length; i += 3) {
            // get the vertices in this triangle
            Vertex v1 = vertices[indices[i    ]];
            Vertex v2 = vertices[indices[i + 1]];
            Vertex v3 = vertices[indices[i + 2]];

            // calculate the edge vectors
            Vector3f edge1 = Vector3f.subtract(v2.getPosition(), v1.getPosition());
            Vector3f edge2 = Vector3f.subtract(v3.getPosition(), v1.getPosition());

            // calculate the difference in texture coord
            Vector2f deltaUV1 = Vector2f.subtract(v2.getTextureCoord(), v1.getTextureCoord());
            Vector2f deltaUV2 = Vector2f.subtract(v3.getTextureCoord(), v1.getTextureCoord());

            // calculate fractional f
            float f = 1.0f / (deltaUV1.getX() * deltaUV2.getY() - deltaUV2.getX() * deltaUV1.getY());

            float tx = f * (deltaUV2.getY() * edge1.getX() - deltaUV1.getY() * edge2.getX());
            float ty = f * (deltaUV2.getY() * edge1.getY() - deltaUV1.getY() * edge2.getY());
            float tz = f * (deltaUV2.getY() * edge1.getZ() - deltaUV1.getY() * edge2.getZ());

            float bx = f * (-deltaUV2.getX() * edge1.getX() + deltaUV1.getX() * edge2.getX());
            float by = f * (-deltaUV2.getX() * edge1.getY() + deltaUV1.getX() * edge2.getY());
            float bz = f * (-deltaUV2.getX() * edge1.getZ() + deltaUV1.getX() * edge2.getZ());

            Vector3f tangent = new Vector3f(tx, ty, tz);
            Vector3f bitangent = new Vector3f(bx, by, bz);

            // set the tangents
            if (!v1.hasTangent()) {
                v1.setTangent(tangent);
            }
            if (!v2.hasTangent()) {
                v2.setTangent(tangent);
            }
            if (!v3.hasTangent()) {
                v3.setTangent(tangent);
            }

            // set the bitangents
            if (!v1.hasBitangent()) {
                v1.setBitangent(bitangent);
            }
            if (!v2.hasBitangent()) {
                v2.setBitangent(bitangent);
            }
            if (!v3.hasBitangent()) {
                v3.setBitangent(bitangent);
            }
        }

        // preallocate memory
        FloatBuffer tangentBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        FloatBuffer bitangentBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);

        // create new temp array to store data
        float[] tangentData = new float[vertices.length * 3];
        float[] bitangentData = new float[vertices.length * 3];

        // add all the tangent and bitangent ata
        for (int i = 0; i < vertices.length; i ++) {
            tangentData[i * 3    ] = vertices[i].getTangent().getX();
            tangentData[i * 3 + 1] = vertices[i].getTangent().getY();
            tangentData[i * 3 + 2] = vertices[i].getTangent().getZ();
            bitangentData[i * 3    ] = vertices[i].getBitangent().getX();
            bitangentData[i * 3 + 1] = vertices[i].getBitangent().getY();
            bitangentData[i * 3 + 2] = vertices[i].getBitangent().getZ();
        }

        // flip the data to make it handleable by OpenGL
        tangentBuffer.put(tangentData).flip();
        bitangentBuffer.put(bitangentData).flip();

        // store the data in the buffer object
        this.tan = storeData(tangentBuffer, 3, 3);
        this.bbo = storeData(bitangentBuffer, 4, 3);
    }

    /**
     * helper method to create the color buffer object
     */
    public void makeColorBuffer() {
        FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(vertices.length * 4);
        float[] colorData = new float[vertices.length * 4];
        for (int i = 0; i < vertices.length; i++) {
            colorData[i * 4] = vertices[i].getColor().getX();
            colorData[i * 4 + 1] = vertices[i].getColor().getY();
            colorData[i * 4 + 2] = vertices[i].getColor().getZ();
            colorData[i * 4 + 3] = vertices[i].getColor().getW();
        }
        colorBuffer.put(colorData).flip();

        cbo = storeData(colorBuffer, 5, 4);
    }

    /**
     * helper method to create the index buffer object
     */
    private void makeIndexBuffer() {
        // preallocate memory
        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);

        // put the indices into the index buffer flipped
        indicesBuffer.put(indices).flip();

        // generate a buffer object
        ibo = GL15.glGenBuffers();

        // bind the buffer object
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);

        // add the index data
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);

        // unbind the buffer object
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    /**
     * helper method to store data in a certain buffer object
     * @param buffer - the float buffer containing the float data
     * @param index - the index of the data
     * @param size - the size of the data
     * @return - the buffer ID
     */
    private int storeData(FloatBuffer buffer, int index, int size) {
        // generate a buffer ID
        int bufferID = GL15.glGenBuffers();

        // bind the buffer
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);

        // add the data from the buffer to the gl buffer object
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        // set the GL attribute
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);

        // unbind the buffer
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        return bufferID;
    }

    /**
     * getter method
     * @return - the vertices of this mesh
     */
    public Vertex[] getVertices() {
        return this.vertices;
    }

    /**
     * getter method
     * @return - the indices of this mesh
     */
    public int[] getIndices() {
        return this.indices;
    }

    /**
     * getter method
     * @return - the material this mesh is made of
     */
    public Material getMaterial() {
        return this.material;
    }

    /**
     * getter method
     * @return - the vertex array object
     */
    public int getVAO() {
        return this.vao;
    }

    /**
     * getter method
     * @return - the position buffer object
     */
    public int getPBO() {
        return this.pbo;
    }

    /**
     * getter method
     * @return - the texture buffer object
     */
    public int getCBO() {
        return this.cbo;
    }

    /**
     * getter method
     * @return - the normal vector buffer object
     */
    public int getNBO() {
        return this.nbo;
    }

    /**
     * getter method
     * @return - the tangent buffer object
     */
    public int getTAN() {
        return this.tbo;
    }

    /**
     * getter method
     * @return - the bitangent buffer object
     */
    public int getBBO() {
        return this.bbo;
    }

    /**
     * getter method
     * @return - the index buffer object
     */
    public int getIBO() {
        return this.ibo;
    }

    /**
     * release the mesh
     */
    public void destroy() {
        // delete the buffers
        GL15.glDeleteBuffers(pbo);
        GL15.glDeleteBuffers(tbo);
        GL15.glDeleteBuffers(ibo);
        GL15.glDeleteBuffers(tan);
        GL15.glDeleteBuffers(bbo);

        // delete the vertex array
        GL30.glDeleteVertexArrays(vao);
    }

}
