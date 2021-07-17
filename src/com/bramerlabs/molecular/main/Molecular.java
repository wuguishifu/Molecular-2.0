package com.bramerlabs.molecular.main;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import com.bramerlabs.engine.objects.shapes.shapes_3d.Sphere;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL46;


public class Molecular implements Runnable {

    private final Input input = new Input();
    private final Window window = new Window(input);
    private Camera camera;
    private Shader shader;
    private final Vector3f lightPosition = new Vector3f(0, 100, 0);

    boolean canPrint = true;
    boolean rotate = false;
    boolean[] keysDown;
    boolean[] keysDownOld;

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

    }

    public void init() {
        window.create();
        shader = new Shader("shaders/default/vertex.glsl",
                "shaders/default/fragment.glsl").create();
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

        // copy keys to keys old - for use in single click buttons
        System.arraycopy(keysDown, 0, keysDownOld, 0, keysDown.length);
        System.arraycopy(input.getKeysDown(), 0, keysDown, 0, input.getKeysDown().length);

    }

    public void render() {
        window.swapBuffers();
    }

    public void close() {
        window.destroy();
        shader.destroy();
    }

}
