/**
 * 
 */
package QueryExecution;
import java.io.*;
import java.util.*;

import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;

import IndexConstruction.Edge;
import IndexConstruction.Graph;

/**
 * The ranking after matching baseline.
 * Calls the SPath query execution for the matching part.
 * Prints all the matches in matches.txt besides returning the topK matches.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class RankingAfterMatching {
	public static String baseDir="";
	public static String graphFile="";
	public static String typesFile="";
	public static String queryFile="";
	public static String queryTypesFile="";
	public static String spathFile="";
	public static int k0;
	private static int topK=0;
	public static String resultDir="";
	
	public static void main(String[] args) throws Throwable 
	{
		baseDir=args[0];
		graphFile=args[1];
		typesFile=args[2];
		k0 = Integer.parseInt(args[3]);
		queryFile=args[4];
		queryTypesFile=args[5];
		spathFile=args[6];
		topK=Integer.parseInt(args[7]);
		resultDir=args[8];
		//get the matches
		
		SPathQueryExecutor.init(args);
		SPathQueryExecutor.loadGraphNodesType();
		SPathQueryExecutor.loadGraphSignatures();
		//load the graph
		Graph network = new Graph();
		network.loadGraph(new File(baseDir, graphFile));
		
		//read the queryGraph file names and the queryType file names.
		ArrayList<String> queryGraphFiles = new ArrayList<String>();
		ArrayList<String> queryTypeFiles = new ArrayList<String>();
		String str="";
		BufferedReader br = new BufferedReader(new FileReader(new File(baseDir, queryFile)));
		while((str=br.readLine())!=null)
			queryGraphFiles.add(str);
		br.close();
		br = new BufferedReader(new FileReader(new File(baseDir, queryTypesFile)));
		while((str=br.readLine())!=null)
			queryTypeFiles.add(str);
		br.close();
		//process for each query.
		for(int q=0;q<queryGraphFiles.size();q++)
		{
			try{
			System.err.println("Processing query: "+q);
			queryFile=queryGraphFiles.get(q);
			args[4]=queryFile;
			queryTypesFile=queryTypeFiles.get(q);
			args[5]=queryTypesFile;
			SPathQueryExecutor.initForEveryQuery(args);
			System.setOut(new PrintStream(new File(baseDir+resultDir+"/matches.topK="+topK+"_K0="+k0+"_"+graphFile.split("\\.")[0]+"_"+queryFile.split("/")[1])));
		
		
			long time1=new Date().getTime();
			SPathQueryExecutor.main(args);
			HashMap<String, Double> matches = new HashMap<String, Double>();
			//load the matches
			BufferedReader in = new BufferedReader(new FileReader(new File(baseDir+resultDir+"/matches.topK="+topK+"_K0="+k0+"_"+graphFile.split("\\.")[0]+"_"+queryFile.split("/")[1])));
			while ((str = in.readLine()) != null) {
				if(!str.contains("New"))
					matches.put(str, 0.0);
			}
			in.close();
			System.setOut(new PrintStream(new File(baseDir+resultDir+"/SPath.topK="+topK+"_K0="+k0+"_"+graphFile.split("\\.")[0]+"_"+queryFile.split("/")[1])));
			//load the query graph
			Graph query = new Graph();
			query.loadGraph(new File(baseDir, queryFile));
	
			//process the queries
			HashSet<Edge> queryEdgeSet= query.edges;
			ArrayList<String> actualQueryEdges= new ArrayList<String>();
			for(Edge e:queryEdgeSet)
			{
				int n1=query.nodeId2NodeMap.get(e.src);
				int n2=query.nodeId2NodeMap.get(e.dst);
				if(n1<=n2)
					actualQueryEdges.add(n1+"#"+n2);
			}
			//compute scores
			for(String m:matches.keySet())
			{
				String tokens[]=m.split(",");
				HashMap<Integer,Integer> map = new HashMap<Integer, Integer>();
				for(String t:tokens)
				{
					String toks2[]=t.split("->");
					map.put(Integer.parseInt(toks2[0]), Integer.parseInt(toks2[1]));
				}
				double sum=0;
				for(String s:actualQueryEdges)
				{
					int n1=map.get(Integer.parseInt(s.split("#")[0]));
					int n2=map.get(Integer.parseInt(s.split("#")[1]));
					int id1=network.node2NodeIdMap.get(n1);
					int id2=network.node2NodeIdMap.get(n2);
					sum+=network.getEdge(id1, id2).weight;
				}
				matches.put(m, sum);
			}
			//sort the matches with respect to their scores
			FibonacciHeap<String> heap = new FibonacciHeap<String>();
			for(String m:matches.keySet())
			{
				FibonacciHeapNode<String> node = new FibonacciHeapNode<String>(m, -matches.get(m));
				heap.insert(node, node.getKey());
			}
			//return topk
	//		for(int i=0;i<topK&&!heap.isEmpty();i++)
	//		{
	//			FibonacciHeapNode<String> node = heap.removeMin();
	//			System.err.println(node.getData()+" "+(-node.getKey()/2));
	//		}
			FibonacciHeap<String> heap2 = new FibonacciHeap<String>();
			for(int i=0;i<topK&&!heap.isEmpty();i++)
			{
				FibonacciHeapNode<String> node = heap.removeMin();
				String newStr="";
				String tokens[]=node.getData().split(",");
				HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
				for(String t:tokens)
				{
					String toks[]=t.split("->");
					int num1=Integer.parseInt(toks[0]);
					int num2=Integer.parseInt(toks[1]);
					map.put(num1, num2);
				}
				for(int k=0;k<actualQueryEdges.size();k++)
				{
					String s= actualQueryEdges.get(k);
					int v1=map.get(Integer.parseInt(s.split("#")[0]));
					int v2=map.get(Integer.parseInt(s.split("#")[1]));
					newStr+=v1+"#"+v2+"\t";
				}
				FibonacciHeapNode<String> node2 = new FibonacciHeapNode<String>(newStr, -node.getKey());
				heap2.insert(node2, node2.getKey());
			}
			for(int i=0;i<topK&&!heap2.isEmpty();i++)
			{
				FibonacciHeapNode<String> node = heap2.removeMin();
				System.out.println(node.getData()+" "+(node.getKey()));
			}
			long time2=new Date().getTime();
			System.out.println("Time: "+(time2-time1));
		}
			catch(Exception e)
			{
				System.err.println("Query execution failed");
				e.printStackTrace();
			}
		}
	}
}

