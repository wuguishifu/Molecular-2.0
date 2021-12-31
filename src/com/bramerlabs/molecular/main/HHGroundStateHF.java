package com.bramerlabs.molecular.main;

import com.bramerlabs.molecular.engine3D.math.comparators.ArrayIndexComparator;
import com.bramerlabs.molecular.engine3D.math.functions.ErrorFunction;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;
import java.util.Arrays;

public class HHGroundStateHF {

    public static void main(String[] args) {
        double Z = 1, Elast, Dii;
        double[] alpha1;
        double[] alpha;
        double[] ra;
        double[] rplot;
        double[] EVal;
        double Vi;
        double[][] T = new double[8][8], S = new double[8][8], A = new double[8][8], C, Ci, P, S_D, F = new double[8][8];
        double[][][][] G = new double[8][8][8][8];
        int asize, count;
        double as, ap, rat, rp, F0, as1, ap1, as2, ap2, rq;
        RealMatrix V, D, Vecp, Vec, Val;
        double r, NNr, nor, J;
        ArrayList<Double> Erec = new ArrayList<>();

        for (int y = 1; y <= 1; y++) {
            r = 0.45f + 0.05f * y; // distance between nucleii in a.u. (units of a0)
            NNr = 1/r;

            // alphas for the Gaussian basis functions
            alpha1 = new double[]{13.00773, 1.962079, 0.444529, 0.1219492};
            alpha = new double[]{13.00773, 1.962079, 0.444529, 0.1219492,
                    13.00773, 1.962079, 0.444529, 0.1219492};
            asize = alpha.length;

            // one H nucleus at origin, other at r
            ra = new double[]{0, 0, 0, 0, r, r, r, r};

            // run over all basis functions
            for (int i = 0; i < 8; i++) {
                // do not run over all basis functions to exploit symmetry
                for (int j = 0; j < i; j++) {
                    as = alpha[i] + alpha[j];
                    ap = alpha[i] * alpha[j];
                    rat = ap/as;

                    // location of the Gaussian resulting from product of the 2 basis functions
                    rp = (alpha[i] * ra[i] + alpha[j] * ra[j]) / as;
                    double a = -rat * (ra[i] - ra[j]) * (ra[i] - ra[j]);
                    S[i][j] = Math.pow((Math.PI/as), 1.5) * Math.exp(a);
                    S[j][i] = S[i][j];
                    T[i][j] = 0.5 * rat * (6-4 * rat * (ra[i]-ra[j]) * (ra[i]-ra[j])) * S[i][j];
                    T[j][i] = T[i][j];
                    double sqrt = Math.sqrt(as * (rp - r) * (rp - r));
                    if (rp == 0) {
                        F0 = 1+Math.sqrt(Math.PI)/2 * ErrorFunction.erf(sqrt) / sqrt;
                    } else if (rp == r) {
                        F0 = Math.sqrt(Math.PI)/2 * ErrorFunction.erf(as*rp*rp)/Math.sqrt(as*rp*rp)+1;
                    } else {
                        F0 = Math.sqrt(Math.PI)/2 * (ErrorFunction.erf(Math.sqrt(as*rp*rp))/Math.sqrt(as*rp*rp)
                                                        + ErrorFunction.erf(sqrt)/sqrt);
                    }
                    A[i][j] = -2*Math.PI*Z/as*Math.exp(a)*F0;
                    A[j][i] = A[i][j];
                }
            }

            S_D = new double[8][8];
            for (int i = 0; i < 8; i++) {
                System.arraycopy(S[i], 0, S_D[i], 0, 8);
            }
            RealMatrix S_matrix = MatrixUtils.createRealMatrix(S_D);
            EigenDecomposition decomposition = new EigenDecomposition(S_matrix);
            V = decomposition.getV();
            D = decomposition.getD();

            double[][] V_array_temp = new double[8][8];
            for (int i = 0; i < asize; i++) {
                double[] V_array = V.getColumn(0);
                Dii = Math.pow(D.getEntry(i, i), 0.5);
                System.out.println(Dii);
                for (int j = 0; j < V_array.length; j++) {
                    V_array[j] /= Dii;
                    V_array_temp[i][j] = V_array[j];
                }
            }
            V = MatrixUtils.createRealMatrix(V_array_temp);

            // iterate over each Gaussian basis function
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < i; j++) {
                    as1 = alpha[i] + alpha[j];
                    ap1 = alpha[i] * alpha[j];
                    rp = (alpha[i] * ra[i] + alpha[j] * ra[j])/as1;
                    for (int k = 0; k < i - 1; k++) {
                        double v = -ap1 * (ra[i] - ra[j]) * (ra[i] - ra[j]) / as1;
                        for (int l = 0; l < k; l++) {
                            as2 = alpha[k] + alpha[l];
                            ap2 = alpha[k] * alpha[l];
                            rq = (alpha[k] * ra[k] + alpha[l] * ra[l])/as2;
                            if ((rp - rq) == 0) {
                                F0 = 1;
                            } else {
                                double sqrt = Math.sqrt(as1 * as2 * (rp - rq) * (rp - rq) / (as1 + as2));
                                F0 = Math.sqrt(Math.PI)/2 * ErrorFunction.erf(sqrt) / sqrt;
                            }
                            G[i][j][k][l] = 2 * Math.pow(Math.PI, 2.5) / as1 / as2 / Math.sqrt(as1+as2)
                                                                * Math.exp(v - ap2 * (ra[k]-ra[l]) * (ra[k]-ra[l])) * F0;
                            G[k][l][i][j] = G[i][j][k][l]; // via symmetry
                            G[j][i][k][l] = G[i][j][k][l];
                            G[i][j][l][k] = G[i][j][k][l];
                            G[j][i][l][k] = G[i][j][k][l];
                            G[k][l][j][i] = G[i][j][k][l];
                            G[l][k][i][j] = G[i][j][k][l];
                            G[l][k][j][i] = G[i][j][k][l];
                        }
                        k = i;
                        for (int l = 0; l < j; l++) {
                            as2 = alpha[k] + alpha[l];
                            ap2 = alpha[k] * alpha[l];
                            rq = (alpha[k] * ra[k] + alpha[l] * ra[l])/as2;
                            if ((rp - rq) == 0) {
                                F0 = 1;
                            } else {
                                double sqrt = Math.sqrt(as1 * as2 * (rp-rq) * (rp-rq) / (as1+as2));
                                F0 = Math.sqrt(Math.PI)/2 * ErrorFunction.erf(sqrt) / sqrt;
                            }
                            G[i][j][k][l] = 2 * Math.pow(Math.PI, 2.5) / as1 / as2 / Math.sqrt(as1+as2)
                                                                * Math.exp(v - ap2 * (ra[k]-ra[l]) * (ra[k]-ra[l]) / as2) * F0;
                            G[k][l][i][j] = G[i][j][k][l]; // via symmetry
                            G[j][i][k][l] = G[i][j][k][l];
                            G[i][j][l][k] = G[i][j][k][l];
                            G[j][i][l][k] = G[i][j][k][l];
                            G[k][l][j][i] = G[i][j][k][l];
                            G[l][k][i][j] = G[i][j][k][l];
                            G[l][k][j][i] = G[i][j][k][l];
                        }
                    }
                }
            }

            // self consistent loop
            C = new double[1][asize];
            Ci = new double[asize][1];
            for (int i = 0; i < asize; i++) {
                C[0][i] = 1;
                Ci[i][0] = 1;
            }
            RealMatrix C_M = MatrixUtils.createRealMatrix(C);
            RealMatrix C_Mi = MatrixUtils.createRealMatrix(Ci);
            nor = C_M.multiply(S_matrix).multiply(C_Mi).getColumn(0)[0];
            for (int i = 0; i < asize; i++) {
                C[0][i] /= nor;
            }

            Elast = 1;
            EVal = new double[8];
            P = new double[asize][asize];
            for (int i = 0; i < asize; i++) {
                for (int j = 0; j < asize; j++) {
                    P[i][j] = C[0][i] * C[0][j];
                }
            }
            count = 0;
            rplot = new double[500];
            for (int i = 0; i < 500; i++) {
                rplot[i] = -2 + 0.01f * i;
            }

            // generating Fock matrix
            while ((Math.abs(EVal[0] - Elast)) > 0.000001) {
                count += 1;
                Elast = EVal[0];
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        J = 0;
                        for (int k = 0; k < 8; k++) {
                            for (int l = 0; l < 8; l++) {
                                J = J + P[k][l] * G[i][j][k][l];
                            }
                        }
                        F[i][j] = T[i][j] + A[i][j] + J;
                    }
                }

                // solve generalized eigenvalue problem FC=ESC
                // inverse of V is its conjugate transpose because V is unitary
                RealMatrix F_M = MatrixUtils.createRealMatrix(F);
                RealMatrix V_T = V.transpose();
                RealMatrix F_P_ = V_T.multiply(V);
                RealMatrix F_P = F_P_.multiply(V);
                EigenDecomposition eigenDecomposition = new EigenDecomposition(F_P);
                Vecp = eigenDecomposition.getV();
                Val = eigenDecomposition.getD();
                Vec = V.multiply(Vecp);
                RealMatrix EigVal = MatrixUtils.createRealDiagonalMatrix(V.getColumn(0));
                double[] vals = V.getColumn(0);
                Double[] vals_ = new Double[vals.length];
                for (int i = 0; i < vals.length; i++) {
                    vals_[i] = vals[i];
                }

                ArrayIndexComparator comparator = new ArrayIndexComparator(vals_);
                Integer[] indices = comparator.createIndexArray();
                Arrays.sort(indices, comparator);
                Arrays.sort(vals);

                Erec.add(EVal[0]);
            }
            System.out.println(Erec);
        }
    }

}
