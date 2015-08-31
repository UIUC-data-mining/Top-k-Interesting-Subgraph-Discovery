package dataGen.DBLP.netclus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 * This class maintains the current ranking vector as a hashmap from node id to node score
 * @author gupta58
 * Originally coded by Yizhou Sun in C#
 */
public class RankVec {
    HashMap<Integer, Double> rankHT;
    public RankVec()
    {
        rankHT = new HashMap<Integer, Double>();
    }
    /**
     * Initialized the rankng vector to an initial score
     * @param keys -- node IDs
     * @param initialValue -- initial node scores
     */
    public RankVec(Set<Integer> keys, double initialValue)
    {
        rankHT = new HashMap<Integer, Double>();
        for(Integer ID : keys)
            rankHT.put(ID, initialValue);
    }
    
    /**
     * Creates a copy of the ranking vector
     * @param rv
     */
    public void copy(RankVec rv)
    {
        rankHT = new HashMap<Integer, Double>();
        for (Integer ID : rv.rankHT.keySet())
            rankHT.put(ID, rv.get(ID));
    }
    
    /**
     * @param ID
     * @return gets the rank score for the node represented by ID
     */
    public double get(int ID)
    {
            if (rankHT.containsKey(ID))
                return rankHT.get(ID);
            else
                return 0;
    }
    
    /**
     * Sets the rank score of node ID to "value"
     * @param ID
     * @param value
     */
    public void set(int ID, double value)
        {
            rankHT.put(ID,value);
        }
    
    /**
     * Sorts the nodes with respect to their scores in descending order 
     * @return arraylist of score-sorted nodes
     */
    public ArrayList<IDScore> sort()
    {
        ArrayList<IDScore> a = new ArrayList<IDScore>();
        for (int ID: rankHT.keySet()) {
            IDScore curIDRank = new IDScore ();
            curIDRank.ID = ID;
            curIDRank.rankscore = get(ID);
            a.add(curIDRank);
		}
        Collections.sort(a, new RankComp ());
        return a;
    }
    /**
     * Normalizes the ranking score vector
     */
    public void normalization()
    {
        double sum = 0;
        for (int ID: rankHT.keySet())
            sum += rankHT.get(ID);
        if(sum!=0)
        {
            for (int ID: rankHT.keySet())
            	rankHT.put(ID, rankHT.get(ID)/sum);
        }
        else
        {
        	for (int ID: rankHT.keySet())
        		rankHT.put(ID, 0.);
        }
    }

    /**
     * Computes the l1 norm of the difference between 2 input ranking vectors rv1 and rv2 
     * @param rv1
     * @param rv2
     * @return
     */
    public static double difference(RankVec rv1, RankVec rv2)
    {
        double sum = 0;
    	for (int ID : rv1.rankHT.keySet()) {
			sum += Math.abs(rv1.get(ID) - rv2.get(ID));
		}
        return sum;
    }
}
