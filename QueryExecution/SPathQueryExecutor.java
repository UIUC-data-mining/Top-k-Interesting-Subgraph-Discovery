package QueryExecution;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import IndexConstruction.Edge;
import IndexConstruction.Graph;

/**
 * Refer to "On Graph Query Optimization in Large Networks" paper for details
 * Implements the SPath based subgraph matching algorithm
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class SPathQueryExecutor {
	public static String baseDir="";
	public static String graphFile="";
	public static String typesFile="";
	public static String queryFile="";
	public static String queryTypesFile="";
	public static String spathFile="";
	
	public static Graph query;
	public static HashMap<Integer, ArrayList<Integer>> candidates = new HashMap<Integer, ArrayList<Integer>>();
	public static HashMap<Integer, Integer> queryNodeID2Type = new HashMap<Integer, Integer>();
	public static HashMap<Integer, HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>> querySign = new HashMap<Integer, HashMap<Integer,HashMap<Integer,ArrayList<Integer>>>>();
	public static HashMap<Integer, HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>> graphSign = new HashMap<Integer, HashMap<Integer,HashMap<Integer,ArrayList<Integer>>>>();
	public static HashMap<Integer, HashSet<Integer>> graphType2IDSet = new HashMap<Integer, HashSet<Integer>>();
	public static ArrayList<Integer> types = new ArrayList<Integer>();
	public static HashMap<Integer, HashSet<Path>> u2Paths = new HashMap<Integer, HashSet<Path>>();
	public static int k0;
	
	public static int totalTypes=0;
public static void main(String[] args) throws Throwable {
	if(graphType2IDSet.size()==0)
	{
		init(args);
		loadGraphNodesType();
		loadGraphSignatures();
	}
	//read the query
	query = new Graph();
	query.loadGraph(new File(baseDir, queryFile));
	BufferedReader in = new BufferedReader(new FileReader(new File(baseDir, queryTypesFile)));
	String str="";
	while((str=in.readLine())!=null)
	{
		String tokens[]=str.split("\\t");
		queryNodeID2Type.put(query.node2NodeIdMap.get(Integer.parseInt(tokens[0])), Integer.parseInt(tokens[1]));
	}
	in.close();
	getQuerySignatures(); //fills in querySign
	int min=Integer.MAX_VALUE;
	int mini=-1;
	for(int i=0;i<query.numNodes;i++)
	{
		HashSet<Integer> c1 = graphType2IDSet.get(queryNodeID2Type.get(i));
		ArrayList<Integer> c2 = new ArrayList<Integer>();
		for(int c:c1)
		{
			int kstar=NSContained(i, c);
			if(kstar!=-1)
			{
				c2.add(c);
				//populate u2Paths
				HashSet<Path> oldSet = new HashSet<Path>();
				if(u2Paths.containsKey(c))
					oldSet=u2Paths.get(c);
				oldSet.addAll(getPaths(i, c, kstar));
				u2Paths.put(c, oldSet);
			}
		}
		candidates.put(i, c2);
		System.out.println("New Size: "+c2.size());
		if(c2.size()<min)
		{
			min=c2.size();
			mini=i;
		}
	}
	int vStar=mini;
	for(int u:candidates.get(vStar))
	{
		Path pu=null;
		double sel=Double.MAX_VALUE;
		for(Path p:u2Paths.get(u))
		{
			if(p.nodes.get(0)!=vStar)
				continue;
			double thisPathSel=Math.pow(2, p.nodes.size());
			for(int i:p.nodes)
				thisPathSel/=candidates.get(i).size();
			if(sel>thisPathSel)
			{
				sel=thisPathSel;
				pu=p;
			}
		}
		HashSet<PathAndInstance> I = new HashSet<PathAndInstance>();
		recursiveSearch(pu, I);
	}
}
public static void init(String args[]) {
	baseDir=args[0];
	graphFile=args[1]+".txt";
	typesFile=args[2];
	k0 = Integer.parseInt(args[3]);
	queryFile=args[4];
	queryTypesFile=args[5];
	spathFile=args[6];
	candidates = new HashMap<Integer, ArrayList<Integer>>();
	queryNodeID2Type = new HashMap<Integer, Integer>();
	querySign = new HashMap<Integer, HashMap<Integer,HashMap<Integer,ArrayList<Integer>>>>();
	graphSign = new HashMap<Integer, HashMap<Integer,HashMap<Integer,ArrayList<Integer>>>>();
	graphType2IDSet = new HashMap<Integer, HashSet<Integer>>();
	types = new ArrayList<Integer>();
	u2Paths = new HashMap<Integer, HashSet<Path>>();
}
public static void initForEveryQuery(String args[]) {
	queryFile=args[4];
	queryTypesFile=args[5];
	candidates = new HashMap<Integer, ArrayList<Integer>>();
	queryNodeID2Type = new HashMap<Integer, Integer>();
	querySign = new HashMap<Integer, HashMap<Integer,HashMap<Integer,ArrayList<Integer>>>>();
	u2Paths = new HashMap<Integer, HashSet<Path>>();
}
/**
 * @param pu
 * @param i
 */
private static void recursiveSearch(Path pu, HashSet<PathAndInstance> I) {
	ArrayList<Path> all = getAllInstantiations(pu, I);
	for(Path ipu:all)
	{
		PathAndInstance pi= new PathAndInstance(pu, ipu);
		if(!joinable(I, pi))
			continue;
		I.add(pi);
		if(allEdgesCovered(I))
			outputMatching(I);
		else
		{
			ArrayList<Integer> uVertices = getUCoveredByI(I);
			HashSet<Path> allPaths = new HashSet<Path>();
			HashMap<Integer, Integer> mapping = new HashMap<Integer, Integer>();
			for(PathAndInstance pii:I)
			{
				Path abc=pii.instance;
				for(int key=0;key<abc.nodes.size();key++)
				{
					mapping.put(abc.nodes.get(key), pii.p.nodes.get(key));
				}
			}
			for(int uv:uVertices)
			{
				HashSet<Path> currSet=u2Paths.get(uv);
				for(Path csp:currSet)
				{
					if(csp.nodes.get(0)==mapping.get(uv))
						allPaths.add(csp);
				}
			}
//			int rand=(int)(Math.random()*uVertices.size());
//			int uDash=uVertices.get(rand);
//			while(uDash==pu.u)
//			{
//				rand=(int)(Math.random()*uVertices.size());
//				uDash=uVertices.get(rand);
//			}
			Path puDash=null;
			double sel=Double.MAX_VALUE;
			for(Path p:allPaths)
			{
				int same=0;
				for(PathAndInstance pi2:I)
				{
					if(p.equals(pi2.p))
					{
						same=1;
						break;
					}
				}
				if(same==1)
					continue;
				double thisPathSel=Math.pow(2, p.nodes.size());
				for(int i:p.nodes)
					thisPathSel/=candidates.get(i).size();
				if(sel>thisPathSel)
				{
					sel=thisPathSel;
					puDash=p;
				}
			}
			if(puDash!=null)
				recursiveSearch(puDash, I);
		}
		I.remove(pi);
	}
}

/**
 * @return
 */
private static ArrayList<Integer> getUCoveredByI(HashSet<PathAndInstance> i) {
	ArrayList<Integer> list = new ArrayList<Integer>();
	HashSet<Integer> set = new HashSet<Integer>();
	for(PathAndInstance pi:i)
		for(int i2=0;i2<pi.instance.nodes.size();i2++)
			set.add(pi.instance.nodes.get(i2));
	for(int s:set)
		list.add(s);
	return list;
}
/**
 * @param i
 */
private static void outputMatching(HashSet<PathAndInstance> i) {
	HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
	for(PathAndInstance pi:i)
		for(int i2=0;i2<pi.p.nodes.size();i2++)
			map.put(pi.p.nodes.get(i2), pi.instance.nodes.get(i2));
	for(int j=0;j<query.numNodes;j++)
		System.out.print(query.nodeId2NodeMap.get(j)+"->"+map.get(j)+",");
	System.out.println();
}
/**
 * @param i
 * @return
 */
private static boolean allEdgesCovered(HashSet<PathAndInstance> i) {
	HashSet<Edge> edgeSet = query.edges;
	HashSet<String> set = new HashSet<String>();
	for(PathAndInstance pi:i)
		for(int i2=0;i2<pi.p.nodes.size()-1;i2++)
			set.add(pi.p.nodes.get(i2)+"#"+pi.p.nodes.get(i2+1));
	for(Edge e:edgeSet)
		if(!set.contains(e.src+"#"+e.dst) && !set.contains(e.dst+"#"+e.src))
			return false;
	return true;
}
/**
 * @param i
 * @param pi
 * @return
 */
private static boolean joinable(HashSet<PathAndInstance> i, PathAndInstance pi) {
	for(PathAndInstance a:i)
	{
		for(int i1=0;i1<a.p.nodes.size();i1++)
		{
			int s=a.p.nodes.get(i1);
			if(pi.p.nodes.contains(s))
			{
				for(int i2=0;i2<pi.p.nodes.size();i2++)
				{
					if(pi.p.nodes.get(i2)==s)
					{
						if(!a.instance.nodes.get(i1).equals(pi.instance.nodes.get(i2)))
							return false;
					}
				}
			}
		}
	}
	return true;
}

/**
 * @param pu
 * @return
 */
private static ArrayList<Path> getAllInstantiations(Path pu, HashSet<PathAndInstance> I) {
	int l=pu.nodes.size();
	HashSet<Path> set = new HashSet<Path>();
	Path sp = new Path();
	sp.nodes.add(pu.u);
	sp.u=pu.u;
	set.add(sp);
	HashSet<Path> newSet = new HashSet<Path>();
	HashSet<Integer> lastNode = new HashSet<Integer>();
	HashSet<Integer> newLastNode = new HashSet<Integer>();
	lastNode.add(pu.u);
	
	for(int i=1;i<l;i++)
	{
		for(int ln:lastNode)
		{
			int label=queryNodeID2Type.get(pu.nodes.get(i));
			ArrayList<Integer> nbrs =	graphSign.get(ln).get(1).get(label);
			ArrayList<Integer> candi = candidates.get(pu.nodes.get(i));
			HashSet<Integer> intersection = new HashSet<Integer>();
			for(int n:nbrs)
				if(candi.contains(n))
					intersection.add(n);
			for(int n:intersection)
			{
				newLastNode.add(n);
				for(Path p:set)
				{
					if(p.nodes.get(p.nodes.size()-1)==ln)
					{
						Path q = p.copyPath();
						q.nodes.add(n);
						newSet.add(q);
					}
				}
			}
		}
		lastNode=newLastNode;
		set=newSet;
		newLastNode=new HashSet<Integer>();
		newSet= new HashSet<Path>();
	}
	ArrayList<Path> ret = new ArrayList<Path>();
	for(Path p:set)
	{
		int same=0;
		for(PathAndInstance pi:I)
		{
			if(p.equals(pi.instance))
			{
				same=1;
				break;
			}
		}
		if(same==0)
			ret.add(p);
	}
	return ret;
}
/**
 * @param i
 * @return
 */
private static HashSet<Path> getPaths(int i, int c, int kstar) {
	HashSet<Path> set = new HashSet<Path>();
	Path sp = new Path();
	sp.nodes.add(i);
	sp.u=c;
	set.add(sp);
	ArrayList<Integer> currList = new ArrayList<Integer>();
	HashMap<Integer, Integer> considered = new HashMap<Integer, Integer>();
	considered.put(i, 1);
	currList.add(i);
	for(int k=0;k<kstar;k++)
	{
		ArrayList<Integer> newList = new ArrayList<Integer>();
		HashSet<Integer> newListCopy = new HashSet<Integer>();
		for(int n:currList)
		{
			ArrayList<Edge> nbrs= query.inLinks.get(n);
			for(Edge e:nbrs)
			{
				if((!considered.containsKey(e.src) &&!newListCopy.contains(e.src))||considered.get(e.src)==k+1)
				{
					if(!considered.containsKey(e.src) &&!newListCopy.contains(e.src))
					{
						newList.add(e.src);
						newListCopy.add(e.src);
						considered.put(e.src, k+1);
					}
					HashSet<Path> extras = new HashSet<Path>();
					for(Path p:set)
					{
						if(p.nodes.get(p.nodes.size()-1)==n)
						{
							Path q = p.copyPath();
							q.nodes.add(e.src);
							extras.add(q);
						}
					}
					for(Path p:extras)
						set.add(p);
				}
			}
		}
		currList=newList;
	}
	set.remove(sp);
	return set;
}
public static void loadGraphSignatures() throws Throwable {
	BufferedReader in = new BufferedReader(new FileReader(new File(baseDir, spathFile)));
	String str="";
	while((str=in.readLine())!=null)
	{
		HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> map1 = new HashMap<Integer, HashMap<Integer,ArrayList<Integer>>>();
		String tokens[]=str.split("\\s+");
		int k=1;
		for(int t=1;t<tokens.length;t++)
		{
			HashMap<Integer, ArrayList<Integer>> map2 = new HashMap<Integer, ArrayList<Integer>>();
			//create map2 from t
			String toks2[]=tokens[t].split(";");
			int count=1;
			for(String t2:toks2)
			{
				ArrayList<Integer> list = new ArrayList<Integer>();
				if(!t2.equals("0#"))
				{
					for(String s2:t2.split("#")[1].split(","))
						list.add(Integer.parseInt(s2));
				}
				map2.put(count, list);
				count++;
			}
			map1.put(k, map2);
			k++;
		}
		graphSign.put(Integer.parseInt(tokens[0]), map1);
	}
	in.close();
}
private static int NSContained(int v, int u) {
	HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> NSv = querySign.get(v);
	HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> NSu = graphSign.get(u);
	int count[] = new int[types.size()];
	int kstar=k0;
	for(int k=1;k<k0;k++)
	{
		int same=1;
		for(int l=0;l<types.size();l++)
		{
			int count1=0;
			if(NSu!=null && NSu.get(k)!=null && NSu.get(k).containsKey(types.get(l)))
			{
				count[l]+=NSu.get(k).get(types.get(l)).size();
				count1=NSu.get(k).get(types.get(l)).size();
			}
			int count2=0;
			if(NSv!=null && NSv.get(k)!=null && NSv.get(k).containsKey(types.get(l)))
				count2=NSv.get(k).get(types.get(l)).size();
			if(count2>count[l])
				return -1;
			count[l]-=count2;
			if(count1!=count2)
				same=0;
		}
		if(same==0 &&kstar==k0)
			kstar=k;
	}
	return kstar;
}

public static void loadGraphNodesType() throws Throwable {
	//load types file
	HashMap<Integer, Integer> node2Type = new HashMap<Integer, Integer>();
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
	for(int t=1;t<=totalTypes;t++)
	{
		types.add(t);
		graphType2IDSet.put(t, new HashSet<Integer>());
	}
	for(int n:node2Type.keySet())
		graphType2IDSet.get(node2Type.get(n)).add(n);
}
public static void getQuerySignatures() {
	for(int i=0;i<query.numNodes;i++)
	{
		HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> map1 = new HashMap<Integer, HashMap<Integer,ArrayList<Integer>>>();
		ArrayList<Integer> currList = new ArrayList<Integer>();
		HashSet<Integer> considered = new HashSet<Integer>();
		considered.add(i);
		currList.add(i);
		for(int k=0;k<k0;k++)
		{
			ArrayList<Integer> newList = new ArrayList<Integer>();
			HashSet<Integer> newListCopy = new HashSet<Integer>();
			for(int n:currList)
			{
				ArrayList<Edge> nbrs= query.inLinks.get(n);
				for(Edge e:nbrs)
				{
					if(!considered.contains(e.src) &&!newListCopy.contains(e.src))
					{
						newList.add(e.src);
						newListCopy.add(e.src);
						considered.add(e.src);
					}
				}
			}
			currList=newList;
			HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
			for(int c:currList)
			{
				ArrayList<Integer> al = new ArrayList<Integer>();
				int label=queryNodeID2Type.get(c);
				if(map.containsKey(label))
					al= map.get(label);
				al.add(c);
				map.put(label, al);
			}
			for(int s:map.keySet())
				Collections.sort(map.get(s));
			map1.put(k+1, map);
		}
		querySign.put(i, map1);
	}
}
}