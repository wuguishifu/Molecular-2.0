package com.bramerlabs.molecular.engine3D.objects;

import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;

public class Triangle {

    private Vector3f v1, v2, v3;

    public Triangle(Vector3f v1, Vector3f v2, Vector3f v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public Vector3f getV1() {
        return this.v1;
    }

    public Vector3f getV2() {
        return this.v2;
    }

    public Vector3f getV3() {
        return this.v3;
    }

}
