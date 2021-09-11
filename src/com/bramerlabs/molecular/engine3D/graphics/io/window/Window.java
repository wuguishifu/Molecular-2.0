package com.bramerlabs.molecular.engine3D.graphics.io.window;

import com.bramerlabs.molecular.engine3D.math.matrix.Matrix4f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL46;
import org.lwjglx.LWJGLException;
import org.lwjglx.opengl.Display;

import java.awt.*;

public class Window {

    private String title;
    private static final int framerateCapped = GLFW.GLFW_TRUE;
    private DisplayMode displayMode;
    public int width, height;
    private int defaultWidth, defaultHeight;

    public Vector3f backgroundColor;
    public float r, g, b;

    private long windowHandle;

    private int frames;
    private long time;

    private Input input;
    private Matrix4f projection;

    public Window(WindowConstants wc, Input input) {
        this.title = wc.name;
        this.backgroundColor = wc.backgroundColor;
        this.r = backgroundColor.x;
        this.g = backgroundColor.y;
        this.b = backgroundColor.z;

        this.displayMode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        this.defaultWidth = displayMode.getWidth()/2;
        this.defaultHeight = displayMode.getHeight()/2;

        this.width = defaultWidth;
        this.height = defaultHeight;

        this.projection = Matrix4f.projection(70.0f, (float) width / (float) height, 0.1f, 1000f);
        this.input = input;
    }

    public void create() {
        this.time = System.currentTimeMillis();
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize the GLFW window.");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 4);

        this.windowHandle = GLFW.glfwCreateWindow(width, height, title, GLFW.GLFW_FALSE, GLFW.GLFW_FALSE);
        if (windowHandle == GLFW.GLFW_FALSE) {
            throw new RuntimeException("Failed to initialize the GLFW window.");
        }

        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        assert vidMode != null;
        GLFW.glfwSetWindowSize(windowHandle, vidMode.width()/2, vidMode.height()/2);
        GLFW.glfwSetWindowPos(windowHandle, vidMode.width()/4, vidMode.height()/4);
        input.setWindowX(vidMode.width()/4);
        input.setWindowY(vidMode.height()/4);
//        GLFW.glfwSetWindowSize(windowHandle, width, height);
//        GLFW.glfwSetWindowPos(windowHandle, 0, 0);
//        input.setWindowX(0);
//        input.setWindowY(0);

        GLFW.glfwMakeContextCurrent(windowHandle);
        GL.createCapabilities();

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glDepthFunc(GL11.GL_LESS);
        GL11.glEnable(GL46.GL_MULTISAMPLE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL46.glClearColor(r, g, b, 1);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
        GL46.glViewport(0, 0, width, height);

        GLFW.glfwSetKeyCallback(windowHandle, input.getKeyCallback());
        GLFW.glfwSetMouseButtonCallback(windowHandle, input.getMouseButtonCallback());
        GLFW.glfwSetCursorPosCallback(windowHandle, input.getCursorPosCallback());
        GLFW.glfwSetWindowSizeCallback(windowHandle, input.getWindowSizeCallback());
        GLFW.glfwSetWindowPosCallback(windowHandle, input.getWindowPosCallback());
        GLFW.glfwSetScrollCallback(windowHandle, input.getScrollCallback());

        GLFW.glfwShowWindow(windowHandle);

        GLFW.glfwSwapInterval(framerateCapped);
        GLFW.glfwSetWindowTitle(windowHandle, title + " | FPS: " + frames);
    }

    public void update() {
        if (input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            GLFW.glfwSetWindowShouldClose(windowHandle, true);
        }

        GLFW.glfwPollEvents();

        if (input.isResized()) {
            this.width = input.getWindowWidth();
            this.height = input.getWindowHeight();
            this.projection = Matrix4f.projection(70.0f, (float) width / (float) height, 0.1f, 100f);
            input.setResized(false);
        }

        frames++;
        if (System.currentTimeMillis() > time + 1000) {
            GLFW.glfwSetWindowTitle(windowHandle, title + " | FPS: " + frames);
            time = System.currentTimeMillis();
            frames = 0;
        }
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(windowHandle);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(windowHandle);
    }

    public void close() {
        GLFW.glfwSetWindowShouldClose(windowHandle, true);
    }

    public void destroy() {
        input.destroy();
        GLFW.glfwSetWindowShouldClose(windowHandle, true);
        GLFW.glfwDestroyWindow(windowHandle);
        GLFW.glfwTerminate();
    }

    public void clear() {
        GL46.glClearColor(this.r, this.g, this.b, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
    }

    public Matrix4f getProjectionMatrix() {
        return this.projection;
    }

}
