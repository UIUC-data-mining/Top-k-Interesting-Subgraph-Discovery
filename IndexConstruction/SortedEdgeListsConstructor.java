/**
 * 
 */
package IndexConstruction;
import java.io.*;
import java.util.*;

/**
 * Generates the sorted edge lists index for a graph
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class SortedEdgeListsConstructor {
	public static String baseDir = "/home/cs/gupta58/2012/HNODA3/synthetic/";
	public static HashMap<Integer, Integer> node2Type = new HashMap<Integer, Integer>();
	public static HashMap<String, ArrayList<Edge>> sortedEdgeLists = new HashMap<String, ArrayList<Edge>>();
	public static void main(String[] args) throws Throwable {
		String baseDir=args[0];
		String graphFile=args[1];
		String typesFile=args[2];
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
		//create typed edges
		for(int t=1;t<=totalTypes;t++)
		{
			for(int t2=t;t2<=totalTypes;t2++)
			{
				String key=t+"#"+t2;
				sortedEdgeLists.put(key, new ArrayList<Edge>());
			}
		}
		//load edges
		in = new BufferedReader(new FileReader(new File(baseDir, graphFile)));
		while((str=in.readLine())!=null)
		{
			if(str.startsWith("#"))
				continue;
			String tokens[]=str.split("#");
			int from=Integer.parseInt(tokens[0]);
			int to=Integer.parseInt(tokens[1]);
			if(from>to)
				continue;
			double wt=Double.parseDouble(tokens[2]);
			int fromType=node2Type.get(from);
			int toType = node2Type.get(to);
			if(fromType>toType)
			{
				int tmp=fromType;
				fromType=toType;
				toType=tmp;
				tmp=from;
				from=to;
				to=tmp;
			}
			Edge e = new Edge(from, to, wt);
			ArrayList<Edge> arr = sortedEdgeLists.get(fromType+"#"+toType);
			arr.add(e);
			sortedEdgeLists.put(fromType+"#"+toType, arr);
		}
		in.close();
		//sort the arraylists in descending order
		for(String key:sortedEdgeLists.keySet())
			Collections.sort(sortedEdgeLists.get(key), new EdgeComparator());
		//save the sorted edge lists
		for(String key:sortedEdgeLists.keySet())
		{
			String fileName=graphFile.split("\\.")[0]+"_"+key+".list";
			BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, fileName)));
			ArrayList<Edge> arr = sortedEdgeLists.get(key);
			for(Edge e:arr)
				out.write(e.src+"#"+e.dst+"#"+e.weight+"\n");
			out.close();
		}
	}
}