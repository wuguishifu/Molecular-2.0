package com.bramerlabs.engine.objects.shapes.shapes_2d;

import com.bramerlabs.engine.graphics.Mesh;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.objects.RenderObject;

import java.util.ArrayList;

public class Circle extends RenderObject {

    // position of the focus of the circle
    private Vector3f position;

    // radius of the circle
    private float radius;

    // vector normal to this circle
    private Vector3f normal;

    // the number of triangles used to make this circle
    private final int NUM_TRIANGLES = 120;

    // list of triangles used in the mesh of this circle
    ArrayList<Triangle> triangles = new ArrayList<>();

    // the golden ratio
    private static final float phi = 1.6180339f;

    /**
     * default constructor for specified values
     *
     * @param mesh     - the mesh that this object is made of
     * @param position - the position of this object
     * @param rotation - the rotation of this object
     * @param scale    - the scale of this object
     */
    public Circle(Mesh mesh, Vector3f position, Vector3f rotation, Vector3f scale) {
        super(mesh, position, rotation, scale);
    }


    /**
     * default constructor
     * @param position - the position of the focus of this circle
     * @param radius - the radius of this circle
     * @param normal - a vector normal to the circle
     */
    public static Circle getInstance(Vector3f position, float radius, Vector3f normal) {
        return null;
    }


}
