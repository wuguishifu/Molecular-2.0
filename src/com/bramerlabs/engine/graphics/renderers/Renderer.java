package com.bramerlabs.engine.graphics.renderers;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.structures.LightStructure;
import com.bramerlabs.engine.graphics.structures.MaterialStructure;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.matrix.Matrix4f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import com.bramerlabs.engine.objects.RenderObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Renderer {

    /** render codes */
    public static final int
            LIGHT =     0x1, // light rendering
            TEXTURE =   0x2, // texture rendering
            COLOR =     0x3, // normal rendering
            STRUCTURE = 0x4; // structured rendering

    // the window to render to
    protected final Window window;

    // the position of the light source;
    protected final Vector3f lightPosition;

    // the light color
    protected final Vector3f lightColor = new Vector3f(1.0f, 1.0f, 1.0f);

    /**
     * default constructor
     * @param window - the window to render to
     * @param lightPosition - the position of the light source
     */
    public Renderer(Window window, Vector3f lightPosition) {
        this.window = window;
        this.lightPosition = lightPosition;
    }

    /**
     * renders an object
     * @param object - the object to render
     * @param camera - the camera perspective
     * @param shader - the shader to use to render
     */
    public void renderMesh(RenderObject object, Camera camera, Shader shader, int type) {

        switch (type) {
            case LIGHT:
                renderPlainColorMesh(object, camera, shader);
                break;
            case TEXTURE:
                renderTextureMesh(object, camera, shader);
                break;
            case COLOR:
                renderShadeColorMesh(object, camera, shader);
                break;
        }
    }

    public void renderInstancedMesh(RenderObject object, Vector3f position, Vector3f rotation, Vector3f scale,
                                    Vector4f color, Camera camera, Shader shader) {
        // create MVP matrices
        Matrix4f vModel = Matrix4f.transform(position, rotation, scale);
        Matrix4f vView = Matrix4f.view(camera.getPosition(), camera.getRotation());
        Matrix4f vProjection = window.getProjectionMatrix();

        // bind the vertex array object
        GL30.glBindVertexArray(object.getMesh().getVAO());

        // enable vertex attributes
        GL30.glEnableVertexAttribArray(0); // position buffer
        GL30.glEnableVertexAttribArray(2); // normal buffer

        // bind the index buffer
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());

        // bind the shader
        shader.bind();

        // set the uniforms
        shader.setUniform("vModel", vModel);
        shader.setUniform("vView", vView);
        shader.setUniform("vProjection", vProjection);
        shader.setUniform("lightPos", lightPosition);
        shader.setUniform("lightLevel", 0.3f);
        shader.setUniform("viewPos", camera.getPosition());
        shader.setUniform("lightColor", lightColor);
        shader.setUniform("reflectiveness", 32);
        shader.setUniform("passColor", color);

        // draw the triangles making up the mesh
        GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);

        // unbind the shader
        shader.unbind();

        // unbind the index buffer
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        // disable the vertex attributes
        GL30.glDisableVertexAttribArray(0); // position buffer
        GL30.glDisableVertexAttribArray(2); // normal buffer

        // unbind the vertex array object
        GL30.glBindVertexArray(0);
    }

    public void renderInstancedMesh(RenderObject object, Matrix4f model, Matrix4f view, Matrix4f projection,
                                    Vector4f color, Camera camera, Shader shader) {
        //bind the vertex array object
        GL30.glBindVertexArray(object.getMesh().getVAO());

        // enable vertex attributes
        GL30.glEnableVertexAttribArray(0); // position buffer
        GL30.glEnableVertexAttribArray(2); // normal buffer

        // bind the index buffer
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());

        // bind the shader
        shader.bind();

        // set the uniforms
        shader.setUniform("vModel", model);
        shader.setUniform("vView", view);
        shader.setUniform("vProjection", projection);
        shader.setUniform("lightPos", lightPosition);
        shader.setUniform("lightLevel", 0.3F);
        shader.setUniform("viewPos", camera.getPosition());
        shader.setUniform("lightColor", lightColor);
        shader.setUniform("reflectiveness", 32);
        shader.setUniform("passColor", color);

        // draw the triangles making up the mesh
        GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);

        // unbind the shader
        shader.unbind();

        // unbind the index buffer
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        // disable the vertex attributes
        GL30.glDisableVertexAttribArray(0); // position buffer
        GL30.glDisableVertexAttribArray(2); // normal buffer

        // unbind the vertex array object
        GL30.glBindVertexArray(0);
    }

    public void renderStructuredMesh(RenderObject object, Camera camera, Shader shader, MaterialStructure material, LightStructure light) {
        // bind the vertex array object
        GL30.glBindVertexArray(object.getMesh().getVAO());

        // enable the vertex attributes
        GL30.glEnableVertexAttribArray(0); // position buffer
        GL30.glEnableVertexAttribArray(2); // normal buffer
        GL30.glEnableVertexAttribArray(5); // color buffer

        // bind the index buffer
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());

        // bind the shader
        shader.bind();

        // set the shader uniforms
        // default uniforms
        shader.setUniform("vModel", Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));
        shader.setUniform("vView", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("vProjection", window.getProjectionMatrix());
        shader.setUniform("viewPos", camera.getPosition());
        // structured uniforms - Light
        shader.setUniform("light.lightPos", light.getLightPosition());
        shader.setUniform("light.lightColor", light.getLightColor());
        shader.setUniform("light.lightLevel", light.getLightLevel());
        // structured uniforms - Material
        shader.setUniform("material.reflectiveness", material.getReflectiveness());
        shader.setUniform("material.specularStrength", material.getSpecularStrength());
        shader.setUniform("material.roughness", material.getRoughness());

        // draw the triangles this shader is made of
        GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);

        // unbind the shader
        shader.unbind();

        // unbind the index buffer
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        // disable the vertex attributes
        GL30.glDisableVertexAttribArray(0); // position buffer
        GL30.glDisableVertexAttribArray(2); // normal buffer
        GL30.glDisableVertexAttribArray(5); // color buffer

        // unbind the vertex array object
        GL30.glBindVertexArray(0);
    }

    /**
     * renders the mesh of a colored object with shading
     * @param object - the object to render
     * @param camera - the camera perspective
     * @param shader - the shader to use to render
     */
    public void renderShadeColorMesh(RenderObject object, Camera camera, Shader shader) {
        // bind the vertex array object
        GL30.glBindVertexArray(object.getMesh().getVAO());

        // enable the vertex attributes
        GL30.glEnableVertexAttribArray(0); // position buffer
        GL30.glEnableVertexAttribArray(2); // normal buffer
        GL30.glEnableVertexAttribArray(5); // color buffer

        // bind the index buffer
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());

        // bind the shader
        shader.bind();

        // set the uniforms
        shader.setUniform("vModel", Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));
        shader.setUniform("vView", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("vProjection", window.getProjectionMatrix());
        shader.setUniform("lightPos", lightPosition);
        shader.setUniform("lightLevel", 0.3f);
        shader.setUniform("viewPos", camera.getPosition());
        shader.setUniform("lightColor", lightColor);
        shader.setUniform("reflectiveness", 32); // the value of the specular reflectiveness

        // draw the triangles making up the mesh
        GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);

        // unbind the shader
        shader.unbind();

        // unbind the index buffer
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        // disable the vertex attributes
        GL30.glDisableVertexAttribArray(0); // position buffer
        GL30.glDisableVertexAttribArray(2); // normal buffer
        GL30.glDisableVertexAttribArray(5); // color buffer

        // unbind the vertex array object
        GL30.glBindVertexArray(0);
    }

    /**
     * renders the mesh of a colored object
     * @param object - the object to render
     * @param camera - the camera perspective
     * @param shader - the shader to use to render
     */
    public void renderPlainColorMesh(RenderObject object, Camera camera, Shader shader) {
        // bind the vertex array object
        GL30.glBindVertexArray(object.getMesh().getVAO());

        // enable the vertex attributes
        GL30.glEnableVertexAttribArray(0); // position buffer
        GL30.glEnableVertexAttribArray(5); // color buffer

        // bind the index buffer
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());

        // bind the shader
        shader.bind();

        // set the uniforms
        shader.setUniform("vModel", Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));
        shader.setUniform("vView", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("vProjection", window.getProjectionMatrix());

        // draw the triangles making up the mesh
        GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);

        // unbind the shader
        shader.unbind();

        // unbind the index buffer
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        // disable the vertex attributes
        GL30.glDisableVertexAttribArray(0); // position buffer
        GL30.glDisableVertexAttribArray(5); // color buffer

        // unbind the vertex array object
        GL30.glBindVertexArray(0);
    }

    /**
     * renders the mesh of a textured object
     * @param object - the object to render
     * @param camera - the camera perspective
     * @param shader - the shader to use to render
     */
    public void renderTextureMesh(RenderObject object, Camera camera, Shader shader) {
        // bind the vertex array object
        GL30.glBindVertexArray(object.getMesh().getVAO());

        // enable the vertex attributes
        GL30.glEnableVertexAttribArray(0); // position buffer
        GL30.glEnableVertexAttribArray(1); // texture buffer
        GL30.glEnableVertexAttribArray(2); // normal buffer
        GL30.glEnableVertexAttribArray(3); // tangent buffer
        GL30.glEnableVertexAttribArray(4); // bitangent buffer

        // bind the index buffer
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());

        // bind the textures
        GL13.glActiveTexture(GL13.GL_TEXTURE0); // base color
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, object.getMesh().getMaterial().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + 1); // specular map
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, object.getMesh().getMaterial().getSpecularID());
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + 2); // normal map
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, object.getMesh().getMaterial().getNormalID());

        // bind the shader
        shader.bind();

        // set the shader uniforms
        shader.setUniform("vModel", Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));
        shader.setUniform("vView", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("vProjection", window.getProjectionMatrix());
        shader.setUniform("lightPos", lightPosition);
        shader.setUniform("lightLevel", 0.3f);
        shader.setUniform("viewPos", camera.getPosition());
        shader.setUniform("lightColor", lightColor);

        // draw the triangles making up the mesh
        GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);

        // unbind the shader
        shader.unbind();

        // unbind the textures
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + 1);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + 2);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        // unbind the index buffer
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        // disable to vertex attributes
        GL30.glDisableVertexAttribArray(0); // position buffer
        GL30.glDisableVertexAttribArray(1); // texture buffer
        GL30.glDisableVertexAttribArray(2); // normal buffer
        GL30.glDisableVertexAttribArray(3); // tangent buffer
        GL30.glDisableVertexAttribArray(4); // bitangent buffer

        // unbind the vertex array object
        GL30.glBindVertexArray(0);
    }

}
