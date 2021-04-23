package com.bramerlabs.engine.io.window;

import com.bramerlabs.engine.math.matrix.Matrix4f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.test.EngineConstants;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL46;

import java.awt.*;

public class Window {

    // the window title
    private String TITLE = EngineConstants.APPLICATION_NAME;

    // the framerate - true designates use default refresh rate
    private static final int FRAMERATE = GLFW.GLFW_TRUE; // 60 fps on standard monitors

    // the fullscreen display mode size
    private DisplayMode displayMode;

    // window size and position
    private int width, height;
    private int defaultWidth, defaultHeight;
    private int x, y;

    // background color
    public Vector3f backgroundColor = EngineConstants.BACKGROUND_COLOR;
    public float r = EngineConstants.BACKGROUND_R;
    public float g = EngineConstants.BACKGROUND_G;
    public float b = EngineConstants.BACKGROUND_B;

    // the window handle
    private long windowHandle;

    // framerate calculation
    private int frames;
    private long time;

    // window altering
    private boolean isFullscreen = false;

    // window callbacks
    private Input input;

    // the projection matrix
    private Matrix4f projection;

    /**
     * constructor for specified input object
     * @param input - the input for handling callbacks in this window
     */
    public Window(Input input) {
        // get the display mode
        this.displayMode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();

        // set the default width and height variables
        this.defaultWidth = displayMode.getWidth() / 2;
        this.defaultHeight = displayMode.getHeight() / 2;

        // set the current width and height to the default
        this.width = defaultWidth;
        this.height = defaultHeight;

        // create a projection matrix
        this.projection = Matrix4f.projection(70.0f, (float) width / (float) height, 0.1f, 100f);

        // set the input device
        this.input = input;
    }

    /**
     * initialize the window
     */
    public void create() {
        // the start time of the window - used for framerate calculation
        this.time = System.currentTimeMillis();

        // attempt to initialize the GLFW window
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize the GLFW window.");
        }

        // set the window hints
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 4); // used for antialiasing

        // create the window
        this.windowHandle = GLFW.glfwCreateWindow(
                isFullscreen ? displayMode.getWidth() : width,
                isFullscreen ? displayMode.getHeight() : height,
                TITLE,
                isFullscreen ? GLFW.glfwGetPrimaryMonitor() : GLFW.GLFW_FALSE,
                GLFW.GLFW_FALSE
        );

        // check to see if the window was properly initialized
        if (windowHandle == GLFW.GLFW_FALSE) {
            throw new RuntimeException("Failed to initialize the GLFW window.");
        }

        // get the current monitor resolution
        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

        // center the window
        if (!isFullscreen) {
            assert vidMode != null;
            GLFW.glfwSetWindowSize(windowHandle, vidMode.width() / 2, vidMode.height() / 2);
            GLFW.glfwSetWindowPos(windowHandle, vidMode.width() / 4, vidMode.height() / 4);

            // store the centered position of the window
            this.x = vidMode.width() / 4;
            this.y = vidMode.width() / 4;
        }

        // set the GLFW context
        GLFW.glfwMakeContextCurrent(windowHandle);

        // make an OpenGL window - must be done before running any OpenGL methods
        GL.createCapabilities();

        // only renders objects that are facing the camera - cull any object not facing the camera
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glDepthFunc(GL11.GL_LESS);

        // enable antialiasing
        GL11.glEnable(GL46.GL_MULTISAMPLE);

        // enable blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // set the clear color
        GL46.glClearColor(r, g, b, 1);

        // clear the buffer bits
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);

        // set the viewport
        GL46.glViewport(0, 0, defaultWidth, defaultHeight);

        // set the callbacks
        GLFW.glfwSetKeyCallback(windowHandle, input.getKeyboardCallback());
        GLFW.glfwSetMouseButtonCallback(windowHandle, input.getMouseButtons());
        GLFW.glfwSetCursorPosCallback(windowHandle, input.getCursorPosition());
        GLFW.glfwSetWindowSizeCallback(windowHandle, input.getWindowSize());
        GLFW.glfwSetWindowPosCallback(windowHandle, input.getWindowPosition());
        GLFW.glfwSetScrollCallback(windowHandle, input.getScrollWheel());

        // set the window's position callback's position
        assert vidMode != null;
        input.setWindowX(vidMode.width() / 4);
        input.setWindowY(vidMode.height() / 4);

        // show the window
        GLFW.glfwShowWindow(windowHandle);

        // set the framerate of the window
        GLFW.glfwSwapInterval(FRAMERATE);
        GLFW.glfwSetWindowTitle(windowHandle, TITLE + " | FPS: " + frames);
    }

    /**
     * updates the window
     */
    public void update() {
        // decide if the window should close
        if (input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            GLFW.glfwSetWindowShouldClose(windowHandle, true);
        }

        // poll GLFW for callbacks
        GLFW.glfwPollEvents();

        // set the resized window variables
        if (input.isResized()) {
            // set the size and position variables
            this.x = input.getWindowX();
            this.y = input.getWindowY();
            this.width = input.getWindowWidth();
            this.height = input.getWindowHeight();

            // calculate a new projection matrix
            this.projection = Matrix4f.projection(70.0f, (float) width / (float) height, 0.1f, 1000f);
        }

        // calculate framerate
        frames++;
        if (System.currentTimeMillis() > time + 1000) {
            GLFW.glfwSetWindowTitle(windowHandle, TITLE + " | FPS: " + frames);
            time = System.currentTimeMillis();
            frames = 0;
        }
    }

    /**
     * swap the window's frame buffers
     */
    public void swapBuffers() {
        GLFW.glfwSwapBuffers(windowHandle);
    }

    /**
     * getter method
     * @return - true if the window should close
     */
    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(windowHandle);
    }

    /**
     * close the window
     */
    public void close() {
        GLFW.glfwSetWindowShouldClose(windowHandle, true);
    }

    /**
     * free the window
     */
    public void destroy() {
        input.destroy();
        GLFW.glfwSetWindowShouldClose(windowHandle, true);
        GLFW.glfwDestroyWindow(windowHandle);
        GLFW.glfwTerminate();
    }

    /**
     * sets the current mouse state of the window
     * @param lock - whether or not the mouse should be locked
     */
    public void setMouseState(boolean lock) {
        GLFW.glfwSetInputMode(windowHandle, GLFW.GLFW_CURSOR, lock ? GLFW.GLFW_CURSOR_DISABLED : GLFW.GLFW_CURSOR_NORMAL);
    }

    /**
     * toggles fullscreen
     */
    public void toggleFullscreen() {
        this.isFullscreen = !isFullscreen;
    }

    /**
     * getter method
     * @return - the title of this window
     */
    public String getTitle() {
        return this.TITLE;
    }

    /**
     * getter method
     * @return - the width of this window
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * getter method
     * @return - the height of this window
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * getter method
     * @return - the handle of this window
     */
    public long getWindowHandle() {
        return this.windowHandle;
    }

    /**
     * getter method
     * @return - whether or not the screen is fullscreen
     */
    public boolean isFullscreen() {
        return this.isFullscreen;
    }

    /**
     * getter method
     * @return - the projection matrix of this window
     */
    public Matrix4f getProjectionMatrix() {
        return this.projection;
    }

}
