package dataGen.DBLP.netclus;

import java.util.HashMap;

/**
 *  This class maintains a hashmap of "fromtype" to a hashmap of "totype to Relation"
 *  Basically it relates "fromtype" and "totype" to an instance of class Relation
 * @author gupta58
 * Originally coded by Yizhou Sun in C#
 */
public class RelationSet {
    HashMap<String, HashMap<String,Relation>> RelSetHT;

    /**
     * Reads data from files and fills the Relations by calling appropriate load methods
     * @param fromtypeList -- for DBLP dataset, this has all entries set to "paper"
     * @param totypeList -- for DBLP dataset, these entries are like "author", "conf" ...
     * @param relFileList -- files to be read
     * @throws Throwable
     */
      public RelationSet(String[] fromtypeList, String[] totypeList, String[] relFileList) throws Throwable
      {
          RelSetHT = new HashMap<String, HashMap<String,Relation>>();
          for (int i = 0; i < fromtypeList.length; i++)
          {
              Relation newRelation = new Relation();
              newRelation.loadData(relFileList[i]);
              set(fromtypeList[i], totypeList[i],newRelation);
          }
      }

      /**
       * @param fromtype
       * @param totype
       * @return the Relation object tied to the (fromtype, totype) edge
       */
      public Relation get(String fromtype, String totype)
      {
              if (RelSetHT.containsKey(fromtype))
              {
                  HashMap<String, Relation> curFromHT = RelSetHT.get(fromtype);
                  if (curFromHT.containsKey(totype))
                      return curFromHT.get(totype);
              }
              return null;
      }
      
      /**
       * associates a Relation with the (fromtype, totype) pair
       * @param fromtype - this is basically the target type (paper for DBLP)
       * @param totype
       * @param value
       */
          public void set(String fromtype, String totype, Relation value)
          {
              if (RelSetHT.containsKey(fromtype))
              {
                  HashMap<String, Relation> curFromHT = RelSetHT.get(fromtype);
                  curFromHT.put(totype, value);
                  RelSetHT.put(fromtype, curFromHT);
              }
              else
              {
                  HashMap<String, Relation> newFromHT = new HashMap<String, Relation>();
                  newFromHT.put(totype, value);
                  RelSetHT.put(fromtype, newFromHT);
              }
          }
}
