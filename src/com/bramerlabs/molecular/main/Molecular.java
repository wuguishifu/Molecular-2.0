package com.bramerlabs.molecular.main;

import com.bramerlabs.molecular.engine3D.graphics.Camera;
import com.bramerlabs.molecular.engine3D.graphics.Shader;
import com.bramerlabs.molecular.engine3D.graphics.io.window.Input;
import com.bramerlabs.molecular.engine3D.graphics.io.window.Window;
import com.bramerlabs.molecular.engine3D.graphics.io.window.WindowConstants;
import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector4f;
import com.bramerlabs.molecular.engine3D.objects.Cylinder;
import com.bramerlabs.molecular.engine3D.objects.IcoSphere;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.MoleculeRenderer;
import com.bramerlabs.molecular.molecule.components.atom.Atom;
import com.bramerlabs.molecular.molecule.components.bond.Bond;
import com.bramerlabs.molecular.molecule.groups.FunctionalGroupManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.Color;
import java.nio.ByteBuffer;

import static com.bramerlabs.molecular.molecule.groups.FunctionalGroupManager.Group.*;

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
        }
        this.close();
    }

    private void initMolecule() {

        molecule = new Molecule();
        FunctionalGroupManager.createGroup(molecule, TETRAHEDRAL,
                new Vector3f(0, 1, 0), 0);

        camera.setFocus(molecule.getCenter());
        molecule.bondMap.print();
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
        Atom.sphere = new IcoSphere(new Vector3f(0), new Vector4f(0), 1.0f);
        Atom.sphere.createMesh();

        Bond.cylinder = new Cylinder(new Vector3f(0), new Vector3f(1, 0, 0),
                new Vector4f(1, 1, 1, 1), 0.2f);
        Bond.cylinder.createMesh();

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
        camera.update();

        // update buttons and keys
        System.arraycopy(keysDown, 0, keysDownLast, 0, keysDown.length);
        System.arraycopy(input.getKeysDown(), 0, keysDown, 0, input.getKeysDown().length);
        System.arraycopy(buttonsDown, 0, buttonsDownLast, 0, buttonsDown.length);
        System.arraycopy(input.getButtonsDown(), 0, buttonsDown, 0, input.getButtonsDown().length);
    }

    private void handleButtonPresses() {
        if (keyPressed(GLFW.GLFW_KEY_P)) {
            camera.printValues();
        }
        if (keyPressed(GLFW.GLFW_KEY_ENTER)) {
            camera.setIdealPosition();
        }
        if (buttonPressed(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
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
        Vector3f color = new Vector3f(data.get(0), data.get(1), data.get(2));
        if (color.x < 0) {
            color.x += 256;
        }
        if (color.y < 0) {
            color.y += 256;
        }
        if (color.z < 0) {
            color.z += 256;
        }

        int ID = MoleculeRenderer.getPickingID(color);
//        molecule.toggleSelection(ID);

        if (molecule.isAtomID(ID)) {
            Atom carbon = molecule.getConnectedAtoms(molecule.getAtom(ID)).get(0);
            Atom hydrogen = molecule.getAtom(ID);
            Vector3f normal = Vector3f.subtract(hydrogen.position, carbon.position);
            FunctionalGroupManager.createAndReplace(molecule, BENT, hydrogen, normal, 0);
            camera.setFocus(molecule.getCenter());
        }
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
        Atom.sphere.destroy();
        Bond.cylinder.destroy();
    }

    public boolean keyPressed(int key) {
        return keysDown[key] && !keysDownLast[key];
    }

    public boolean keyReleased(int key) {
        return !keysDown[key] && keysDownLast[key];
    }

    public boolean buttonPressed(int button) {
        return buttonsDown[button] && !buttonsDownLast[button];
    }

    public boolean buttonReleased(int button) {
        return !buttonsDown[button] && buttonsDownLast[button];
    }

}
