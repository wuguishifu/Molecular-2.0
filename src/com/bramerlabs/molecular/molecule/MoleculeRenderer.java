package com.bramerlabs.molecular.molecule;

import com.bramerlabs.molecular.engine3D.graphics.Camera;
import com.bramerlabs.molecular.engine3D.graphics.Shader;
import com.bramerlabs.molecular.engine3D.graphics.io.window.Window;
import com.bramerlabs.molecular.engine3D.graphics.mesh.Mesh;
import com.bramerlabs.molecular.engine3D.graphics.renderers.Renderer;
import com.bramerlabs.molecular.engine3D.math.matrix.Matrix4f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector4f;
import com.bramerlabs.molecular.engine3D.objects.Cylinder;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.bond.Bond;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class MoleculeRenderer extends Renderer {

    public MoleculeRenderer(Window window, Vector3f lightPos) {
        super(window, lightPos);
    }

    public void renderMolecule(Molecule molecule, Camera camera, Shader shader) {
        for (Atom atom : molecule.getAtoms()) {
            this.renderAtom(atom, camera, shader);
        }
        for (Bond bond : molecule.getBonds()) {
            this.renderBond(bond, camera, shader);
        }
    }

    public void renderPickingMolecule(Molecule molecule, Camera camera, Shader shader) {
        for (Atom atom : molecule.getAtoms()) {
            this.renderPickingAtom(atom, camera, shader);
        }
        for (Bond bond : molecule.getBonds()) {
            this.renderPickingBond(bond, camera, shader);
        }
    }

    public void renderAtom(Atom atom, Camera camera, Shader shader) {
        float radius = Atom.DataCompiler.getRadius(atom.data.atomicNumber);
        Vector4f color = new Vector4f(new Vector3f(Atom.DataCompiler.getColor(atom.data.atomicNumber)), 1.0f);

        GL30.glBindVertexArray(Atom.sphere.getMesh().getVao());
        GL30.glEnableVertexAttribArray(Mesh.POSITION);
        GL30.glEnableVertexAttribArray(Mesh.NORMAL);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, Atom.sphere.getMesh().getIbo());
        shader.bind();
        shader.setUniform("vModel", Matrix4f.transform(atom.position, Vector3f.zero, new Vector3f(radius)));
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
        Vector4f color = new Vector4f(0.2f, 0.2f, 0.2f, 1.0f);

        for (Cylinder cylinder : bond.cylinders) {
            GL30.glBindVertexArray(cylinder.getMesh().getVao());
            GL30.glEnableVertexAttribArray(Mesh.POSITION);
            GL30.glEnableVertexAttribArray(Mesh.NORMAL);
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, cylinder.getMesh().getIbo());
            shader.bind();
            shader.setUniform("vModel", Matrix4f.transform(Vector3f.zero, Vector3f.zero, Vector3f.one));
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

    public void renderPickingAtom(Atom atom, Camera camera, Shader shader) {
        float radius = Atom.DataCompiler.getRadius(atom.data.atomicNumber);
        Vector4f color = new Vector4f(1.0f, (255.0f - atom.ID * 5)/255.0f, 1.0f, 1.0f);

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
        Vector4f color = new Vector4f(1.0f, (255.0f - bond.ID * 5)/255.0f, 1.0f, 1.0f);

        for (Cylinder cylinder : bond.cylinders) {
            GL30.glBindVertexArray(cylinder.getMesh().getVao());
            GL30.glEnableVertexAttribArray(Mesh.POSITION);
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, cylinder.getMesh().getIbo());
            shader.bind();
            shader.setUniform("vModel", Matrix4f.transform(Vector3f.zero, Vector3f.zero, Vector3f.one));
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

}
