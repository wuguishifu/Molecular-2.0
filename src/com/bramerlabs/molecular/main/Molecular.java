package com.bramerlabs.molecular.main;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.MoleculeRenderer;
import com.bramerlabs.molecular.molecule.default_molecules.Benzaldehyde;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL46;


public class Molecular implements Runnable {

    private final Input input = new Input();
    private final Window window = new Window(input);
    private Camera camera;
    private Shader shader;
    private MoleculeRenderer renderer;
    private final Vector3f lightPosition = new Vector3f(0, 100, 0);

    boolean canPrint = true;
    boolean rotate = false;
    boolean[] keysDown;
    boolean[] keysDownOld;

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
        molecule = new Benzaldehyde(new Vector3f(0, 0, 0));
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

        // initialize molecules
        initMolecule();

        // initialize key inputs
        keysDown = new boolean[GLFW.GLFW_KEY_LAST];
        keysDownOld = new boolean[GLFW.GLFW_KEY_LAST];
    }

    public void update() {
        window.update();
        GL46.glClearColor(window.r, window.g, window.b, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
        camera.updateArcball();

        if (rotate) {
            camera.setHorizontalAngle(camera.getHorizontalAngle() + 0.5f);
        }

        if (keysDown[GLFW.GLFW_KEY_P] && !keysDownOld[GLFW.GLFW_KEY_P]) {
            System.out.println(molecule);
        }
        if (keysDown[GLFW.GLFW_KEY_R] && !keysDownOld[GLFW.GLFW_KEY_R]) {
            rotate = !rotate;
        }

        // copy keys to keys old - for use in single click buttons
        System.arraycopy(keysDown, 0, keysDownOld, 0, keysDown.length);
        System.arraycopy(input.getKeysDown(), 0, keysDown, 0, input.getKeysDown().length);

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
