package com.bramerlabs.engine.objects.shapes.shapes_3d;

import com.bramerlabs.engine.graphics.Mesh;
import com.bramerlabs.engine.graphics.Vertex;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import com.bramerlabs.engine.objects.RenderObject;

public class Cube extends RenderObject {

    /**
     *
     * @param position - the position of this cube
     * @param rotation - the rotation of this cube
     * @param scale - the scale of this cube
     * @param color - the color of this cube
     */
    public Cube(Vector3f position, Vector3f rotation, Vector3f scale, Vector4f color) {
        super(new Mesh(new Vertex[] {
                // front face
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), color, new Vector3f(0, 0, 1)), // 0, 1
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), color, new Vector3f(0, 0, 1)), // 1, 1
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), color, new Vector3f(0, 0, 1)), // 1, 0
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), color, new Vector3f(0, 0, 1)), // 0, 0

                // back face
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), color, new Vector3f(0, 0, -1)),
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), color, new Vector3f(0, 0, -1)),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), color, new Vector3f(0, 0, -1)),
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), color, new Vector3f(0, 0, -1)),

                // right face
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), color, new Vector3f(1, 0, 0)),
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), color, new Vector3f(1, 0, 0)),
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), color, new Vector3f(1, 0, 0)),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), color, new Vector3f(1, 0, 0)),

                // left face
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), color, new Vector3f(-1, 0, 0)),
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), color, new Vector3f(-1, 0, 0)),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), color, new Vector3f(-1, 0, 0)),
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), color, new Vector3f(-1, 0, 0)),

                // top face
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), color, new Vector3f(0, 1, 0)),
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), color, new Vector3f(0, 1, 0)),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), color, new Vector3f(0, 1, 0)),
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), color, new Vector3f(0, 1, 0)),

                // bottom face
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), color, new Vector3f(0, -1, 0)),
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), color, new Vector3f(0, -1, 0)),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), color, new Vector3f(0, -1, 0)),
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), color, new Vector3f(0, -1, 0)),
        }, new int[] {
                // front face
                0, 1, 2,
                2, 3, 0,

                // back face
                5, 4, 7,
                5, 7, 6,

                // right face
                8, 10, 11,
                8, 11, 9,

                // left face
                14, 12, 13,
                14, 13, 15,

                // top face
                17, 16, 18,
                17, 18, 19,

                // bottom face
                20, 22, 23,
                20, 23, 21,
        }, null), position, rotation, scale);
    }



    /**
     * constructor for specified position with default rotation [0, 0, 0] and default scale [1, 1, 1]
     * @param position - the position of this cube
     * @param color - the color of this cube
     */
    public Cube(Vector3f position, Vector4f color) {
        super(new Mesh(new Vertex[] {
                // front face
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), color, new Vector3f(0, 0, 1)), // 0, 1
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), color, new Vector3f(0, 0, 1)), // 1, 1
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), color, new Vector3f(0, 0, 1)), // 1, 0
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), color, new Vector3f(0, 0, 1)), // 0, 0

                // back face
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), color, new Vector3f(0, 0, -1)),
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), color, new Vector3f(0, 0, -1)),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), color, new Vector3f(0, 0, -1)),
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), color, new Vector3f(0, 0, -1)),

                // right face
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), color, new Vector3f(1, 0, 0)),
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), color, new Vector3f(1, 0, 0)),
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), color, new Vector3f(1, 0, 0)),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), color, new Vector3f(1, 0, 0)),

                // left face
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), color, new Vector3f(-1, 0, 0)),
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), color, new Vector3f(-1, 0, 0)),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), color, new Vector3f(-1, 0, 0)),
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), color, new Vector3f(-1, 0, 0)),

                // top face
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), color, new Vector3f(0, 1, 0)),
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), color, new Vector3f(0, 1, 0)),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), color, new Vector3f(0, 1, 0)),
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), color, new Vector3f(0, 1, 0)),

                // bottom face
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), color, new Vector3f(0, -1, 0)),
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), color, new Vector3f(0, -1, 0)),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), color, new Vector3f(0, -1, 0)),
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), color, new Vector3f(0, -1, 0)),
        }, new int[] {
                // front face
                0, 1, 2,
                2, 3, 0,

                // back face
                5, 4, 7,
                5, 7, 6,

                // right face
                8, 10, 11,
                8, 11, 9,

                // left face
                14, 12, 13,
                14, 13, 15,

                // top face
                17, 16, 18,
                17, 18, 19,

                // bottom face
                20, 22, 23,
                20, 23, 21,
        }, null), position, new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
    }
}
