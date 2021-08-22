package com.bramerlabs.molecular.main;

import com.bramerlabs.molecular.engine3D.graphics.Camera;
import com.bramerlabs.molecular.engine3D.graphics.Shader;
import com.bramerlabs.molecular.engine3D.graphics.io.window.Input;
import com.bramerlabs.molecular.engine3D.graphics.io.window.Window;
import com.bramerlabs.molecular.engine3D.graphics.io.window.WindowConstants;
import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector4f;
import com.bramerlabs.molecular.engine3D.objects.IcoSphere;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.MoleculeRenderer;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.default_molecules.Benzene;

import java.awt.Color;

public class Molecular implements Runnable {

    private final Input input;
    private final Window window;
    private final Camera camera;

    private Shader shader;
    private MoleculeRenderer renderer;

    private Molecule molecule;

    public static void main(String[] args) {
        new Molecular().start();
    }

    public Molecular() {
        input = new Input();
        window = new Window(new WindowConstants("Molecular", new Color(204, 232, 220)), input);
        camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), input);
    }

    public void start() {
        Thread main = new Thread(this, "main thread");
        main.start();
    }

    public void run() {
        this.init();
        while (!window.shouldClose()) {
            this.update();
            this.render();
        }
        this.close();
    }

    private void initMolecule() {
        this.molecule = new Benzene();
    }

    private void init() {
        window.create();

        // initialize camera
        camera.setFocus(new Vector3f(0, 0, 0));

        // initialize shaders
        shader = new Shader("shaders/molecule/vertex.glsl", "shaders/molecule/fragment.glsl");
        shader.create();

        // initialize renderers
        renderer = new MoleculeRenderer(window, new Vector3f(5, 20, 10));

        // initialize atom and bond data
        Atom.DataCompiler.init();
        Atom.sphere = new IcoSphere(new Vector3f(0), new Vector4f(0), 1.0f);
        Atom.sphere.createMesh();

        // initialize molecule
        initMolecule();
    }

    private void update() {
        window.update();
        window.clear();
        camera.updateArcball();
    }

    private void render() {
        renderer.renderMolecule(molecule, camera, shader);
        window.swapBuffers();
    }

    private void close() {
        window.destroy();
        shader.destroy();
        molecule.destroy();
        Atom.sphere.destroy();
    }

}
