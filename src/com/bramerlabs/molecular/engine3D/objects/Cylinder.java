package com.bramerlabs.molecular.engine3D.objects;

import com.bramerlabs.molecular.engine3D.graphics.mesh.ColorMesh;
import com.bramerlabs.molecular.engine3D.graphics.mesh.Mesh;
import com.bramerlabs.molecular.engine3D.graphics.vertex.ColorVertex;
import com.bramerlabs.molecular.engine3D.math.Triangle;
import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector4f;

import java.util.ArrayList;

public class Cylinder extends RenderObject {

    private static final int numCircleVertices = 120;

    public Cylinder(Mesh mesh) {
        super(mesh, Vector3f.zero, Vector3f.zero, Vector3f.one);
    }

    public Cylinder(Vector3f p1, Vector3f p2, Vector4f color, float radius) {
        super(generateMesh(p1, p2, radius, color), Vector3f.zero, Vector3f.zero, Vector3f.one);
    }

    public static Cylinder makeCylinder(Vector3f p1, Vector3f p2, Vector4f color, float radius) {
        ArrayList<Triangle> triangles = generateTriangles(p1, p2, radius, numCircleVertices);
        ArrayList<Triangle> sphere1 = IcoSphere.generateTriangles(4);
        ArrayList<Triangle> sphere2 = IcoSphere.generateTriangles(4);
        for (Triangle t : sphere1) {
            t.v1 = Vector3f.add(Vector3f.normalize(t.v1, radius), p1);
            t.v2 = Vector3f.add(Vector3f.normalize(t.v2, radius), p1);
            t.v3 = Vector3f.add(Vector3f.normalize(t.v3, radius), p1);
        }
        for (Triangle t : sphere2) {
            t.v1 = Vector3f.add(Vector3f.normalize(t.v1, radius), p2);
            t.v2 = Vector3f.add(Vector3f.normalize(t.v2, radius), p2);
            t.v3 = Vector3f.add(Vector3f.normalize(t.v3, radius), p2);
        }
        return new Cylinder(generateMesh(p1, p2, triangles, sphere1, sphere2, color));
    }

    public static Mesh generateMesh(Vector3f p1, Vector3f p2, ArrayList<Triangle> triangles, ArrayList<Triangle> sphere1, ArrayList<Triangle> sphere2, Vector4f color) {
        ArrayList<ColorVertex> v = new ArrayList<>();
        for (Triangle t : triangles) {
            v.add(new ColorVertex(t.v1, t.normal, color));
            v.add(new ColorVertex(t.v2, t.normal, color));
            v.add(new ColorVertex(t.v3, t.normal, color));
        }
        for (Triangle t : sphere1) {
            v.add(new ColorVertex(t.v1, Vector3f.subtract(t.v1, p1), color));
            v.add(new ColorVertex(t.v2, Vector3f.subtract(t.v2, p1), color));
            v.add(new ColorVertex(t.v3, Vector3f.subtract(t.v3, p1), color));
        }
        for (Triangle t : sphere2) {
            v.add(new ColorVertex(t.v1, Vector3f.subtract(t.v1, p1), color));
            v.add(new ColorVertex(t.v2, Vector3f.subtract(t.v2, p1), color));
            v.add(new ColorVertex(t.v3, Vector3f.subtract(t.v3, p1), color));
        }

        ColorVertex[] vertices = new ColorVertex[v.size()];
        for (int i = 0; i < v.size(); i++) {
            vertices[i] = v.get(i);
        }

        int[] indices = new int[v.size()];
        for (int i = 0; i < v.size(); i++) {
            indices[i] = i;
        }

        return new ColorMesh(vertices, indices);
    }

    public static Mesh generateMesh(Vector3f p1, Vector3f p2, float radius, Vector4f color) {
        ArrayList<Triangle> triangles = generateTriangles(p1, p2, radius, numCircleVertices);
        ColorVertex[] vertices = new ColorVertex[triangles.size() * 3];
        for (int i = 0; i < triangles.size(); i++) {
            Triangle t = triangles.get(i);
            vertices[3 * i] = new ColorVertex(t.v1, t.normal, color);
            vertices[3 * i + 1] = new ColorVertex(t.v2, t.normal, color);
            vertices[3 * i + 2] = new ColorVertex(t.v3, t.normal, color);
        }
        int[] indices = new int[triangles.size() * 3];
        for (int i = 0; i < triangles.size() * 3; i++) {
            indices[i] = i;
        }
        return new ColorMesh(vertices, indices);
    }

    private static ArrayList<Triangle> generateTriangles(Vector3f p1, Vector3f p2, float radius, int numCircleVertices) {
        Circle[] circles = generateCircles(p1, p2, radius, numCircleVertices);
        ArrayList<Triangle> faces = new ArrayList<>();

        ArrayList<Vector3f> v1 = circles[0].getVertices();
        ArrayList<Vector3f> v2 = circles[1].getVertices();

        for (int i = 0; i < v1.size() - 1; i++) {

            // make normal vectors
            Vector3f n1 = Vector3f.cross(Vector3f.subtract(v1.get(i), v1.get(i + 1)),
                    Vector3f.subtract(v2.get(i), v1.get(i + 1)));
            Vector3f n2 = Vector3f.cross(Vector3f.subtract(v1.get(i + 1), v2.get(i + 1)),
                    Vector3f.subtract(v2.get(i), v2.get(i + 1)));

            // make the triangles
            faces.add(new Triangle(v2.get(i), v1.get(i + 1), v1.get(i), n1));
            faces.add(new Triangle(v2.get(i + 1), v1.get(i + 1), v2.get(i), n2));
        }

        // make normal vectors
        Vector3f n1 = Vector3f.cross(Vector3f.subtract(v1.get(v1.size() - 1), v1.get(0)),
                Vector3f.subtract(v2.get(v2.size() - 1), v1.get(v1.size() - 1)));
        Vector3f n2 = Vector3f.cross(Vector3f.subtract(v1.get(0), v2.get(v2.size() - 1)),
                Vector3f.subtract(v2.get(v2.size() - 1), v2.get(0)));

        // make the triangles
        faces.add(new Triangle(v2.get(v1.size() - 1), v1.get(0), v1.get(v1.size() - 1), n1));
        faces.add(new Triangle(v2.get(0), v1.get(0), v2.get(v2.size() - 1), n2));

        return faces;
    }

    private static Circle[] generateCircles(Vector3f p1, Vector3f p2, float radius, int numCircleVertices) {
        Vector3f normal = Vector3f.subtract(p1, p2);
        return new Circle[] {
                new Circle(p1, radius, normal, numCircleVertices),
                new Circle(p2, radius, normal, numCircleVertices)
        };
    }

}
