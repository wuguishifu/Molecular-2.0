package com.bramerlabs.molecular.molecule.bond;

import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import com.bramerlabs.engine.objects.RenderObject;
import com.bramerlabs.engine.objects.shapes.shapes_3d.Cylinder;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.atom.AtomicData;

public class Bond {

    // the atoms this bond connects
    private Atom left, right;
    private Vector3f vLeft, vRight;
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

    public static final float DEFAULT_BOND_LENGTH = AtomicData.getBondLength(6, 6, 1);

    private Vector3f position, scale;
    private Vector3f[] rotationAxes;
    private float[] rotationAngles;

    boolean hasEmpty;

    // makes a single bond
    public Bond(Atom left, Atom right) {
        this.left = left;
        this.right = right;
        this.vLeft = left.getPosition();
        this.vRight = right.getPosition();
        this.hasEmpty = false;
        recalculateModelComponents();
    }

    // makes a variable order bond
    public Bond(Atom left, Atom right, int order) {
        this.left = left;
        this.right = right;
        this.order = order;
        this.vLeft = left.getPosition();
        this.vRight = right.getPosition();
        this.hasEmpty = false;
        recalculateModelComponents();
    }

    public Bond(Atom left, Vector3f vRight, int order) {
        this.left = left;
        this.right = null;
        this.order = order;
        this.vLeft = left.getPosition();
        this.vRight = vRight;
        this.hasEmpty = true;
        recalculateModelComponents();
    }

    public Vector3f getEmptyPosition() {
        if (this.left == null) {
            return this.vLeft;
        } else if (this.right == null) {
            return this.vRight;
        } else {
            return null;
        }
    }

    public int getEmptySide() {
        if (this.left == null) {
            return LEFT;
        } else if (this.right == null) {
            return RIGHT;
        } else {
            return NULL;
        }
    }

    public void setEmptyAtom(Atom atom) {
        if (hasEmpty) {
            if (this.right == null) {
                this.right = atom;
            } else if (this.left == null) {
                this.left = atom;
            }
            hasEmpty = false;
        }
    }

    public void setEmptyPos(Vector3f empty) {
        if (hasEmpty) {
            if (this.right == null) {
                this.vRight = empty;
            } else {
                this.vLeft = empty;
            }
        }
    }

    public void recalculateModelComponents() {
        if (left != null) {
            vLeft = left.getPosition();
        }
        if (right != null) {
            vRight = right.getPosition();
        }

        this.position = Vector3f.midpoint(vLeft, vRight);
        this.scale = new Vector3f(1, Vector3f.distance(vLeft, vRight), 1);

        Vector3f normal = Vector3f.normalize(Vector3f.subtract(vLeft, vRight));
        Vector3f direct = Vector3f.normalize(Vector3f.subtract(position, vRight));

        Vector3f v = Vector3f.e2;
        if (normal.equals(Vector3f.subtract(new Vector3f(0, 0, 0), v))) {
            v = Vector3f.e1;
        }

        rotationAxes = new Vector3f[] {
                Vector3f.normalize(Vector3f.cross(normal, v)),
                null
        };
        rotationAngles = new float[] {
                (float) Math.toDegrees(Math.acos(Vector3f.dot(v, direct)
                        * Vector3f.quickInverseSqrt(v) * Vector3f.quickInverseSqrt(direct))),
                0
        };
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

    public Vector3f getvLeft() {
        return vLeft;
    }

    public Vector3f getvRight() {
        return vRight;
    }

    public void setvLeft(Vector3f vLeft) {
        this.vLeft = vLeft;
    }

    public void setvRight(Vector3f vRight) {
        this.vRight = vRight;
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

    @Override
    public String toString() {
        return "Bond connecting (" + left + ") and (" + right + ")";
    }

}
