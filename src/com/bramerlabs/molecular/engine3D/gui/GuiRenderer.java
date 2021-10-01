package com.bramerlabs.molecular.engine3D.gui;

import com.bramerlabs.molecular.engine3D.graphics.Shader;
import com.bramerlabs.molecular.engine3D.graphics.io.window.Window;
import com.bramerlabs.molecular.engine3D.graphics.mesh.Mesh;
import com.bramerlabs.molecular.engine3D.graphics.renderers.Renderer;
import com.bramerlabs.molecular.engine3D.math.matrix.Matrix4f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class GuiRenderer extends Renderer {

    public GuiRenderer(Window window, Vector3f lightPos) {
        super(window, lightPos);
    }

    public void renderGuiMesh(Gui gui, Shader shader) {
        for (GuiObject object : gui.guiObjects) {

            GuiMesh mesh = object.mesh;
            Matrix4f model = object.model;

            GL30.glBindVertexArray(object.mesh.getVao());

            GL30.glEnableVertexAttribArray(Mesh.POSITION);
            GL30.glEnableVertexAttribArray(Mesh.TEXTURE_COORD);

            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.mesh.getIbo());

            shader.bind();
            shader.setUniform("vModel", model);
            shader.setUniform("vProjection", window.getProjectionMatrix());
            GL11.glDrawElements(GL11.GL_TRIANGLES, object.mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
            shader.unbind();

            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

            GL30.glDisableVertexAttribArray(Mesh.POSITION);
            GL30.glDisableVertexAttribArray(Mesh.TEXTURE_COORD);

            GL30.glBindVertexArray(0);
        }
    }

}
