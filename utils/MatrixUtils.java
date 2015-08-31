package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

public class MatrixUtils {
	/**
	 * Gets the identity matrix
	 * @param size
	 * @return
	 */
	public static Double[][] getIdentityMatrix(int size)
	{
		Double[][] mat = new Double[size][size];
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				if(i==j)
					mat[i][j]=1.;
				else
					mat[i][j]=0.;
			}
		}
		return mat;
	}
	/**
	 * Compute determinant of a matrix
	 * @param mat
	 * @return
	 */
	public static Double determinant(Double[][] mat) { 
		Double result = 0.; 

		if(mat.length == 1) { 
		result = mat[0][0]; 
		return result; 
		} 

		if(mat.length == 2) { 
		result = mat[0][0] * mat[1][1] - mat[0][1] * mat[1][0]; 
		return result; 
		} 

		for(int i = 0; i < mat[0].length; i++) { 
		Double temp[][] = new Double[mat.length - 1][mat[0].length - 1]; 

		for(int j = 1; j < mat.length; j++) { 
		System.arraycopy(mat[j], 0, temp[j-1], 0, i); 
		System.arraycopy(mat[j], i+1, temp[j-1], i, mat[0].length-i-1); 
		} 

		result += mat[0][i] * Math.pow(-1, i) * determinant(temp); 
		} 
		return result; 
	}
	/**
	 * Writes a matrix to a tab separated file 
	 * @param mat
	 * @param file
	 * @throws Throwable
	 */
	public static void writeMatrixToFile(Object[][] mat, String file) throws Throwable
	{
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(file)));
		int rows=mat.length;
		int cols=mat[0].length;
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
				out.write(mat[i][j]+"\t");
			out.write("\n");
		}
		out.close();
	}
	
	public static void writeMatrixToFile(ArrayList<Double[]> mat, String file) throws Throwable
	{
		Double[][] matrix = new Double[mat.size()][mat.get(0).length];
		for(int i=0;i<mat.size();i++)
			matrix[i]=mat.get(i);
		writeMatrixToFile(matrix, file);
	}
	
	/**
	 * Writes a matrix to an ARFF file 
	 * @param mat
	 * @param file
	 * @throws Throwable
	 */
	public static void writeMatrixToARFFFile(Double[][] mat, String file) throws Throwable
	{
		BufferedWriter out = null;
		try
		{
			out=new BufferedWriter(new FileWriter(new File(file)));
		}
		catch(Exception e)
		{
			Thread.sleep(1000);
			out=new BufferedWriter(new FileWriter(new File(file)));
		}
		int rows=mat.length;
		int cols=mat[0].length;
		out.write("@relation 'rel'\n");
		for(int i=0;i<cols;i++)
			out.write("@attribute a"+i+" real\n");
		out.write("\n\n@data\n");
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
				out.write(mat[i][j]+",");
			out.write("\n");
		}
		out.close();
	}

	/**
	 * Initializes all elements of the matrix mat to the value init.
	 * @param mat
	 * @param init
	 */
	public static void initMatToValue(Double[][] mat, double init) {
		int rows=mat.length;
		int cols=mat[0].length;
		for(int i=0;i<rows;i++)
			for(int j=0;j<cols;j++)
				mat[i][j]=init;
	}
	/**
	 * Initializes all elements of the matrix mat to random value between 0 and 1.
	 * @param mat
	 * @param init
	 */
	public static void initMatToRandom(Double[][] mat) {
		int rows=mat.length;
		int cols=mat[0].length;
		for(int i=0;i<rows;i++)
			for(int j=0;j<cols;j++)
				mat[i][j]=Math.random();
	}
	/**
	 * A right stochastic matrix is a square matrix each of whose rows consists of nonnegative real numbers, with each row summing to 1.
	 * @param mat
	 */
	public static void convertMatrixToRightStochastic(Double[][] mat)
	{
		int rows=mat.length;
		int cols=mat[0].length;
		for(int i=0;i<rows;i++)
		{
			double sum=0;
			for(int j=0;j<cols;j++)
				sum+=mat[i][j];
			for(int j=0;j<cols;j++)
			{
				if(sum!=0)
					mat[i][j]/=sum;
				else
					mat[i][j]=1./cols;
			}
		}
	}
	/**
	 * Prints out the matrix
	 * @param mat
	 */
	public static void printMatrix(Double[][] mat)
	{
		int rows=mat.length;
		int cols=mat[0].length;
		System.out.println("========================");
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				System.out.print(mat[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("========================");
	}
	/**
	 * Reads an ARFF file into a matrix
	 * @param file
	 * @return
	 * @throws Throwable
	 */
	public static Double[][] readMatrixFromARFFFile(String file) throws Throwable
	{
		ArrayList<String> list = new ArrayList<String>();
		BufferedReader in = new BufferedReader(new FileReader(new File(file)));
		String str="";
		int start=-1;
		while((str=in.readLine())!=null)
		{
			if(start==0)
				list.add(str.replaceAll(",", "\t"));
			else if (start==-1&&(str.contains("@data")||str.contains("@DATA")))
				start=0;
		}
		in.close();
		int rows=list.size();
		int cols=list.get(0).split("\\t").length;
		Double [][] mat = new Double[rows][cols];
		for(int i=0;i<rows;i++)
		{
			String tokens[]=list.get(i).split("\\t");
			for(int j=0;j<cols;j++)
				mat[i][j]=Double.parseDouble(tokens[j]);
		}
		return mat;
	}

	
	/**
	 * Reads a tsv file into a matrix
	 * @param file
	 * @return
	 * @throws Throwable
	 */
	public static Object[][] readMatrixFromFile(String file, String type) throws Throwable
	{
		ArrayList<String> list = new ArrayList<String>();
		BufferedReader in = new BufferedReader(new FileReader(new File(file)));
		String str="";
		while((str=in.readLine())!=null)
			list.add(str);
		in.close();
		int rows=list.size();
		int cols=list.get(0).split("\\t").length;
		if(type.equals("Double"))
		{
			Double [][] mat = new Double[rows][cols];
			for(int i=0;i<rows;i++)
			{
				String tokens[]=list.get(i).split("\\t");
				for(int j=0;j<cols;j++)
					mat[i][j]=Double.parseDouble(tokens[j]);
			}
			return mat;
		}
		else if(type.equals("Integer"))
		{
			Integer [][] mat = new Integer[rows][cols];
			for(int i=0;i<rows;i++)
			{
				String tokens[]=list.get(i).split("\\t");
				for(int j=0;j<cols;j++)
					mat[i][j]=Integer.parseInt(tokens[j]);
			}
			return mat;
		}
		return null;
	}
	public static Double[][] readMatrixFromFile(String file) throws Throwable
	{
		return (Double[][]) readMatrixFromFile(file, "Double");
	}
	
	public static Double[][] getRandomMatrix(int rows, int cols)
	{
		Double [][] tmp = new Double[rows][cols];
		initMatToRandom(tmp);
		return tmp;
	}
	public static double getFrobeniusNorm(Double[][] a) {
		int rows = a.length;
		int cols = a[0].length;
		double ans = 0;
		for(int i=0;i<rows;i++)
			for(int j=0;j<cols;j++)
				ans+=a[i][j]*a[i][j];
		return ans;
	}
	
	public static Double[][] diffMatrix(Double[][] a, Double[][] b) {
		int rows = a.length;
		int cols = a[0].length;
		Double[][] ans = new Double[rows][cols];
		for(int i=0;i<rows;i++)
			for(int j=0;j<cols;j++)
				ans[i][j]=a[i][j]-b[i][j];
		return ans;
	}
	public static Double[][] addMatrices(Double[][] a, Double[][] b) {
		int rows=a.length;
		int cols=a[0].length;
		Double c[][]=new Double[rows][cols];
		for(int i=0;i<rows;i++)
			for(int j=0;j<cols;j++)
				c[i][j]=a[i][j]+b[i][j];
		return c;
	}
	
	public static Double[] multiplyVecByMatrix(Double[] v, Double[][] a) {
		int rows=a.length;
		int cols=a[0].length;
		Double ret[] = new Double[cols];
		for(int i=0;i<cols;i++)
			ret[i]=0.;
		for(int i=0;i<cols;i++)
			for(int j=0;j<rows;j++)
				ret[i]+=v[j]*a[j][i];
		return ret;
	}

	public static Double[] multiplyMatrixByVec(Double[][] a, Double[] v) {
		int rows=a.length;
		int cols=a[0].length;
		Double ret[] = new Double[rows];
		for(int i=0;i<rows;i++)
			ret[i]=0.;
		for(int i=0;i<rows;i++)
			for(int j=0;j<cols;j++)
				ret[i]+=v[j]*a[i][j];
		return ret;
	}
	
	/**
	 * Multiply all elements of a matrix by a constant value
	 * @param a
	 * @param val
	 * @return
	 */
	public static Double[][] multiplyMatrixByValue(Double[][] a, double val) {
		int rows=a.length;
		int cols=a[0].length;
		Double c[][]=new Double[rows][cols];
		for(int i=0;i<rows;i++)
			for(int j=0;j<cols;j++)
				c[i][j]=a[i][j]*val;
		return c;
	}
	/**
	 * Multiply two matrices
	 * @param a
	 * @param b
	 * @return
	 */
	public static Double[][] mulMatrices(Double[][] a, Double[][] b) {
		int rows=a.length;
		int cols=b[0].length;
		Double c[][]=new Double[rows][cols];
		initMatToValue(c, 0);
		for(int i=0;i<rows;i++)
			for(int j=0;j<cols;j++)
				for(int k=0;k<a[0].length;k++)
					c[i][j]+=a[i][k]*b[k][j];
		return c;
	}
    /**
     * Computes the covariance matrix given the input matrix.
     * @param input
     * @param meanValues
     * @return
     */
    public static Double[][] getCovariance(Double[][] input) {
        int numDataVectors = input.length;
        int n = input[0].length;

        double[] sum = new double[n];
        double[] mean = new double[n];
        for (int i = 0; i < numDataVectors; i++) {
          Double[] vec = input[i];
          for (int j = 0; j < n; j++) {
            sum[j] = sum[j] + vec[j];
          }
        }
        for (int i = 0; i < sum.length; i++) {
          mean[i] = sum[i] / numDataVectors;
        }

        Double[][] ret = new Double[n][n];
        for (int i = 0; i < n; i++) {
          for (int j = i; j < n; j++) {
            double v = getCovariance(input, i, j, mean);
            ret[i][j] = v;
            ret[j][i] = v;
          }
        }
        return ret;
      }

    /**
     * Gives covariance between vectors in an n-dimensional space.
     */
    private static double getCovariance(Double[][] matrix, int colA, int colB, double[] mean) {
      double sum = 0;
      for (int i = 0; i < matrix.length; i++) {
        double v1 = matrix[i][colA] - mean[colA];
        double v2 = matrix[i][colB] - mean[colB];
        sum = sum + (v1 * v2);
      }
      int n = matrix.length;
      double ret = (sum / (n - 1));
      return ret;
    }

    public static Double[][] getCovariance(ArrayList<Double[]> input)
    {
    	Double[][] data = new Double[input.size()][input.get(0).length];
		for(int i=0;i<input.size();i++)
			data[i]=input.get(i);
    	return getCovariance(data);
    }
    /**
     * Gets the largest eigen value and the corresponding eigen vector.
     * @param mat
     * @return
     */
    public static ArrayList<Object> getMaxEigenValueAndVector(Double[][] mat)
    {
    	Double[] maxEigenVector = new Double[mat[0].length];
    	Double[][] cov = getCovariance(mat);
    	double[][] covD = new double[cov.length][cov[0].length];
    	for(int i=0;i<cov.length;i++)
    		for(int j=0;j<cov[0].length;j++)
    			covD[i][j]=cov[i][j];
    	Matrix covMatrix= new Matrix(covD);
    	EigenvalueDecomposition eigenstuff = covMatrix.eig();
    	double[] eigenvalues = eigenstuff.getRealEigenvalues();
    	Matrix eigenvectors = eigenstuff.getV();
        double[][] vecs = eigenvectors.getArray();
        ArrayList<Object> list = new ArrayList<Object>();
        int lastColumn=eigenvalues.length-1;
        list.add(eigenvalues[lastColumn]);
        for(int i=0;i<vecs.length;i++)
        	maxEigenVector[i]=vecs[i][lastColumn];
        list.add(maxEigenVector);
        return list;
    }
    
	public static Object[][] transposeMatrix(Object[][] a) {
		if(a[0][0] instanceof Integer)
		{
			Integer mat[][]= new Integer[a[0].length][a.length];
			for(int i=0;i<a[0].length;i++)
				for(int j=0;j<a.length;j++)
					mat[i][j]=(Integer)a[j][i];
			return mat;
		}
		if(a[0][0] instanceof Double)
		{
			Double mat[][]= new Double[a[0].length][a.length];
			for(int i=0;i<a[0].length;i++)
				for(int j=0;j<a.length;j++)
					mat[i][j]=(Double)a[j][i];
			return mat;
		}
		return null;
	}
	/**
	 * Copy the matrix into a new one.
	 * @param currLabels
	 * @return
	 */
	public static Double[][] copyMatrix(Double[][] currLabels)
	{
		int rows=currLabels.length;
		int cols = currLabels[0].length;
		Double copy[][] = new Double[rows][cols];
		for(int i=0;i<rows;i++)
			for(int j=0;j<cols;j++)
				copy[i][j]=currLabels[i][j];
		return copy;
	}
}
