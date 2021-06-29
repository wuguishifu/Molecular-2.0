package com.bramerlabs.molecular.main;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.MoleculeRenderer;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.default_molecules.Benzene;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL46;

import java.util.ArrayList;

public class Molecular implements Runnable {

    private final Input input = new Input();
    private final Window window = new Window(input);
    private Camera camera;
    private Shader shader;
    private MoleculeRenderer renderer;
    private final Vector3f lightPosition = new Vector3f(0, 100, 0);

    // objects to render
    Molecule molecule;

    public static void main(String[] args) {
        new Molecular().start();
    }

    public void start() {
        Thread main = new Thread(this, "Molecular");
        main.start();
    }

    public void run() {
        init();
        while (!window.shouldClose()) {
            update();
            render();
        }
        close();
    }

    public void initMolecule() {
        molecule = new Benzene(new Vector3f(0, 0, 0));
    }

    public void init() {
        window.create();
        shader = new Shader("shaders/default/vertex.glsl",
                "shaders/default/fragment.glsl").create();
        renderer = new MoleculeRenderer(window, lightPosition);
        camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), input);
        camera.setFocus(new Vector3f(0, 0, 0));
        camera.setVerticalAngle(-30);
        camera.setDistance(10);
        initMolecule();
    }

    public void update() {
        window.update();
        GL46.glClearColor(window.r, window.g, window.b, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
        camera.updateArcball();
    }

    public void render() {
        renderer.renderMolecule(molecule, camera, shader);
        window.swapBuffers();
    }

    public void close() {
        window.destroy();
        molecule.destroy();
        shader.destroy();
    }

}
