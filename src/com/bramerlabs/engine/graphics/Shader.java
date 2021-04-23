package com.bramerlabs.engine.graphics;

import com.bramerlabs.engine.io.file_util.FileUtils;
import com.bramerlabs.engine.math.matrix.Matrix4f;
import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class Shader {

    // the number of attributes this shader has
    private static final int NUM_ATTRIBS = 2;

    // the vertex and fragment shaders
    private String vertexFile, fragmentFile;

    // the pointers to the shader programs
    private int vertexID, fragmentID, programID;

    /**
     * default constructor for the shader from specified path to vertex and fragment shader
     * @param pathToVertexShader - the path to the vertex shader
     * @param pathToFragmentShader - the path to the fragment shader
     */
    public Shader(String pathToVertexShader, String pathToFragmentShader) {
        this.vertexFile = FileUtils.loadAsString(pathToVertexShader);
        this.fragmentFile = FileUtils.loadAsString(pathToFragmentShader);
    }

    /**
     * initializes the shader
     * @return - this shader
     */
    public Shader create() {
        programID = GL20.glCreateProgram();

        // create the shaders
        vertexID = createShader(GL20.GL_VERTEX_SHADER, vertexFile);
        fragmentID = createShader(GL20.GL_FRAGMENT_SHADER, fragmentFile);

        // attach shaders to the program
        GL20.glAttachShader(programID, vertexID);
        GL20.glAttachShader(programID, fragmentID);

        // link the program
        linkProgram(programID);
        validateProgram(programID);

        return this;
    }

    /**
     * creates a shader
     * @param shaderType - the type of shader to create
     * @param shaderFile - the file of the shader
     * @return - the shader program id
     */
    private static int createShader(int shaderType, String shaderFile) {
        // create an id
        int shaderID = GL20.glCreateShader(shaderType);

        // point to the shader source
        GL20.glShaderSource(shaderID, shaderFile);

        // compile shader
        GL20.glCompileShader(shaderID);

        // check to see if compilation was successful
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE) {
            System.err.println("Error: could not compile shader.");
            System.err.println("Shader: " + GL20.glGetShaderInfoLog(shaderID));
            throw new RuntimeException();
        }

        return shaderID;
    }

    /**
     * links the program
     * @param programID - the program id
     */
    private void linkProgram(int programID) {
        // attempt to link the program
        GL20.glLinkProgram(programID);

        // error catching
        if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            System.err.println("Error: program could not be linked.");
            System.err.println("Program: " + GL20.glGetProgramInfoLog(programID));
            throw new RuntimeException();
        }
    }

    /**
     * validate the program with OpenGL
     * @param programID - the program id
     */
    private void validateProgram(int programID) {
        // attempt to validate the program
        GL20.glValidateProgram(programID);

        // error catching
        if (GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Error: program could not be validated.");
            System.err.println("Program: " + GL20.glGetProgramInfoLog(programID));
            throw new RuntimeException();
        }
    }

    /**
     * getter method
     * @param uniformName - the name of the uniform
     * @return - the location of the uniform
     */
    public int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(programID, uniformName);
    }

    /**
     * sets the uniform to a float value
     * @param name - the name of the uniform
     * @param value - the value of the uniform
     */
    public void setUniform(String name, float value) {
        GL20.glUniform1f(getUniformLocation(name), value);
    }

    /**
     * sets the uniform to an int value
     * @param name - the name of the uniform
     * @param value - the value
     */
    public void setUniform(String name, int value) {
        GL20.glUniform1i(getUniformLocation(name), value);
    }

    /**
     * sets the uniform to a boolean value (converts boolean to 1 or 0)
     * @param name - the name of the uniform
     * @param value - the value of the uniform
     */
    public void setUniform(String name, boolean value) {
        GL20.glUniform1f(getUniformLocation(name), value ? 1.0f : 0.0f);
    }

    /**
     * sets the uniform to a vec2 value
     * @param name - the name of the uniform
     * @param value - the value of the uniform
     */
    public void setUniform(String name, Vector2f value) {
        GL20.glUniform2f(getUniformLocation(name), value.getX(), value.getY());
    }

    /**
     * sets the uniform to a vec3 value
     * @param name - the name of the uniform
     * @param value - the value of the uniform
     */
    public void setUniform(String name, Vector3f value) {
        GL20.glUniform3f(getUniformLocation(name), value.getX(), value.getY(), value.getZ());
    }

    /**
     * sets the uniform to a vec4 value
     * @param name - the name of the uniform
     * @param value - the value of the uniform
     */
    public void setUniform(String name, Vector4f value) {
        GL20.glUniform4f(getUniformLocation(name), value.getX(), value.getY(), value.getZ(), value.getW());
    }

    /**
     * sets the uniform to a mat4 value
     * @param name - the name of the uniform
     * @param value - the value of the uniform
     */
    public void setUniform(String name, Matrix4f value) {
        // preallocate memory for the float values
        FloatBuffer matrix = MemoryUtil.memAllocFloat(Matrix4f.SIZE * Matrix4f.SIZE);

        // put the values flipped of the matrix into the float buffer
        matrix.put(value.getAll()).flip();

        // transpose is true because we create it in row major order instead of column major order
        GL20.glUniformMatrix4fv(getUniformLocation(name), true, matrix);
    }

    /**
     * binds the gl program to the application
     */
    public void bind() {
        GL20.glUseProgram(programID);
    }

    /**
     * unbinds the gl program
     */
    public void unbind() {
        GL20.glUseProgram(0);
    }

    /**
     * releases the shaders
     */
    public void destroy() {
        // delete the shaders because we now have a program that does the shader stuff
        GL20.glDeleteShader(vertexID);
        GL20.glDeleteShader(fragmentID);

        // delete the shader program
        GL20.glDeleteShader(programID);
    }

}
