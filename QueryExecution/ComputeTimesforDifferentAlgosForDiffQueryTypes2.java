package QueryExecution;

import java.io.*;
import java.util.*;

/**
 * To draw Table 4 in the paper.
 * Running Time (msec) Split between Candidate Filtering (CFT) and Top-K Execution (TET) for graph G2
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class ComputeTimesforDifferentAlgosForDiffQueryTypes2 {
	public static String baseDir = "/home/gupta58/2012/VLDB12/data/synthetic/results/";

	public static void main(String[] args) throws Throwable {
		HashMap<String, Double> candidateGen = new HashMap<String, Double>();
		HashMap<String, Double> overall = new HashMap<String, Double>();
		HashMap<String, Double> freq = new HashMap<String, Double>();
		String types[]={"Clique","Path","Subgraph"};
		String algos[]={"RWM3"};
		int maxSize=5;
		for(int size=2;size<=maxSize;size++)
		{
			for(String s:types)
			{
				for(String a:algos)
				{
					candidateGen.put(a+s+size, 0.);
					overall.put(a+s+size, 0.);
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
				BufferedReader in =  new BufferedReader(new FileReader(new File(dir, "QBSQueryExecutorV2.topK=10_K0=2_GT_10000_100000_"+q)));
				while((str=in.readLine())!=null)
				{
					if(str.startsWith("Overall Time:"))
					{
						overall.put("RWM3"+type+size, overall.get("RWM3"+type+size)+Integer.parseInt(str.split("\\s+")[2]));
						freq.put("RWM3"+type+size, freq.get("RWM3"+type+size)+1);
					}
					if(str.contains("Candidate"))
					{
						candidateGen.put("RWM3"+type+size, candidateGen.get("RWM3"+type+size)+Integer.parseInt(str.split("\\s+")[3]));
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
					System.out.print(candidateGen.get(a+s+size)/(freq.get(a+s+size))+"\t"+(overall.get(a+s+size)/(freq.get(a+s+size))-candidateGen.get(a+s+size)/(freq.get(a+s+size)))+"\t");
				}
				System.out.println();
			}
			System.out.println();
			System.out.println();
			System.out.println();
		}
	}
}

