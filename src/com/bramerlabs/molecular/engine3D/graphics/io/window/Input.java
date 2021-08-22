package com.bramerlabs.molecular.engine3D.graphics.io.window;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL46;

public class Input {

    private GLFWKeyCallback keyCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWCursorPosCallback cursorPosCallback;
    private GLFWWindowSizeCallback windowSizeCallback;
    private GLFWWindowPosCallback windowPosCallback;
    private GLFWScrollCallback scrollCallback;

    private boolean[] keysDown = new boolean[GLFW.GLFW_KEY_LAST];
    private boolean[] buttonsDown = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    private float mouseX, mouseY;
    private float scrollX, scrollY;
    private int windowX, windowY;
    private int windowWidth, windowHeight;
    private boolean resized;

    public Input() {
        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keysDown[key] = (action != GLFW.GLFW_RELEASE);
            }
        };

        mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                buttonsDown[button] = (action != GLFW.GLFW_RELEASE);
            }
        };

        cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mouseX = (float) xpos;
                mouseY = (float) ypos;
            }
        };

        windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                windowWidth = width;
                windowHeight = height;
                GL46.glViewport(0, 0, width, height);
                resized = true;
            }
        };

        windowPosCallback = new GLFWWindowPosCallback() {
            @Override
            public void invoke(long window, int xpos, int ypos) {
                windowX = xpos;
                windowY = ypos;
            }
        };

        scrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                scrollX += xoffset;
                scrollY += yoffset;
            }
        };
    }

    public void destroy() {
        keyCallback.free();
        mouseButtonCallback.free();
        cursorPosCallback.free();
        windowPosCallback.free();
        windowSizeCallback.free();
        scrollCallback.free();
    }

    public GLFWKeyCallback getKeyCallback() {
        return keyCallback;
    }

    public GLFWMouseButtonCallback getMouseButtonCallback() {
        return mouseButtonCallback;
    }

    public GLFWCursorPosCallback getCursorPosCallback() {
        return cursorPosCallback;
    }

    public GLFWWindowSizeCallback getWindowSizeCallback() {
        return windowSizeCallback;
    }

    public GLFWWindowPosCallback getWindowPosCallback() {
        return windowPosCallback;
    }

    public GLFWScrollCallback getScrollCallback() {
        return scrollCallback;
    }

    public boolean[] getKeysDown() {
        return keysDown;
    }

    public boolean[] getButtonsDown() {
        return buttonsDown;
    }

    public float getMouseX() {
        return mouseX;
    }

    public float getMouseY() {
        return mouseY;
    }

    public float getScrollX() {
        return scrollX;
    }

    public float getScrollY() {
        return scrollY;
    }

    public int getWindowX() {
        return windowX;
    }

    public int getWindowY() {
        return windowY;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public boolean isResized() {
        return resized;
    }

    public void setKeyCallback(GLFWKeyCallback keyCallback) {
        this.keyCallback = keyCallback;
    }

    public void setMouseButtonCallback(GLFWMouseButtonCallback mouseButtonCallback) {
        this.mouseButtonCallback = mouseButtonCallback;
    }

    public void setCursorPosCallback(GLFWCursorPosCallback cursorPosCallback) {
        this.cursorPosCallback = cursorPosCallback;
    }

    public void setWindowSizeCallback(GLFWWindowSizeCallback windowSizeCallback) {
        this.windowSizeCallback = windowSizeCallback;
    }

    public void setWindowPosCallback(GLFWWindowPosCallback windowPosCallback) {
        this.windowPosCallback = windowPosCallback;
    }

    public void setScrollCallback(GLFWScrollCallback scrollCallback) {
        this.scrollCallback = scrollCallback;
    }

    public void setKeysDown(boolean[] keysDown) {
        this.keysDown = keysDown;
    }

    public void setButtonsDown(boolean[] buttonsDown) {
        this.buttonsDown = buttonsDown;
    }

    public void setMouseX(float mouseX) {
        this.mouseX = mouseX;
    }

    public void setMouseY(float mouseY) {
        this.mouseY = mouseY;
    }

    public void setScrollX(float scrollX) {
        this.scrollX = scrollX;
    }

    public void setScrollY(float scrollY) {
        this.scrollY = scrollY;
    }

    public void setWindowX(int windowX) {
        this.windowX = windowX;
    }

    public void setWindowY(int windowY) {
        this.windowY = windowY;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public void setResized(boolean resized) {
        this.resized = resized;
    }

    public boolean isKeyDown(int key) {
        return keysDown[key];
    }

    public boolean isMouseButtonDown(int button) {
        return buttonsDown[button];
    }
}
