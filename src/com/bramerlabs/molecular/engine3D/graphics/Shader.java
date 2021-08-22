package com.bramerlabs.molecular.engine3D.graphics;

import com.bramerlabs.molecular.engine3D.graphics.io.file_util.FileLoader;
import com.bramerlabs.molecular.engine3D.math.matrix.Matrix4f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector2f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class Shader {

    private final String vertexFile, fragmentFile;
    private int vertexID, fragmentID, programID;

    public Shader(String vertexPath, String fragmentPath) {
        this.vertexFile = FileLoader.load(vertexPath);
        this.fragmentFile = FileLoader.load(fragmentPath);
    }

    public void create() {
        programID = GL20.glCreateProgram();
        vertexID = createShader(GL20.GL_VERTEX_SHADER, vertexFile);
        fragmentID = createShader(GL20.GL_FRAGMENT_SHADER, fragmentFile);
        GL20.glAttachShader(programID, vertexID);
        GL20.glAttachShader(programID, fragmentID);
        linkProgram(programID);
        validateProgram(programID);
    }

    private static int createShader(int type, String file) {
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, file);
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE) {
            System.err.println("Could not compile shader.");
            System.err.println("Shader: " + GL20.glGetShaderInfoLog(shaderID));
            throw new RuntimeException();
        }
        return shaderID;
    }

    private void linkProgram(int programID) {
        GL20.glLinkProgram(programID);
        if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL20.GL_FALSE) {
            System.err.println("Program could not be linked.");
            System.err.println("Program: " + GL20.glGetProgramInfoLog(programID));
            throw new RuntimeException();
        }
    }

    private void validateProgram(int programID) {
        GL20.glValidateProgram(programID);
        if (GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == GL20.GL_FALSE) {
            System.err.println("Program could not be validated.");
            System.err.println("Program: " + GL20.glGetProgramInfoLog(programID));
            throw new RuntimeException();
        }
    }

    public int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(programID, uniformName);
    }

    public void setUniform(String name, float value) {
        GL20.glUniform1f(getUniformLocation(name), value);
    }

    public void setUniform(String name, Vector2f value) {
        GL20.glUniform2f(getUniformLocation(name), value.x, value.y);
    }

    public void setUniform(String name, Vector3f value) {
        GL20.glUniform3f(getUniformLocation(name), value.x, value.y, value.z);
    }

    public void setUniform(String name, Vector4f value) {
        GL20.glUniform4f(getUniformLocation(name), value.x, value.y, value.z, value.w);
    }

    public void setUniform(String name, Matrix4f value) {
        FloatBuffer matrix = MemoryUtil.memAllocFloat(Matrix4f.SIZE * Matrix4f.SIZE);
        matrix.put(value.getAll()).flip();
        GL20.glUniformMatrix4fv(getUniformLocation(name), true, matrix);
    }

    public void bind() {
        GL20.glUseProgram(programID);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    public void destroy() {
        GL20.glDeleteShader(vertexID);
        GL20.glDeleteShader(fragmentID);
        GL20.glDeleteShader(programID);
    }

}
