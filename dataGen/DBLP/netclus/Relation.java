package dataGen.DBLP.netclus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Set;


/**
 * This class stores a relation between 2 entity types in the form of a sparse matrix and its transpose
 * @author gupta58
 * Originally coded by Yizhou Sun in C#
 */
public class Relation {
    HashMap<Integer, HashMap<Integer, Double>> matrix;
    HashMap<Integer, HashMap<Integer, Double>> transpose_matrix;
    
    /**
     * Contructor -- initializes the matrix and the transpose matrix
     */
    public Relation()
    {
        matrix = new HashMap<Integer, HashMap<Integer, Double>>();
        transpose_matrix = new HashMap<Integer, HashMap<Integer, Double>>();
    }

    /**
     * @return the ids of all entities of target entity type participating in the relation
     */
    public Set<Integer> getRowKeys()
    {
        return matrix.keySet();
    }

    /**
     * @return the ids of all entities of attribute entity type participating in the relation 
     */
    public Set<Integer> getColKeys()
    {
        return transpose_matrix.keySet();
    }

    /**
     * Get the maximum score of any entity related to the target entity instance "rowID" from ranking score vector rv 
     * @param rowID
     * @param rv
     * @return
     */
    public double getRowMax(int rowID, RankVec rv)
    {
        HashMap<Integer, Double> rowHT = getRow(rowID);
        if (rowHT == null)
            return 0;
        double max = Double.MIN_VALUE;
        for (int colID: rowHT.keySet()) {
            if (max < rv.get(colID))
                max = rv.get(colID);
		}
        return max;
    }

/**
 * Get the average score of all entities related to the target entity instance "rowID" from ranking score vector rv
 * @param rowID
 * @param rv
 * @return
 */
public double getRowAverage(int rowID, RankVec rv)
{
    HashMap<Integer, Double> rowHT = getRow(rowID);
    if (rowHT == null)
        return 0;
    double sum = 0;
    for (int colID : rowHT.keySet())
        sum += rv.get(colID);
    return sum/rowHT.size();
}

/**
 * Gets the row (set of attribute type instances) from the matrix corresponding to rowID
 * @param rowID
 * @return
 */
public HashMap<Integer, Double> getRow(int rowID)
{
    if (matrix.containsKey(rowID))
        return matrix.get(rowID);
    else
        return null;
}

/**
 * Gets the target type instances related to the attribute type instance colID
 * @param colID
 * @return
 */
public HashMap<Integer, Double> getCol(int colID)
{
    if (transpose_matrix.containsKey(colID))
        return transpose_matrix.get(colID);
    else
        return null;
}

/**
 * Gets the weight of the edge (rowID, colID)
 * @param rowID is an instance of target type entity
 * @param colID is an instance of attribute type entity
 * @return
 */
public double get(int rowID, int colID)
{
        if (matrix.containsKey(rowID))
        {
            HashMap<Integer, Double> curRowHT = matrix.get(rowID);
            if (curRowHT.containsKey(colID))
                return curRowHT.get(colID);
        }
        return -1;
}

/**
 * Sets appropriate weights and edges in matrix and transpose_matrix
 * @param rowID
 * @param colID
 * @param value = weight of (rowID, colID) edge.
 */
    public void set(int rowID, int colID, double value)
    {
        if (matrix.containsKey(rowID))
        {
            HashMap<Integer, Double> curRowHT = matrix.get(rowID);
        	curRowHT.put(colID,value);
            matrix.put(rowID, curRowHT);
        }
        else
        {
            HashMap<Integer, Double> newRowHT = new HashMap<Integer, Double>();
            newRowHT.put(colID, value);
            matrix.put(rowID, newRowHT);
        }

        if (transpose_matrix.containsKey(colID))
        {
            HashMap<Integer, Double> curColHT = transpose_matrix.get(colID);
            curColHT.put(rowID, value);
            transpose_matrix.put(colID, curColHT);
        }
        else
        {
            HashMap<Integer, Double> newColHT = new HashMap<Integer, Double>();
            newColHT.put(rowID, value);
            transpose_matrix.put(colID, newColHT);
        }
    }

    /**
     * Loads data from file to matrix and transpose_matrix
     * @param file
     * @throws Throwable
     */
    public void loadData(String file) throws Throwable
    {
    	BufferedReader in = new BufferedReader(new FileReader(new File(Global.dataDir, file)));
        String line = "";
        while ((line = in.readLine()) != null)
        {
            String[] strList = line.split("\\t");
            set(Integer.parseInt(strList[0]), Integer.parseInt(strList[1]),1);
        }
        in.close();
    }
} 
