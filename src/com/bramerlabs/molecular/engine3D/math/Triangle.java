package com.bramerlabs.molecular.engine3D.math;

import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;

public class Triangle {

    public Vector3f v1, v2, v3;
    public Vector3f normal;

    public Triangle(Vector3f v1, Vector3f v2, Vector3f v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public Triangle(Vector3f v1, Vector3f v2, Vector3f v3, Vector3f normal) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.normal = normal;
    }

}
