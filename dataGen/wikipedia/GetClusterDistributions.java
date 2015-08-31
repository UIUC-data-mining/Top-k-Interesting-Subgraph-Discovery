/**
 * 
 */
package dataGen.wikipedia;
import java.io.*;
import java.util.*;

/**
 * Generates the augmented network for the baseline as described in the paper
 * Performs partitioning of the augmented network.
 * Computes soft clustering distributions for each of the entities in the original network (as described in the paper).
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class GetClusterDistributions {
	public static String baseDir = "/home/cs/gupta58/2012/HNODA3/wikipedia/";
	public static HashMap<String, Integer> name2Id = new HashMap<String, Integer>();
	public static HashMap<Integer, String> id2Name = new HashMap<Integer, String>();
	public static HashMap<String, Integer> type2IdAttr = new HashMap<String, Integer>();
	public static HashMap<String, HashSet<Integer>> type2IntAttrSet = new HashMap<String, HashSet<Integer>>();
	public static ArrayList<ArrayList<String[]>> data = new ArrayList<ArrayList<String[]>>();
	public static HashMap<Integer, HashSet<Integer>> nbrs = new HashMap<Integer, HashSet<Integer>>();
	public static HashSet<Integer> origIDs = new HashSet<Integer>();
	public static int kMetisClusters=20;
	public static void main(String[] args) throws Throwable {
		baseDir=args[0];
		//load ten types.
		HashMap<String, Integer> tenTypeMap = new HashMap<String, Integer>();
		BufferedReader in = new BufferedReader(new FileReader(new File(baseDir, "10types.txt")));
		String str="";
		int c=0;
		while((str=in.readLine())!=null)
		{
			tenTypeMap.put(str.trim(),c);
			data.add(new ArrayList<String[]>());
			c++;
		}
		in.close();
		System.err.println("Loaded type ids. (total "+c+")");
		//load data for each type.
		//Get the guessedTypes for each attribute.
		int i=-1;
		for(String t:tenTypeMap.keySet())
		{
			i++;
			System.err.println("Starting to process type: "+t);
			//load the data.
			int lc=0;
			in = new BufferedReader(new FileReader(new File(baseDir, t+".sql")));
			while((str=in.readLine())!=null)
			{
				if(lc%1000==0)
					System.err.println("Processed lines from "+t+".sql: "+lc);
				lc++;
				if(!str.contains("insert"))
					continue;
				str=str.substring(str.indexOf('(')+1).replaceAll("'\\);"," ");
				int instance=Integer.parseInt(str.split(",")[0]);
				str=str.replaceAll("–", "-");
				String tokens[]=str.split("','");
				if(tokens[0].split("'").length>1)
					tokens[0]=tokens[0].split("'")[1];
				else
					tokens[0]="";
				String toks[]=new String[tokens.length+1];
				toks[0]=instance+"";
				for(int j=0;j<tokens.length;j++)
				{
					String tmp=tokens[j].replaceAll("#@#and", "#@#").replaceAll("and#@#", "#@#");
					String toks2[]=tmp.split("#@#");
					tmp="";
					for(String t2:toks2)
						if(!t2.trim().equals(""))
							tmp+=t2.trim()+"#@#";
					if(!tmp.equals(""))
						tmp=tmp.substring(0,tmp.length()-3);
					toks[j+1]=tmp;
				}
				data.get(i).add(toks);
			}
			in.close();
			type2IntAttrSet.put(t, new HashSet<Integer>());
//			Check every attribute at1, at2 , . . . , atDt in At .
			int dim=data.get(i).get(0).length;
			for(int d=1;d<dim;d++)
			{
				System.err.println("Processing attribute: "+d+" of type "+t);
				ArrayList<String> arr = new ArrayList<String>();
				for(int j=0;j<data.get(i).size();j++)
					arr.add(data.get(i).get(j)[d]);
				String sample="";
				Clustering.baseDir = baseDir;
				Clustering.guessType(arr);
				System.out.println("GuessedType:"+d+"\t"+t+"\t"+Clustering.typeSupporters+"\t"+sample+" "+Clustering.type);
				if(Clustering.type.equals("CATEGORY")||Clustering.type.equals("SETOFSTRINGS"))
					type2IntAttrSet.get(t).add(d);
			}
		}
		//create the network.
		in = new BufferedReader(new FileReader(new File(baseDir, "10EntityTypeNetwork.nodes")));
		while((str=in.readLine())!=null)
		{
			String tokens[]=str.split("\\t");
			String name=tokens[1];
			int id=Integer.parseInt(tokens[0]);
			name2Id.put(name, id);
			id2Name.put(id, name);
			origIDs.add(id);
		}
		in.close();
		//load Edges.
		in = new BufferedReader(new FileReader(new File(baseDir, "10EntityTypeNetwork.edges")));
		while((str=in.readLine())!=null)
		{
			String toks[]=str.split("#");
			int num1=Integer.parseInt(toks[0]);
			int num2=Integer.parseInt(toks[1]);
			HashSet<Integer> tmp1 = new HashSet<Integer>();
			if(nbrs.containsKey(num1))
				tmp1=nbrs.get(num1);
			tmp1.add(num2);
			nbrs.put(num1, tmp1);
			HashSet<Integer> tmp2 = new HashSet<Integer>();
			if(nbrs.containsKey(num2))
				tmp2=nbrs.get(num2);
			tmp2.add(num1);
			nbrs.put(num2, tmp2);
		}
		in.close();
		int idCounter=1;
		//add more edges and new nodes.
		i=-1;
		for(String t:tenTypeMap.keySet())
		{
			i++;
			System.err.println("Starting to process type: "+t);
			HashSet<Integer> usefulDims = type2IntAttrSet.get(t);
			ArrayList<String[]> list = data.get(i);
			for(int j=0;j<list.size();j++)
			{
				String[] toks= list.get(j);
				int from=Integer.parseInt(toks[0]);
				for(int d:usefulDims)
				{
					String toks2[]=toks[d].split("#@#");
					HashSet<Integer> set = new HashSet<Integer>();
					for(String t2:toks2)
					{
						if(t2.trim().equals(""))
							continue;
						if(name2Id.containsKey(t2))
							set.add(name2Id.get(t2));
						else
						{
							while(id2Name.containsKey(idCounter))
								idCounter++;
							name2Id.put(t2, idCounter);
							id2Name.put(idCounter, t2);
							set.add(idCounter);
						}
					}
					for(int s1:set)
					{
						for(int s2:set)
						{
							if(s1<s2)
							{
								HashSet<Integer> tmp3= new HashSet<Integer>();
								if(nbrs.containsKey(s1))
									tmp3=nbrs.get(s1);
								tmp3.add(s2);
								nbrs.put(s1, tmp3);
								tmp3= new HashSet<Integer>();
								if(nbrs.containsKey(s2))
									tmp3=nbrs.get(s2);
								tmp3.add(s1);
								nbrs.put(s2, tmp3);
							}
						}
						if(from!=s1)
						{
							HashSet<Integer> tmp3= new HashSet<Integer>();
							if(nbrs.containsKey(s1))
								tmp3=nbrs.get(s1);
							tmp3.add(from);
							nbrs.put(s1, tmp3);
							tmp3= new HashSet<Integer>();
							if(nbrs.containsKey(from))
								tmp3=nbrs.get(from);
							tmp3.add(s1);
							nbrs.put(from, tmp3);
						}
					}
				}
			}
		}
//		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, "augmentedEdges.txt")));
//		for(String e:edges)
//			out.write(e+"\n");
//		out.close();
		
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, "augmentedNodes.txt")));
		for(int id:id2Name.keySet())
			out.write(id+"\t"+id2Name.get(id)+"\n");
		out.close();
		int numEdges=0;
		for(int n:nbrs.keySet())
			numEdges+=nbrs.get(n).size();
		
		//convert network to kmetis form
		HashMap<Integer, Integer> old2NewNodeMap = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> new2OldNodeMap = new HashMap<Integer, Integer>();
		int count=0;
		for(int id:nbrs.keySet())
		{
			count++;
			old2NewNodeMap.put(id, count);
			new2OldNodeMap.put(count, id);
		}

		out = new BufferedWriter(new FileWriter(new File(baseDir, "tmp.metis")));
		out.write(new2OldNodeMap.size()+" "+(numEdges/2)+"\n");
		System.err.println("Number of Edges: "+numEdges);
		System.err.println("new2OldNodeMap: "+new2OldNodeMap.size());
		System.err.println("count: "+count);
		for(int id=1;id<=new2OldNodeMap.size();id++)
		{
			ArrayList<Integer> list = new ArrayList<Integer>();
			for(int k:nbrs.get(new2OldNodeMap.get(id)))
				list.add(old2NewNodeMap.get(k));
			for(int l:list)
					out.write(" "+l);
			out.write("\n");
		}
		out.close();
		//run kmetis
//		new File(baseDir, "tmp.metis.part."+kMetisClusters).delete();
//		try{
//			Runtime rt2 = Runtime.getRuntime();
//			Process pr2 =null;
//			String processString[]=new String[3];
//			processString[0]="sh";
//			processString[1]="-c";
//			processString[2]=baseDir+"kmetis tmp.metis "+kMetisClusters;
//			pr2 = rt2.exec(processString);
//		    BufferedReader input2 = new BufferedReader(new InputStreamReader(pr2.getInputStream()));
//		    String line2="";
//		    while((line2=input2.readLine()) != null) 
//		    	System.err.println(line2);
//		    pr2.waitFor();
//		    input2.close();
//		}
//		catch(Exception e)
//		{
//			System.err.println("Could not perform kmetis");
//			e.printStackTrace();
//		}
	    //read the kmetis results
	    if(new File(baseDir, "tmp.metis.part."+kMetisClusters).exists())
	    {
		    BufferedReader in3 = new BufferedReader(new FileReader(new File(baseDir, "tmp.metis.part."+kMetisClusters)));
		    String str3="";
		    int labels3[]= new int[new2OldNodeMap.size()];
		    int f=0;
		    while((str3=in3.readLine())!=null)
		    {
		    	labels3[f]=Integer.parseInt(str3);
    	        f++;
		    }
		    in3.close();
		    HashMap<Integer, Double[]> clusterResults = new HashMap<Integer, Double[]>();
//			//do soft clustering based on kmetis results
		    for(int y=1;y<=new2OldNodeMap.size();y++)
		    {
		    	if(y%10000==0)
		    		System.out.println("Processed: "+y);
		    	int o=new2OldNodeMap.get(y);
		    	if(origIDs.contains(o))
		    	{
			    	Double d[] = new Double[kMetisClusters];
			    	for(int k=0;k<kMetisClusters;k++)
			    		d[k]=0.;
			    	d[labels3[y-1]]++;
		    		HashSet<Integer> nbrSet = nbrs.get(o);
		    		for(int n:nbrSet)
		    		{
		    			if(!origIDs.contains(n))
		    			{
		    				int newID=old2NewNodeMap.get(n);
		    				d[labels3[newID]]++;
		    			}
		    		}
		    		double sum=0;
		    		for(int k=0;k<kMetisClusters;k++)
		    			sum+=d[k];
		    		for(int k=0;k<kMetisClusters;k++)
	    				d[k]/=sum;
		    		clusterResults.put(o, d);
		    	}
		    }
		    //write out soft clustering results
		    out = new BufferedWriter(new FileWriter(new File(baseDir, "clusteringResults.txt")));
		    for(int k:clusterResults.keySet())
		    {
		    	out.write(k+"\t");
		    	for(int k2=0;k2<kMetisClusters;k2++)
		    		out.write(clusterResults.get(k)[k2]+"\t");
		    	out.write("\n");
		    }
		    out.close();
		}

	}
}

