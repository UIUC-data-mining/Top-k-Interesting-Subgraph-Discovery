package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import Jama.Matrix;

public class StatUtils {
	 public static double klDivergence(double[] p1, double[] p2) {
	      double klDiv = 0.0;
	      for (int i = 0; i < p1.length; ++i) {
	        if (p1[i] == 0.) { continue; }
	        if (p2[i] == 0.0) { continue; }
	        klDiv += p1[i] * Math.log( p1[i] / p2[i] );
	      }
	      return klDiv / Math.log(2);
	 }
	 public static double klDivergence(Double[] p1, Double[] p2) {
	      double klDiv = 0.0;
	      for (int i = 0; i < p1.length; ++i) {
	        if (p1[i] == 0.) { continue; }
	        if (p2[i] == 0.0) { continue; }
	        klDiv += p1[i] * Math.log( p1[i] / p2[i] );
	      }
	      if(Double.isNaN(klDiv))
	      {
	    	  for(double d:p1)
	    		  System.out.print(d+" ");
	    	  System.out.println();
	    	  for(double d:p2)
	    		  System.out.print(d+" ");
	    	  System.out.println();
	    	  System.out.println("NaN computed");
	      }
	      return klDiv / Math.log(2);
	 }

		public static double[] normalizeVector(double[] arr)
		{
			double tmp[]=getMeanAndStdDevOfVector(arr);
			for(int i=0;i<arr.length;i++)
				if(tmp[1]!=0)
					arr[i]=(arr[i]-tmp[0])/(tmp[1]);
			return arr;
		}
	public static Double[] split(int parts)
	{
		Double ret[] = new Double[parts];
		for(int i=0;i<parts;i++)
			ret[i]=Math.random();
		return normalize(ret);
	}
	/**
	 * Compute correlation coefficient for two vectors
	 * @param x
	 * @param y
	 * @return
	 */
	public static double correlationCoefficient(Double x[], Double y[])
	{
		double corr=0;
		double sumX=0;
		double sumY=0;
		double sumXY=0;
		double sumX2=0;
		double sumY2=0;
		int N=x.length;
		for(int i=0;i<N;i++)
		{
			sumX+=x[i];
			sumY+=y[i];
			sumX2+=x[i]*x[i];
			sumY2+=y[i]*y[i];
			sumXY+=x[i]*y[i];
		}
		corr=(N*sumXY-sumX*sumY)/(Math.sqrt((N*sumX2-sumX*sumX)*(N*sumY2-sumY*sumY)));
		return corr;
	}

	/**
	 * compute the density using the d-dimensional Gaussian.
	 * http://en.wikipedia.org/wiki/Multivariate_normal_distribution
	 * @param clusterCentroids
	 * @param covarianceMatrix
	 * @return
	 */
	public static double gaussianDensity(Double[] point, Double[] clusterCentroid, Double[][] covarianceMatrix)
	{
		double ret=0;
		int k=clusterCentroid.length;
		double exp=0;
		double[][] cov = new double[k][k];
		double[][] covInverse = new double[k][k];
		for(int i=0;i<k;i++)
			for(int j=0;j<k;j++)
				cov[i][j]=covarianceMatrix[i][j];
		//to deal with the problem of negative det of covariance matrix, we add values on diagonal (http://www2.gsu.edu/~mkteer/npdmatri.html)
//		for(int i=0;i<k;i++)
//			cov[i][i]+=1;
		Matrix m = new Matrix(cov);
		double det=m.det();
		covInverse = m.inverse().getArray();
		for(int i=0;i<k;i++)
			for(int j=0;j<k;j++)
				covarianceMatrix[i][j]=covInverse[i][j];
		Double v[]= new Double[k];
		for(int i=0;i<k;i++)
			v[i]=point[i]-clusterCentroid[i];
		v=MatrixUtils.multiplyVecByMatrix(v, covarianceMatrix);
		for(int i=0;i<k;i++)
			exp+=(-0.5)*(v[i])*(point[i]-clusterCentroid[i]);
		ret=(1/(Math.pow(2*Math.PI, k/2)*Math.sqrt(det)))*Math.pow(Math.E, exp);
		return ret;
	}
	public static double logGamma(double x) {
	      double tmp = (x - 0.5) * Math.log(x + 4.5) - (x + 4.5);
	      double ser = 1.0 + 76.18009173    / (x + 0)   - 86.50532033    / (x + 1)
	                       + 24.01409822    / (x + 2)   -  1.231739516   / (x + 3)
	                       +  0.00120858003 / (x + 4)   -  0.00000536382 / (x + 5);
	      return tmp + Math.log(ser * Math.sqrt(2 * Math.PI));
	   }
	
	/**
	 * Given the alpha distribution and a new point, this returns the prob with which the point can be sampled from the dirichlet represented
	 * by the alphas. The point is actually the theta vector in conventional notation.
	 * @return
	 */
	public static double dirichletDensity(double[] alpha, double[] theta)
	{
		double sum=0;
		for(double d:alpha)
			sum+=d;
		double product=1;
		for(double d:alpha)
			product*=gamma(d);
		double prod=1;
		for(int i=0;i<alpha.length;i++)
			prod*=Math.pow(theta[i], alpha[i]-1);
		return sum*prod/product;
	}
	
/**
 *  *  Reads in a command line input x and prints Gamma(x) and
 *  log Gamma(x). The Gamma function is defined by
 *  
 *        Gamma(x) = integral( t^(x-1) e^(-t), t = 0 .. infinity)
 *
 *  Uses Lanczos approximation formula.
 * @param x
 * @return
 */
	public static double gamma(double x) { return Math.exp(logGamma(x)); }


	/**
	 * Normalize an array
	 * @param vector
	 * @return
	 */
	public static Double[] normalize(Double[] vector)
	{
		double sum=0;
		for(int i=0;i<vector.length;i++)
			sum+=vector[i];
		for(int i=0;i<vector.length;i++)
			vector[i]/=sum;
		return vector;
	}
	/**
	 * http://en.wikipedia.org/wiki/Gaussian_function#Two-dimensional_Gaussian_function
	 * Returns the probability that the point (x,y) was generated using the 2D Gaussian specified by (amp, xMean, yMean, xStdDev, yStdDev)
	 * @param amplitude
	 * @param x
	 * @param y
	 * @param xMean
	 * @param yMean
	 * @param xStdDev
	 * @param yStdDev
	 * @return
	 */
	public static double twoDGaussian(double amplitude, double x, double y, double xMean, double yMean, double xStdDev, double yStdDev)
	{
		return amplitude*Math.pow(Math.E, -(((x-xMean)*(x-xMean))/(2*xStdDev*xStdDev)+((y-yMean)*(y-yMean))/(2*yStdDev*yStdDev)));
	}
	/**
	 * Returns the probability that the point (x) was generated using the 1D Gaussian specified by (mean, stdDev)
	 * @param amplitude
	 * @param x
	 * @param y
	 * @param xMean
	 * @param yMean
	 * @param xStdDev
	 * @param yStdDev
	 * @return
	 */
	public static double gaussian(double x, double mean, double stdDev)
	{
		return (1/Math.sqrt(2*Math.PI*stdDev*stdDev))*Math.pow(Math.E, -(((x-mean)*(x-mean))/(2*stdDev*stdDev)));
	}
	/**
	 * 
	 * @param midpoint
	 * @param mean
	 * @param stdDev
	 * @param length
	 * @return
	 */
	public static Double[] gaussianArr(double width, double midpoint, double mean, double stdDev, int length)
	{
		Double[] arr = new Double[length];
		//we fix the total length to be 2 std deviations.
		double tot=width*stdDev;
		double unit= tot/length;
		double start=mean-(tot*(midpoint)/length);
		for(int i=0;i<length;i++)
		{
			arr[i]=gaussian(start, mean, stdDev);
			if(i>0&&arr[i]==arr[i-1])
				i--;
			start+=unit;
		}
		return arr;
	}
	/**
	 * Returns a random real number between a and b [a,b)
	 * @param a (low range)
	 * @param b (high range)
	 * @return
	 */
	public static double uniform(double a, double b) {
	    return a + Math.random() * (b-a);
	}

	/**
	 * Return a real number with a standard Gaussian distribution with unit mean and stdDev
	 */
	public static double gaussian() {
	    // use the polar form of the Box-Muller transform
	    double r, x, y;
	    do {
	        x = uniform(-1.0, 1.0);
	        y = uniform(-1.0, 1.0);
	        r = x*x + y*y;
	    } while (r >= 1 || r == 0);
	    return x * Math.sqrt(-2 * Math.log(r) / r);
	    // Remark:  y * Math.sqrt(-2 * Math.log(r) / r)
	    // is an independent random gaussian
	}
	/**
	 * Return a real number sampled from a 1D Gaussian distribution with given mean and stddev
	 */
	public static double gaussian(double mean, double stddev) {
	    return mean + stddev * gaussian();
	}
	/**
	 * Gets the min and max value from the array and their corresponding indices. 
	 * @param arr
	 * @return [min, max, mini, maxi]
	 */
	public static Double[] getMinMaxValueFromArray(Double[] arr)
	{
		double min = Double.MAX_VALUE;
		double max = -Double.MAX_VALUE;
		double mini=-1;
		double maxi=-1;
		for(int i=0;i<arr.length;i++)
		{
			if(arr[i]<min)
			{
				min=arr[i];
				mini=i;
			}
			if(arr[i]>max)
			{
				max=arr[i];
				maxi=i;
			}
		}
		Double ans[]= new Double[4];
		ans[0]=min;
		ans[1]=max;
		ans[2]=mini;
		ans[3]=maxi;
		return ans;
	}
	/**
	 * gets Mean and standard deviation of a double vector
	 * @param arr
	 * @return [mean, StdDev]
	 */
	public static double[] getMeanAndStdDevOfVector(double[] arr)
	{
		double ans[]= new double[2];
		for(int i=0;i<arr.length;i++)
			ans[0]+=arr[i];
		ans[0]/=arr.length;
		for(int i=0;i<arr.length;i++)
			ans[1]+=(arr[i]-ans[0])*(arr[i]-ans[0]);
		ans[1]=Math.sqrt(ans[1]/arr.length);
		return ans;
	}
	public static Double[] getRandomVector(int cols)
	{
		Double [][] tmp = new Double[1][cols];
		MatrixUtils.initMatToRandom(tmp);
		return tmp[0];
	}

	public static double cosineSim(Double[] a, Double[] b) {
		double sum=0;
		for(int i=0;i<a.length;i++)
			sum+=a[i]*b[i];
		return sum;
	}
	
	public static double getVectorNorm(Double[] a) {
		double ans = 0;
		for(int i=0;i<a.length;i++)
			ans+=a[i]*a[i];
		return Math.sqrt(ans);
	}
	public static double EuclideanDistance(Double[] a, Double[] b)
	{
		double sum=0;
		for(int i=0;i<a.length;i++)
			sum+=(a[i]-b[i])*(a[i]-b[i]);
		return Math.sqrt(sum);
	}
	
    /**
     * Test whether or not a given data array satisfies a normal distribution
     * using the Anderson Darling Text2Binary at the given significance niveau.
     * @param data sample data, will be sorted afterwards
     * @param p significance niveau (0.1, 0.05, 0.025, 0.001, 0.0001)
     * @return true if data seems to be normal distributed
     */
    public static boolean andersonDarlingNormal(double[] data, double p) {
        // available critical values, feel free to extend
        HashMap<Double, Double> adt_map = new HashMap<Double, Double>();
        adt_map.put(0.1000, 0.656);
        adt_map.put(0.0500, 0.787);
        adt_map.put(0.0250, 0.918);
        adt_map.put(0.0010, 1.092);
        adt_map.put(0.0001, 1.8692);
        double val=andersonDarlingNormalCriticalValue(data);
        System.out.println(val);
        return val < adt_map.get(p);
    }
    
    /**
     * Compute the Anderson Darling critical value for the given 1D data and 
     * significance niveau. The larger the value, the more likely the data is
     * result of a normal distribution
     * @param data
     * @return
     */
    public static double andersonDarlingNormalCriticalValue(double[] data) {
        // normalize to N(0,1)
        double m = 0;
        double s = 0;
        int n = data.length;
        for (int i = 0; i < n; ++i) {
            m += data[i];
            s += data[i] * data[i];
        }
        m /= n;
        s = Math.sqrt(s / n - m * m);

        double[] data_mod = new double[data.length];
        for (int i = 0; i < n; ++i)
            data_mod[i] = (data[i] - m) / s;

        // sort
        Arrays.sort(data_mod);

        // compute F values
        for (int i = 0; i < n; ++i)
            data_mod[i] = cdfGaussianN01(data_mod[i]);

        // evaluate
        return andersonDarlingSatistic(data_mod, true);
    }
    /**
     * Compute the cumulative density function (CDF) of a N(0,1) distribution.
     * Based on Hart, J.F. et al, 'Computer Approximations', Wiley 1968 (FORTRAN 
     * transcript).
     * @param z
     * @return
     */
    public static double cdfGaussianN01(double z) {
        double zabs;
        double p;
        double expntl, pdf;

        final double p0 = 220.2068679123761;
        final double p1 = 221.2135961699311;
        final double p2 = 112.0792914978709;
        final double p3 = 33.91286607838300;
        final double p4 = 6.373962203531650;
        final double p5 = .7003830644436881;
        final double p6 = .3526249659989109E-01;

        final double q0 = 440.4137358247522;
        final double q1 = 793.8265125199484;
        final double q2 = 637.3336333788311;
        final double q3 = 296.5642487796737;
        final double q4 = 86.78073220294608;
        final double q5 = 16.06417757920695;
        final double q6 = 1.755667163182642;
        final double q7 = .8838834764831844E-1;

        final double cutoff = 7.071;
        final double root2pi = 2.506628274631001;

        zabs = Math.abs(z);
        if (z > 37.0)
            return 1.;
        if (z < -37.0)
            return 0.;

        expntl = StrictMath.exp(-.5 * zabs * zabs);
        pdf = expntl / root2pi;

        if (zabs < cutoff) {
            p = expntl
                    * ((((((p6 * zabs + p5) * zabs + p4) * zabs + p3)
                            * zabs + p2)
                            * zabs + p1)
                            * zabs + p0)
                    / (((((((q7 * zabs + q6) * zabs + q5) * zabs + q4)
                            * zabs + q3)
                            * zabs + q2)
                            * zabs + q1)
                            * zabs + q0);
        } else {
            p = pdf
                    / (zabs + 1.0 / (zabs + 2.0 / (zabs + 3.0 / (zabs + 4.0 / (zabs + 0.65)))));
        }

        if (z < 0.)
            return p;
        else
            return 1. - p;
    }
    /**
     * Compute the Anderson Darling statistic
     * 
     * @param sortedData Sample data normalized to (0,1) and sorted
     * @param normalize Perform Stephen's normalization
     * @return
     */
    private static double andersonDarlingSatistic(double[] F, boolean normalize) {
        int n = F.length;

        // accumulate
        double sum = 0.;
        for (int i = 0; i < n; ++i) {
            double z1 = StrictMath.log(F[i]);
            double z2 = StrictMath.log1p(-F[n - i - 1]); // supposed to be more accurate
            sum += (2. * i + 1) * (z1 + z2);
        }

        double a2 = -sum / (double) n - (double) n;

        if (normalize)
            a2 *= (1. + 4. / (double) n - 25. / ((double) (n * n)));

        return a2;
    }

	public static double correlationCoefficient(ArrayList<Double> x, ArrayList<Double> y) {
		Double a[]= new Double[x.size()];
		Double b[]= new Double[y.size()];
		for(int i=0;i<x.size();i++)
			a[i]=x.get(i);
		for(int i=0;i<y.size();i++)
			b[i]=y.get(i);
		return correlationCoefficient(a, b);
	}
}
