package com.bramerlabs.molecular.engine3D.objects;

import com.bramerlabs.molecular.engine3D.graphics.mesh.ColorMesh;
import com.bramerlabs.molecular.engine3D.graphics.mesh.Mesh;
import com.bramerlabs.molecular.engine3D.graphics.vertex.ColorVertex;
import com.bramerlabs.molecular.engine3D.math.Triangle;
import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector4f;

import java.util.ArrayList;

public class IcoSphere extends RenderObject {

    public IcoSphere(Vector3f position, Vector4f color, float radius) {
        super(generateMesh(color), position, new Vector3f(0), new Vector3f(radius));
    }

    public static Mesh generateMesh(Vector4f color) {
        ArrayList<Triangle> triangles = generateTriangles(4);
        ColorVertex[] vertices = new ColorVertex[triangles.size() * 3];
        for (int i = 0; i < triangles.size(); i++) {
            Triangle t = triangles.get(i);
            vertices[3 * i    ] = new ColorVertex(t.v1, t.v1, color);
            vertices[3 * i + 1] = new ColorVertex(t.v2, t.v2, color);
            vertices[3 * i + 2] = new ColorVertex(t.v3, t.v3, color);
        }
        int[] indices = new int[triangles.size() * 3];
        for (int i = 0; i < triangles.size() * 3; i++) {
            indices[i] = i;
        }
        return new ColorMesh(vertices, indices);
    }

    private static final float PHI_OVER_TWO = 1.618033988749895f/2.0f;
    private static final Vector3f[] v = new Vector3f[]{
            new Vector3f( 0.5f, 0,  PHI_OVER_TWO),
            new Vector3f( 0.5f, 0, -PHI_OVER_TWO),
            new Vector3f(-0.5f, 0,  PHI_OVER_TWO),
            new Vector3f(-0.5f, 0, -PHI_OVER_TWO),
            new Vector3f( PHI_OVER_TWO,  0.5f, 0),
            new Vector3f( PHI_OVER_TWO, -0.5f, 0),
            new Vector3f(-PHI_OVER_TWO,  0.5f, 0),
            new Vector3f(-PHI_OVER_TWO, -0.5f, 0),
            new Vector3f(0,  PHI_OVER_TWO,  0.5f),
            new Vector3f(0,  PHI_OVER_TWO, -0.5f),
            new Vector3f(0, -PHI_OVER_TWO,  0.5f),
            new Vector3f(0, -PHI_OVER_TWO, -0.5f),
    };

    public static ArrayList<Triangle> generateTriangles(int depth) {
        ArrayList<Triangle> faces = new ArrayList<>();
        faces.addAll(subdivide(v[0], v[2], v[10], depth));
        faces.addAll(subdivide(v[0], v[10], v[5], depth));
        faces.addAll(subdivide(v[0], v[5], v[4], depth));
        faces.addAll(subdivide(v[0], v[4], v[8], depth));
        faces.addAll(subdivide(v[0], v[8], v[2], depth));
        faces.addAll(subdivide(v[3], v[1], v[11], depth));
        faces.addAll(subdivide(v[3], v[11], v[7], depth));
        faces.addAll(subdivide(v[3], v[7], v[6], depth));
        faces.addAll(subdivide(v[3], v[6], v[9], depth));
        faces.addAll(subdivide(v[3], v[9], v[1], depth));
        faces.addAll(subdivide(v[2], v[6], v[7], depth));
        faces.addAll(subdivide(v[2], v[7], v[10], depth));
        faces.addAll(subdivide(v[10], v[7], v[11], depth));
        faces.addAll(subdivide(v[10], v[11], v[5], depth));
        faces.addAll(subdivide(v[5], v[11], v[1], depth));
        faces.addAll(subdivide(v[5], v[1], v[4], depth));
        faces.addAll(subdivide(v[4], v[1], v[9], depth));
        faces.addAll(subdivide(v[4], v[9], v[8], depth));
        faces.addAll(subdivide(v[8], v[9], v[6], depth));
        faces.addAll(subdivide(v[8], v[6], v[2], depth));
        return faces;
    }

    private static ArrayList<Triangle> subdivide(Vector3f v1, Vector3f v2, Vector3f v3, int depth) {
        ArrayList<Triangle> faces = new ArrayList<>();
        if (depth == 0) {
            Vector3f v1p = Vector3f.normalize(v1);
            Vector3f v2p = Vector3f.normalize(v2);
            Vector3f v3p = Vector3f.normalize(v3);
            faces.add(new Triangle(v1p, v2p, v3p));
            return faces;
        }
        Vector3f v12 = Vector3f.normalize(new Vector3f(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z));
        Vector3f v23 = Vector3f.normalize(new Vector3f(v2.x + v3.x, v2.y + v3.y, v2.z + v3.z));
        Vector3f v31 = Vector3f.normalize(new Vector3f(v3.x + v1.x, v3.y + v1.y, v3.z + v1.z));
        faces.addAll(subdivide( v1, v12, v31, depth - 1));
        faces.addAll(subdivide( v2, v23, v12, depth - 1));
        faces.addAll(subdivide( v3, v31, v23, depth - 1));
        faces.addAll(subdivide(v12, v23, v31, depth - 1));
        return faces;
    }

}
