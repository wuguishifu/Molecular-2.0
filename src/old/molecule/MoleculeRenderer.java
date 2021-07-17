package old.molecule;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.renderers.Renderer;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.matrix.Matrix4f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.objects.RenderObject;
import old.molecule.atom.Atom;
import old.molecule.bond.Bond;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class MoleculeRenderer extends Renderer {

    private final Shader colorInstanceShader;

    /**
     * default constructor
     *
     * @param window        - the window to render to
     * @param lightPosition - the position of the light source
     */
    public MoleculeRenderer(Window window, Vector3f lightPosition) {
        super(window, lightPosition);
        colorInstanceShader = new Shader("shaders/uniform_color/vertex.glsl",
                "shaders/uniform_color/fragment.glsl").create();
    }

    public void renderMolecule(Molecule molecule, Camera camera, Shader shader) {
        for (Bond bond : molecule.getBonds()) {
            for (RenderObject object : molecule.getBondObject(bond.getOrder())) {
                renderInstancedBond(object, camera, shader, bond);
            }
        }
        for (Atom atom : molecule.getAtoms()) {
            renderInstancedAtom(molecule.getAtomObject(atom.getAtomicNumber()), camera, shader, atom.getPosition());
        }
    }

    public void renderInstancedAtom(RenderObject object, Camera camera, Shader shader, Vector3f position) {
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

        // set the shader uniform
        // MVP uniforms
        shader.setUniform("vModel", Matrix4f.transform(position, object.getRotation(), object.getScale()));
        shader.setUniform("vView", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("vProjection", window.getProjectionMatrix());
        // lighting uniforms
        shader.setUniform("lightPos", lightPosition);
        shader.setUniform("lightLevel", 0.3f);
        shader.setUniform("lightColor", lightColor);
        shader.setUniform("reflectiveness", 32);
        shader.setUniform("viewPos", camera.getPosition());

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

    public void renderInstancedBond(RenderObject object, Camera camera, Shader shader, Bond bond) {
        GL30.glBindVertexArray(object.getMesh().getVAO());

        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(2);
        GL30.glEnableVertexAttribArray(5);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());

        shader.bind();

        shader.setUniform("vModel", Matrix4f.transform(bond.getPosition(), bond.getRotationAxes(),
                bond.getRotationAngles(), bond.getScale()));
        shader.setUniform("vView", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("vProjection", window.getProjectionMatrix());

        shader.setUniform("lightPos", lightPosition);
        shader.setUniform("lightLevel", 0.3f);
        shader.setUniform("lightColor", lightColor);
        shader.setUniform("reflectiveness", 32);
        shader.setUniform("viewPos", camera.getPosition());

        GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);

        shader.unbind();

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        // disable the vertex attributes
        GL30.glDisableVertexAttribArray(0); // position buffer
        GL30.glDisableVertexAttribArray(2); // normal buffer
        GL30.glDisableVertexAttribArray(5); // color buffer

        // unbind the vertex array object
        GL30.glBindVertexArray(0);
    }

}
