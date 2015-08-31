package QueryExecution;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import IndexConstruction.Edge;
import IndexConstruction.Graph;

/**
 * Generates a random graph for finding a decent running example to put into the paper.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class EgRandomGraphGen {
	public static String baseDir = "/home/gupta58/2012/VLDB12/";//C:\\Users\\manish\\Desktop\\tmpData\\VLDB12\\";
	public static int totalTypes = 3;
	public static int D = 15;
	public static DecimalFormat twoDForm = new DecimalFormat("#.#");
	public static void main(String[] args) throws Throwable {
		D=Integer.parseInt(args[0]);
		int avg=Integer.parseInt(args[1]);
		Graph g = new Graph();
		HashMap<Integer, Integer> numEdgesSoFar = new HashMap<Integer, Integer>();
		for(int i=0;i<D;i++)
			numEdgesSoFar.put(i, 0);
		for(int i=0;i<D;i++)
		{
			int numEdgesPerNode=(int) (Math.random()*avg+1); //1 to avg
			HashSet<Integer> set = new HashSet<Integer>();
			while(set.size()<numEdgesPerNode-numEdgesSoFar.get(i))
			{
				int rand=(int) (Math.random()*(D));
				if(rand!=i)
					set.add(rand);
			}
			for(int s:set)
			{
				if(g.getEdge(i, s)==null)
				{
					double val=Math.random();
					g.addEdge(i, s, val);
					g.addEdge(s, i, val);
					numEdgesSoFar.put(s, numEdgesSoFar.get(s)+1);
					g.numEdges++;
				}
			}
		}
		g.numNodes=D;
		//save the graph
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir,"queryGraph.txt")));
		out.write("#Randomly generated graph:\n");
		Date d = new Date(); 
		out.write("#Time: "+ d+"\n");
		out.write("#Nodes: "+g.numNodes+"\n");
		out.write("#Edges: "+g.numEdges+"\n");
		out.write("#Undirected graph (each pair of nodes is saved twice) -- contains no self loops. #edges is #directed edges"+"\n");
		for (Edge edge : g.edges) {
			out.write((edge.src+1)+"#"+(edge.dst+1)+"#"+Double.valueOf(twoDForm.format(edge.weight))+"\n");
		}
		out.close();
		//write out nodes with randomly fixed type
		out = new BufferedWriter(new FileWriter(new File(baseDir, "queryTypes.txt")));
		for(int n=0;n<g.numNodes;n++)
		{
			int rand=(int) (Math.random()*totalTypes)+1;
			out.write((n+1)+"\t"+rand+"\n");
		}
		out.close();
	}
}

