package dataGen.DBLP.netclus;
import java.io.*;
import java.util.*;

import utils.StatUtils;
public class Star2GeneralGraph {
	public static HashMap<Integer, String> authID2Name = new HashMap<Integer, String>();
	public static HashMap<Integer, String> confID2Name = new HashMap<Integer, String>();
	public static HashMap<Integer, String> termID2Name = new HashMap<Integer, String>();
	public static LinkedHashSet<Integer> paperIDs = new LinkedHashSet<Integer>();
	public static HashMap<Integer, HashSet<Integer>> paper2Auth = new HashMap<Integer, HashSet<Integer>>();
	public static HashMap<Integer, HashSet<Integer>> paper2Conf = new HashMap<Integer, HashSet<Integer>>();
	public static HashMap<Integer, HashSet<Integer>> paper2Term = new HashMap<Integer, HashSet<Integer>>();
	public static LinkedHashMap<Integer, String> nodeID2NameAndType = new LinkedHashMap<Integer, String>();
	public static LinkedHashMap<String, Integer> nameAndType2NodeID = new LinkedHashMap<String, Integer>();
	public static HashMap<Integer, Integer> term2Freq = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Double[]> distributionMap = new HashMap<Integer, Double[]>();
	public static String baseDir=Global.dataDir;
	public static HashMap<String, Double> edgeMap = new HashMap<String, Double>();
	private static int topKTerms2ChopOff=10;
public static void main(String[] args) throws Throwable {
	baseDir=args[0];
	loadHashMaps();
	createIDs();
	loadDistributionMap();
	generateGraph();
	writeOutEdgeMap();
}

/**
 * @throws Throwable 
 * 
 */
private static void loadDistributionMap() throws Throwable {
	BufferedReader in = new BufferedReader(new FileReader(new File(baseDir, "ClusOutput0/author.clus")));
	String str="";
	while((str=in.readLine())!=null)
	{
		String tokens[]=str.split("\\t");
		String key="A"+tokens[0];
		Double arr[]= new Double[tokens.length-3];
		double sum=0;
		for(int i=2;i<tokens.length-1;i++)
		{
			arr[i-2]=Double.parseDouble(tokens[i]);
			sum+=arr[i-2];
		}
		for(int i=2;i<tokens.length-1;i++)
		{
			if(sum!=0)
			arr[i-2]/=sum;
		}
		distributionMap.put(nameAndType2NodeID.get(key), arr);
	}
	in.close();
	in = new BufferedReader(new FileReader(new File(baseDir, "ClusOutput0/conf.clus")));
	while((str=in.readLine())!=null)
	{
		String tokens[]=str.split("\\t");
		String key="C"+tokens[0];
		Double arr[]= new Double[tokens.length-3];
		double sum=0;
		for(int i=2;i<tokens.length-1;i++)
		{
			arr[i-2]=Double.parseDouble(tokens[i]);
			sum+=arr[i-2];
		}
		for(int i=2;i<tokens.length-1;i++)
		{
			if(sum!=0)
				arr[i-2]/=sum;
		}
		distributionMap.put(nameAndType2NodeID.get(key), arr);
	}
	in.close();
	in = new BufferedReader(new FileReader(new File(baseDir, "ClusOutput0/term.clus")));
	while((str=in.readLine())!=null)
	{
		String tokens[]=str.split("\\t");
		String key="T"+tokens[0];
		Double arr[]= new Double[tokens.length-3];
		double sum=0;
		for(int i=2;i<tokens.length-1;i++)
		{
			arr[i-2]=Double.parseDouble(tokens[i]);
			sum+=arr[i-2];
		}
		for(int i=2;i<tokens.length-1;i++)
		{
			if(sum!=0)
			arr[i-2]/=sum;
		}
		distributionMap.put(nameAndType2NodeID.get(key), arr);
	}
	in.close();
}

private static void writeOutEdgeMap() throws Throwable {
	BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir,"graph.txt")));
	out.write("#Time: "+new Date()+"\n");
	out.write("#Nodes: "+nodeID2NameAndType.size()+"\n");
	out.write("#Edges: "+edgeMap.size()+"\n");
	out.write("#Graph for files in: "+baseDir+"\n");
	for(String s:edgeMap.keySet())
	{
		if(edgeMap.get(s)!=0.)
		{
			out.write(s+"#"+edgeMap.get(s)+"\n");
			out.write(s.split("#")[1]+"#"+s.split("#")[0]+"#"+edgeMap.get(s)+"\n");
		}
	}
	out.close();
}

private static void generateGraph() {
	for(int p:paperIDs)
	{
		HashSet<String> entitySet = getAllEntitiesForPaper(p);
		//fill in the edge Map.
		for(String s1: entitySet)
		{
			for(String s2: entitySet)
			{
				if(s1!=s2)
				{
					int id1=nameAndType2NodeID.get(s1);
					int id2=nameAndType2NodeID.get(s2);
					if(id1<id2)
					{
						String key=id1+"#"+id2;
						if(!edgeMap.containsKey(key))
							edgeMap.put(key, getScore(key));
					}
				}
			}
		}
	}
}

/**
 * @param key
 * @return
 */
private static double getScore(String key) {
	Double[] arr1=distributionMap.get(Integer.parseInt(key.split("#")[0]));
	Double[] arr2=distributionMap.get(Integer.parseInt(key.split("#")[1]));
	return (StatUtils.klDivergence(arr1, arr2)+StatUtils.klDivergence(arr2, arr1))/2;
}

private static HashSet<String> getAllEntitiesForPaper(int p) {
	HashSet<String> set = new HashSet<String>();
	if(paper2Auth.containsKey(p))
		for(int i:paper2Auth.get(p))
			set.add("A"+i);
	if(paper2Conf.containsKey(p))
		for(int i:paper2Conf.get(p))
			set.add("C"+i);
	if(paper2Term.containsKey(p))
		for(int i:paper2Term.get(p))
			set.add("T"+i);
	return set;
}

private static void createIDs() throws Throwable {
	nodeID2NameAndType = new LinkedHashMap<Integer, String>();
	int nodeID=1;
	for(int a:authID2Name.keySet())
	{
		nodeID2NameAndType.put(nodeID, "A\t"+authID2Name.get(a));
		nameAndType2NodeID.put("A"+a, nodeID);
		nodeID++;
	}
	for(int c:confID2Name.keySet())
	{
		nodeID2NameAndType.put(nodeID, "C\t"+confID2Name.get(c));
		nameAndType2NodeID.put("C"+c, nodeID);
		nodeID++;
	}
	for(int t:termID2Name.keySet())
	{
		nodeID2NameAndType.put(nodeID, "T\t"+termID2Name.get(t));
		nameAndType2NodeID.put("T"+t, nodeID);
		nodeID++;
	}
	//create the IDs file.
	BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, "ids.txt")));
	for(int id:nodeID2NameAndType.keySet())
		out.write(id+"\t"+nodeID2NameAndType.get(id)+"\n");
	out.close();
	//create the types file.
	out = new BufferedWriter(new FileWriter(new File(baseDir, "types.txt")));
	HashMap<String, Integer> typeMap=new HashMap<String, Integer>();
	typeMap.put("A", 1);
	typeMap.put("C", 2);
	typeMap.put("T", 3);
	for(int id:nodeID2NameAndType.keySet())
		out.write(id+"\t"+typeMap.get(nodeID2NameAndType.get(id).split("\\t")[0])+"\n");
	out.close();
}

private static void loadHashMaps() throws Throwable {
	loadIDMaps("author.txt", authID2Name);
	loadIDMaps("term.txt", termID2Name);
	loadIDMaps("conf.txt", confID2Name);
	loadPaper2XMaps("paper_author.txt", paper2Auth);
	loadPaper2XMaps("paper_conf.txt", paper2Conf);
	loadPaper2XMaps("paper_term.txt", paper2Term);
	for(int p:paper2Term.keySet())
	{
		HashSet<Integer> set = paper2Term.get(p);
		for(int t:set)
		{
		if(term2Freq.containsKey(t))
			term2Freq.put(t, term2Freq.get(t)+1);
		else
			term2Freq.put(t, 1);
		}
	}
	ArrayList<Integer> list = new ArrayList<Integer>();
	for(int t:term2Freq.keySet())
		list.add(term2Freq.get(t));
	Collections.sort(list);
	int cutoff=list.get((int)(list.size()*(1-topKTerms2ChopOff/100.)));
	HashSet<Integer> chopSet = new HashSet<Integer>();
	for(int t:term2Freq.keySet())
		if(term2Freq.get(t)>cutoff)
			chopSet.add(t);
	HashMap<Integer, HashSet<Integer>> paper2TermCopy = new HashMap<Integer, HashSet<Integer>>();
	for(Integer t:paper2Term.keySet())
	{
		HashSet<Integer> set2 = paper2Term.get(t);
		HashSet<Integer> tmp = new HashSet<Integer>();
		for(int j:set2)
			if(!chopSet.contains(j))
				tmp.add(j);
		paper2TermCopy.put(t, tmp);
	}
	paper2Term=paper2TermCopy;
	HashMap<Integer, String> termID2NameCopy = new HashMap<Integer, String>();
	for(int t:termID2Name.keySet())
	{
		if(!chopSet.contains(t))
			termID2NameCopy.put(t, termID2Name.get(t));
	}
	termID2Name=termID2NameCopy;
	loadPaperIDs("paper.txt");
}

private static void loadPaperIDs(String file) throws Throwable {
	BufferedReader in = new BufferedReader(new FileReader(new File(baseDir, file)));
	String str="";
	while((str=in.readLine())!=null)
	{
		String tokens[]=str.split("\\t");
		paperIDs.add(Integer.parseInt(tokens[0]));
	}
	in.close();
	
}

private static void loadPaper2XMaps(String file, HashMap<Integer, HashSet<Integer>> map) throws Throwable {
	BufferedReader in = new BufferedReader(new FileReader(new File(baseDir, file)));
	String str="";
	while((str=in.readLine())!=null)
	{
		String tokens[]=str.split("\\t");
		HashSet<Integer> set = new HashSet<Integer>();
		if(map.containsKey(Integer.parseInt(tokens[0])))
			set= map.get(Integer.parseInt(tokens[0]));
		set.add(Integer.parseInt(tokens[1]));
		map.put(Integer.parseInt(tokens[0]), set);
	}
	in.close();
}

private static void loadIDMaps(String file, HashMap<Integer, String> map) throws Throwable {
	BufferedReader in = new BufferedReader(new FileReader(new File(baseDir, file)));
	String str="";
	while((str=in.readLine())!=null)
	{
		String tokens[]=str.split("\\t");
		map.put(Integer.parseInt(tokens[0]), tokens[1]);
	}
	in.close();
}
}
