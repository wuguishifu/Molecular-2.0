package com.bramerlabs.molecular.main.molecule;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.renderers.Renderer;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.matrix.Matrix4f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import com.bramerlabs.engine.objects.shapes.shapes_3d.Cylinder;
import com.bramerlabs.engine.objects.shapes.shapes_3d.Sphere;
import com.bramerlabs.molecular.main.molecule.bond.Bond;
import com.bramerlabs.molecular.main.molecule.node.Node;

import java.awt.*;

public class MoleculeRenderer extends Renderer {

    private final Sphere sphere;
    private final Cylinder cylinder;

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
        cylinder = new Cylinder(
                new Vector3f(-1, 0, 0), new Vector3f(1, 0, 0),
                new Vector4f(1, 1, 1, 1),
                0.2f, false
        );
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
        for (Bond bond : molecule.bonds) {
            Vector3f position = Vector3f.midpoint(bond.getNode1().getPosition(), bond.getNode2().getPosition());
            float bondLength = Vector3f.distance(bond.getNode1().getPosition(), bond.getNode2().getPosition());
            Vector3f scale = new Vector3f(bondLength, 1, 1);
            Vector3f normal = Vector3f.subtract(bond.getNode2().getPosition(), bond.getNode1().getPosition());
            float angle = Vector3f.angleBetween(normal, new Vector3f(1, 0, 0));
            Vector3f axis = Vector3f.normalize(Vector3f.cross(normal, new Vector3f(1, 0, 0)));
            Matrix4f model = Matrix4f.transform(position, new Vector3f[]{axis}, new float[]{angle}, scale);
            Matrix4f view = Matrix4f.view(camera.getPosition(), camera.getRotation());
            Vector4f color = new Vector4f(Vector3f.scale(
                    new Vector3f(new Color(57, 57, 57)), 1/255f), 1);
            renderInstancedMesh(cylinder, model, view, window.getProjectionMatrix(), color, camera, shader);
        }
    }
}
