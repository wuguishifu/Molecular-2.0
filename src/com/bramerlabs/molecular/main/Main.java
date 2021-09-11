package com.bramerlabs.molecular.main;

import com.bramerlabs.molecular.engine3D.graphics.Camera;
import com.bramerlabs.molecular.engine3D.graphics.Shader;
import com.bramerlabs.molecular.engine3D.graphics.io.window.Input;
import com.bramerlabs.molecular.engine3D.graphics.io.window.Window;
import com.bramerlabs.molecular.engine3D.graphics.io.window.WindowConstants;
import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.molecule.MoleculeRenderer;
import org.lwjgl.glfw.GLFW;
import org.openscience.cdk.iupac.parser.MoleculeBuilder;

import java.awt.*;

public class Main implements Runnable {

    private final Input input;
    private final Window window;
    private final Camera camera;

    private Shader shader;
    private MoleculeRenderer renderer;

    private boolean[] keysDown, keysDownLast;
    private boolean[] buttonsDown, buttonsDownLast;

    private MoleculeBuilder moleculeBuilder;

    public static void main(String[] args) {
        new Main().start();
    }

    public Main() {
        input = new Input();
        window = new Window(new WindowConstants("Molecular", new Color(204, 232, 220)), input);
        camera = new Camera(Vector3f.zero, Vector3f.zero, input);
        camera.setDistance(20);
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

    private void init() {
        window.create();

        camera.setFocus(Vector3f.zero);

        shader = new Shader("shaders/molecule/vertex.glsl", "shaders/molecule/fragment.glsl");
        shader.create();

        renderer = new MoleculeRenderer(window, new Vector3f(0, 100, 0));

        keysDown = new boolean[GLFW.GLFW_KEY_LAST];
        keysDownLast = new boolean[GLFW.GLFW_KEY_LAST];
        buttonsDown = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
        buttonsDownLast = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    }

    private void update() {
        window.update();
        window.clear();
        camera.update();

        System.arraycopy(keysDown, 0, keysDownLast, 0, keysDown.length);
        System.arraycopy(input.getKeysDown(), 0, keysDown, 0, input.getKeysDown().length);
        System.arraycopy(buttonsDown, 0, buttonsDownLast, 0, buttonsDown.length);
        System.arraycopy(input.getButtonsDown(), 0, buttonsDown, 0, input.getButtonsDown().length);
    }

    private void render() {
        window.swapBuffers();
    }

    private void close() {
        window.destroy();
        shader.destroy();
    }

    private boolean keyPressed(int key) {
        return keysDown[key] && !keysDownLast[key];
    }

    private boolean keyReleased(int key) {
        return !keysDown[key] && keysDownLast[key];
    }

    private boolean buttonPressed(int button) {
        return buttonsDown[button] && !buttonsDownLast[button];
    }

    private boolean buttonReleased(int button) {
        return !buttonsDown[button] && buttonsDownLast[button];
    }

}
