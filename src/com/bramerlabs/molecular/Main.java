package com.bramerlabs.molecular;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.renderers.Renderer;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import com.bramerlabs.engine.objects.shapes.shapes_3d.Sphere;
import com.bramerlabs.molecular.molecule.Atom;
import com.bramerlabs.molecular.molecule.Molecule;
import org.lwjgl.opengl.GL46;

import java.util.ArrayList;

public class Main implements Runnable {

    // the object to track inputs
    private final Input input = new Input();

    // window to render to
    private final Window window = new Window(input);

    // camera for the scene
    private Camera camera;

    // shaders to use
    private Shader shader;

    // the position of the light source
    private Vector3f lightPosition = new Vector3f(0, 100, 0);

    // renderers
    private Renderer renderer;

    // objects to render
    private ArrayList<Molecule> molecules;

    /**
     * runs the program
     * @param args - JVM arguments
     */
    public static void main(String[] args) {
        new Main().start();
    }

    /**
     * starts the thread
     */
    public void start() {
        Thread main = new Thread(this, "Molecular 2.0");
        main.start();
    }

    /**
     * runs the thread
     */
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

    private void render() {
        // render the objects
        for (Molecule molecule : molecules) {
            for (Atom atom : molecule.getAtoms()) {
                renderer.renderMesh(atom.getRenderSphere(), camera, shader, Renderer.COLOR);
            }
        }

        // swap frame buffer at the end
        window.swapBuffers();
    }

    private void initMolecule() {
        this.molecules = new ArrayList<>();
        Molecule molecule = new Molecule();
        molecule.addAtom(new Atom(1, new Vector3f(0, 0, 0)));
        this.molecules.add(molecule);
    }

    private void init() {
        window.create();
        shader = new Shader("shaders/default/vertex.glsl", "shaders/default/fragment.glsl").create();
        renderer = new Renderer(window, lightPosition);

        // initialize the camera
        camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), input);
        camera.setFocus(new Vector3f(0, 0, 0));

        // initialize molecule
        initMolecule();
    }

    private void update() {
        window.update();

        // clear the screen and mask bits
        GL46.glClearColor(window.r, window.g, window.b, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);

        // update objects

        // update the camera
        camera.updateArcball();
    }

    private void close() {
        // release the window
        window.destroy();

        // release the shader s
        shader.destroy();
    }

}
