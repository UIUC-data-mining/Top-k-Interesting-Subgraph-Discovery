package dataGen.DBLP.netclus;

import java.util.HashMap;
import java.util.Set;

/**
 * This class stores the weight of each object for a network, different from clusterIndicator, which is conditional probability
 * @author gupta58
 * Originally coded by Yizhou Sun in C#
 */
public class TypeMemberShip {
		HashMap<String, HashMap<Integer, Double>> typeMembershipHT;

		public TypeMemberShip()
		{
		    typeMembershipHT = new HashMap<String, HashMap<Integer, Double>>();
		}

		public Set<String> getKeys()
		{
		    return typeMembershipHT.keySet();
		}

		public void copy(TypeMemberShip tm)
		{
		    Set<String> keys = tm.getKeys();
		    for (String type: keys)
		    {
		        set(type, tm.get(type));
		    }
		}
		public HashMap<Integer, Double> get(String type)
		{
		        if (typeMembershipHT.containsKey(type))
		        {
		            return typeMembershipHT.get(type);
		        }
		        else
		        {
		            return null;
		        }
		}
		    public void set(String type, HashMap<Integer, Double> value)
		    {
		        typeMembershipHT.put(type, value);
		    }

}
