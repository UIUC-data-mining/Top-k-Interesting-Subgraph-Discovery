package dataGen.DBLP.netclus;
/**
 * This class contains the scores for each of the K clusters. It also contains a weight.
 * Basically a MeasureRecord is the representation of the entity instance in the new measure space. (Basically the vector v as mentioned in section 4.3 of paper)
 * @author gupta58
 * Originally coded by Yizhou Sun in C#
 */
public class MeasureRecord {
	public double weight; // belong to the cluster, {0,1} or [0,1]
	public double[] k_vector;
	public int K;

	public MeasureRecord(int K)
	{
	    this.K = K;
	    k_vector = new double[K];
	    for (int k = 0; k < K; k++)
	    {
	        k_vector[k] = 0;
	    }
	}

	public double get(int k)
	{
	        return k_vector[k];
	}
	    public void set(int k, double value)
	    {
	        k_vector[k] = value;
	    }

	public static MeasureRecord sum(MeasureRecord mr1, MeasureRecord mr2)
	{
	    int K = mr1.K;
	    MeasureRecord sum = new MeasureRecord(K);
	    for (int k = 0; k < K; k++)
	    {
	        sum.set(k,  mr1.get(k) + mr2.get(k));
	        sum.weight = mr1.weight + mr2.weight;
	    }
	    return sum;
	}

	public static MeasureRecord divide(MeasureRecord mr, double scalar)
	{
	    int K = mr.K;
	    MeasureRecord result = new MeasureRecord(K);
	    for (int k = 0; k < K; k++)
	    {
    		result.set(k, mr.get(k) / scalar);
	    }
    	result.weight = mr.weight / scalar;
	    return result;
	}

	public static MeasureRecord mul (MeasureRecord mr, double scalar)
	{
	    int K = mr.K;
	    MeasureRecord result = new MeasureRecord(K);
	    for (int k = 0; k < K; k++)
	    {
	        result.set(k, mr.get(k) * scalar);
	    }
	    result.weight = mr.weight * scalar;
	    return result;
	}

	public static double length(MeasureRecord mr)
	{
	    double len = 0;
	    int K = mr.K;
	    for (int k = 0; k < K; k++)
	    {
	        len += mr.get(k)*mr.get(k);
	    }
	    len = Math.sqrt(len);
	    return len;
	}
	public static double cosine_sim(MeasureRecord mr1, MeasureRecord mr2)
	{
		if(mr1==null || mr2==null)
			return 0.0;
	    double s = 0;
	    int K = mr1.K;
	    double len1 = length(mr1);
	    double len2 = length(mr2);

	    for (int k = 0; k < K; k++)
	    {
	        s += mr1.get(k) * mr2.get(k);
	    }
	    s = s /len1/len2;
	    return s;
	}
}
