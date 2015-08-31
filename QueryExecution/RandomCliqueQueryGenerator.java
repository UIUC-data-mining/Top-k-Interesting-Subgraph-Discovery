package QueryExecution;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import IndexConstruction.Graph;

/**
 * Generates random clique queries
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class RandomCliqueQueryGenerator {
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
		for(int i=0;i<D;i++)
		{
			for(int j=0;j<D;j++)
			{
				if(i!=j)
				{
					g.addEdge(i,j,1.);
					g.addEdge(j,i,1.);
				}
			}
		}
		g.numNodes=D;
		g.numEdges=(D*(D-1))/2;
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

