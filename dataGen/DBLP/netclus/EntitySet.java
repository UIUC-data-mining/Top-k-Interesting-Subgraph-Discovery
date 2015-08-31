package dataGen.DBLP.netclus;

import java.util.HashMap;
import java.util.Set;

/**
 * This class maintains a hashmap of "type" to Entity
 * @author gupta58
 * Originally coded by Yizhou Sun in C#
 */
public class EntitySet {

    public HashMap<String, Entity> ESHT; //type->Entity

    /**
     * @param type -- type of entities desired e.g. paper, conf, author for DBLP
     * @return IDs of the desired entity type.
     */
    public Set<Integer> getKeys(String type)
    {
        if (ESHT.containsKey(type))
        {
            return ESHT.get(type).getKeys();
        }
        else
            return null;
    }
    /**
     * Initializer -- fills up data structure of class Entity and EntitySet by calling appropriate loading functions
     * @param typeList -- type of entities to be read from files
     * @param fileList -- files to be used to read in data
     * @throws Throwable
     */
    public EntitySet(String [] typeList, String [] fileList) throws Throwable
    {
        ESHT = new HashMap<String, Entity>();
        for (int i = 0; i < typeList.length; i++)
        {
            Entity curEntity = new Entity();
            curEntity.loadData(fileList[i]);
            ESHT.put(typeList[i],curEntity);
        }
    }
/**
 * @param type -- entity type e.g. paper, conf
 * @return Instance of type Entity for the desired type
 */
    public Entity getByType(String type)
    {
            return ESHT.get(type);
    }
}
