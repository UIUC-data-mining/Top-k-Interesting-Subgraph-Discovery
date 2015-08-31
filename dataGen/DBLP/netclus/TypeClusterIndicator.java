package dataGen.DBLP.netclus;

import java.util.HashMap;
/**
 * This class stores relation between type and the soft cluster assignments for the entity instances of that type
 * @author gupta58
 * Originally coded by Yizhou Sun in C#
 */
public class TypeClusterIndicator {
	HashMap<String, ClusterIndicator> typeClusterIndicHT;
	public TypeClusterIndicator()
	{
	    typeClusterIndicHT = new HashMap<String, ClusterIndicator>();
	}
	
	public TypeMemberShip getTypeMembership(int k)
	{
	    TypeMemberShip typeMembership = new TypeMemberShip();
	    for (String type: typeClusterIndicHT.keySet())
	        typeMembership.set(type, get(type).getMembershipHT(k));
	    return typeMembership;
	}
	
	public ClusterIndicator get(String type)
	{
        if (typeClusterIndicHT.containsKey(type))
            return typeClusterIndicHT.get(type);
        else
            return null;
	}
    public void set(String type, ClusterIndicator value)
    {
        typeClusterIndicHT.put(type, value);
    }

	public TypeEntropy getEntropy(int tK)
	{
	    TypeEntropy typeEntropy = new TypeEntropy();
	    for (String type :typeClusterIndicHT.keySet())
	    {
	        HashMap<Integer, Double> curEntropyHT = get(type).getEntropy(tK);
	        typeEntropy.set(type, curEntropyHT);
	    }
	    return typeEntropy;
	}
}
