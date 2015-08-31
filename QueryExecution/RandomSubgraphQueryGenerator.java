package QueryExecution;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;

import IndexConstruction.Graph;

/**
 * Generates random subgraph queries
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class RandomSubgraphQueryGenerator {
	public static String baseDir = "";

	public static void main(String[] args) throws Throwable {
		String baseDir=args[0];
		String typesFile=args[2];
		int D = Integer.parseInt(args[3]);
		int totalTypes = 0;
		//load types file
		BufferedReader in = new BufferedReader(new FileReader(new File(baseDir,typesFile)));
		String str = "";
		while ((str = in.readLine()) != null) {
			String tokens[] = str.split("\\t");
			int type=Integer.parseInt(tokens[1]);
			if(type>totalTypes)
				totalTypes=type;
		}
		in.close();
		Graph g = new Graph();
		HashMap<Integer, Integer> numEdgesSoFar = new HashMap<Integer, Integer>();
		for(int i=0;i<D;i++)
			numEdgesSoFar.put(i, 0);
		for(int i=0;i<D;i++)
		{
			//min 1 edge and max D-1 edges
			int numEdgesPerNode=(int) (Math.random()*(D-1))+1;
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
					g.addEdge(i, s, 1.);
					g.addEdge(s, i, 1.);
					numEdgesSoFar.put(s, numEdgesSoFar.get(s)+1);
					g.numEdges++;
				}
			}
		}
		g.numNodes=D;
		g.saveGraph(new File(baseDir,"queryGraph.txt"));
		//write out nodes with randomly fixed type
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, "queryTypes.txt")));
		for(int n=0;n<g.numNodes;n++)
		{
			int rand=(int) (Math.random()*totalTypes)+1;
			out.write((n+1)+"\t"+rand+"\n");
		}
		out.close();
	}
}

