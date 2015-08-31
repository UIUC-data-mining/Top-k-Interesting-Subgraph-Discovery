package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;

/**
 * Standard Utils file -- shared across projects
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class SharedUtils {
	public static void sortDoubleList(ArrayList<Double> list, ArrayList<Double> newList, Integer[] index)
	{
		FibonacciHeap<Integer> heap = new FibonacciHeap<Integer>();
		for(int i=0;i<list.size();i++)
		{
			FibonacciHeapNode<Integer> node = new FibonacciHeapNode<Integer>(i, (Double) list.get(i));
			heap.insert(node, node.getKey());
		}
		for(int i=0;i<list.size();i++)
		{
			FibonacciHeapNode<Integer> node = heap.removeMin();
			newList.set(i, node.getKey());
			index[i]=node.getData();
		}
	}
	public static void addToMap(HashMap<String, Integer> map, String key, int value)
	{
		if(map.containsKey(key))
			map.put(key, map.get(key)+value);
		else
			map.put(key, value);
	}
	public static void addToMap(HashMap<String, Double> map, String key, double value)
	{
		if(map.containsKey(key))
			map.put(key, map.get(key)+value);
		else
			map.put(key, value);
	}
	public static void addToMap(HashMap<Integer, Integer> map, Integer key, int value)
	{
		if(map.containsKey(key))
			map.put(key, map.get(key)+value);
		else
			map.put(key, value);
	}
	public static void addToMap(HashMap<Integer, Double> map, Integer key, double value)
	{
		if(map.containsKey(key))
			map.put(key, map.get(key)+value);
		else
			map.put(key, value);
	}
	
	
	
	/**
	 * Reads a tsv file representing a matrix into a minheap. Each matrix entry is inserted as is into the heap with key as lineIndex_columnIndex
	 * @param file
	 * @return
	 * @throws Throwable
	 */
	public static FibonacciHeap<String> convertMatrixFileToHeap(String file) throws Throwable
	{
		FibonacciHeap<String> heap = new FibonacciHeap<String>();
		BufferedReader in = new BufferedReader(new FileReader(new File(file)));
		int lc=0;
		String str="";
		while((str=in.readLine())!=null)
		{
			String tokens[]=str.split("\\t");
			for(int i=0;i<tokens.length;i++)
			{
				if(Double.parseDouble(tokens[i])==1)
					continue;
				FibonacciHeapNode<String> node = new FibonacciHeapNode<String>(lc+"_"+i, Double.parseDouble(tokens[i]));
				heap.insert(node, node.getKey());
			}
			lc++;
		}
		in.close();
		return heap;
	}
}