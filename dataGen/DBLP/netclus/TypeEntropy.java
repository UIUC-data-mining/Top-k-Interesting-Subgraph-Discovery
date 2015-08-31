package dataGen.DBLP.netclus;

import java.util.HashMap;

/**
 * This class relates entity type to a hashmap of entity type instances and their entropies with respect to the current soft clustering of the instance.
 * @author gupta58
 * Originally coded by Yizhou Sun in C#
 */
public class TypeEntropy {
	HashMap<String, HashMap<Integer, Double>> typeEntropyHT;
	public TypeEntropy()
	{
	    typeEntropyHT = new HashMap<String, HashMap<Integer, Double>>();
	}
	public HashMap<Integer, Double> get(String type)
	{
	        if (!typeEntropyHT.containsKey(type))
	            return null;
	        else
	            return typeEntropyHT.get(type);
	}
    public void set(String type , HashMap<Integer, Double> value)
    {
        typeEntropyHT.put(type, value);
    }
}
