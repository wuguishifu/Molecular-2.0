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
        double[][][][] G = new double[8][8][8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 8; k++) {
                    for (int l = 0; l < 8; l++) {
                        G[i][j][k][l] = i + 10 * j + 100 * k + 1000 * l;
                    }
                }
            }
        }
        System.out.println();
    }

}
