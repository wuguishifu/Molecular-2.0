package com.bramerlabs.engine.io.window;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL46;

public class Input {

    // the keyboard callback
    private GLFWKeyCallback keyboard;

    // the mouse buttons callback
    private GLFWMouseButtonCallback mouseButtons;

    // the cursor position callback
    private GLFWCursorPosCallback cursorPosition;

    // the window size callback
    private GLFWWindowSizeCallback windowSize;

    // the window position callback
    private GLFWWindowPosCallback windowPosition;

    // scroll wheel callback
    private GLFWScrollCallback scrollWheel;

    // a list of booleans representing if the keyboard buttons are pressed or not
    private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];

    // a list of booleans representing if the mouse buttons are pressed or not
    private boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    // the current and last position of the mouse
    private double mouseX, mouseY;
    private double prevMouseX, prevMouseY;

    // the current and last scroll wheel position
    private double scrollX, scrollY;

    // the window size
    private int windowWidth, windowHeight;
    private boolean resized;

    // the window position
    private int windowX, windowY;

    /**
     * default constructor
     */
    public Input() {
        keyboard = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keys[key] = (action != GLFW.GLFW_RELEASE);
            }
        };

        mouseButtons = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                buttons[button] = (action != GLFW.GLFW_RELEASE);
            }
        };

        cursorPosition = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                prevMouseX = mouseX;
                prevMouseY = mouseY;
                mouseX = xpos;
                mouseY = ypos;
            }
        };

        windowSize = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                windowWidth = width;
                windowHeight = height;
                GL46.glViewport(0, 0, width, height);
                resized = true;
            }
        };

        windowPosition = new GLFWWindowPosCallback() {
            @Override
            public void invoke(long window, int xpos, int ypos) {
                windowX = xpos;
                windowY = ypos;
            }
        };

        scrollWheel = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                scrollX += xoffset;
                scrollY += yoffset;
            }
        };
    }

    /**
     * frees all the callbacks, releasing them from memory
     */
    public void destroy() {
        keyboard.free();
        mouseButtons.free();
        cursorPosition.free();
        scrollWheel.free();
        windowSize.free();
        windowPosition.free();
    }

    /**
     * getter method
     * @return - the keyboard callback
     */
    public GLFWKeyCallback getKeyboardCallback() {
        return keyboard;
    }

    /**
     * getter method
     * @return - the mouse buttons callback
     */
    public GLFWMouseButtonCallback getMouseButtons() {
        return mouseButtons;
    }

    /**
     * getter method
     * @return - the cursor position
     */
    public GLFWCursorPosCallback getCursorPosition() {
        return cursorPosition;
    }

    /**
     * getter method
     * @return - the window size callback
     */
    public GLFWWindowSizeCallback getWindowSize() {
        return windowSize;
    }

    /**
     * getter method
     * @return - the window position callback
     */
    public GLFWWindowPosCallback getWindowPosition() {
        return windowPosition;
    }

    /**
     * getter method
     * @return - the scroll wheel callback
     */
    public GLFWScrollCallback getScrollWheel() {
        return scrollWheel;
    }

    /**
     * getter method
     * @return - the x position of the cursor
     */
    public double getMouseX() {
        return mouseX;
    }

    /**
     * getter method
     * @return - the y position of the cursor
     */
    public double getMouseY() {
        return mouseY;
    }

    /**
     * getter method
     * @return - the previous mouse x position
     */
    public double getPrevMouseX() {
        return prevMouseX;
    }

    /**
     * getter method
     * @return - the previous mouse y position
     */
    public double getPrevMouseY() {
        return prevMouseY;
    }

    /**
     * getter method
     * @return - the current scroll x position
     */
    public double getScrollX() {
        return scrollX;
    }

    /**
     * getter method
     * @return  - the current scroll y position
     */
    public double getScrollY() {
        return scrollY;
    }

    /**
     * getter method
     * @return - the current width of the window
     */
    public int getWindowWidth() {
        return windowWidth;
    }

    /**
     * getter method
     * @return - the current height of the window
     */
    public int getWindowHeight() {
        return windowHeight;
    }

    /**
     * getter method
     * @return if the window is resized
     */
    public boolean isResized() {
        return resized;
    }

    /**
     * getter method
     * @return - the x position of the window
     */
    public int getWindowX() {
        return windowX;
    }

    /**
     * getter method
     * @return - the y position of the window
     */
    public int getWindowY() {
        return windowY;
    }

    /**
     * checks if a key is currently down
     * @param key - the key to check
     * @return - true if the key is down
     */
    public boolean isKeyDown(int key) {
        return keys[key];
    }

    /**
     * getter method
     * @return - the array of booleans representing the key presses
     */
    public boolean[] getKeysDown() {
        return this.keys;
    }

    /**
     * getter method
     * @return - the array of booleans representing the button presses
     */
    public boolean[] getButtonsDown() {
        return this.buttons;
    }

    /**
     * checks if a mouse button is currently down
     * @param button - the button to check
     * @return - true if the button is currently down
     */
    public boolean isMouseButtonDown(int button) {
        return buttons[button];
    }

    /**
     * sets a key's current state
     * @param key - the key to set
     * @param bool - if the key is down
     */
    public void setKeyDown(int key, boolean bool) {
        keys[key] = bool;
    }

    /**
     * sets a mouse button's current state
     * @param button - the mouse button
     * @param bool - if the mouse button is down
     */
    public void setButtonDown(int button, boolean bool) {
        buttons[button] = bool;
    }

    /**
     * sets the window x position
     * @param windowX - the new window x position
     */
    public void setWindowX(int windowX) {
        this.windowX = windowX;
    }

    /**
     * sets the window y position
     * @param windowY - the new window y position
     */
    public void setWindowY(int windowY) {
        this.windowY = windowY;
    }
}
