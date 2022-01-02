package com.bramerlabs.molecular.main;

import com.bramerlabs.molecular.engine3D.graphics.graph_util.GraphDisplay;
import com.bramerlabs.molecular.engine3D.graphics.graph_util.GraphRenderer;
import com.bramerlabs.molecular.engine3D.math.comparators.ArrayIndexComparator;
import com.bramerlabs.molecular.engine3D.math.functions.ErrorFunction;
import com.bramerlabs.molecular.engine3D.math.linear.Linear;
import com.bramerlabs.molecular.engine3D.math.vector.Vector2f;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static com.bramerlabs.molecular.engine3D.math.functions.ErrorFunction.erf;
import static java.lang.Math.*;

public class HHGroundStateHF {

    public static void main(String[] args) {

        int asize, count;
        double Z = 1, Elast, Vi, as, ap, rat, rp, F0, as1, ap1, as2, ap2, rq, r, NNr, nor, J, Qs;
        double[] a, a1, ra, rplot, Eval, groundWaveFunction, Eg;
        double[][] T = new double[8][8], S = new double[8][8], A = new double[8][8], Ci, S_D, F = new double[8][8];
        double[][][][] G = new double[8][8][8][8];
        RealMatrix V_re, D_re, Vecp, Vec, Val, C, P, Fp;
        RealMatrix V_im, D_im;

        ArrayList<Double> Erec = new ArrayList<>();

        double pi = Math.PI, e = Math.E;

        rplot = new double[(int) ((3 - (-2)) / 0.01)]; // range in A.U. for plotting probability density
        for (int i = 0; i < rplot.length; i++) {
            rplot[i] = 0.01 * i - 2;
        }

        Eg = new double[42];

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
                for (int j = 0; j < 8; j++) {
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
                    } else if (rp - r < 0.00001f) { // use a error range because of rounding error
                        double u = sqrt(as * rp * rp);
                        F0 = sqrt(pi) / 2 * erf(u) / u + 1;
                    } else {
                        double u = sqrt(as * rp * rp);
                        F0 = sqrt(pi) / 2 * (erf(u) / u + erf(v) / v);
                    }

                    A[i][j] = -2 * pi * Z / as * exp(a2) * F0;
                }
            }

            RealMatrix[] eigS = Linear.eig(S);
            V_re = eigS[0];
            D_re = eigS[1];

            // reconditioning the V and D matrices
            RealMatrix V_temp = MatrixUtils.createRealMatrix(new double[8][8]);
            RealMatrix D_temp = MatrixUtils.createRealMatrix(new double[8][8]);
            V_re = V_re.scalarMultiply(-1);
            for (int i = 0; i < 8; i++) {
                V_temp.setColumn(7 - i, V_re.getColumn(i));
            }
            for (int i = 0; i < 8; i++) {
                D_temp.setEntry(i, i, D_re.getEntry(7 - i, 7 - i));
            }
            V_re = V_temp.copy();
            D_re = D_temp.copy();

            RealMatrix V_array = MatrixUtils.createRealMatrix(new double[8][8]);
            for (int i = 0; i < asize; i++) {
                double[] V_col = V_re.getColumn(i);
                double Dii = sqrt(D_re.getEntry(i, i));
                for (int j = 0; j < V_col.length; j++) {
                    V_col[j] /= Dii;
                }
                V_array.setColumn(i, V_col);
            }
            V_re = V_array.copy();

            // iterate over each gaussian basis function
            // this is the naive approach, does not exploit symmetry of the system
            // for future optimization, symmetry of the system should be used, i.e. g(i, j, k, l) = g(l, k, j, i) etc.
            for (int i = 0; i < asize; i++) {
                for (int j = 0; j < asize; j++) {
                    as1 = a[i] + a[j];
                    ap1 = a[i] * a[j];
                    rp = (a[i] * ra[i] + a[j] * ra[j]) / as1;

                    for (int k = 0; k < asize; k++) {
                        for (int l = 0; l < asize; l++) {
                            as2 = a[k] + a[l];
                            ap2 = a[k] * a[l];
                            rq = (a[k] * ra[k] + a[l] * ra[l]) / as2;

                            if ((rp - rq) < 0.00001f) { // error bias
                                F0 = 1;
                            } else {
                                double v = as1 * as2 * (rp - rq) * (rp - rq) / (as1 + as2);
                                F0 = sqrt(pi) / 2 * erf(sqrt(v)) / sqrt(v);
                            }

                            G[i][j][k][l] = 2 * pow(pi, 2.5) / as1 / as2 / sqrt(as1 + as2)
                                    * exp(-ap1 * (ra[i] - ra[j]) * (ra[i] - ra[j]) / as1 - ap2
                                    * (ra[k] - ra[l]) * (ra[k] - ra[l]) / as2) * F0;
                        }
                    }
                }
            }

            // self-consistent loop
            double[][] C_val = new double[1][asize];
            for (int i = 0; i < asize; i++) {
                C_val[0][i] = 1;
            }
            C = MatrixUtils.createRealMatrix(C_val);
            nor = C.multiply(MatrixUtils.createRealMatrix(S)).multiply(C.transpose()).getEntry(0, 0);
            C = C.scalarMultiply(1 / nor); // initial guess
            Elast = 1;
            P = C.transpose().multiply(C);
            count = 0;

            // generating Fock matrix
            double Ecur = 100;
            Erec.clear();
            while ((abs(Ecur - Elast)) > 0.000001f) {
                count++;
                Elast = Ecur;
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        J = 0;
                        for (int k = 1; k < 8; k++) {
                            for (int l = 1; l < 8; l++) {
                                J = J + P.getEntry(k, l) * G[i][j][k][l];
                            }
                        }
                        F[i][j] = T[i][j] + A[i][j] + J;
                    }
                }

                RealMatrix V_transpose = (V_re.copy()).transpose();
                RealMatrix F_Matrix = MatrixUtils.createRealMatrix(F);
                Fp = V_transpose.multiply(F_Matrix).multiply(V_re);
                RealMatrix[] eigF = Linear.eig(Fp);
                Vecp = eigF[0];
                Val = eigF[1];
                Vec = V_re.multiply(Vecp);

                double[] EigVal = new double[8];
                Double[] EigValComp = new Double[8];
                for (int i = 0; i < 8; i++) {
                    EigVal[i] = Val.getEntry(i, i);
                    EigValComp[i] = Val.getEntry(i, i);
                }

                ArrayIndexComparator comparator = new ArrayIndexComparator(EigValComp);
                Integer[] indices = comparator.createIndexArray();
                Arrays.sort(indices, comparator);
                Arrays.sort(EigVal);
                Ecur = EigVal[0];
                Erec.add(EigVal[0]);

                double[] GrCoeff = Vec.getColumn(indices[0]);

                // new C matrix, normalized w.r.t. S
                C = MatrixUtils.createRealMatrix(new double[1][8]);
                C.setRow(0, GrCoeff);

                // new input density matrix
                P = P.scalarMultiply(0.8).add(C.transpose().scalarMultiply(0.2).multiply(C));
            }

            // only want to populate the ground level wave function once for the optimized C
            groundWaveFunction = new double[rplot.length];
            for (int x = 0; x < 500; x++) {
                double sum = 0;
                double[] exp1 = new double[4];
                double[] exp2 = new double[4];
                for (int i = 0; i < 4; i++) {
                    sum += C.getEntry(0, i) * exp(-a[i] * rplot[x] * rplot[x]);
                    sum += C.getEntry(0, i + 4) * exp(-a[i + 4] * (rplot[x] - r) * (rplot[x] - r));
                }
                groundWaveFunction[x] = sum;
            }

            Qs = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {
                            Qs += G[i][j][k][l] * C.getEntry(0, i) * C.getEntry(0, j)
                                    * C.getEntry(0, k) * C.getEntry(0, l);
                        }
                    }
                }
            }
            Eg[y] = 2 * C.multiply(MatrixUtils.createRealMatrix(T).add(MatrixUtils.createRealMatrix(A)))
                    .multiply(C.transpose()).getEntry(0, 0) + Qs + NNr;
            System.out.println(Eg[y]);

        }

        double[] Y = new double[Erec.size()];
        for (int i = 0; i < Erec.size(); i++) {
            Y[i] = Erec.get(i);
        }
        Arrays.sort(Y);

        // Fock energy level convergence
        GraphDisplay gd_fl = new GraphDisplay(new Dimension(800, 600));
        GraphRenderer gr_fl = new GraphRenderer(gd_fl);
        gd_fl.addRenderer(gr_fl);
        gr_fl.addAxis(0, Erec.size(), 5, "Step of Convergence", GraphRenderer.X, false);
        double yDiff = Y[Y.length - 1] - Y[0];
        double yDiffTenth = yDiff * 0.1d;
        gr_fl.addAxis((float) (Y[0] - yDiffTenth), (float) (Y[Y.length - 1] + yDiffTenth), 5, "Fock Energy Level (a.u.)", GraphRenderer.Y, true);
        for (int i = 0; i < Erec.size(); i++) {
            gr_fl.addComponent(new Vector2f(i, Erec.get(i).floatValue()));
        }
        gr_fl.addTitle("Fock Energy Level Convergence vs. Step");
        gd_fl.repaint();

        // Total energy
        GraphDisplay gd_te = new GraphDisplay(new Dimension(800, 600));
        GraphRenderer gr_te = new GraphRenderer(gd_te);
        gd_te.addRenderer(gr_te);
        gr_te.addAxis(0.45f, 2.5f, 5, "H-H Bond Length", GraphRenderer.X, false);
        double[] temp = new double[Eg.length];
        System.arraycopy(Eg, 0, temp, 0, temp.length);
        Arrays.sort(temp);
        yDiff = temp[temp.length - 1] - temp[0];
        yDiffTenth = yDiff * 0.1d;
        gr_te.addAxis((float) (temp[0] - yDiffTenth), (float) (temp[temp.length - 1] + yDiffTenth), 5, "Total Energy (in a.u.)", GraphRenderer.Y, true);
        for (int i = 0; i < Eg.length; i++) {
            double x = 0.45 + 0.05 * i; // distance between nuclei in A.U. (bohr radius)
            gr_te.addComponent(new Vector2f((float) x, (float) Eg[i]));
        }
        gr_te.addTitle("Total Energy vs. H-H Bond Length");
        gd_te.repaint();

    }


}

