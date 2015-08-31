/**
 * 
 */
package IndexConstruction;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Generates the SPD (or MPW), Topology and the SPath index. Also stores the metapath information in the indexes except for the SPath index.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class SPDAndTopologyAndSPathIndexConstructor2 {
	public static String baseDir = "";
	public static HashMap<Integer, Integer> node2Type = new HashMap<Integer, Integer>();
	
	public static HashMap<Integer, HashSet<ArrayList<Integer>>> paths = new HashMap<Integer, HashSet<ArrayList<Integer>>>();
	public static DecimalFormat twoDForm = new DecimalFormat("#.####");
	public static void main(String[] args) throws Throwable {
		String baseDir=args[0];
		String graphFile=args[1];
		String typesFile=args[2];
		int D = Integer.parseInt(args[3]);
		int totalTypes = 0;
		//load types file
		BufferedReader in = new BufferedReader(new FileReader(new File(baseDir,typesFile)));
		String str = "";
		while ((str = in.readLine()) != null) {
			String tokens[] = str.split("\\t");
			int node=Integer.parseInt(tokens[0]);
			int type=Integer.parseInt(tokens[1]);
			node2Type.put(node, type);
			if(type>totalTypes)
				totalTypes=type;
		}
		in.close();
		//fix the ordering
		HashMap<Integer,ArrayList<String>> ordering = new HashMap<Integer, ArrayList<String>>();
		for(int d=1;d<=D;d++)
			ordering.put(d, new ArrayList<String>());
		for(int i=1;i<=totalTypes;i++)
			ordering.get(1).add(i+"");
		for(int d=2;d<=D;d++)
		{
			for(int i=1;i<=totalTypes;i++)
			{
				for(String s:ordering.get(d-1))
				{
					if(s.length()==d-1)
						ordering.get(d).add(s+i);
				}
			}
		}
		//load the graph
		Graph g = new Graph();
		g.loadGraph(new File(baseDir,graphFile));
		String filename=graphFile.split("\\.")[0]+".spath";
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, filename)));
		String topologyFilename=graphFile.split("\\.")[0]+".topology";
		BufferedWriter outTopology = new BufferedWriter(new FileWriter(new File(baseDir, topologyFilename)));
		String spdFilename=graphFile.split("\\.")[0]+".spd";
		BufferedWriter outSPD = new BufferedWriter(new FileWriter(new File(baseDir, spdFilename)));
		
		long time1=new Date().getTime();
		for(int i=1;i<=g.numNodes;i++)
		{
			if(i%10==0)
				System.err.println("Nodes processed: "+i+" out of "+g.numNodes);
			out.write(i+"\t");
			outTopology.write(i+"\t");
			outSPD.write(i+"\t");
			int n=g.node2NodeIdMap.get(i);//internalID
			HashSet<Integer> queue = new HashSet<Integer>();
			HashMap<Integer, Double> sumWeight = new HashMap<Integer, Double>();
			queue.add(n);
			sumWeight.put(n,0.);
			HashSet<Integer> considered = new HashSet<Integer>();
			considered.add(n);
			paths = new HashMap<Integer, HashSet<ArrayList<Integer>>>();
			ArrayList<Integer> ll  = new ArrayList<Integer>();
			ll.add(n);
			HashSet<ArrayList<Integer>> hs = new HashSet<ArrayList<Integer>>();
			hs.add(ll);
			paths.put(n, hs);
			for(int d=0;d<D;d++)
			{
				//perform BFS from each node.
				HashMap<Integer, HashSet<ArrayList<Integer>>> newPaths = new HashMap<Integer, HashSet<ArrayList<Integer>>>();
				HashSet<Integer> newQueue = new HashSet<Integer>();
				HashMap<Integer, Double> newSumWeight = new HashMap<Integer, Double>();
				for(int q:queue)
				{
					ArrayList<Edge> nbrs= g.inLinks.get(q); 
					for(Edge e:nbrs)
					{
						int qDash=e.src;
						double newWt=sumWeight.get(q)+e.weight;
						if((newSumWeight.containsKey(qDash)&&newSumWeight.get(qDash)<newWt)||(!newSumWeight.containsKey(qDash)&&!considered.contains(qDash)))
						{
							considered.add(qDash);
							newQueue.add(qDash);
							newSumWeight.put(qDash, sumWeight.get(q)+e.weight);
						}
						if(newSumWeight.containsKey(qDash)||(!newSumWeight.containsKey(qDash)&&!considered.contains(qDash)))
						{
							HashSet<ArrayList<Integer>> hsai = new HashSet<ArrayList<Integer>>();
							if(newPaths.containsKey(qDash))
								hsai=newPaths.get(qDash);
							for(ArrayList<Integer> ai:paths.get(q))
							{
								ArrayList<Integer> nali = new ArrayList<Integer>(ai);
								nali.add(qDash);
								hsai.add(nali);
							}
							newPaths.put(qDash, hsai);
						}
					}
				}
				queue= newQueue;
				sumWeight=newSumWeight;
				paths= newPaths;
				HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
				for(int q:queue)
				{
					int actualID=g.nodeId2NodeMap.get(q);
					int label=node2Type.get(actualID);
					ArrayList<Integer> al = new ArrayList<Integer>();
					if(map.containsKey(label))
						al= map.get(label);
					al.add(actualID);
					map.put(label, al);
				}
				//processing for SPath index.
				for(Integer s:map.keySet())
					Collections.sort(map.get(s));
				for(int s=1;s<=totalTypes;s++)
				{
					if(map.containsKey(s))
					{
						out.write(map.get(s).size()+"#");
						for(int t:map.get(s))
							out.write(t+",");
					}
					else
					{
						out.write("0#,");
					}
					out.write(";");
				}
				out.write(" ");
//				process paths
				HashMap<String, ArrayList<Integer>> topo = new HashMap<String, ArrayList<Integer>>();
				HashMap<String, Double> spd = new HashMap<String, Double>();
				for(int ii:paths.keySet())
				{
					for(ArrayList<Integer> p:paths.get(ii))
					{
						String types="";
						double totWeight=0;
						for(int j=0;j<p.size();j++)
						{
							int a=p.get(j);
							if(j!=0)
							{
								int aDash=p.get(j-1);
								totWeight+=g.getEdge(a, aDash).weight;
								int b=g.nodeId2NodeMap.get(a);
								types+=node2Type.get(b);
							}
						}
						if((spd.containsKey(types)&&spd.get(types)<totWeight)||!spd.containsKey(types))
							spd.put(types, totWeight);
						int lastNode=ii;
						ArrayList<Integer> l = new ArrayList<Integer>();
						if(topo.containsKey(types))
							l=topo.get(types);
						if(!l.contains(lastNode))
							l.add(lastNode);
						topo.put(types, l);
					}
				}
				//write out topology index.
				for(String o:ordering.get(d+1))
				{
					if(!topo.containsKey(o))
						outTopology.write("0;");
					else
					{
						int t = topo.get(o).size();
						outTopology.write(t+";");
					}
				}
//				outTopology.write(" ");
				//write out spd index.
				for(String o:ordering.get(d+1))
				{
					if(!spd.containsKey(o))
						outSPD.write("0.;");
					else
					{
						double t = spd.get(o);
						outSPD.write(Double.valueOf(twoDForm.format(t))+";");
					}
				}
//				outSPD.write(" ");
			}
			out.write("\n");
			outTopology.write("\n");
			outSPD.write("\n");
		}
		long time2=new Date().getTime();
		System.err.println("Time:"+(time2-time1));
		out.close();
		outTopology.close();
		outSPD.close();	
	}
}

