package com.bramerlabs.molecular.molecule;

import com.bramerlabs.molecular.engine3D.graphics.Camera;
import com.bramerlabs.molecular.engine3D.graphics.Shader;
import com.bramerlabs.molecular.engine3D.graphics.io.window.Window;
import com.bramerlabs.molecular.engine3D.graphics.mesh.Mesh;
import com.bramerlabs.molecular.engine3D.graphics.renderers.Renderer;
import com.bramerlabs.molecular.engine3D.math.matrix.Matrix4f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector4f;
import com.bramerlabs.molecular.molecule.components.atom.Atom;
import com.bramerlabs.molecular.molecule.components.bond.Bond;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.awt.*;

public class MoleculeRenderer extends Renderer {

    Vector4f selectionColor = new Vector4f(0.5f, 0.5f, 0.0f, 0.6f);
    float reflectiveness = 32.0f;
    float lightLevel = 0.4f;

    public MoleculeRenderer(Window window, Vector3f lightPos) {
        super(window, lightPos);
    }

    public void renderMolecule(Molecule molecule, Camera camera, Shader shader) {
        for (Atom atom : molecule.getAtoms().values()) {
            this.renderAtom(atom, camera, shader);
        }
        for (Bond bond : molecule.getBonds().values()) {
            this.renderBond(bond, camera, shader);
        }

        // render selections
        for (Atom atom : molecule.getAtoms().values()) {
            if (atom.selected) {
                renderHighlightedAtom(atom, camera, shader);
            }
        }
        for (Bond bond : molecule.getBonds().values()) {
            if (bond.selected) {
                renderHighlightedBond(bond, camera, shader);
            }
        }
    }

    public void renderPickingMolecule(Molecule molecule, Camera camera, Shader shader) {
        for (Atom atom : molecule.getAtoms().values()) {
            this.renderPickingAtom(atom, camera, shader);
        }
        for (Bond bond : molecule.getBonds().values()) {
            this.renderPickingBond(bond, camera, shader);
        }
    }

    public void renderAtom(Atom atom, Camera camera, Shader shader) {
        float radius = Atom.DataCompiler.getRadius(atom.data.atomicNumber);
        Vector4f color = new Vector4f(new Vector3f(Atom.DataCompiler.getColor(atom.data.atomicNumber)), 1.0f);

        Matrix4f vModel = Matrix4f.transform(atom.position, new Vector3f[]{}, new float[]{}, new Vector3f(radius));

        GL30.glBindVertexArray(Atom.sphere.getMesh().getVao());
        GL30.glEnableVertexAttribArray(Mesh.POSITION);
        GL30.glEnableVertexAttribArray(Mesh.NORMAL);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, Atom.sphere.getMesh().getIbo());
        shader.bind();
//        shader.setUniform("vModel", Matrix4f.transform(atom.position, Vector3f.zero, new Vector3f(radius)));
        shader.setUniform("vModel", vModel);
        shader.setUniform("vView", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("vProjection", super.window.getProjectionMatrix());
        shader.setUniform("meshColor", color);
        shader.setUniform("lightPos", lightPos);
        shader.setUniform("lightLevel", lightLevel);
        shader.setUniform("viewPos", camera.getPosition());
        shader.setUniform("lightColor", lightColor);
        shader.setUniform("reflectiveness", reflectiveness);
        GL11.glDrawElements(GL11.GL_TRIANGLES, Atom.sphere.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        shader.unbind();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(Mesh.POSITION);
        GL30.glDisableVertexAttribArray(Mesh.NORMAL);
        GL30.glBindVertexArray(0);
    }

    public void renderBond(Bond bond, Camera camera, Shader shader) {
        Vector4f color = new Vector4f(0.3f, 0.3f, 0.3f, 1.0f);

        for (Matrix4f vModel : bond.modelMatrices) {
            GL30.glBindVertexArray(Bond.cylinder.getMesh().getVao());
            GL30.glEnableVertexAttribArray(Mesh.POSITION);
            GL30.glEnableVertexAttribArray(Mesh.NORMAL);
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, Bond.cylinder.getMesh().getIbo());
            shader.bind();
            shader.setUniform("vModel", vModel);
            shader.setUniform("vView", Matrix4f.view(camera.getPosition(), camera.getRotation()));
            shader.setUniform("vProjection", super.window.getProjectionMatrix());
            shader.setUniform("meshColor", color);
            shader.setUniform("lightPos", lightPos);
            shader.setUniform("lightLevel", lightLevel);
            shader.setUniform("viewPos", camera.getPosition());
            shader.setUniform("lightColor", lightColor);
            shader.setUniform("reflectiveness", reflectiveness);
            GL11.glDrawElements(GL11.GL_TRIANGLES, Atom.sphere.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
            shader.unbind();
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
            GL30.glDisableVertexAttribArray(Mesh.POSITION);
            GL30.glDisableVertexAttribArray(Mesh.NORMAL);
            GL30.glBindVertexArray(0);
        }
    }

    public void renderHighlightedAtom(Atom atom, Camera camera, Shader shader) {
        float radius = Atom.DataCompiler.getRadius(atom.data.atomicNumber) + 0.2f;

        GL30.glBindVertexArray(Atom.sphere.getMesh().getVao());
        GL30.glEnableVertexAttribArray(Mesh.POSITION);
        GL30.glEnableVertexAttribArray(Mesh.NORMAL);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, Atom.sphere.getMesh().getIbo());
        shader.bind();
        shader.setUniform("vModel", Matrix4f.transform(atom.position, Vector3f.zero, new Vector3f(radius)));
        shader.setUniform("vView", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("vProjection", super.window.getProjectionMatrix());
        shader.setUniform("meshColor", selectionColor);
        shader.setUniform("lightPos", lightPos);
        shader.setUniform("lightLevel", lightLevel);
        shader.setUniform("viewPos", camera.getPosition());
        shader.setUniform("lightColor", lightColor);
        shader.setUniform("reflectiveness", reflectiveness);
        GL11.glDrawElements(GL11.GL_TRIANGLES, Atom.sphere.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        shader.unbind();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(Mesh.POSITION);
        GL30.glDisableVertexAttribArray(Mesh.NORMAL);
        GL30.glBindVertexArray(0);
    }

    public void renderHighlightedBond(Bond bond, Camera camera, Shader shader) {
        for (Matrix4f vModel : bond.highlightModelMatrices) {
            GL30.glBindVertexArray(Bond.cylinder.getMesh().getVao());
            GL30.glEnableVertexAttribArray(Mesh.POSITION);
            GL30.glEnableVertexAttribArray(Mesh.NORMAL);
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, Bond.cylinder.getMesh().getIbo());
            shader.bind();
            shader.setUniform("vModel", vModel);
            shader.setUniform("vView", Matrix4f.view(camera.getPosition(), camera.getRotation()));
            shader.setUniform("vProjection", super.window.getProjectionMatrix());
            shader.setUniform("meshColor", selectionColor);
            shader.setUniform("lightPos", lightPos);
            shader.setUniform("lightLevel", lightLevel);
            shader.setUniform("viewPos", camera.getPosition());
            shader.setUniform("lightColor", lightColor);
            shader.setUniform("reflectiveness", reflectiveness);
            GL11.glDrawElements(GL11.GL_TRIANGLES, Atom.sphere.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
            shader.unbind();
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
            GL30.glDisableVertexAttribArray(Mesh.POSITION);
            GL30.glDisableVertexAttribArray(Mesh.NORMAL);
            GL30.glBindVertexArray(0);
        }
    }

    public void renderPickingAtom(Atom atom, Camera camera, Shader shader) {
        float radius = Atom.DataCompiler.getRadius(atom.data.atomicNumber);
        Vector4f color = getPickingColor(atom.ID);

        GL30.glBindVertexArray(Atom.sphere.getMesh().getVao());
        GL30.glEnableVertexAttribArray(Mesh.POSITION);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, Atom.sphere.getMesh().getIbo());
        shader.bind();
        shader.setUniform("vModel", Matrix4f.transform(atom.position, Vector3f.zero, new Vector3f(radius)));
        shader.setUniform("vView", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("vProjection", super.window.getProjectionMatrix());
        shader.setUniform("pickingColor", color);
        GL11.glDrawElements(GL11.GL_TRIANGLES, Atom.sphere.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        shader.unbind();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(Mesh.POSITION);
        GL30.glBindVertexArray(0);
    }

    public void renderPickingBond(Bond bond, Camera camera, Shader shader) {
        Vector4f color = getPickingColor(bond.ID);

        for (Matrix4f vModel : bond.modelMatrices) {
            GL30.glBindVertexArray(Bond.cylinder.getMesh().getVao());
            GL30.glEnableVertexAttribArray(Mesh.POSITION);
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, Bond.cylinder.getMesh().getIbo());
            shader.bind();
            shader.setUniform("vModel", vModel);
            shader.setUniform("vView", Matrix4f.view(camera.getPosition(), camera.getRotation()));
            shader.setUniform("vProjection", super.window.getProjectionMatrix());
            shader.setUniform("pickingColor", color);
            GL11.glDrawElements(GL11.GL_TRIANGLES, Atom.sphere.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
            shader.unbind();
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
            GL30.glDisableVertexAttribArray(Mesh.POSITION);
            GL30.glBindVertexArray(0);
        }
    }

    public static Vector4f getPickingColor(int i) {
        Color c = Color.decode("#" + Integer.toHexString(i));
        return new Vector4f(new Vector3f(c), 1.0f);
    }

    public static int getPickingID(Vector3f color) {
        String r = String.format("%2s", Integer.toHexString((int) (color.x))).replace(' ', '0');
        String g = String.format("%2s", Integer.toHexString((int) (color.y))).replace(' ', '0');
        String b = String.format("%2s", Integer.toHexString((int) (color.z))).replace(' ', '0');
        String colorAsString = "0x" + r + g + b;
        return Integer.decode(colorAsString);
    }
}
