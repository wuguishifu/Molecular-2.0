package com.bramerlabs.molecular.main.molecule;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.renderers.Renderer;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import com.bramerlabs.engine.objects.shapes.shapes_3d.Sphere;
import com.bramerlabs.molecular.main.molecule.node.Node;

public class MoleculeRenderer extends Renderer {

    private final Sphere sphere;

    /**
     * default constructor
     *
     * @param window        - the window to render to
     * @param lightPosition - the position of the light source
     */
    public MoleculeRenderer(Window window, Vector3f lightPosition) {
        super(window, lightPosition);
        sphere = new Sphere(new Vector3f(0, 0, 0), new Vector4f(1, 1, 1, 1), 1);
        sphere.createMesh();
    }

    public void renderMolecule(Molecule molecule, Camera camera, Shader shader) {
        for (Node node : molecule.nodes) {
            if (node.getAtom() != null) {
                this.renderInstancedMesh(
                        sphere, node.getPosition(), new Vector3f(0), new Vector3f(node.getAtom().radius),
                        new Vector4f(Vector3f.scale(new Vector3f(node.getAtom().color), 1 / 255f), 1.0f),
                        camera, shader
                );
            }
        }
    }
}
