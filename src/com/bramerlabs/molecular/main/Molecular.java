package com.bramerlabs.molecular.main;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.MoleculeRenderer;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.atom.common.Carbon;
import com.bramerlabs.molecular.molecule.atom.common.Hydrogen;
import com.bramerlabs.molecular.molecule.bond.Bond;
import com.bramerlabs.molecular.molecule.default_molecules.*;
import com.bramerlabs.molecular.molecule.functional_groups.CarbonSP3;
import com.bramerlabs.molecular.molecule.functional_groups.FunctionalGroup;
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

    boolean canPrint = true;

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
        molecule = new Molecule(new Vector3f(0, 0 , 0), new ArrayList<>(), new ArrayList<>());
        CarbonSP3 sp3 = new CarbonSP3();

        Carbon c1 = new Carbon(sp3.getAtoms()[0].getPosition());
        Bond[] bonds = new Bond[]{
                new Bond(c1, sp3.getAtoms()[1].getPosition()),
                new Bond(c1, sp3.getAtoms()[2].getPosition()),
                new Bond(c1, sp3.getAtoms()[3].getPosition()),
                new Bond(c1, sp3.getAtoms()[4].getPosition()),
        };

        molecule.add(c1);
        molecule.add(bonds);

        FunctionalGroup[] fgs = new FunctionalGroup[]{
                new CarbonSP3(bonds[0]),
                new CarbonSP3(bonds[1]),
                new CarbonSP3(bonds[2]),
                new CarbonSP3(bonds[3]),
        };

        molecule.add(fgs);

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
    }

    public void update() {
        window.update();
        GL46.glClearColor(window.r, window.g, window.b, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
        camera.updateArcball();

        if (input.isKeyDown(GLFW.GLFW_KEY_P)) {
            if (canPrint) {
                canPrint = false;
                System.out.println(molecule);
            }
        } else {
            canPrint = true;
        }

//        molecule.update();

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
