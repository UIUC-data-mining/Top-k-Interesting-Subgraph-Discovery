/**
 * 
 */
package dataGen.wikipedia;
import java.io.*;
import java.util.*;

import IndexConstruction.Graph;

import utils.MatrixUtils;
import utils.StatUtils;

/**
 * Generates the weighted wikipedia graph by taking input as the unweighted graph topology of Wikipedia and the weights specified in clusteringResults.txt
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class WikipediaGraphGen {
	public static String baseDir = "/home/cs/gupta58/2012/VLDB12/wikipedia/";
//	public static String baseDir = "C:\\Users\\manish\\Desktop\\tmpData\\synthetic\\";
	public static HashMap<Integer, Double[]> clusterResults = new HashMap<Integer, Double[]>();
	
	public static void main(String[] args) throws Throwable {
		//read clustering results
		Double[][] mat = MatrixUtils.readMatrixFromFile(baseDir+"/clusteringResults.txt");
		for(int i=0;i<mat.length;i++)
		{
			Double d[]=new Double[mat[i].length-1];
			for(int j=0;j<mat[i].length-1;j++)
				d[j]=mat[i][j+1];
			double d1=mat[i][0];
			clusterResults.put((int)d1, d);
		}
		//read the graph and associate weights.
		HashMap<Integer, String> nodeInfo = new HashMap<Integer, String>();
		HashMap<Integer, Integer> new2OldNodeIDMap = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> old2NewNodeIDMap = new HashMap<Integer, Integer>();
		BufferedReader in = new BufferedReader(new FileReader(new File(baseDir, "10EntityTypeNetwork.nodes")));
		String str="";
		while((str=in.readLine())!=null)
		{
			String tokens[]=str.split("\\t");
			int oldID=Integer.parseInt(tokens[0]);
			nodeInfo.put(oldID, tokens[1]+"\t"+tokens[2]);
		}
		in.close();
		//read the edges
		Graph g = new Graph();
		in = new BufferedReader(new FileReader(new File(baseDir, "10EntityTypeNetwork.edges")));
		int count=0;
		while((str=in.readLine())!=null)
		{
			String tokens[]=str.split("#");
			int node1=Integer.parseInt(tokens[0]);
			int node2=Integer.parseInt(tokens[1]);
			double weight=(StatUtils.klDivergence(clusterResults.get(node1), clusterResults.get(node2))+StatUtils.klDivergence(clusterResults.get(node2), clusterResults.get(node1)))/2;
			if(!old2NewNodeIDMap.containsKey(node1))
			{
				old2NewNodeIDMap.put(node1, count);
				new2OldNodeIDMap.put(count, node1);
				count++;
				g.numNodes++;
			}
			if(!old2NewNodeIDMap.containsKey(node2))
			{
				old2NewNodeIDMap.put(node2, count);
				new2OldNodeIDMap.put(count, node2);
				count++;
				g.numNodes++;
			}
			int id1=old2NewNodeIDMap.get(node1);
			int id2=old2NewNodeIDMap.get(node2);
			if(g.getEdge(id1, id2)==null)
			{
				g.addEdge(id1, id2, weight);
				g.addEdge(id2, id1, weight);
				g.numEdges++;
			}
		}
		in.close();
		//save the graph
		g.saveGraph(new File(baseDir+File.separator+"graph.txt"));
		//load the types file.
		HashMap<String, Integer> type2ID = new HashMap<String, Integer>();
		in = new BufferedReader(new FileReader(new File(baseDir, "10types.txt")));
		int type=1;
		while((str=in.readLine())!=null)
		{
			type2ID.put(str.trim(), type++);
		}
		in.close();
		//save the type file
		BufferedWriter out = new  BufferedWriter(new FileWriter(new File(baseDir, "nodeTypes.txt")));
		for(Integer i:old2NewNodeIDMap.keySet())
		{
			String typeStr=nodeInfo.get(i).split("\\t")[1];
			if(typeStr.equals("musical artist"))
				typeStr="musicalArtist";
			if(typeStr.equals("football biography"))
				typeStr="footballBiography";
			out.write((old2NewNodeIDMap.get(i)+1)+"\t"+type2ID.get(typeStr)+"\n");
		}
		out.close();
		//save the actual ids and name of node.
		out = new  BufferedWriter(new FileWriter(new File(baseDir, "nodeInfo.txt")));
		for(Integer i:old2NewNodeIDMap.keySet())
			out.write(i+"\t"+(old2NewNodeIDMap.get(i)+1)+"\t"+nodeInfo.get(i)+"\n");
		out.close();
	}
}

