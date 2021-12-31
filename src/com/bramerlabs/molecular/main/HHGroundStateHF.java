package com.bramerlabs.molecular.main;

import com.bramerlabs.molecular.engine3D.math.comparators.ArrayIndexComparator;
import com.bramerlabs.molecular.engine3D.math.functions.ErrorFunction;
import com.bramerlabs.molecular.engine3D.math.linear.Linear;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;
import java.util.Arrays;

import static com.bramerlabs.molecular.engine3D.math.functions.ErrorFunction.erf;
import static java.lang.Math.*;

public class HHGroundStateHF {

    public static void main(String[] args) {

        int asize, count;
        double Z = 1, Elast, Dii, Vi, as, ap, rat, rp, F0, as1, ap1, as2, ap2, rq, r, NNr, nor, J;
        double[] a, a1, ra, rplot, Eval;
        double[][] T = new double[8][8], S = new double[8][8], A = new double[8][8], C, Ci, P, S_D, F = new double[8][8];
        double[][][][] G = new double[8][8][8][8];
        RealMatrix V_re, D_re, Vecp, Vec, Val;
        RealMatrix V_im, D_im;

        ArrayList<Double> Erec = new ArrayList<>();

        double pi = Math.PI, e = Math.E;

        for (int y = 1; y <= 41; y++) {
            r = 0.45 + 0.05 * y; // distance between nuclei in A.U. (bohr radius)
            NNr = 1 / r;

            // alphas for the gaussian basis functions for each H atom
            a1 = new double[]{13.00773, 1.962079, 0.444529, 0.1219492};
            a = new double[8];
            for (int i = 0; i < 4; i++) {
                a[i] = a[i + 4] = a1[i];
            }
            asize = a.length;

            // one H nucleus origin, one at r
            ra = new double[]{0, 0, 0, 0, r, r, r, r};

            // run over all basis functions to calculate the overlap, kinetic energy, and coulomb matrices
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j <= i; j++) {
                    as = a[i] + a[j];
                    ap = a[i] * a[j];
                    rat = ap / as;

                    // location of resulting gaussian
                    double a2 = -rat * (ra[i] - ra[j]) * (ra[i] - ra[j]);
                    rp = (a[i] * ra[i] + a[j] * ra[j]) / as;
                    S[i][j] = pow(pi / as, 1.5) * exp(a2);
                    S[j][i] = S[i][j]; // symmetry
                    T[i][j] = 0.5 * rat * (6 - 4 * rat * (ra[i] - ra[j]) * (ra[i] - ra[j])) * S[i][j];
                    T[j][i] = T[i][j]; // symmetry

                    double v = sqrt(as * (rp - r) * (rp - r));
                    if (rp == 0) {
                        F0 = 1 + sqrt(pi) / 2 * erf(v) / v;
                    } else if (rp == r) {
                        double u = sqrt(as * rp * rp);
                        F0 = sqrt(pi) / 2 * erf(u) / u;
                    } else {
                        double u = sqrt(as * rp * rp);
                        F0 = sqrt(pi) / 2 * (erf(u) / u + erf(v) / v);
                    }

                    A[i][j] = -2 * pi * Z / as * exp(a2) * F0;
                    A[j][i] = A[i][j]; // symmetry
                }
            }

            RealMatrix[] eigS = Linear.eig(S);
            V_re = eigS[0];
            D_re = eigS[1];

            double[][] V_array = new double[8][8];
            for (int i = 0; i < asize; i++) {
                double[] V_col = V_re.getColumn(0);
                Dii = Math.sqrt(D_re.getEntry(i, i));
                for (int j = 0; j < V_col.length; j++) {
                    V_col[j] /= Dii;
                    V_array[i][j] = V_col[j];
                }
            }
            V_re = MatrixUtils.createRealMatrix(V_array);

            // test
//            for (int i = 0; i < 8; i++) {
//                for (int j = 0; j < 8; j++) {
//                    for (int k = 0; k < 8; k++) {
//                        for (int l = 0; l < 8; l++) {
//                            G[i][j][k][l] = 1000;
//                        }
//                    }
//                }
//            }

            // iterate over each gaussian basis function
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j <= i; j++) {
                    as1 = a[i] + a[j];
                    ap1 = a[i] * a[j];
                    rp = (a[i] * ra[i] + a[j] * ra[j]) / as1;
                    for (int k = 0; k <= i - 1; k++) {
                        double v = -ap1 * (ra[i] - ra[j]) * (ra[i] - ra[j]) / as1;
                        for (int l = 0; l <= k; l++) {
                            as2 = a[k] + a[l];
                            ap2 = a[k] * a[l];
                            rq = (a[k] * ra[k] + a[l] * ra[l]) / as2;
                            if ((rp - rq) == 0) {
                                F0 = 1;
                            } else {
                                double sqrt = Math.sqrt(as1 * as2 * (rp - rq) * (rp - rq) / (as1 + as2));
                                F0 = Math.sqrt(Math.PI) / 2 * ErrorFunction.erf(sqrt) / sqrt;
                            }
                            G[i][j][k][l] = 2 * Math.pow(Math.PI, 2.5) / as1 / as2 / Math.sqrt(as1 + as2)
                                    * Math.exp(v - ap2 * (ra[k] - ra[l]) * (ra[k] - ra[l]) / as2) * F0;
                            G[k][l][i][j] = G[i][j][k][l]; // via symmetry
                            G[j][i][k][l] = G[i][j][k][l];
                            G[i][j][l][k] = G[i][j][k][l];
                            G[j][i][l][k] = G[i][j][k][l];
                            G[k][l][j][i] = G[i][j][k][l];
                            G[l][k][i][j] = G[i][j][k][l];
                            G[l][k][j][i] = G[i][j][k][l];
                        }
                        k = i;
                        for (int l = 0; l <= j; l++) {
                            as2 = a[k] + a[l];
                            ap2 = a[k] * a[l];
                            rq = (a[k] * ra[k] + a[l] * ra[l]) / as2;
                            if ((rp - rq) == 0) {
                                F0 = 1;
                            } else {
                                double sqrt = Math.sqrt(as1 * as2 * (rp - rq) * (rp - rq) / (as1 + as2));
                                F0 = Math.sqrt(Math.PI) / 2 * ErrorFunction.erf(sqrt) / sqrt;
                            }
                            G[i][j][k][l] = 2 * Math.pow(Math.PI, 2.5) / as1 / as2 / Math.sqrt(as1 + as2)
                                    * Math.exp(v - ap2 * (ra[k] - ra[l]) * (ra[k] - ra[l]) / as2) * F0;
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

            System.out.println();

            // self consistent loop
            C = new double[1][asize];
            Ci = new double[asize][1];
            for (int i = 0; i < asize; i++) {
                C[0][i] = 1;
                Ci[i][0] = 1;
            }
            RealMatrix C_M = MatrixUtils.createRealMatrix(C);
            RealMatrix C_Mi = MatrixUtils.createRealMatrix(Ci);
            nor = C_M.multiply(MatrixUtils.createRealMatrix(S)).multiply(C_Mi).getEntry(0, 0);
            for (int i = 0; i < asize; i++) {
                C[0][i] /= nor;
            }

            Elast = 1;
            Eval = new double[8];
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
            while ((Math.abs(Eval[0] - Elast)) > 0.000001) {
                count += 1;
                Elast = Eval[0];
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
                RealMatrix V_T = V_re.transpose();
                RealMatrix F_P_ = V_T.multiply(V_re);
                RealMatrix F_P = F_P_.multiply(V_re);
                EigenDecomposition eigenDecomposition = new EigenDecomposition(F_P);
                Vecp = eigenDecomposition.getV();
                Val = eigenDecomposition.getD();
                Vec = V_re.multiply(Vecp);
                RealMatrix EigVal = MatrixUtils.createRealDiagonalMatrix(V_re.getColumn(0));
                double[] vals = V_re.getColumn(0);
                Double[] vals_ = new Double[vals.length];
                for (int i = 0; i < vals.length; i++) {
                    vals_[i] = vals[i];
                }

                ArrayIndexComparator comparator = new ArrayIndexComparator(vals_);
                Integer[] indices = comparator.createIndexArray();
                Arrays.sort(indices, comparator);
                Arrays.sort(vals);

                Erec.add(Eval[0]);
            }
        }

        }
    }


