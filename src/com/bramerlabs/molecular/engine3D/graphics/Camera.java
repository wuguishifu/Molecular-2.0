package com.bramerlabs.molecular.engine3D.graphics;

import com.bramerlabs.molecular.engine3D.graphics.io.window.Input;
import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {

    // the position and rotation of the camera
    private Vector3f position, rotation;

    // the input object for handling callbacks
    private Input input;

    // mouse motion variables
    private final static float moveSpeed = 0.05f, mouseSensitivity = 0.1f;
    private final static float rotateSpeed = 0.02f * 360;

    // arcball camera variables
    private Vector3f focus; // the position the camera is looking at

    private static final float DEFAULT_DISTANCE = 3f;
    private float distance = DEFAULT_DISTANCE; // the magnitude distance to the looking position

    private static final float DEFAULT_HORIZONTAL_DISTANCE = 0, DEFAULT_VERTICAL_DISTANCE = 0; // default distance from looking position
    private float horizontalDistance = 0, verticalDistance = 0; // distance from the looking position

    private static final float DEFAULT_VERTICAL_ANGLE = -30, DEFAULT_HORIZONTAL_ANGLE = 30; // default angles
    private float verticalAngle = -90, horizontalAngle = 0; // used for looking straight forward

    private boolean rotatingVertical = false, rotatingHorizontal = false; // used for constraint rotation
    private boolean translatingNorthSouth = false, translatingEastWest = false; // used for constraint translation

    // the position of the mouse
    private double oldMouseX = 0, oldMouseY = 0, newMouseX, newMouseY;

    // the position of the scroll wheel
    private double oldScrollX = 0, oldScrollY = 0, newScrollX = 0, newScrollY = 0;

    /**
     * default constructor for specified position, rotation, and input object
     * @param position - the position of the camera object
     * @param rotation - the rotation of the camera object
     * @param input - the callback input object
     */
    public Camera(Vector3f position, Vector3f rotation, Input input) {
        this.position = position;
        this.rotation = rotation;
        this.input = input;
//        setIdealPosition();
    }

    /**
     * sets the distance of the camera
     * @param distance - the new distance
     */
    public void setDistance(float distance) {
        this.distance = distance;
    }

    /**
     * sets the ideal position for taking screenshots of benzaldehyde - used for the website
     */
    public void setIdealPosition() {
        this.verticalDistance = -11.076831f;
        this.horizontalDistance = 10.114534f;
        this.distance = 15.0f;
        this.verticalAngle = -47.6000006f;
        this.horizontalAngle = -78.2f;
        this.focus = new Vector3f(1.1676779f, -1.4711119f, 0.15995185f);
    }

    /**
     * sets the vector that the arcball camera is looking at
     * @param v - the position the camera is rotating around
     */
    public void setFocus(Vector3f v) {
        this.focus = v;
    }

    /**
     * updates the camera based on keyboard and mouse input
     */
    public void update() {

        newMouseX = input.getMouseX();
        newMouseY = input.getMouseY();

        float x = (float)Math.sin(Math.toRadians(rotation.getY())) * moveSpeed;
        float z = (float)Math.cos(Math.toRadians(rotation.getY())) * moveSpeed;

        // handle the WASD keys
        if (input.isKeyDown(GLFW.GLFW_KEY_A)) position = Vector3f.add(position, new Vector3f(-z, 0,  x));
        if (input.isKeyDown(GLFW.GLFW_KEY_D)) position = Vector3f.add(position, new Vector3f( z, 0, -x));
        if (input.isKeyDown(GLFW.GLFW_KEY_W)) position = Vector3f.add(position, new Vector3f(-x, 0, -z));
        if (input.isKeyDown(GLFW.GLFW_KEY_S)) position = Vector3f.add(position, new Vector3f( x, 0,  z));

        // handle going up and down
        if (input.isKeyDown(GLFW.GLFW_KEY_SPACE)) position = Vector3f.add(position, new Vector3f(0, moveSpeed, 0));
        if (input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) position = Vector3f.add(position, new Vector3f(0, -moveSpeed, 0));

        // handle mouse motion
        float dx = (float) (newMouseX - oldMouseX);
        float dy = (float) (newMouseY - oldMouseY);

        oldMouseX = newMouseX;
        oldMouseY = newMouseY;

        // rotate according to the mouse motion
        rotation = Vector3f.add(rotation, new Vector3f(-dy * mouseSensitivity, -dx * mouseSensitivity, 0)); //dx, dy must be flipped and inverted
    }

    /**
     * update method for an arcball camera
     */
    public void updateArcball() {
        if (input.isKeyDown(GLFW.GLFW_KEY_ENTER)) {
            this.verticalAngle = DEFAULT_VERTICAL_ANGLE;
            this.horizontalAngle = DEFAULT_HORIZONTAL_ANGLE;
            this.distance = DEFAULT_DISTANCE;
        }

        // get the new x and y components of the mouse position
        newMouseX = input.getMouseX();
        newMouseY = input.getMouseY();

        // handle mouse motion
        float dmx = (float) (newMouseX - oldMouseX);
        float dmy = (float) (newMouseY - oldMouseY);

        // get the new x and y components of the scroll wheel
        //newScrollX = input.getScrollX();
        newScrollY = input.getScrollY();

        // handle scroll motion
        //float dsx = (float) (newScrollX - oldScrollX);
        float dsy = (float) (newScrollY - oldScrollY);

        oldMouseX = newMouseX;
        oldMouseY = newMouseY;
        //oldScrollX = newScrollX;
        oldScrollY = newScrollY;

        // change the rotation using the mouse
        if (input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            verticalAngle -= dmy * mouseSensitivity;
            horizontalAngle += dmx * mouseSensitivity;
        }

        // change the camera distance using the scroll wheel
        distance = Math.max(distance - dsy, 0);

        // get the vertical and horizontal distances
        this.horizontalDistance = (float) (distance * Math.cos(Math.toRadians(verticalAngle))); // using formula h = r*cos(theta_x)
        this.verticalDistance = (float) (distance * Math.sin(Math.toRadians(verticalAngle))); // using formula v = r*sin(theta_x)

        float xOffset = (float) (horizontalDistance * Math.sin(Math.toRadians(-horizontalAngle)));
        float zOffset = (float) (horizontalDistance * Math.cos(Math.toRadians(-horizontalAngle)));

        // set the new camera position based on the object
        this.position.set(focus.getX() + xOffset,
                focus.getY() - verticalDistance,
                focus.getZ() + zOffset);

        // set the new camera rotation based on the object
        this.rotation.set(verticalAngle, -horizontalAngle, 0);
    }

    /**
     * translates the point at which the camera is looking at
     */
    public void translate() {
        // get the new x and y components of the mouse position
        newMouseX = input.getMouseX();
        newMouseY = input.getMouseY();

        // handle mouse motion
        float dmx = (float) (newMouseX - oldMouseX);
        float dmy = (float) (newMouseY - oldMouseY);

        // handle constraint motion
        if (!translatingNorthSouth && !translatingEastWest) {
            if (Math.abs(dmx) > Math.abs(dmy)) {
                dmy = 0;
                translatingEastWest = true;
            } else {
                dmx = 0;
                translatingNorthSouth = true;
            }
        }
        if (translatingEastWest) {
            dmy = 0;
        }
        if (translatingNorthSouth) {
            dmx = 0;
        }

        // store the previous mouse position
        oldMouseX = newMouseX;
        oldMouseY = newMouseY;

        // find the vector pointing from the camera to the looking point
        Vector3f lookingDirection = Vector3f.normalize(Vector3f.subtract(focus, position));
        // create a non-parallel vector in the plane of the horizontal angle
        Vector3f tempV1 = Vector3f.add(lookingDirection, new Vector3f(0, 1.f, 0)); // will never be parallel
        if (tempV1.equals(new Vector3f(0), 0.0001f)) {
            tempV1 = Vector3f.add(lookingDirection, new Vector3f(0, 2.f, 0));
        }

        // create normal vector to these two vectors
        Vector3f zxNormal = Vector3f.normalize(Vector3f.cross(tempV1, lookingDirection));
        // create normal vector to the zx normal and the looking direction
        Vector3f yNormal = Vector3f.normalize(Vector3f.cross(lookingDirection, zxNormal));

        if (dmx < 0) {
            this.focus = Vector3f.add(this.focus, Vector3f.normalize(zxNormal, moveSpeed * mouseSensitivity * Math.abs(dmx)));
            this.position = Vector3f.add(this.position, Vector3f.normalize(zxNormal, moveSpeed * mouseSensitivity * Math.abs(dmx)));
        }
        if (dmx > 0) {
            this.focus = Vector3f.subtract(this.focus, Vector3f.normalize(zxNormal, moveSpeed * mouseSensitivity * Math.abs(dmx)));
            this.position = Vector3f.subtract(this.position, Vector3f.normalize(zxNormal, moveSpeed * mouseSensitivity * Math.abs(dmx)));
        }
        if (dmy > 0) {
            this.focus = Vector3f.subtract(this.focus, Vector3f.normalize(yNormal, moveSpeed * mouseSensitivity * Math.abs(dmy)));
            this.position = Vector3f.subtract(this.position, Vector3f.normalize(yNormal, moveSpeed * mouseSensitivity * Math.abs(dmy)));
        }
        if (dmy < 0) {
            this.focus = Vector3f.add(this.focus, Vector3f.normalize(yNormal, moveSpeed * mouseSensitivity * Math.abs(dmy)));
            this.position = Vector3f.add(this.position, Vector3f.normalize(yNormal, moveSpeed * mouseSensitivity * Math.abs(dmy)));
        }
    }

    /**
     * resets the vertical and horizontal angles to their default values
     */
    public void resetPosition(Vector3f focus) {
        // reset the looking vector
        this.focus = focus;

        // reset the angles
        this.verticalAngle = DEFAULT_VERTICAL_ANGLE;
        this.horizontalAngle = DEFAULT_HORIZONTAL_ANGLE;

        // reset the distances
        this.verticalDistance = DEFAULT_VERTICAL_DISTANCE;
        this.horizontalDistance = DEFAULT_HORIZONTAL_ANGLE;
        this.distance = DEFAULT_DISTANCE;
    }

    /**
     * rotates the camera but some angle
     * @param dTheta - the change in the angle
     */
    public void incHorizontalAngle(float dTheta) {
        this.horizontalAngle += Math.toRadians(dTheta);
    }

    /**
     * getter method
     * @return - the position of this camera
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * getter method
     * @return - the rotation of this camera
     */
    public Vector3f getRotation() {
        return rotation;
    }

    /**
     * getter method
     * @return - the vertical angle
     */
    public float getVerticalAngle() {
        return this.verticalAngle;
    }

    /**
     * getter method
     * @return - the horizontal angle
     */
    public float getHorizontalAngle() {
        return this.horizontalAngle;
    }

    /**
     * getter method
     * @return - the point this camera is looking at
     */
    public Vector3f getLookingAt() {
        return this.focus;
    }

    /**
     * getter method
     * @return - the horizontal distance to the looking position
     */
    public float getHorizontalDistance() {
        return this.horizontalDistance;
    }

    /**
     * getter method
     * @return - the vertical distance to the looking position
     */
    public float getVerticalDistance() {
        return this.verticalDistance;
    }

    public void setHorizontalDistance(float horizontalDistance) {
        this.horizontalDistance = horizontalDistance;
    }

    public void setVerticalDistance(float verticalDistance) {
        this.verticalDistance = verticalDistance;
    }

    public void setVerticalAngle(float verticalAngle) {
        this.verticalAngle = verticalAngle;
    }

    public void setHorizontalAngle(float horizontalAngle) {
        this.horizontalAngle = horizontalAngle;
    }
}
