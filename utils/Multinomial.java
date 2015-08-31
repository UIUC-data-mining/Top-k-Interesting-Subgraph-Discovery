package utils;

public class Multinomial
{
	public int numDimensions;
	public int aa;
	public Integer[] distribution;
	public int sumTotalOfAllElements;
	public int numObjects;
	public double Z0;
	Multinomial(HH hh)
	{
		numDimensions = hh.numDimensions;
		aa = hh.b/hh.numDimensions;
		distribution = new Integer[hh.numDimensions]; //similar to theta on slide 73/80 of pdf
		for(int i=0;i<distribution.length;i++)
			distribution[i]=0;
		sumTotalOfAllElements = 0;
		numObjects = 0;
		Z0 = 0;
	}
	
	public Multinomial() {
	}

	/**
	 * returns the probability of generating an instance from the multinomial distribution
	 * @param xx
	 * @return
	 */
	public double logDensity(Integer[] xx)
	{
		int thismm=0;
		for(int i=0;i<xx.length;i++)
		  thismm+=xx[i];
		double sum=0;
		for(int i=0;i<xx.length;i++)
			sum+=StatUtils.logGamma(xx[i]+1);
		double density = StatUtils.logGamma(thismm+1) - sum;
		double[] normDist = new double[distribution.length];
		double sum2=0;
		for(int i=0;i<distribution.length;i++)
			sum2+=distribution[i];
		for(int i=0;i<distribution.length;i++)
			normDist[i]=distribution[i]/sum2;
		for(int i=0;i<xx.length;i++)
			density+=xx[i]*Math.log(normDist[i]);
		if(Double.isNaN(density))
			density=-Double.MAX_VALUE; // essentially NaN happens when one of the entries in normDist[i] is 0. And this means that the density should be 0. and so logDensity should be -Infinity.
		return density;
	}
	/**
	 * log predictive probability of inputData given other data items in the component
	 * log p(inputData|x_1,...,x_n)
	 * @param inputData
	 * @return
	 */
	public Double logpredictive(Integer[] xx) {
		int thismm=0;
		for(int i=0;i<xx.length;i++)
		  thismm+=xx[i];
		double sum=0;
		for(int i=0;i<xx.length;i++)
			sum+=StatUtils.logGamma(xx[i]+1);
		double sum1 = 0;
		for(int i=0;i<xx.length;i++)
			sum1+=StatUtils.logGamma(aa+distribution[i]+xx[i]);
		for(int i=0;i<xx.length;i++)
			sum1-=StatUtils.logGamma(aa+distribution[i]);
		Double ll = StatUtils.logGamma(thismm+1) - sum + StatUtils.logGamma(aa*numDimensions+sumTotalOfAllElements) - StatUtils.logGamma(aa*numDimensions+sumTotalOfAllElements+thismm)+sum1;
		return ll;
	}
	/**
	 * deletes data item xx into component mixtureComponents.
	 * @param xx
	 */
	void delitem(Integer[] xx)
	{
		int thismm=0;
		for(int i=0;i<xx.length;i++)
		  thismm+=xx[i];
		numObjects = numObjects - 1;
		for(int i=0;i<xx.length;i++)
			distribution[i] = distribution[i] - xx[i];
		sumTotalOfAllElements = sumTotalOfAllElements - thismm;
		double sum=0;
		for(int i=0;i<xx.length;i++)
			sum+=StatUtils.logGamma(xx[i]+1);
		Z0 = Z0 - StatUtils.logGamma(thismm+1) + sum;
	}
	void delitem(int xx)
	{
		  numObjects--;
		  distribution[xx]--;
		  sumTotalOfAllElements--;
	}
	/**
	 * adds data item inputData into component mixtureComponents.
	 * @param inputData
	 */
	void additem(Integer[] xx)
	{
//		  [ii jj distribution] = find(inputData);
		int thismm=0;
		for(int i=0;i<xx.length;i++)
		  thismm+=xx[i];
		numObjects = numObjects + 1;
		for(int i=0;i<xx.length;i++)
			distribution[i] = distribution[i] + xx[i];
		sumTotalOfAllElements = sumTotalOfAllElements + thismm;
		double sum=0;
		for(int i=0;i<xx.length;i++)
			sum+=StatUtils.logGamma(xx[i]+1);
		Z0 = Z0 + StatUtils.logGamma(thismm+1) - sum;
	}
	void additem(int xx)
	{
		  numObjects++;
		  distribution[xx]++;
		  sumTotalOfAllElements++;
	}
}