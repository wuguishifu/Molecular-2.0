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
import com.bramerlabs.molecular.molecule.components.atom.Atom;
import com.bramerlabs.molecular.molecule.components.bond.Bond;
import com.bramerlabs.molecular.molecule.default_molecules.Benzene;
import com.bramerlabs.molecular.molecule.groups.inorganic.Tetrahedral;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.Color;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

public class Molecular implements Runnable {

    private final Input input;
    private final Window window;
    private final Camera camera;

    private Shader shader, pickingShader;
    private MoleculeRenderer renderer;

    private Molecule molecule;
    private boolean windowShouldSwapBuffers = true;

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
//            window.close();
        }
        this.close();
    }

    private void initMolecule() {
//        this.molecule = new Benzene();
        this.molecule = new Molecule(new HashMap<>(), new HashMap<>());
        Tetrahedral t = new Tetrahedral();
        for (Atom atom : t.getAtoms()) {
            molecule.add(atom.ID, atom);
        }
        for (Bond bond : t.getBonds()) {
            molecule.add(bond.ID, bond);
        }
        Vector3f position = molecule.getAtoms().get(3).position;
        t = new Tetrahedral(position, position);
        for (Atom atom : t.getAtoms()) {
            molecule.add(atom.ID, atom);
        }
        for (Bond bond : t.getBonds()) {
            molecule.add(bond.ID, bond);
        }
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

        if (buttonsDown[GLFW.GLFW_MOUSE_BUTTON_RIGHT] && !buttonsDownLast[GLFW.GLFW_MOUSE_BUTTON_RIGHT]) {
            windowShouldSwapBuffers = false;
            getSelectedAtom();
        }
    }

    private void getSelectedAtom() {
        renderer.renderPickingMolecule(molecule, camera, pickingShader);
        GL11.glFlush();
        GL11.glFinish();
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        float x = input.getMouseX();
        float y = input.getMouseY();
        int height = window.height;
        y = height - y;
        ByteBuffer data = BufferUtils.createByteBuffer(3);
        GL11.glReadPixels((int) x, (int) y, 1, 1, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, data);
        if (data.get(0) < 0 || data.get(1) < 0 || data.get(2) < 0) {
            return;
        }

        Vector3f color = new Vector3f(data.get(0), data.get(1), data.get(2));
        int ID = MoleculeRenderer.getPickingID(color);
        molecule.toggleSelection(ID);

    }

    private void render() {
        renderer.renderMolecule(molecule, camera, shader);
        if (windowShouldSwapBuffers) {
            window.swapBuffers();
        } else {
            windowShouldSwapBuffers = true;
        }
    }

    private void close() {
        window.destroy();
        shader.destroy();
        molecule.destroy();
        Atom.sphere.destroy();
    }

}
