package com.bramerlabs.molecular.engine3D.graphics.renderers;

import com.bramerlabs.molecular.engine3D.graphics.Camera;
import com.bramerlabs.molecular.engine3D.graphics.Shader;
import com.bramerlabs.molecular.engine3D.graphics.io.window.Window;
import com.bramerlabs.molecular.engine3D.graphics.mesh.Mesh;
import com.bramerlabs.molecular.engine3D.math.matrix.Matrix4f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector4f;
import com.bramerlabs.molecular.engine3D.objects.RenderObject;
import com.bramerlabs.molecular.molecule.atom.Atom;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Renderer {

    protected final Window window;
    protected Vector3f lightPos, lightColor = new Vector3f(1.0f, 1.0f, 1.0f);
    protected float lightLevel = 0.3f, reflectiveness = 32.0f;

    public Renderer(Window window, Vector3f lightPos) {
        this.window = window;
        this.lightPos = lightPos;
    }

    public void renderMesh(RenderObject object, Camera camera, Shader shader) {
        renderColoredMesh(object, camera, shader);
    }

    public void setLightPos(Vector3f lightPos) {
        this.lightPos = lightPos;
    }

    public void setLightColor(Vector3f lightColor) {
        this.lightColor = lightColor;
    }

    public void setLightLevel(float lightLevel) {
        this.lightLevel = lightLevel;
    }

    public void setReflectiveness(float reflectiveness) {
        this.reflectiveness = reflectiveness;
    }

    public Vector3f getLightPos() {
        return this.lightPos;
    }

    public Vector3f getLightColor() {
        return this.lightColor;
    }

    public float getLightLevel() {
        return lightLevel;
    }

    public float getReflectiveness() {
        return reflectiveness;
    }

    private void renderColoredMesh(RenderObject object, Camera camera, Shader shader) {
        GL30.glBindVertexArray(object.getMesh().getVao());
        GL30.glEnableVertexAttribArray(Mesh.POSITION);
        GL30.glEnableVertexAttribArray(Mesh.NORMAL);
        GL30.glEnableVertexAttribArray(Mesh.COLOR);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIbo());
        shader.bind();
        shader.setUniform("vModel", Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));
        shader.setUniform("vView", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("vProjection", window.getProjectionMatrix());
        shader.setUniform("lightPos", lightPos);
        shader.setUniform("lightLevel", lightLevel);
        shader.setUniform("viewPos", camera.getPosition());
        shader.setUniform("lightColor", lightColor);
        shader.setUniform("reflectiveness", reflectiveness); // the value of the specular reflectiveness
        GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        shader.unbind();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(Mesh.POSITION);
        GL30.glDisableVertexAttribArray(Mesh.NORMAL);
        GL30.glDisableVertexAttribArray(Mesh.COLOR);
        GL30.glBindVertexArray(0);
    }

}
