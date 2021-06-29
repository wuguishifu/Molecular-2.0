package com.bramerlabs.engine.test;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.renderers.Renderer;
import com.bramerlabs.engine.graphics.structures.LightStructure;
import com.bramerlabs.engine.graphics.structures.MaterialStructure;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.matrix.Matrix4f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import com.bramerlabs.engine.objects.shapes.shapes_3d.Cube;
import com.bramerlabs.engine.objects.shapes.shapes_3d.Cylinder;
import com.bramerlabs.engine.objects.shapes.shapes_3d.Sphere;
import org.lwjgl.opengl.GL46;

public class TestMain implements Runnable {

    // the object to track inputs
    private final Input input = new Input();

    // the window to render to
    private final Window window = new Window(input);

    // the POV camera
    private Camera camera;

    // shaders to use
    private Shader textureShader, lightShader, colorShader, structureShader;

    // renderers to use
    private Renderer renderer;

    // the position of the light source
    private final Vector3f lightPosition = new Vector3f(0, 2.0f, 3.0f);

    // test objects to render
    private Sphere sphere, sphere1, sphere2;
    private Cube lightCube;
    private MaterialStructure material;
    private LightStructure light;
    private Cylinder cylinder;

    /**
     * the main runnable method
     * @param args - jvm arguments
     */
    public static void main(String[] args) {
        // start the main thread
        new TestMain().start();
    }

    /**
     * starts the thread
     */
    public void start() {
        // initialize the new thread
        Thread main = new Thread(this, "Test Thread");

        // start the thread
        main.start();
    }

    public void run() {
        // initialize the program
        init();

        // application run loop
        while (!window.shouldClose()) {
            update();
            render();
        }

        // clean up
        close();
    }

    /**
     * initialize the program
     */
    private void init() {
        // initialize the window
        window.create();

        // initialize the shader
        textureShader = new Shader(
                "shaders/texture/vertex.glsl", // path to the vertex shader
                "shaders/texture/fragment.glsl" // path to the fragment shader
        ).create();
        lightShader = new Shader(
                "shaders/light/vertex.glsl",
                "shaders/light/fragment.glsl"
        ).create();
        colorShader = new Shader(
                "shaders/default/vertex.glsl",
                "shaders/default/fragment.glsl"
        ).create();
        structureShader = new Shader(
                "shaders/structured/vertex.glsl",
                "shaders/structured/fragment.glsl"
        ).create();

        // initialize the renderers
        renderer = new Renderer(window, lightPosition);

        // initialize the camera
        camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), input);
        camera.setFocus(new Vector3f(0, 0, 0));

        // test rotation matrix
//        Matrix4f rotation = Matrix4f.rotate(45, new Vector3f(0, 1, 0));
//        Vector3f position1 = new Vector3f(1, 0 ,0);
//        Vector3f position2 = Matrix4f.multiply(rotation, new Vector4f(position1, 1.0f)).xyz();

        // initialize objects
        sphere = Sphere.getInstance(
                new Vector3f(0, 0, 0),
                new Vector4f(0.5f, 0.5f, 0.5f, 1.0f),
                0.5f
        );
        sphere1 = Sphere.getInstance(
                new Vector3f(1, 0, 0),
                new Vector4f(0.5f, 0, 0.5f, 1.0f),
                0.25f
        );
        sphere2 = Sphere.getInstance(
                new Vector3f(0, 0, 1),
                new Vector4f(0.5f, 0, 0.5f, 1.0f),
                0.25f
        );
        lightCube = new Cube(
                lightPosition,
                new Vector3f(0.0f),
                new Vector3f(0.5f),
                new Vector4f(1.0f)
        );
        cylinder = Cylinder.getInstance(
                new Vector3f(0, 0, 0),
                new Vector3f(1, 1, 1),
                new Vector4f(0.5f, 0.5f, 0.5f, 1.0f),
                0.5f,
                true
        );

        // initialize the object meshes
        sphere.createMesh();
        sphere1.createMesh();
        sphere2.createMesh();
        lightCube.createMesh();
        cylinder.createMesh();

        light = new LightStructure(
                lightPosition,
                new Vector3f(1.0f, 1.0f, 1.0f),
                0.3f
        );
        material = new MaterialStructure(32, 1, 1);
    }

    Matrix4f rotation11 = Matrix4f.rotate(5, new Vector3f(0, 1, 0));
    Matrix4f rotation12 = Matrix4f.rotate(5, new Vector3f(0, 0, 1));
    Matrix4f rotation13 = Matrix4f.rotate(5, new Vector3f(1, 0, 0));
    Matrix4f rotation1 = Matrix4f.multiply(rotation11, rotation12);
    Matrix4f rotation_1 = Matrix4f.multiply(rotation1, rotation13);

    Matrix4f rotation21 = Matrix4f.rotate(5, new Vector3f(0, 1, 0));
    Matrix4f rotation22 = Matrix4f.rotate(5, new Vector3f(1, 0, 0));
    Matrix4f rotation2 = Matrix4f.multiply(rotation21, rotation22);

    /**
     * updates the program
     */
    private void update() {
        window.update();

        // clear the screen and mask bits
        GL46.glClearColor(window.r, window.g, window.b, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);

        // update render objects
        sphere1.setPosition(Matrix4f.multiply(rotation1, new Vector4f(sphere1.getPosition(), 1.0f)).xyz());
        sphere2.setPosition(Matrix4f.multiply(rotation2, new Vector4f(sphere2.getPosition(), 1.0f)).xyz());

        // update the camera
        camera.updateArcball();
//        camera.update();
    }

    /**
     * renders the program
     */
    private void render() {
        // render the objects
//        renderer.renderMesh(blank, camera, colorShader, Renderer.COLOR);
        renderer.renderStructuredMesh(sphere, camera, structureShader, material, light);
        renderer.renderStructuredMesh(sphere1, camera, structureShader, material, light);
//        renderer.renderStructuredMesh(sphere2, camera, structureShader, material, light);
        renderer.renderMesh(lightCube, camera, lightShader, Renderer.LIGHT);
        renderer.renderMesh(cylinder, camera, colorShader, Renderer.COLOR);

        // swap the frame buffers
        window.swapBuffers();
    }

    /**
     * closes the program
     */
    private void close() {
        // release the window
        window.destroy();

        sphere.destroy();
        sphere1.destroy();
        sphere2.destroy();
        lightCube.destroy();

        // release the shaders
        textureShader.destroy();
        lightShader.destroy();
    }

}
