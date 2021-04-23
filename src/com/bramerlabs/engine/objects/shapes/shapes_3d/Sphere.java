package com.bramerlabs.engine.objects.shapes.shapes_3d;

import com.bramerlabs.engine.graphics.Mesh;
import com.bramerlabs.engine.graphics.Vertex;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import com.bramerlabs.engine.objects.RenderObject;
import com.bramerlabs.engine.objects.shapes.shapes_2d.Triangle;

import java.util.ArrayList;

public class Sphere extends RenderObject {

    // the amount of times to recursively subdivide faces
    private static final int depth = 4;

    // the golden ratio
    private static final float phi = 1.16180339f;

    /**
     * default constructor for specified values
     *
     * @param mesh     - the mesh that this object is made of
     * @param position - the position of this object
     * @param rotation - the rotation of this object
     * @param scale    - the scale of this object
     */
    public Sphere(Mesh mesh, Vector3f position, Vector3f rotation, Vector3f scale) {
        super(mesh, position, rotation, scale);
    }

    /**
     * create a sphere
     * @return - a new sphere
     */
    public static Sphere getInstance(Vector3f position, Vector4f color, float radius) {
        return new Sphere(generateMesh(color, radius), position, new Vector3f(0), new Vector3f(1));
    }

    /**
     * generates a mesh
     * @param color - the color of the sphere
     * @param radius - the radius of the sphere
     * @return - the mesh of the sphere
     */
    public static Mesh generateMesh(Vector4f color, float radius) {

        // generate the triangles
        ArrayList<Triangle> triangles = generateTriangles(radius);

        // create the vertex array
        Vertex[] vertices = new Vertex[triangles.size() * 3];
        for (int i = 0; i < triangles.size(); i++) {
            Triangle t = triangles.get(i);
            vertices[3 * i] =     new Vertex(t.getV1(), color, Vector3f.subtract(t.getV1(), new Vector3f(0)));
            vertices[3 * i + 1] = new Vertex(t.getV2(), color, Vector3f.subtract(t.getV2(), new Vector3f(0)));
            vertices[3 * i + 2] = new Vertex(t.getV3(), color, Vector3f.subtract(t.getV3(), new Vector3f(0)));
        }

        int[] indices = new int[triangles.size() * 3];
        for (int i = 0; i < triangles.size() * 3; i++) {
            indices[i] = i;
        }

        // make a new mesh
        return new Mesh(vertices, indices, null);
    }

    /**
     * generates a list of vertices;
     * @param position - the position of the sphere
     * @param color - the color of the sphere
     * @param radius - the radius of the sphere
     * @return - a vertex array
     */
    public static Vertex[] generateVertices(Vector3f position, Vector4f color, float radius) {
        // generate the triangles
        ArrayList<Triangle> triangles = generateTriangles(radius);

        // create the vertex array
        Vertex[] vertices = new Vertex[triangles.size() * 3];
        for (int i = 0; i < triangles.size(); i++) {
            Triangle t = triangles.get(i);
            vertices[3 * i] = new Vertex(t.getV1(), color, Vector3f.subtract(t.getV1(), position));
            vertices[3 * i + 1] = new Vertex(t.getV2(), color, Vector3f.subtract(t.getV2(), position));
            vertices[3 * i + 2] = new Vertex(t.getV3(), color, Vector3f.subtract(t.getV3(), position));
        }

        return vertices;
    }

    /**
     * generates vertices of this sphere
     * @param radius - the radius of the sphere
     */
    public static ArrayList<Triangle> generateTriangles(float radius) {

        ArrayList<Triangle> faces = new ArrayList<>();

        // define a regular icosahedron using 12 vertices
        Vector3f[] vertices = new Vector3f[12];
        vertices[0]  = new Vector3f( 0.5f * radius, 0,  phi/2 * radius);
        vertices[1]  = new Vector3f( 0.5f * radius, 0, -phi/2 * radius);
        vertices[2]  = new Vector3f(-0.5f * radius, 0,  phi/2 * radius);
        vertices[3]  = new Vector3f(-0.5f * radius, 0, -phi/2 * radius);
        vertices[4]  = new Vector3f( phi/2 * radius,  0.5f * radius, 0);
        vertices[5]  = new Vector3f( phi/2 * radius, -0.5f * radius, 0);
        vertices[6]  = new Vector3f(-phi/2 * radius,  0.5f * radius, 0);
        vertices[7]  = new Vector3f(-phi/2 * radius, -0.5f * radius, 0);
        vertices[8]  = new Vector3f(0,  phi/2 * radius, 0.5f * radius);
        vertices[9]  = new Vector3f(0,  phi/2 * radius,-0.5f * radius);
        vertices[10] = new Vector3f(0, -phi/2 * radius, 0.5f * radius);
        vertices[11] = new Vector3f(0, -phi/2 * radius,-0.5f * radius);

        // subdivide each triangular face (20 total) recursively
        faces.addAll(subdivide(vertices[0],  vertices[2],  vertices[10], depth, radius));
        faces.addAll(subdivide(vertices[0],  vertices[10], vertices[5],  depth, radius));
        faces.addAll(subdivide(vertices[0],  vertices[5],  vertices[4],  depth, radius));
        faces.addAll(subdivide(vertices[0],  vertices[4],  vertices[8],  depth, radius));
        faces.addAll(subdivide(vertices[0],  vertices[8],  vertices[2],  depth, radius));
        faces.addAll(subdivide(vertices[3],  vertices[1],  vertices[11], depth, radius));
        faces.addAll(subdivide(vertices[3],  vertices[11], vertices[7],  depth, radius));
        faces.addAll(subdivide(vertices[3],  vertices[7],  vertices[6],  depth, radius));
        faces.addAll(subdivide(vertices[3],  vertices[6],  vertices[9],  depth, radius));
        faces.addAll(subdivide(vertices[3],  vertices[9],  vertices[1],  depth, radius));
        faces.addAll(subdivide(vertices[2],  vertices[6],  vertices[7],  depth, radius));
        faces.addAll(subdivide(vertices[2],  vertices[7],  vertices[10], depth, radius));
        faces.addAll(subdivide(vertices[10], vertices[7],  vertices[11], depth, radius));
        faces.addAll(subdivide(vertices[10], vertices[11], vertices[5],  depth, radius));
        faces.addAll(subdivide(vertices[5],  vertices[11], vertices[1],  depth, radius));
        faces.addAll(subdivide(vertices[5],  vertices[1],  vertices[4],  depth, radius));
        faces.addAll(subdivide(vertices[4],  vertices[1],  vertices[9],  depth, radius));
        faces.addAll(subdivide(vertices[4],  vertices[9],  vertices[8],  depth, radius));
        faces.addAll(subdivide(vertices[8],  vertices[9],  vertices[6],  depth, radius));
        faces.addAll(subdivide(vertices[8],  vertices[6],  vertices[2],  depth, radius));

        return faces;
    }

    /**
     * recursively subdivides a triangle into 4 triangles, and then normalizes each new vertex to a radius of 1
     * @param v1 - the first vertex of the triangle
     * @param v2 - the second vertex of the triangle
     * @param v3 - the third vertex of the triangle
     * @param depth - the current depth of recursion
     */
    private static ArrayList<Triangle> subdivide(Vector3f v1, Vector3f v2, Vector3f v3, long depth, float radius) {

        ArrayList<Triangle> faces = new ArrayList<>();

        // default condition
        if (depth == 0) {

            // create new vectors to modify
            Vector3f v1p = Vector3f.normalize(v1, radius);
            Vector3f v2p = Vector3f.normalize(v2, radius);
            Vector3f v3p = Vector3f.normalize(v3, radius);

            faces.add(new Triangle(v1p, v2p, v3p));
            return faces;
        }


        // create new vertices for each face
        Vector3f v12 = Vector3f.normalize(new Vector3f(v1.getX() + v2.getX(), v1.getY() + v2.getY(), v1.getZ() + v2.getZ()), radius);
        Vector3f v23 = Vector3f.normalize(new Vector3f(v2.getX() + v3.getX(), v2.getY() + v3.getY(), v2.getZ() + v3.getZ()), radius);
        Vector3f v31 = Vector3f.normalize(new Vector3f(v3.getX() + v1.getX(), v3.getY() + v1.getY(), v3.getZ() + v1.getZ()), radius);

        // recursive part
        faces.addAll(subdivide(v1, v12, v31, depth-1, radius));
        faces.addAll(subdivide(v2, v23, v12, depth-1, radius));
        faces.addAll(subdivide(v3, v31, v23, depth-1, radius));
        faces.addAll(subdivide(v12, v23, v31,depth-1, radius));

        return faces;
    }

    /**
     * moves a sphere to a new position
     * @param position - the new position
     */
    public void moveTo(Vector3f position) {
        this.setPosition(position);
    }
}