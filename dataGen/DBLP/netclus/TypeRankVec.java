package dataGen.DBLP.netclus;

import java.util.HashMap;
import java.util.Set;

/**
 * This class associates the entity type (paper, conf etc) to its ranking score vector
 * @author gupta58
 * Originally coded by Yizhou Sun in C#
 */
public class TypeRankVec {
    HashMap<String, RankVec> typeRankVecHT;

    public TypeRankVec()
    {
        typeRankVecHT = new HashMap<String, RankVec>();
    }

    public boolean containsType(String type)
    {
        return typeRankVecHT.containsKey(type);
    }

    public RankVec get(String type)
    {
            return typeRankVecHT.get(type);
    }
    public void set(String type, RankVec value)
        {
            typeRankVecHT.put(type, value);
        }

    public double get(String type, int ID)
    {
        if (typeRankVecHT.containsKey(type))
        {
            return (get(type)).get(ID);
        }
        else
        {
            return 0;
        }
    }
    public void set(String type, int ID, double value)
    {
        if (typeRankVecHT.containsKey(type))
        {
            RankVec curRankVec = get(type);
            curRankVec.set(ID,value);
            set(type, curRankVec);
        }
        else
        {
            RankVec newRankVec = new RankVec();
            newRankVec.set(ID, value);
            set(type, newRankVec);
        }
    }

public Set<String> getKeys()
{
    return typeRankVecHT.keySet();
}

public void copy(TypeRankVec newTypeRankVec)
{
    for (String type : newTypeRankVec.getKeys())
    {
        set(type, newTypeRankVec.get(type));
    }
}
}
