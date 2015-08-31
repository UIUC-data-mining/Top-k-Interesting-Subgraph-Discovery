package dataGen.DBLP.netclus;

import java.util.ArrayList;


/**
 * This class contains an ArrayList which stores K ranking score vectors for a particular type
 * @author gupta58
 * Originally coded by Yizhou Sun in C#
 */
public class ClusterRank
{
ArrayList<RankVec> clusterRankAL;
int K;
public ClusterRank(int K)
{
    this.K = K;
    clusterRankAL = new ArrayList<RankVec>(K);
    for (int k = 0; k < K; k++)
    {
        RankVec curRankVec = new RankVec();
        clusterRankAL.add(k, curRankVec);
    }
}

//get conditional rank in each cluster
public RankVec get(int k)
{
        return clusterRankAL.get(k);
}
    public void set(int k, RankVec value)
    {
        clusterRankAL.set(k, value);
    }

public double get(int ID, int k)
{
        return clusterRankAL.get(k).get(ID);
}
    public void set(int ID, int k, double value)
    {
        clusterRankAL.get(k).set(ID, value);
    }
}