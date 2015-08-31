package dataGen.DBLP.netclus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;


/**
 * This class maps the type to its clusterRank (i.e. arraylist of K ranking score vectors)
 * @author gupta58
 * Originally coded by Yizhou Sun in C#
 */
public class TypeClusterRank {
	HashMap<String, ClusterRank> typeClusterRankHT;
	
	public boolean containsType(String type)
	{
	    return typeClusterRankHT.containsKey(type);
	}
	
	public TypeClusterRank()
	{
	    typeClusterRankHT = new HashMap<String, ClusterRank>();
	}
	
	/**
	 * Reads the prior files and sets them as initial values for the ClusterRank object members(for the appropriate type)
	 * Note that the prior values for any cluster may not add up to 1 in the file. This code normalizes the prior scores per cluster.
	 * @param priorType
	 * @param priorFile -- every line contains cluster#, entityName, ranking score
	 * @param K
	 * @throws Throwable
	 */
	 public TypeClusterRank(String[] priorType, String[] priorFile, int K) throws Throwable
	{
	    int len = priorFile.length;
	    typeClusterRankHT = new HashMap<String, ClusterRank>();
	
	    for (int i = 0; i < len; i++)
	    {
	        BufferedReader in = new BufferedReader(new FileReader(new File(Global.dataDir , priorFile[i])));
	        String line = null;
	
	        ClusterRank curClusterRank = new ClusterRank(K);
	        while ((line = in.readLine()) != null)
	        {
	            String[] strList = line.split("\\s+");
	            int clusterIndex = Integer.parseInt(strList[0]);
	            int ID = Global.entitySet.getByType(priorType[i]).getIDByName(strList[1]);
//	            if(ID==-1)
//	            	continue;
	            double rank = Double.parseDouble(strList[2]);
	            curClusterRank.set(ID, clusterIndex, rank);
	        }
//	        for(int clus=0;clus<K;clus++)
//	        {
//	        	curClusterRank.get(clus).normalization();
//	        }
	        typeClusterRankHT.put(priorType[i], curClusterRank);
	        in.close();
	    }
	}

	public ClusterRank get(String type)
	{
	    if (typeClusterRankHT.containsKey(type))
	    {
	        return typeClusterRankHT.get(type);
	    }
	    else
	    {
	        return null;
	    }
	}
    public void set(String type, ClusterRank value)
    {
        typeClusterRankHT.put(type,value);
    }
    /**
     * @param k
     * @return TypeRankVec for a particular k. i.e. the ranking score vectors for every type for a particular cluster
     */
	public TypeRankVec get(int k)
	{
	        TypeRankVec typeRankVec = new TypeRankVec();
	        for (String type: typeClusterRankHT.keySet())
	        {
	            typeRankVec.set(type,get(type).get(k));
	        }
	        return typeRankVec;
	}
}
