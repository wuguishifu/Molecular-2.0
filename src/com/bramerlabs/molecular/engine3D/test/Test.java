package com.bramerlabs.molecular.engine3D.test;

import com.bramerlabs.molecular.engine3D.graphics.Camera;
import com.bramerlabs.molecular.engine3D.graphics.Material;
import com.bramerlabs.molecular.engine3D.graphics.Shader;
import com.bramerlabs.molecular.engine3D.graphics.io.window.Input;
import com.bramerlabs.molecular.engine3D.graphics.io.window.Window;
import com.bramerlabs.molecular.engine3D.graphics.io.window.WindowConstants;
import com.bramerlabs.molecular.engine3D.graphics.renderers.Renderer;
import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector4f;
import com.bramerlabs.molecular.engine3D.objects.Cube;
import com.bramerlabs.molecular.engine3D.objects.IcoSphere;
import org.lwjgl.opengl.GL46;

import java.awt.Color;

public class Test implements Runnable {

    private final Input input;
    private final Window window;
    private final Camera camera;

    private Shader shader;
    private Renderer renderer;

    private IcoSphere sphere;
    private Cube cube;

    public static void main(String[] args) {
        new Test().start();
    }

    public Test() {
        input = new Input();
        window = new Window(new WindowConstants("Test", new Color(204, 232, 220)), input);
        camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), input);
    }

    public void start() {
        Thread main = new Thread(this, "Test Thread");
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
        camera.setFocus(new Vector3f(0, 0, 0));

        shader = new Shader("shaders/colored/vertex.glsl", "shaders/colored/fragment.glsl");
        shader.create();

        renderer = new Renderer(window, new Vector3f(5, 20, 10));

        sphere = new IcoSphere(new Vector3f(-1, 0, 0), new Vector4f(0.5f, 0.5f, 0.5f, 1.0f), 1.0f);
        sphere.createMesh();

        cube = new Cube(new Vector3f(1, 0, 0), new Vector3f(0), new Vector3f(1), new Vector4f(0.5f, 0.0f, 0.5f, 1.0f));
        cube.createMesh();

    }

    private void update() {
        window.update();
        GL46.glClearColor(window.r, window.g, window.b, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
        camera.updateArcball();
    }

    private void render() {
        renderer.renderMesh(sphere, camera, shader);
        renderer.renderMesh(cube, camera, shader);
        window.swapBuffers();
    }

    private void close() {
        window.destroy();
        shader.destroy();
        sphere.destroy();
    }

}
