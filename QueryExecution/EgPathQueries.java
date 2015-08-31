package QueryExecution;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import IndexConstruction.Graph;

/**
 * Generates random path queries for finding a decent running example to put into the paper.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class EgPathQueries {
	public static String baseDir = EgRandomGraphGen.baseDir;

	public static void main(String[] args) throws Throwable {
		int D = 4;
		int totalTypes = EgRandomGraphGen.totalTypes;
		Graph g = new Graph();
		for(int i=0;i<D-1;i++)
		{
			g.addEdge(i,i+1,1.);
			g.addEdge(i+1,i,1.);
		}
		g.numNodes=D;
		g.numEdges=D-1;
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

