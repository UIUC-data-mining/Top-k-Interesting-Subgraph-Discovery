package dataGen.DBLP.netclus;

import java.util.Comparator;

/**
 * This class is used for sorting the nodes within a ranking score vector
 * @author gupta58
 * Originally coded by Yizhou Sun in C#
 */
public class RankComp implements Comparator<IDScore>{
    public int compare(IDScore x1, IDScore x2)
    {
        if (x1.rankscore<x2.rankscore)
            return 1;
        else if (x1.rankscore>x2.rankscore)
            return -1;
    	else
    		return 0;
    }
}
