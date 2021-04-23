package com.bramerlabs.engine.objects.shapes.shapes_3d;

import com.bramerlabs.engine.graphics.Mesh;
import com.bramerlabs.engine.graphics.Vertex;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import com.bramerlabs.engine.objects.RenderObject;
import com.bramerlabs.engine.objects.shapes.shapes_2d.Circle;
import com.bramerlabs.engine.objects.shapes.shapes_2d.Triangle;

import java.util.ArrayList;

public class Cylinder extends RenderObject {

    private static final Vector3f position = new Vector3f(0, 0, 0);
    private static final Vector3f rotation = new Vector3f(0, 0, 0);
    private static final Vector3f scale    = new Vector3f(1, 1, 1);

    private static final int SMOOTHNESS = 120;

    /**
     * default constructor for specified values
     *
     * @param mesh - the mesh that this object is made of
     * @param position - the position of this object
     * @param rotation - the rotation of this object
     * @param scale    - the scale of this object
     */
    public Cylinder(Mesh mesh, Vector3f position, Vector3f rotation, Vector3f scale) {
        super(mesh, position, rotation, scale);
    }

    public static Cylinder getInstance(Vector3f p1, Vector3f p2, Vector4f color, float radius, boolean sphericalEdges) {
        ArrayList<Triangle> triangles = generateTriangles(p1, p2, radius);

        // the triangles in the spheres at the ends of the cylinder
        ArrayList<Triangle> sphere1 = Sphere.generateTriangles(radius);
        ArrayList<Triangle> sphere2 = Sphere.generateTriangles(radius);

        Mesh mesh;
        if (sphericalEdges) {
            mesh = generateMesh(triangles, color, sphere1, sphere2, p1, p2); // rounded edges
        } else {
            mesh = generateMesh(triangles, color); // flat, unrendered edges
        }
        return new Cylinder(mesh, position, rotation, scale);
    }

    /**
     * generates a mesh for this cylinder
     *
     * @param triangles - the triangles used to make this mesh
     * @param color     - the color of this mesh
     * @return - the new mesh
     */
    private static Mesh generateMesh(ArrayList<Triangle> triangles, Vector4f color) {
        // create the vertex array
        Vertex[] vertices = new Vertex[triangles.size() * 3];
        for (int i = 0; i < triangles.size(); i++) {
            Triangle t = triangles.get(i);
            vertices[3 * i] = new Vertex(t.getV1(), color, t.getNormal());
            vertices[3 * i + 1] = new Vertex(t.getV2(), color, t.getNormal());
            vertices[3 * i + 2] = new Vertex(t.getV3(), color, t.getNormal());
        }
        int[] indices = new int[triangles.size() * 3];
        for (int i = 0; i < triangles.size() * 3; i++) {
            indices[i] = i;
        }
        return new Mesh(vertices, indices, null);
    }

    /**
     * generates a rounded mesh
     * @param triangles - the triangles used in the cylindrical part of this mesh
     * @param color - the color of this mesh
     * @param sphere1 - the triangles in the spheres at the ends of the cylinder
     * @param sphere2 - the triangles in the spheres at the ends of the cylinder
     * @param p1 - the position of the first sphere
     * @param p2 - the position of the second sphere
     * @return - the new mesh
     */
    private static Mesh generateMesh(ArrayList<Triangle> triangles, Vector4f color, ArrayList<Triangle> sphere1, ArrayList<Triangle> sphere2, Vector3f p1, Vector3f p2) {
        ArrayList<Vertex> vertices = new ArrayList<>();
        // the triangles on the cylindrical face
        for (Triangle t : triangles) {
            vertices.add(new Vertex(t.getV1(), color, t.getNormal()));
            vertices.add(new Vertex(t.getV2(), color, t.getNormal()));
            vertices.add(new Vertex(t.getV3(), color, t.getNormal()));
        }
        // the triangles on sphere at point p1
        for (Triangle t : sphere1) {
            t.move(p1);
            vertices.add(new Vertex(t.getV1(), color, Vector3f.subtract(t.getV1(), p1)));
            vertices.add(new Vertex(t.getV2(), color, Vector3f.subtract(t.getV2(), p1)));
            vertices.add(new Vertex(t.getV3(), color, Vector3f.subtract(t.getV3(), p1)));
        }
        // the triangles on the sphere at point p2
        for (Triangle t : sphere2) {
            t.move(p2);
            vertices.add(new Vertex(t.getV1(), color, Vector3f.subtract(t.getV1(), p2)));
            vertices.add(new Vertex(t.getV2(), color, Vector3f.subtract(t.getV2(), p2)));
            vertices.add(new Vertex(t.getV3(), color, Vector3f.subtract(t.getV3(), p2)));
        }
        return new Mesh(vertices);
    }

    private static ArrayList<Triangle> generateTriangles(Vector3f p1, Vector3f p2, float radius) {
        Circle[] circles = generateCircles(p1, p2, radius);
        ArrayList<Triangle> faces = new ArrayList<>();

        ArrayList<Vector3f> v1 = circles[0].getVertices();
        ArrayList<Vector3f> v2 = circles[1].getVertices();

        for (int i = 0; i < v1.size() - 1; i++) {
            // make normal vectors
            Vector3f n1 = Vector3f.cross(Vector3f.subtract(v1.get(i), v1.get(i + 1)), Vector3f.subtract(v2.get(i), v1.get(i + 1)));
            Vector3f n2 = Vector3f.cross(Vector3f.subtract(v1.get(i + 1), v2.get(i + 1)), Vector3f.subtract(v2.get(i), v2.get(i + 1)));
            // make the triangles
            faces.add(new Triangle(v2.get(i), v1.get(i + 1), v1.get(i), n1));
            faces.add(new Triangle(v2.get(i + 1), v1.get(i + 1), v2.get(i), n2));
        }
        // make normal vectors
        Vector3f n1 = Vector3f.cross(Vector3f.subtract(v1.get(v1.size() - 1), v1.get(0)), Vector3f.subtract(v2.get(v2.size() - 1), v1.get(v1.size() - 1)));
        Vector3f n2 = Vector3f.cross(Vector3f.subtract(v1.get(0), v2.get(v2.size() - 1)), Vector3f.subtract(v2.get(v2.size() - 1), v2.get(0)));
        // make the triangles
        faces.add(new Triangle(v2.get(v1.size() - 1), v1.get(0), v1.get(v1.size() - 1), n1));
        faces.add(new Triangle(v2.get(0), v1.get(0), v2.get(v2.size() - 1), n2));
        return faces;
    }

    private static Circle[] generateCircles(Vector3f p1, Vector3f p2, float radius) {
        Vector3f normal = Vector3f.subtract(p1, p2);
        Circle c1 = new Circle(p1, radius, normal, SMOOTHNESS);
        Circle c2 = new Circle(p2, radius, normal, SMOOTHNESS);

        return new Circle[]{c1, c2};
    }

}
