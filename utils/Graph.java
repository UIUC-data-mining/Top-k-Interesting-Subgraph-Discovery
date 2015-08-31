package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;

/**
 * A graph is represented using edges, inLinks, outLinks.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
class Graph {
	public int numNodes;
	public int numEdges;
	public HashSet<Edge> edges;
	public HashMap<Integer,ArrayList<Edge>> inLinks, outLinks;
	/**
	 * Maps the node to an internal node id.
	 */
	public static HashMap<Integer, Integer> node2NodeIdMap = new HashMap<Integer, Integer>();
	/**
	 * Maps internal node id to a node
	 */
	public static HashMap<Integer, Integer> nodeId2NodeMap = new HashMap<Integer, Integer>();
	/**
	 * Maps node id to the number of times it appears in the changed edges
	 */
	public static HashMap<Integer, Integer> nodeFreqInNewEdges = new HashMap<Integer, Integer>();
	/**
	 * Constructor
	 */
	public Graph() {
		edges= new HashSet<Edge>();
		inLinks = new HashMap<Integer, ArrayList<Edge>>();
		outLinks = new HashMap<Integer, ArrayList<Edge>>();
	}
	@SuppressWarnings("unchecked")
	/**
	 * Performs a deep copy of the Graph to create a new graph
	 */
	public static Graph copyGraph(Graph g) {
		Graph g1= new Graph();
		g1.edges= new HashSet<Edge>();
		g1.inLinks = new HashMap<Integer, ArrayList<Edge>>();
		g1.outLinks = new HashMap<Integer, ArrayList<Edge>>();
		for (Edge x : g.edges) {
			g1.edges.add(x);
		}
		for(Integer i: g.inLinks.keySet())
		{
			g1.inLinks.put(i, (ArrayList<Edge>) g.inLinks.get(i).clone());
		}
		for(Integer i: g.outLinks.keySet())
		{
			g1.outLinks.put(i, (ArrayList<Edge>) g.outLinks.get(i).clone());
		}
		return g1;
	}
	public void findConnectedComponents(String baseDir, String graphFile) throws Throwable{
		loadGraph(new File(baseDir+File.separator+graphFile));
		HashSet<Integer> nodes = new HashSet<Integer>();
		int count=0;
		for(int i=0;i<numNodes;i++)
		{
			if(!nodes.contains(i))
			{
				double dist[]=computeMinDistances(i);
				count++;
				nodes.add(i);
				System.out.print(" "+nodeId2NodeMap.get(i));
				for(int j=0;j<dist.length;j++)
				{
					if(dist[j]!=Double.MAX_VALUE && dist[j]!=0.)
					{
						nodes.add(j);
						System.out.print(" "+nodeId2NodeMap.get(j));
					}
				}
				System.out.println();
			}
		}
		System.out.println("Number of components = "+count);
	}
	
	/**
	 * Computes shortest path distances on the graph considering `node' as the source node and fills in shortest path tree T
	 * @param node
	 * @param graph
	 * @return
	 */
	public double[] computeMinDistances(int node, ShortestPathTree t)
	{
		double dist[]= new double[numNodes];
		int prev []= new int[numNodes];
		FibonacciHeap<Integer> fh = new FibonacciHeap<Integer>();
		/* Initialization: set every distance to INFINITY until we discover a path */
		for(int i=0;i<numNodes;i++){
			dist[i]=Double.MAX_VALUE;
			prev[i]=-1;
		}

		HashMap<Integer, FibonacciHeapNode<Integer>> map = new HashMap<Integer, FibonacciHeapNode<Integer>>();
		/* The distance from the source to the source is defined to be zero */
		dist[node] = 0;
		for(int i=0;i<numNodes;i++){
			FibonacciHeapNode<Integer> fhn = new FibonacciHeapNode<Integer>(i,dist[i]);
			fh.insert(fhn, dist[i]);
			map.put(i, fhn);
		}

		while(!fh.isEmpty())
		{
			FibonacciHeapNode<Integer> u = fh.removeMin();
			if(u.getKey()==Double.MAX_VALUE)
				break;
			ArrayList<Edge> list= outLinks.get(u.getData());
			if(list!=null)
			{
			for (Edge edge : list) {
				int neighbor= edge.dst;
				double alt = dist[u.getData()] + edge.weight;
				if(alt<dist[neighbor])
				{
					dist[neighbor]=alt;
					fh.decreaseKey(map.get(neighbor), alt);
					prev[neighbor]=u.getData();
					if(t.addEdge(u.getData(), neighbor)==false)
						System.err.println("Error occurred");
				}
			}
			}
		}
		t.display();
		return dist;
	}
	
	/**
	 * http://www.sciencedirect.com/science/article/pii/S0022000097915348
	 * @param maxNeeded 
	 * @param g
	 * @throws Throwable
	 */
	public void generateCohenLists(String baseDir, String graphFile, String cohenEstimatesFile) throws Throwable
	{
		double matrix[][];
		int maxNeeded=10;
		int maxDist=100;
		int cohenK=50000;
		class CohenInternalNode
		{
			public CohenInternalNode(double d, double vk) {
				dist=d;
				nodeId=vk;
			}
			double nodeId;
			double dist;
		}
		double graphDiameter = 0;
		HashMap<Integer, Integer> rankMap = new HashMap<Integer, Integer>();
		loadGraph(new File(baseDir+File.separator+graphFile));
		System.out.println("Generating lists");
		matrix = new double[numNodes][maxDist];
		for(int k=0;k<cohenK;k++)
		{
			if(k%100==0)
				System.out.println(k);
			double ranks[]= new double[numNodes];
			for(int m=0;m<numNodes;m++)
			{
				ranks[m]=Math.random();
			}
			Arrays.sort(ranks);
			ArrayList<ArrayList<CohenInternalNode>> list = new ArrayList<ArrayList<CohenInternalNode>>();
			
			//reverse edge direction of graph
			//not required as we have an undirected graph
			double dist[]= new double[numNodes];
			double infinity=10000000.00;
			/* Initialization: set every distance to INFINITY until we discover a path */
			for(int j=0;j<numNodes;j++){
				dist[j]=infinity;
			}
			for (int j = 0; j < numNodes; j++) {
				list.add(j,new ArrayList<CohenInternalNode>());
			}
			//generatePermutations
			int ranks2[]=new int[numNodes];
			for(int i=0;i<numNodes;i++)
			{
				ranks2[i]=i;
			}
			for (int k2 = ranks.length - 1; k2 > 0; k2--) {
			    int w = (int)Math.floor(Math.random() * (k2+1));
			    int temp = ranks2[w];
			    ranks2[w] = ranks2[k2];
			    ranks2[k2] = temp;
			}
			for(int k2=0;k2<numNodes;k2++)
				rankMap.put(ranks2[k2],k2);
			//process further
			for (int i = 0; i < numNodes; i++) {
				int vi = rankMap.get(i);//sortedNodeIds[i];
				HashMap<Integer,FibonacciHeapNode<Integer>> heapedNodeToFHN= new HashMap<Integer, FibonacciHeapNode<Integer>>();
				FibonacciHeap<Integer> heap = new FibonacciHeap<Integer>();
				FibonacciHeapNode<Integer> fhn = new FibonacciHeapNode<Integer>(vi,0.0);
				heap.insert(fhn, 0.0);
				heapedNodeToFHN.put(fhn.getData(),fhn);
				while(!heap.isEmpty()){
					FibonacciHeapNode<Integer> fhns = heap.removeMin();
					heapedNodeToFHN.remove(fhns.getData());
					int vk = fhns.getData();//minLableNodeId 
					double D = fhns.getKey();//minLabel
					if(D>maxNeeded)
						break;
					list.get(vk).add(new CohenInternalNode(D,ranks[i]));
					if(D>graphDiameter)
						graphDiameter=D;
					dist[vk]=D;
					if(inLinks.get(vk)!=null)
					{
						for (Edge edge : inLinks.get(vk)) { //reversed graph
							int vj=edge.src;
							double weight=edge.weight;
							if(heapedNodeToFHN.containsKey(vj)){
								FibonacciHeapNode<Integer> tempFHN = (FibonacciHeapNode<Integer>)heapedNodeToFHN.get(vj);
								double currentLabel = tempFHN.getKey();
								double newLabel =  D+weight;
								if(newLabel < currentLabel){
									heap.decreaseKey(tempFHN, newLabel);
								}
							}
							else
							{
								if(D+weight < dist[vj]){
									FibonacciHeapNode<Integer> tempFHN = new FibonacciHeapNode<Integer>(vj,D+weight);
									heap.insert(tempFHN, tempFHN.getKey());
									heapedNodeToFHN.put(tempFHN.getData(), tempFHN);
								}
							}
						}
					}
				}
			}
			for (int j = 0; j < numNodes; j++) {
				ArrayList<CohenInternalNode> al = list.get(j);
				int last=maxDist-1;
				for (CohenInternalNode node : al) {
					if(node.dist>maxDist-1)
						continue;
					for(int i1=last;i1>=node.dist;i1--)
						matrix[j][i1]+=node.nodeId;
					last=(int)(node.dist)-1;
				}
			}
		}
		//print the estimates
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, cohenEstimatesFile)));
		for(int j=0;j<numNodes;j++)
		{
			out.write(Graph.nodeId2NodeMap.get(j)+"#");
			for(int d=0;d<maxDist;d++)
			{
				int val = (int)Math.round((double)cohenK/matrix[j][d])-1;
				out.write(val+",");
			}
			out.write("\n");
		}
		out.close();
	}

	
	/**
	 * computes the importance number of each edge in the graph.
	 * @throws Throwable 
	 */
	public void computeImportanceNumber(String baseDir, String graphFile ) throws Throwable
	{
		ShortestPathTree t=null;
		HashMap<String, Double> imp = new HashMap<String, Double>();
		loadGraph(new File(baseDir+File.separator+graphFile));
		Date d = new Date();
		int numRandomNodes=10;
		int numRandomSpanningTrees=10;
		HashSet<Integer> randomNodes = new HashSet<Integer>(); 
		System.err.println("Started at: "+d);

		//initialize all edge importance values to 0
		for (Edge e : edges) {
			imp.put(e.src+"#"+e.dst,0.0);
		}
		for (int i = 0; i < numRandomNodes; i++) {
			//randomly select a node
			int number= (int)(Math.random()*numNodes);
			if(randomNodes.contains(number))
				i--;
			else
			{
				randomNodes.add(number);
				System.out.println(number);
				t = new ShortestPathTree(number);
				//run dijkstra from those nodes and create an SPT for each run of Dijkstra				
				computeMinDistances(number, t);
				if(t.leaves.size()!=263)
				{
					i--;
					randomNodes.remove(number);
					continue;
				}
				//Get the tight edges for each such SPT
				for (Edge e : edges) {
					t.computeOtherTightEdges(e.src, e.dst);
				}
				for(int j=0;j<numRandomSpanningTrees;j++)
				{
					//Randomly choose a spanning tree.
					//For every node, we would randomly choose an inedge.
					ShortestPathTree t1 = t.randomSample();
					//compute importance of all edges for this spanning tree
					t1.computeImportance(imp);
				}
			}
		}
		for (Edge e : edges) {
			double value=imp.get(e.src+"#"+e.dst);
			if(value!=0)
				System.out.println(e.src+"#"+e.dst+"#"+value);
		}
		d = new Date();
		System.err.println("Ended at: "+d);

	}
	/**
	 * Computes shortest path distances on the graph considering `node' as the source node
	 * using Dijkstra's algorithm
	 * @param node
	 * @param graph
	 * @return
	 */
	public double[] computeMinDistances(int node)
	{
		double dist[]= new double[numNodes];
		int prev []= new int[numNodes];
		FibonacciHeap<Integer> fh = new FibonacciHeap<Integer>();
		/* Initialization: set every distance to INFINITY until we discover a path */
		for(int i=0;i<numNodes;i++){
			dist[i]=Double.MAX_VALUE;
			prev[i]=-1;
		}

		HashMap<Integer, FibonacciHeapNode<Integer>> map = new HashMap<Integer, FibonacciHeapNode<Integer>>();
		/* The distance from the source to the source is defined to be zero */
		dist[node] = 0;
		for(int i=0;i<numNodes;i++){
			FibonacciHeapNode<Integer> fhn = new FibonacciHeapNode<Integer>(i,dist[i]);
			fh.insert(fhn, dist[i]);
			map.put(i, fhn);
		}

		while(!fh.isEmpty())
		{
			FibonacciHeapNode<Integer> u = fh.removeMin();
			if(u.getKey()==Double.MAX_VALUE)
				break;
			ArrayList<Edge> list= outLinks.get(u.getData());
			if(list!=null)
			{
			for (Edge edge : list) {
				int neighbor= edge.dst;
				double alt = dist[u.getData()] + edge.weight;
				if(alt<dist[neighbor])
				{
					dist[neighbor]=alt;
					fh.decreaseKey(map.get(neighbor), alt);
					prev[neighbor]=u.getData();
				}
			}
			}
		}
		return dist;
	}
	/**
	 * Computes shortest path distances from every node to every other node in Graph g.
	 * Infinity and 0 distances are not printed.
	 * Also distance from node a to node b is printed if a<b
	 * @param g
	 */
	public void computeAllPairDistances()
	{
		for(int i=0;i<numNodes;i++)
		{
			double dist[]=computeMinDistances(i);
			for(int j=0;j<numNodes;j++)
			{
				if(dist[j]!=Double.MAX_VALUE && dist[j]!=0. && nodeId2NodeMap.get(i)<nodeId2NodeMap.get(j))
					System.out.println(nodeId2NodeMap.get(i)+" "+nodeId2NodeMap.get(j)+" "+dist[j]);
			}
		}
	}
	
	/**
	 * Returns the edge object if there exists an edge between node1 and node2 else returns null
	 * @param node1
	 * @param node2
	 * @return
	 */
	public Edge getEdge(int node1, int node2)
	{
		if(inLinks.containsKey(node2))
		{
			ArrayList<Edge> a = inLinks.get(node2);
			for (Edge edge : a) {
				if(edge.src==node1)
					return edge;
			}
		}
		return null;
	}
	/**
	 * Adds edge to graph considering direction from a to b
	 * @param a
	 * @param b
	 * @param weight
	 */
	public void addEdge(int a, int b, double weight) {
		Edge e = new Edge(a,b, weight);
		edges.add(e);
		ArrayList<Edge> al = new ArrayList<Edge>();
		if(inLinks.get(b)!=null)
		{
			al = inLinks.get(b);
		}
		al.add(e);
		inLinks.put(b, al);
		al = new ArrayList<Edge>();
		if(outLinks.get(a)!=null)
		{
			al = outLinks.get(a);
		}
		al.add(e);
		outLinks.put(a, al);
	}
	/**
	 * Adds edge to graph considering direction from a to b
	 * @param a
	 * @param b
	 * @param weight
	 */
	public void removeEdge(int a, int b) {
		Edge e=null;
		ArrayList<Edge> al = new ArrayList<Edge>();
		if(inLinks.get(b)!=null)
		{
			al = inLinks.get(b);
		}
		for (Edge edge : al) {
			if(edge.src==a && edge.dst==b)
			{
				e=edge;
				break;
			}
		}
		al.remove(e);
		inLinks.put(b,al);

		al = new ArrayList<Edge>();
		if(outLinks.get(a)!=null)
		{
			al = outLinks.get(a);
		}
		for (Edge edge : al) {
			if(edge.src==a && edge.dst==b)
			{
				e=edge;
				break;
			}
		}
		al.remove(e);
		outLinks.put(a,al);

		edges.remove(e);
	}
	/**
	 * Creates a random graph
	 * @param unitWeighted
	 */
	public void createRandomGraph(boolean unitWeighted)
	{
		for(int i=0;i<numEdges;i++)
		{
			int a= (int)(Math.random()*numNodes)+1;
			int b= (int)(Math.random()*numNodes)+1;
			if(a==b)
			{
				i--;
				continue;
			}
			double weight=Math.random();
			if(unitWeighted)
				weight=1.0;
			if(this.getEdge(a-1, b-1)==null)
			{
				this.addEdge(a-1,b-1, weight);
				this.addEdge(b-1,a-1, weight);
			}
			else
			{
				i--;
			}
		}
	}
	/**
	 * Creates a random graph with integer weights from 1 to 11.
	 */
	public void createRandomGraphWithIntegerWeights()
	{
		for(int i=0;i<numEdges;i++)
		{
			int a= (int)(Math.random()*numNodes)+1;
			int b= (int)(Math.random()*numNodes)+1;
			if(a==b)
			{
				i--;
				continue;
			}
			double weight=(int)(Math.random()*10)+1;
			if(this.getEdge(a-1, b-1)==null)
			{
				this.addEdge(a-1,b-1, weight);
				this.addEdge(b-1,a-1, weight);
			}
			else
			{
				i--;
			}
		}
	}
	/**
	 * Saves the graph to the file
	 * @param f
	 * @throws Throwable
	 */
	public void saveGraph(File f) throws Throwable
	{
		BufferedWriter out = new BufferedWriter(new FileWriter(f));
		out.write("#Randomly generated graph:\n");
		Date d = new Date(); 
		out.write("#Time: "+ d+"\n");
		out.write("#Nodes: "+numNodes+"\n");
		out.write("#Edges: "+numEdges+"\n");
		out.write("#Undirected graph (each pair of nodes is saved twice) -- contains no self loops"+"\n");
		for (Edge edge : this.edges) {
			out.write((edge.src+1)+"#"+(edge.dst+1)+"#"+edge.weight+"\n");
		}
		out.close();
	}
	/**
	 * Loads the graph from the file
	 * @param f
	 * @throws Throwable
	 */
	public void loadGraph(File f) throws Throwable
	{
		BufferedReader in = new BufferedReader(new FileReader(f));
		String str="";
		int nodeCount=node2NodeIdMap.size();
		while((str=in.readLine())!=null)
		{
			if(str.startsWith("#"))
			{
				if(str.contains("# Nodes"))
				{
					numNodes=Integer.parseInt(str.split("\\s+")[1].trim().split(":")[1]);
					numEdges=Integer.parseInt(str.split("\\s+")[4].trim());
				}
				System.err.println(str);
				continue;
			}
			String tokens[]= str.split("#|\\t");
			int from= Integer.parseInt(tokens[0]);
			int to = Integer.parseInt(tokens[1]);
			if(node2NodeIdMap.containsKey(from))
				from=node2NodeIdMap.get(from);
			else
			{
				node2NodeIdMap.put(from, nodeCount);
				nodeId2NodeMap.put(nodeCount, from);
				from=nodeCount;
				nodeCount++;
			}
			if(node2NodeIdMap.containsKey(to))
				to=node2NodeIdMap.get(to);
			else
			{
				node2NodeIdMap.put(to, nodeCount);
				nodeId2NodeMap.put(nodeCount, to);
				to=nodeCount;
				nodeCount++;
			}
			if(tokens.length>2)
			this.addEdge(from, to, Double.parseDouble(tokens[2]));
			else
				this.addEdge(from, to, 1);
		}
		in.close();
	}
	public static void main(String[] args) throws Throwable {
		Graph g = new Graph();
		g.createRandomGraph(false);
		String randomFile=args[1];
		String graphFile=args[2];
		g.saveGraph(new File(args[0]+File.separator+randomFile));
		g.loadGraph(new File(args[0]+File.separator+graphFile));
	}
	/**
	 * Adds new edges to the graph to create a new snapshot (simulating the graph evolution)
	 * Currently the new edges that are added are the ones that don't change the weight of the existing edges. i.e. Evolution here means that 
	 * new edges are added; old edges are not modified.
	 * @param unitWeighted
	 * @param extraEdges
	 */
	public void addMoreEdges(boolean unitWeighted, int extraEdges) {
		for(int i=0;i<extraEdges;i++)
		{
			int a= (int)(Math.random()*numNodes)+1;
			int b= (int)(Math.random()*numNodes)+1;
			if(a==b)
			{
				i--;
				continue;
			}
			double weight=Math.random();
			if(unitWeighted)
				weight=1;
			if(this.getEdge(a-1, b-1)==null)
			{
				if(nodeFreqInNewEdges.containsKey(a-1))
					nodeFreqInNewEdges.put(a-1, nodeFreqInNewEdges.get(a-1)+1);
				else
					nodeFreqInNewEdges.put(a-1, 1);
				if(nodeFreqInNewEdges.containsKey(b-1))
					nodeFreqInNewEdges.put(b-1, nodeFreqInNewEdges.get(b-1)+1);
				else
					nodeFreqInNewEdges.put(b-1, 1);
				this.addEdge(a-1,b-1, weight);
				this.addEdge(b-1,a-1, weight);
				System.out.println("New edge added: "+nodeId2NodeMap.get(a-1)+"--"+nodeId2NodeMap.get(b-1)+":"+weight);
			}
			else
			{
				i--;
			}
		}
	}
	
	/**
	 * This method would remove existing edges from the current graph snapshot denoting the evolution of the graph by removal of edges.
	 * @param unitWeighted
	 */
	public void removeEdges(boolean unitWeighted, int numEdgesToRemove) 
	{
		for(int i=0;i<numEdgesToRemove;i++)
		{
			int a= (int)(Math.random()*numNodes)+1;
			int b= (int)(Math.random()*numNodes)+1;
			if(a==b)
			{
				i--;
				continue;
			}
			if(this.getEdge(a-1, b-1)!=null)
			{
				if(nodeFreqInNewEdges.containsKey(a-1))
					nodeFreqInNewEdges.put(a-1, nodeFreqInNewEdges.get(a-1)+1);
				else
					nodeFreqInNewEdges.put(a-1, 1);
				if(nodeFreqInNewEdges.containsKey(b-1))
					nodeFreqInNewEdges.put(b-1, nodeFreqInNewEdges.get(b-1)+1);
				else
					nodeFreqInNewEdges.put(b-1, 1);
				this.removeEdge(a-1,b-1);
				this.removeEdge(b-1,a-1);
				System.out.println("Edge deleted: "+nodeId2NodeMap.get(a-1)+"--"+nodeId2NodeMap.get(b-1));
			}
			else
			{
				i--;
			}
		}
	}
}
