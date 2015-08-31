package dataGen.DBLP.netclus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
/**
 * This class stores the soft cluster assignments associated with every entity instance
 * @author gupta58
 * Originally coded by Yizhou Sun in C#
 */
public class ClusterIndicator {
	HashMap<Integer, ArrayList<Double>> clusterIndicHT;
	int K;
	public ClusterIndicator(int K)
	{
	    this.K = K;
	    clusterIndicHT = new HashMap<Integer, ArrayList<Double>>();
	}

	public Set<Integer> getKeys()
	{
	    return clusterIndicHT.keySet();
	}

	public void copy(ClusterIndicator CI)
	{
	    for (int ID: CI.getKeys())
	        clusterIndicHT.put(ID, CI.get(ID));
	}

	/**
	 * gets entropy for every node
	 * @param tK
	 * @return
	 */
	public HashMap<Integer, Double> getEntropy(int tK)
	{
	    HashMap<Integer, Double> entropyHT = new HashMap<Integer, Double>();
	    for (int id : clusterIndicHT.keySet())
	    {
	        double entropy = getEntropy(get(id), tK);
	        entropyHT.put(id, entropy);
	    }
	    return entropyHT;
	}

	/**
	 * gets the entropy in a list of doubles
	 * @param al
	 * @param tK
	 * @return
	 */
	public double getEntropy(ArrayList<Double> al, int tK)
	{
	    double sum = 0;
	    double sumE = 0;
	    for (int k = 0; k < tK; k++)
	    {
	        sum += al.get(k);
	    }
	    //be careful that sum is not allowed to be zero
	    //sum can actually become zero if a paper does not have one of authors, conf or terms.
	    if (sum == 0)
	    {
	        sumE = 0-Math.log(tK);
	        return sumE;
	    }
	    for (int k = 0; k < tK; k++)
	    {
	        double p = al.get(k) / sum;
	        if (al.get(k) != 0)
	        {
	            sumE += p * Math.log(p);
	        }
	    }
	    return -sumE;
	}

	/**
	 * A kind of difference between the new and the old clusterings.
	 * It is the absolute sum of the elements of the cluster membership score matrix which is of the dimensions objects versus number of clusters (a kind of matrix L1 norm)
	 * @param oldCI -- old clustering matrix
	 * @param newCI -- new clustering matrix
	 * @return
	 */
	public static double diff(ClusterIndicator oldCI, ClusterIndicator newCI)
	{
	    double difference = 0;
	    double sum_old = 0;
	    //abs(newCI-oldCI)/sum(oldCI)
	    int K = oldCI.K;
	    for(int key :oldCI.getKeys())
	    {
	        ArrayList<Double> oldAL = oldCI.get(key);
	        ArrayList<Double> newAL = newCI.get(key);
	        for (int k = 0; k < K; k++)
	        {
	            sum_old += oldAL.get(k);
	            difference += Math.abs(oldAL.get(k)- newAL.get(k));
	        }
	    }
    	difference = difference / sum_old;
	    return difference;
	}

	public double get(int ID, int k)
	{
	        if (clusterIndicHT.containsKey(ID))
	            return clusterIndicHT.get(ID).get(k);
	        else
	            return 0;
	}
	    public void set(int ID, int k, double value)
	    {
	        if (clusterIndicHT.containsKey(ID))
	        {
	            clusterIndicHT.get(ID).add(k, value);
	        }
	        else
	        {
	            ArrayList<Double> newAL = new ArrayList<Double>(K);
	            newAL.add(k, value);
	            clusterIndicHT.put(ID, newAL);
	        }
	    }

	public ArrayList<Double> get(int ID)
	{
	        if (clusterIndicHT.containsKey(ID))
	            return clusterIndicHT.get(ID);
	        else
	            return null;
	}

	    public void set(int ID, ArrayList<Double> value)
	    {
	            clusterIndicHT.put(ID, value);
	    }

	/**
	 * for cluster K, get the membershipHT(node id -> membership score)
	 * @param k
	 * @return
	 */
	public HashMap<Integer, Double> getMembershipHT(int k)
	{
	    HashMap<Integer, Double> membershipHT = new HashMap<Integer, Double>();
	    	for (int ID: clusterIndicHT.keySet()) {
	    		membershipHT.put(ID, get(ID,k));
	    }
	    return membershipHT;

	}

	/**
	 * @param priorRV -- prior vector (size=number of objects)
	 * @return the cluster probability p(Z) = sum_x p(Z|x)*P(x) where Z is a cluster and x is an object
	 */
	public ArrayList<Double> getClusterProb(RankVec priorRV)
	{
	    ArrayList<Double> clusterProb = new ArrayList<Double>(K);
	    for (int k = 0; k < K; k++)
	    {
	        clusterProb.add(k, 0.0);
	    }
	    int count = clusterIndicHT.size();


	    for (int ID: clusterIndicHT.keySet()) {
	        ArrayList<Double> curAL = get(ID);
	        if (priorRV == null)
	        {
	            for (int k = 0; k < K; k++)
	            {
	                clusterProb.set(k,clusterProb.get(k) + curAL.get(k)/count);
	            }
	        }
	        else
	        {
	            for (int k = 0; k < K; k++)
	            {
	                clusterProb.set(k,clusterProb.get(k) + curAL.get(k) * priorRV.get(ID));
	            }
	        }
	    }

	    return clusterProb;
	}
}
