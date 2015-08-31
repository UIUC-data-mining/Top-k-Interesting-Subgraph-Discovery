package dataGen.DBLP.netclus;

import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Set;
/**
 * The main class for the netclus code.
 * Note that this code does not do any kind of canonicalization of strings, so all 
 * 		cleaning operations like lowercasing, trimming, removing nonalphanumeric characters should be done before data is processed by this code 
 * @author gupta58
 * Originally coded by Yizhou Sun in C#
 */
public class NetClus {
    public static void main(String[] args) throws Throwable
    {
    	for(int i=0;i<Integer.parseInt(args[0]);i++)
    	{
	    	Global.outDir=Global.dataDir+"ClusOutput"+i;
	    	new File(Global.outDir).mkdir();
	    	System.setErr(new PrintStream(new File(Global.outDir, "err.txt")));
	    	new Global();
	        performClustering();
    	}
    }

    /**
     * The main NetClus algorithm
     *  Phase 1: Initialization
        1.1: build initial clusters -- K + one for background cluster
        1.2: for attribute type, initially all have membership to each cluster as well as to background as 1
        1.3: for target type, random partition
        1.4: for target type, each target instance belongs to the background cluster.
        Phase 2: Perform the NetClus clustering and output the clusters.
     * @throws Throwable
     */
    @SuppressWarnings("unchecked")
	public static void performClustering() throws Throwable
    {
        Set<Integer> targetKeys = Global.entitySet.getByType(Global.targetType).getKeys();
        //Phase 1: Initialization
        //1.1: build initial clusters -- K + one for background cluster (global information)
        TypeMemberShip[] typeClusterMembership = new TypeMemberShip[Global.K + 1];

        for (int k = 0; k < Global.K + 1; k++)
        {
            typeClusterMembership[k] = new TypeMemberShip();
        }

        //1.2: for attribute type, initially all have membership to each cluster as well as to background as 1
        for (int i = 0; i < Global.attributeType.length; i++)
        {
            //generate membershipHT for attribute types
            HashMap<Integer, Double> attributeMembershipHT = new HashMap<Integer, Double>();
            Set<Integer> keys = Global.entitySet.getByType(Global.attributeType[i]).getKeys();
            for (int key : keys)
            {
                attributeMembershipHT.put(key, 1.0);
            }
            for (int k = 0; k < Global.K + 1; k++)
            {
                typeClusterMembership[k].set(Global.attributeType[i], attributeMembershipHT);
            }
        }
        
        //1.3: for target type, random partition
        HashMap<Integer, Double>[] targetMembershipHT = new HashMap[Global.K];
        for (int k = 0; k < Global.K; k++)
        {
            targetMembershipHT[k] = new HashMap<Integer, Double>();
            for (int key: targetKeys)
            {
                targetMembershipHT[k].put(key, 0.0);
            }

        }
        System.err.println("Starting initial assignment of clusters");
        for(int key: targetKeys)
        {
        	int d=(int)(Math.random()* Global.K);
            targetMembershipHT[d].put(key, 1.0);
        }
        System.err.println("Finished initial assignment of clusters");
        for (int k = 0; k < Global.K; k++)
        {
            typeClusterMembership[k].set(Global.targetType, targetMembershipHT[k]);
        }
        
        //1.4: for target type, each target instance belongs to the background cluster.
        HashMap<Integer, Double> totalTargetMembershipHT = new HashMap<Integer, Double>();
        for(int key :targetKeys)
        {
            totalTargetMembershipHT.put(key,1.0);
        }
        typeClusterMembership[Global.K].set(Global.targetType, totalTargetMembershipHT);

        //Phase 2: Perform the NetClus clustering and output the clusters.
        Functions.RankClustering(Global.relationSet, Global.targetType, Global.attributeType, typeClusterMembership, Global.K, Global.priorType, Global.priorRank, Global.lambda_S);
    }
}
