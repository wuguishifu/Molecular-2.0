package com.bramerlabs.molecular.molecule;

import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import com.bramerlabs.engine.objects.shapes.shapes_3d.Cylinder;

public class Bond {

    private Vector3f p1, p2;
    private Atom a1, a2;

    private Cylinder renderCylinder;

    private float grayValue = 0.2f;
    private Vector4f color = new Vector4f(new Vector3f(grayValue), 1.0f);

    public Bond(Atom a1, Atom a2) {
        this.a1 = a1;
        this.a2 = a2;
        this.p1 = a1.getPosition();
        this.p2 = a2.getPosition();
        this.renderCylinder = Cylinder.getInstance(p1, p2, color, 0.05f, true);
        this.renderCylinder.createMesh();
    }

    public Bond(Atom a1, Vector3f p2) {
        this.a1 = a1;
        this.a2 = null;
        this.p1 = a1.getPosition();
        this.p2 = p2;
        this.renderCylinder = Cylinder.getInstance(p1, p2, color, 0.05f, true);
        this.renderCylinder.createMesh();
    }

    public Bond(Vector3f p1, Vector3f p2) {
        this.a1 = null;
        this.a2 = null;
        this.p1 = p1;
        this.p2 = p2;
        this.renderCylinder = Cylinder.getInstance(p1, p2, color, 0.05f, true);
        this.renderCylinder.createMesh();
    }

    public Vector3f getP1() {
        return p1;
    }

    public void setP1(Vector3f p1) {
        this.p1 = p1;
    }

    public Vector3f getP2() {
        return p2;
    }

    public void setP2(Vector3f p2) {
        this.p2 = p2;
    }

    public Atom getA1() {
        return a1;
    }

    public void setA1(Atom a1) {
        this.a1 = a1;
        this.setP1(a2.getPosition());
    }

    public Atom getA2() {
        return a2;
    }

    public void setA2(Atom a2) {
        this.a2 = a2;
        this.setP2(a2.getPosition());
    }

    public Cylinder getRenderCylinder() {
        return this.renderCylinder;
    }
}