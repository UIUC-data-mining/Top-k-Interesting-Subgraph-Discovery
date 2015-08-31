/**
 * 
 */
package dataGen;
import java.io.*;
import java.util.*;

import IndexConstruction.Graph;

/**
 * Converts the GTGraph Format to our graph format.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class GTGraphToOurFormatConverter {
	public static String baseDir = "/home/cs/gupta58/2012/VLDB12/synthetic/";
	public static int numTypes=5;
	public static void main(String[] args) throws Throwable {
		baseDir=args[0];
		Graph g = new Graph();
		HashSet<Integer> nodes = new HashSet<Integer>();
		BufferedReader in = new BufferedReader(new FileReader(new File(baseDir, args[1])));
		String str = "";
		int lc=0;
		while ((str = in.readLine()) != null) {
			lc++;
			if(lc%10000==0)
				System.out.println("Lines done: "+lc);
			String tokens[] = str.split("\\s+");
			if(str.contains("No. of vertices"))
				g.numNodes=Integer.parseInt(str.split(":")[1].trim());
			if(str.startsWith("a "))
			{
				int from=Integer.parseInt(tokens[1]);
				int to=Integer.parseInt(tokens[2]);
				int wt=Integer.parseInt(tokens[3]);
				nodes.add(from-1);
				nodes.add(to-1);
				double weight=wt/100.;
				if(g.getEdge(from-1, to-1)==null)
				{
					g.addEdge(from-1,to-1, weight);
					g.addEdge(to-1,from-1, weight);
					g.numEdges++;
				}
			}
		}
		//some nodes may not have any edges. Add edges to other nodes randomly.
		for(int i=0;i<g.numNodes;i++)
		{
			if(!nodes.contains(i))
			{
				int rand=(int) (Math.random()*nodes.size());
				while(!nodes.contains(rand))
					rand=(int) (Math.random()*nodes.size());
				double weight=Math.random();
				g.addEdge(i,rand, weight);
				g.addEdge(rand,i, weight);
				g.numEdges++;
			}
		}
		in.close();
		g.saveGraph(new File(baseDir+File.separator+args[2]));
		//randomly assign type to each of the nodes.
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, args[3])));
		for(int i=1;i<=g.numNodes;i++)
		{
			int rand=(int) ((Math.random()*numTypes)+1);
			out.write(i+"\t"+rand+"\n");
		}
		out.close();
	}
}

