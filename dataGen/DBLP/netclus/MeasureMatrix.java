package dataGen.DBLP.netclus;

import java.util.HashMap;
/**
 * This class maps each entity instance with its measure record
 * @author gupta58
 * Originally coded by Yizhou Sun in C#
 */
public class MeasureMatrix {
	HashMap<Integer, MeasureRecord> recordHT;
	int K;

	public MeasureMatrix(HashMap<Integer,Double> membershipHT, int K)
	{
	    recordHT = new HashMap<Integer, MeasureRecord>();
	    this.K = K;
	    for(Integer ID : membershipHT.keySet())
	    {
	        MeasureRecord newMR = new MeasureRecord (K);
	        newMR.weight = membershipHT.get(ID);
	        recordHT.put(ID, newMR);
	    }
	}

	public MeasureRecord get(int ID)
	{
	        return recordHT.get(ID);
	}
	public double get(int ID, int k)
	{
	        if (recordHT.containsKey(ID))
	        {
	            return (recordHT.get(ID).get(k));
	        }
	        else
	        {
	            return 0;
	        }
	}
	    public void set(int ID, int k,double value)
	    {
	        if (recordHT.containsKey(ID))
	        {
	            recordHT.get(ID).set(k, value);
	        }
	    }


	/**
	 * Performs a weighted average of the measure records
	 * @return
	 */
	public MeasureRecord getMean()
	{
	    MeasureRecord sum = new MeasureRecord(K);

	    for (int ID :recordHT.keySet())
	    {
	        sum = MeasureRecord.sum(sum, MeasureRecord.mul(get(ID),get(ID).weight));
	    }

	    sum = MeasureRecord.divide(sum , sum.weight);
	    return sum;
	}
}
