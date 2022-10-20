package com.example.mobilecourseproject;
import java.util.ArrayList;
import java.lang.Math;

public class MatrixMath {

    ArrayList<ArrayList<Double>> elements;

    int size;

    public MatrixMath(int num)
    {
        size = num;

        elements = zeroSqrMatrix(num);
    }

    static public  ArrayList<Double> gaussianElimination(ArrayList<ArrayList<Double>> A, ArrayList<Double> b)
    {
        int n = b.size();

        // singular or nearly singular
        if (isSingular(A)) {
            return null;
        }

        for (int p = 0; p < n; p++) {

            // find pivot row and swap
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(A.get(i).get(p)) > Math.abs(A.get(max).get(p))) {
                    max = i;
                }
            }
            ArrayList<Double> temp = A.get(p);
            A.set(p, A.get(max));
            A.set(max, temp);
            double t = b.get(p);
            b.set(p, b.get(max));
            b.set(max, t);


            // pivot within A and b
            for (int i = p + 1; i < n; i++) {
                double alpha = A.get(i).get(p) / A.get(p).get(p);
                b.set(i, b.get(i) -  alpha * b.get(p));
                for (int j = p; j < n; j++) {
                    A.get(i).set(j, A.get(i).get(j) - alpha * A.get(p).get(j));
                }
            }
        }

        // back substitution
        ArrayList<Double> x = new ArrayList<>();
        for(int i = 0; i < n; i++)
        {
            x.add(0.0);
        }

        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A.get(i).get(j) * x.get(j);
            }
            x.set(i, (b.get(i) - sum) / A.get(i).get(i));
        }
        return x;
    }

    static public ArrayList<Double> matrixMethodSolve(ArrayList<ArrayList<Double>> A, ArrayList<Double> b)
    {
        ArrayList<Double> res = new ArrayList<>();

        int row1 = A.size() , col1 = A.get(0).size();
        int row2 = b.size(), col2 = 1;

        ArrayList<ArrayList<Double>> bMatrix = new ArrayList<>();
        for(int i = 0; i < row2; i++)
        {
            bMatrix.add(new ArrayList<>());
            bMatrix.get(i).add(b.get(i));
        }

        res = getColumnById(0, mult(row1,col1, getInverseMatrix(A), row2, col2, bMatrix));


        return res;
    }

    static public ArrayList<ArrayList<Double>> getInverseMatrix(ArrayList<ArrayList<Double>> mat)
    {
        int size = mat.size();
        ArrayList<ArrayList<Double>> res = zeroSqrMatrix(size);

        Double det = getDetOfMatrix(mat);
        ArrayList<ArrayList<Double>> adj = getAdjMatrix(mat);

        if(det == 0) return null;
        else
        {
            for(int i = 0; i < size; i++)
            {
                for(int j = 0; j < size; j++)
                {
                    res.get(i).set(j, adj.get(i).get(j)/det);
                }
            }
        }

        return res;
    }

    static public boolean isSingular(ArrayList<ArrayList<Double>> mat)
    {
        return getDetOfMatrix(mat) == 0.0;
    }

    static public ArrayList<ArrayList<Double>> getAdjMatrix(ArrayList<ArrayList<Double>> mat)
    {
        int size = mat.size();
        ArrayList<ArrayList<Double>> res = zeroSqrMatrix(size);

        for(int i = 0; i < size; ++i)
        {
            for(int j = 0; j < size; ++j)
            {

                res.get(j).set(i, Math.pow(-1, i+j)* getDetOfMatrix(getMinor(mat, i, j)));
            }
        }

        return  res;
    }

    static public ArrayList<ArrayList<Double>> getMinor(ArrayList<ArrayList<Double>> mat,int p, int q)
    {
        int i = 0, j = 0;
        int n = mat.size();
        ArrayList<ArrayList<Double>> res = zeroSqrMatrix(n-1);


        // Looping for each element
        // of the matrix
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                // Copying into temporary matrix
                // only those element which are
                // not in given row and column
                if (row != p && col != q) {
                    res.get(i).set(j++, mat.get(row).get(col));
                    // Row is filled, so increase
                    // row index and reset col index
                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }

        return res;
    }

    static public Double getDetOfMatrix(ArrayList<ArrayList<Double>> mat)
    {
        double D = 0; // Initialize result

        int n = mat.size();
        // Base case : if matrix
        // contains single element
        if (n == 1)
            return mat.get(0).get(0);


        // To store sign multiplier
        int sign = 1;

        // Iterate for each element of first row
        for (int f = 0; f < n; f++) {
            // Getting Minor of mat[0][f]

            D += sign * mat.get(0).get(f) * getDetOfMatrix(getMinor(mat,0, f));

            // terms are to be added
            // with alternate sign
            sign = -sign;
        }

        return D;
    }

    static public ArrayList<ArrayList<Double>> zeroSqrMatrix(int num)
    {

        ArrayList<ArrayList<Double>> res = new ArrayList<>();
        for(int i = 0; i < num; ++i)
        {
            res.add(new ArrayList<Double>());

            for(int j = 0; j < num; j++)
            {
                res.get(i).add(0.0);
            }
        }
        return res;
    }

    static public ArrayList<ArrayList<Double>> mult(int row1, int col1, ArrayList<ArrayList<Double>> frst,
                                                    int row2, int col2, ArrayList<ArrayList<Double>> scnd) {
        if (row2 != col1) {
            return null;
        }

        ArrayList<ArrayList<Double>> res = new ArrayList<>();
        for (int i = 0; i < row1; ++i) {
            res.add(new ArrayList<Double>());

            for (int j = 0; j < col2; j++) {
                res.get(i).add(0.0);
            }
        }

        double value = 0;
        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < col2; j++) {
                value = 0;
                for (int k = 0; k < row2; k++) {
                    value += frst.get(i).get(k) * scnd.get(k).get(j);
                }
                res.get(i).set(j, value);
            }
        }
        return res;
    }

    static public ArrayList<Double> getColumnById(int id, ArrayList<ArrayList<Double>> matrix)
    {
        ArrayList<Double> res = new ArrayList<>();
        int n = matrix.size();
        for(int i = 0; i < n; i++)
        {
            res.add(matrix.get(i).get(id));
        }

        return res;
    }
}
