package com.bramerlabs.molecular.engine3D.math.linear;

import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class Linear {

    public static RealMatrix[] eig(double[][] M) {
        EigenDecomposition eigenDecomposition = new EigenDecomposition(MatrixUtils.createRealMatrix(M));
        return new RealMatrix[]{eigenDecomposition.getV(), eigenDecomposition.getD()};
    }

    public static RealMatrix[] eig(RealMatrix M) {
        EigenDecomposition eigenDecomposition = new EigenDecomposition(M);
        return new RealMatrix[]{eigenDecomposition.getV(), eigenDecomposition.getD()};
    }

    public static void main(String[] args) {
        double[][] S = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                S[i][j] = 10 * i + j;
            }
        }
        RealMatrix M = MatrixUtils.createRealMatrix(S);
        RealMatrix N = M.transpose();
        System.out.println(M);
    }

}
