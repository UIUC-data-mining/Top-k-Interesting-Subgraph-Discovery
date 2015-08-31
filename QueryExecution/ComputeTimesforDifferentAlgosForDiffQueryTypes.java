package QueryExecution;

import java.io.*;
import java.util.*;

/**
 * To draw Table 1, 2 and 3 in the paper.
 * Query Execution Time (msec) for Path/Clique/Subgraph Queries (Graph G2 and indexes with D=3)
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class ComputeTimesforDifferentAlgosForDiffQueryTypes {
	public static String baseDir = "/home/gupta58/2012/VLDB12/data/synthetic/results/";

	public static void main(String[] args) throws Throwable {
		HashMap<String, Double> stats = new HashMap<String, Double>();
		HashMap<String, Double> freq = new HashMap<String, Double>();
		String types[]={"Clique","Path","Subgraph"};
		String algos[]={"RAM","RWM0","RWM1","RWM2","RWM3"};
//		String algos[]={"SPath","QBS0","QBS1","QBS2","QBSv2"};
		int maxSize=5;
		for(int size=2;size<=maxSize;size++)
		{
			for(String s:types)
			{
				for(String a:algos)
				{
					stats.put(a+s+size, 0.);
					freq.put(a+s+size, 0.);
				}
			}
		}
		
		ArrayList<String> queries = new ArrayList<String>();
		BufferedReader br = new  BufferedReader(new FileReader(new File(baseDir, "selectedQueryGraphs.txt")));
		String str="";
		while((str=br.readLine())!=null)
		{
			str=str.split("/")[1];
			queries.add(str);
		}
		br.close();
		for(int repeat=1;repeat<=10;repeat++)
		{
			for(String q:queries)
			{
				try{
				String type=q.split("\\.")[1];
				int size=Integer.parseInt(q.split("\\.")[3]);
				String dir=baseDir+"/comparison/"+repeat+"/";
				BufferedReader in = new BufferedReader(new FileReader(new File(dir, "SPath.topK=10_K0=2_GT_10000_100000_"+q)));
				while((str=in.readLine())!=null)
				{
					if(str.startsWith("Time:"))
					{
						stats.put("RAM"+type+size, stats.get("RAM"+type+size)+Integer.parseInt(str.split("\\s+")[1]));
						freq.put("RAM"+type+size, freq.get("RAM"+type+size)+1);
					}
				}
				in.close();
				in =  new BufferedReader(new FileReader(new File(dir, "QBSQueryExecutor.topKOff=0_topK=10_K0=2_GT_10000_100000_"+q)));
				while((str=in.readLine())!=null)
				{
					if(str.startsWith("Overall Time:"))
					{
						stats.put("RWM0"+type+size, stats.get("RWM0"+type+size)+Integer.parseInt(str.split("\\s+")[2]));
						freq.put("RWM0"+type+size, freq.get("RWM0"+type+size)+1);
					}
				}
				in.close();
				
				in =  new BufferedReader(new FileReader(new File(dir, "QBSQueryExecutor.topKOff=1_topK=10_K0=2_GT_10000_100000_"+q)));
				while((str=in.readLine())!=null)
				{
					if(str.startsWith("Overall Time:"))
					{
						stats.put("RWM1"+type+size, stats.get("RWM1"+type+size)+Integer.parseInt(str.split("\\s+")[2]));
						freq.put("RWM1"+type+size, freq.get("RWM1"+type+size)+1);
					}
				}
				in.close();
				
				in =  new BufferedReader(new FileReader(new File(dir, "QBSQueryExecutor.topKOff=2_topK=10_K0=2_GT_10000_100000_"+q)));
				while((str=in.readLine())!=null)
				{
					if(str.startsWith("Overall Time:"))
					{
						stats.put("RWM2"+type+size, stats.get("RWM2"+type+size)+Integer.parseInt(str.split("\\s+")[2]));
						freq.put("RWM2"+type+size, freq.get("RWM2"+type+size)+1);
					}
				}
				in.close();
				
				in =  new BufferedReader(new FileReader(new File(dir, "QBSQueryExecutorV2.topK=10_K0=2_GT_10000_100000_"+q)));
				while((str=in.readLine())!=null)
				{
					if(str.startsWith("Overall Time:"))
					{
						stats.put("RWM3"+type+size, stats.get("RWM3"+type+size)+Integer.parseInt(str.split("\\s+")[2]));
						freq.put("RWM3"+type+size, freq.get("RWM3"+type+size)+1);
					}
				}
				in.close();

				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		//write out stats
		for(String s:types)
		{
			System.out.println(s);
			for(String a:algos)
			{
				System.out.print(a+"\t");
				for(int size=2;size<=maxSize;size++)
				{
					System.out.print(stats.get(a+s+size)/(freq.get(a+s+size))+"\t"+freq.get(a+s+size)+"\t");
				}
				System.out.println();
			}
			System.out.println();
			System.out.println();
			System.out.println();
		}
	}
}

