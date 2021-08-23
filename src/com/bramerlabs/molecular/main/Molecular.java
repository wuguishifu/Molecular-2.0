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
import com.bramerlabs.molecular.molecule.bond.Bond;
import com.bramerlabs.molecular.molecule.default_molecules.Benzene;
import org.lwjgl.glfw.GLFW;

import java.awt.Color;

public class Molecular implements Runnable {

    private final Input input;
    private final Window window;
    private final Camera camera;

    private Shader shader, pickingShader;
    private MoleculeRenderer renderer;

    private Molecule molecule;

    private boolean[] keysDown, keysDownLast;
    private boolean[] buttonsDown, buttonsDownLast;

    public static void main(String[] args) {
        new Molecular().start();
    }

    public Molecular() {
        input = new Input();
        window = new Window(new WindowConstants("Molecular", new Color(204, 232, 220)), input);
        camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), input);
        camera.setDistance(20.0f);
    }

    public void start() {
        Thread main = new Thread(this, "main thread");
        main.start();
    }

    public void run() {
        this.init();
        while (!window.shouldClose()) {
            this.update();
            this.handleButtonPresses();
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
        pickingShader = new Shader("shaders/picking/vertex.glsl", "shaders/picking/fragment.glsl");
        pickingShader.create();

        // initialize renderers
        renderer = new MoleculeRenderer(window, new Vector3f(0, 100, 0));

        // initialize atom and bond data
        Atom.DataCompiler.init();
        Bond.DataCompiler.init();
        Atom.sphere = new IcoSphere(new Vector3f(0), new Vector4f(0), 1.0f);
        Atom.sphere.createMesh();

        // initialize mouse and key listeners
        keysDown = new boolean[GLFW.GLFW_KEY_LAST];
        keysDownLast = new boolean[GLFW.GLFW_KEY_LAST];
        buttonsDown = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
        buttonsDownLast = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

        // initialize molecule
        initMolecule();
    }

    private void update() {
        window.update();
        window.clear();
        camera.updateArcball();

        // update buttons and keys
        System.arraycopy(keysDown, 0, keysDownLast, 0, keysDown.length);
        System.arraycopy(input.getKeysDown(), 0, keysDown, 0, input.getKeysDown().length);
        System.arraycopy(buttonsDown, 0, buttonsDownLast, 0, buttonsDown.length);
        System.arraycopy(input.getButtonsDown(), 0, buttonsDown, 0, input.getButtonsDown().length);
    }

    private void handleButtonPresses() {
        if (keysDown[GLFW.GLFW_KEY_P] && !keysDownLast[GLFW.GLFW_KEY_P]) {
            camera.printValues();
        }
        if (keysDown[GLFW.GLFW_KEY_ENTER] && !keysDownLast[GLFW.GLFW_KEY_ENTER]) {
            camera.setIdealPosition();
        }
    }

    private void render() {
        renderer.renderPickingMolecule(molecule, camera, pickingShader);
        window.swapBuffers();
    }

    private void close() {
        window.destroy();
        shader.destroy();
        molecule.destroy();
        Atom.sphere.destroy();
    }

}
