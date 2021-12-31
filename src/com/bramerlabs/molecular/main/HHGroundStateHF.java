package com.bramerlabs.molecular.main;

import com.bramerlabs.molecular.engine3D.math.functions.ErrorFunction;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class HHGroundStateHF {

    public static void main(String[] args) {
        float Z = 1, F = 0, Vecp = 0, Vec = 0, Val = 0, EVal = 0;
        float[][] T = new float[8][8], S = new float[8][8], A = new float[8][8];
        float[] alpha1, alpha, ra;
        int asize;
        float as, ap, rat, rp, F0;
        RealMatrix V, D;

        for (int y = 1; y <= 41; y++) {
            float r = 0.45f + 0.05f * y; // distance between nucleii in a.u. (units of a0)
            float NNr = 1/r;

            // alphas for the Gaussian basis functions
            alpha1 = new float[]{13.00773f, 1.962079f, 0.444529f, 0.1219492f};
            alpha = new float[]{13.00773f, 1.962079f, 0.444529f, 0.1219492f,
                    13.00773f, 1.962079f, 0.444529f, 0.1219492f};
            asize = alpha.length;

            // one H nucleus at origin, other at r
            ra = new float[]{0, 0, 0, 0, r, r, r, r};

            // run over all basis functions
            for (int i = 0; i < 8; i++) {
                // do not run over all basis functions to exploit symmetry
                for (int j = 0; j < i; j++) {
                    as = alpha[i] + alpha[j];
                    ap = alpha[i] * alpha[j];
                    rat = ap/as;

                    // location of the Gaussian resulting from product of the 2 basis functions
                    rp = (alpha[i] * ra[i] + alpha[j] * ra[j]) / as;
                    float a = -rat * (ra[i] - ra[j]) * (ra[i] - ra[j]);
                    S[i][j] = (float) (Math.pow((Math.PI/as), 1.5) * Math.exp(a));
                    S[j][i] = S[i][j];
                    T[i][j] = (float) (0.5 * rat * (6-4 * rat * (ra[i]-ra[j]) * (ra[i]-ra[j])) * S[i][j]);
                    T[j][i] = T[i][j];
                    double erf = Math.sqrt(as * (rp - r) * (rp - r));
                    if (rp == 0) {
                        F0 = (float) (1+Math.sqrt(Math.PI)/2 * ErrorFunction.erf(erf) / erf);
                    } else if (rp == r) {
                        F0 = (float) (Math.sqrt(Math.PI)/2 * ErrorFunction.erf(as*rp*rp)/Math.sqrt(as*rp*rp)+1);
                    } else {
                        F0 = (float) (Math.sqrt(Math.PI)/2 * (ErrorFunction.erf(Math.sqrt(as*rp*rp))/Math.sqrt(as*rp*rp)
                                                        + ErrorFunction.erf(erf)/erf));
                    }
                    A[i][j] = (float) (-2*Math.PI*Z/as*Math.exp(a)*F0);
                    A[j][i] = A[i][j];
                }
            }

            double[][] S_D = new double[8][8];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    S_D[i][j] = S[i][j];
                }
            }
            RealMatrix S_matrix = MatrixUtils.createRealMatrix(S_D);
            EigenDecomposition decomposition = new EigenDecomposition(S_matrix);
            V = decomposition.getV();
            D = decomposition.getD();
            for (int i = 0; i < asize; i++) {
                double[] Vi = V.getColumn(i);
                double Dii = Math.sqrt(D.getColumn(i)[i]);
                for (int d = 0; d < Vi.length; d++) {
                    Vi[d] /= Dii;
                }
                V.setColumn(i, Vi);
            }
        }
    }

}
