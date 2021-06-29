package com.bramerlabs.molecular.molecule.bond;

import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import com.bramerlabs.engine.objects.RenderObject;
import com.bramerlabs.engine.objects.shapes.shapes_3d.Cylinder;
import com.bramerlabs.molecular.molecule.atom.Atom;

public class Bond {

    // the atoms this bond connects
    private Atom left, right;
    public static final int
        LEFT = 0,
        RIGHT = 1,
        NULL = -1;

    // the order of the bond
    private int order = 1;
    public static final int
        SINGLE = 1,
        DOUBLE = 2,
        TRIPLE = 3;

    private Vector3f position, scale;
    private Vector3f[] rotationAxes;
    private float[] rotationAngles;

    // makes a single bond
    public Bond(Atom left, Atom right) {
        this.left = left;
        this.right = right;
        recalculateModelComponents();
    }

    // makes a variable order bond
    public Bond(Atom left, Atom right, int order) {
        this.left = left;
        this.right = right;
        this.order = order;
        recalculateModelComponents();
    }

    public int getSide(Atom atom) {
        if (atom == left) {
            return LEFT;
        } else if (atom == right) {
            return RIGHT;
        } else {
            return NULL;
        }
    }

    public void recalculateModelComponents() {
        this.position = Vector3f.midpoint(left.getPosition(), right.getPosition());
        this.scale = new Vector3f(1, Vector3f.distance(left.getPosition(), right.getPosition()), 1);

        Vector3f normal = Vector3f.normalize(Vector3f.subtract(left.getPosition(), right.getPosition()));
        Vector3f direct = Vector3f.normalize(Vector3f.subtract(position, right.getPosition()));
        rotationAxes = new Vector3f[] {
                Vector3f.normalize(Vector3f.cross(normal, Vector3f.e2)),
                null
        };
        rotationAngles = new float[] {
                (float) Math.toDegrees(Math.acos(Vector3f.dot(Vector3f.e2, direct)
                        * Vector3f.quickInverseSqrt(Vector3f.e2) * Vector3f.quickInverseSqrt(direct))),
                0
        };

//        // rotates so the double bonds are normal to <0, 1, 0>
//        Matrix4f rotationMatrix = Matrix4f.rotate(rotationAngles[0], rotationAxes[0]);
//        Vector3f binorm = Matrix4f.multiply(rotationMatrix, new Vector4f(1, 0, 0, 0)).xyz();
//        rotationAxes[1] = Vector3f.normalize(Vector3f.cross(Vector3f.e2, binorm));
//        rotationAngles[1] = (float) Math.toDegrees(Math.acos(Vector3f.dot(binorm, Vector3f.e2)
//                * Vector3f.quickInverseSqrt(Vector3f.e2) * Vector3f.quickInverseSqrt(binorm)));

    }

    public int getOrder() {
        return this.order;
    }

    public Atom getLeft() {
        return this.left;
    }

    public Atom getRight() {
        return this.right;
    }

    public void setLeft(Atom left) {
       this.left = left;
    }

    public void setRight(Atom right) {
        this.right = right;
    }

    // attempts to add an atom to a non-connected side
    public boolean addAtom(Atom atom) {
        if (left == null) {
            left = atom;
            return true;
        } else if (right == null) {
            right = atom;
            return true;
        } else {
            return false;
        }
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public Vector3f[] getRotationAxes() {
        return this.rotationAxes;
    }

    public Vector3f getScale() {
        return this.scale;
    }

    public float[] getRotationAngles() {
        return this.rotationAngles;
    }

    private static final float d = 0.4f;

    public static final RenderObject[] SINGLE_BOND = new RenderObject[]{
            Cylinder.getInstance(new Vector3f(0, -0.5f, 0), new Vector3f(0, 0.5f, 0),
                    new Vector4f(0.3f, 0.3f, 0.3f, 1), 0.2f, false).createMesh()
    };

    public static final RenderObject[] DOUBLE_BOND = new RenderObject[]{
            Cylinder.getInstance(new Vector3f(d, -0.5f, 0), new Vector3f(d, 0.5f, 0),
                    new Vector4f(0.3f, 0.3f, 0.3f, 1), 0.2f, false).createMesh(),
            Cylinder.getInstance(new Vector3f(-d, -0.5f, 0), new Vector3f(-d, 0.5f, 0),
                    new Vector4f(0.3f, 0.3f, 0.3f, 1), 0.2f, false).createMesh(),
    };

    public static final RenderObject[] TRIPLE_BOND = new RenderObject[]{
            Cylinder.getInstance(new Vector3f(0, -0.5f, 0), new Vector3f(0, 0.5f, 0),
                    new Vector4f(0.3f, 0.3f, 0.3f, 1), 0.2f, false).createMesh(),
            Cylinder.getInstance(new Vector3f(d, -0.5f, 0), new Vector3f(d, 0.5f, 0),
                    new Vector4f(0.3f, 0.3f, 0.3f, 1), 0.2f, false).createMesh(),
            Cylinder.getInstance(new Vector3f(-d, -0.5f, 0), new Vector3f(-d, 0.5f, 0),
                    new Vector4f(0.3f, 0.3f, 0.3f, 1), 0.2f, false).createMesh(),
    };

}
