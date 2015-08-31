package dataGen.DBLP.netclus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


/**
 * This class defines data structures and operations related to storage of each of the entity types.
 * Data is read from files and stored using hashmaps. Each entity is also provided an index apart from its id mentioned in the file.
 * Note that the index starts from 1. Also, index is unique within all instances of a particular entity but not across different entities.
 * @author gupta58
 * Originally coded by Yizhou Sun in C#
 */
public class Entity
{
    public HashMap<Integer, String> IDHT;
    public HashMap<String, Integer> nameHT;

    public ArrayList<Integer> index2ID;
    public HashMap<Integer, Integer> ID2index;

    /**
     * constructor -- initializes all the hashmaps and arraylists
     */
    public Entity()
    {
        IDHT = new HashMap<Integer, String>();
        nameHT = new HashMap<String, Integer>();
        index2ID = new ArrayList<Integer>();
        ID2index = new HashMap<Integer, Integer>();
    }

    /**
     * @return the set of all entity ids (as mentioned in files
     */
    public Set<Integer> getKeys()
    {
        return IDHT.keySet();
    }

    /**
     * Reads data from file and fills the data members of the class (hashmaps and an arraylist)
     * @param file -- File to be read and processed -- contains 1 line per entity instance. Entity id and entity name separated by a tab
     * @throws Throwable
     */
    public void loadData(String file) throws Throwable
    {
        BufferedReader in = new BufferedReader(new FileReader(new File(Global.dataDir, file)));
        String line = "";
        int index = 0; //from 1
        while ((line = in.readLine()) != null)
        {
            String[] strList = line.split("\\t");
            int ID = Integer.parseInt(strList[0]);
            String name = strList[1];
            IDHT.put(ID,name);
            if (!nameHT.containsKey(name))
            {
                nameHT.put(name, ID);
                index++;
                index2ID.add(ID);
                ID2index.put(ID, index);
            }
            else
            {
                System.err.println(name+" exists already ");
            }
        }
        in.close();
    }
    
    /**
     * @param ID -- ID of the entity instance as mentioned in the file
     * @return the index for the entity instance
     */
    public int getIndexbyID(int ID)
    {
        return ID2index.get(ID);
    }

    /**
     * @param index -- index for the entity instance
     * @return the ID for the entity instance
     */
    public int getIDbyIndex(int index)
    {
        return index2ID.get(index);
    }
	/**
	 * @param ID -- ID of the entity instance as mentioned in the file
	 * @return name of the entity instance
	 */
    public String getNameByID(int ID)
    {
            if (IDHT.containsKey(ID))
                return IDHT.get(ID);
            else
                return "";
    }

   /**
    * @param name - Name of the entity instance
    * @return -- ID of the entity instance as mentioned in the file
    */
    public int getIDByName(String name)
    {
            if (nameHT.containsKey(name))
                return nameHT.get(name);
            else
                return -1;
    }
}