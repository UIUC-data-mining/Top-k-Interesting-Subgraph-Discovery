package dataGen.wikipedia;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.ClusteringUtils;

/**
 * Guesses type given an array of values.
 * Also performs clustering of arrays of multiple types like NUMERIC, DURATION, SETOFSTRINGS, CATEGORY, URL
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class Clustering {
	public static String baseDir = GetInfoboxes.baseDir;
	public static String type="";//NUMERIC, DURATION, SETOFSTRINGS, CATEGORY, URL
	public static ArrayList<String[]> data = new ArrayList<String[]>();
	public static Pattern numberPattern = Pattern.compile("[+-]*([0-9]+\\.*)+");
	public static Pattern fourNosYearPattern = Pattern.compile("[^0-9][1-2][0-9][0-9][0-9][^0-9]");
	public static Pattern twoNosYearPattern = Pattern.compile("[^0-9][0-9][0-9][^0-9]");
	public static double tests=100;
	public static double typeSupporters=0;
	public static String sample="";
	public static int numClusters=10;
	public static void main(String[] args) throws Throwable {
		if(System.getProperty("os.name").contains("Windows"))
			baseDir = GetInfoboxes.windowsBaseDir;
		else
			baseDir = GetInfoboxes.linuxBaseDir;
		System.setOut(new PrintStream(new File(baseDir, "debug.txt")));
		BufferedReader in = new BufferedReader(new FileReader(new File(baseDir, "person.sql")));
		String str = "";
		while ((str = in.readLine()) != null) {
			if(!str.contains("insert"))
				continue;
			str=str.substring(str.indexOf('(')+1).replaceAll("'\\);"," ");
			int instance=Integer.parseInt(str.split(",")[0]);
			String tokens[]=str.split("','");
			tokens[0]=tokens[0].split("'")[1];
			String toks[]=new String[tokens.length+1];
			toks[0]=instance+"";
			for(int i=0;i<tokens.length;i++)
			{
				String tmp=tokens[i].replaceAll("#@#and", "#@#").replaceAll("and#@#", "#@#");
				String toks2[]=tmp.split("#@#");
				tmp="";
				for(String t:toks2)
					if(!t.trim().equals(""))
						tmp+=t.trim()+"#@#";
				if(!tmp.equals(""))
					tmp=tmp.substring(0,tmp.length()-3);
				toks[i+1]=tmp;
			}
			data.add(toks);
		}
		in.close();
		int dim=data.get(0).length;
		for(int i=0;i<dim;i++)
//		for(int i=9;i<10;i++)
		{
			ArrayList<String> arr = new ArrayList<String>();
			for(int j=0;j<data.size();j++)
				arr.add(data.get(j)[i]);
			sample="";
			guessType(arr);
			System.out.println(i+"\t"+type+"\t"+typeSupporters+"\t"+sample);
			String[] labels=cluster(arr);
			System.out.println("Labels:\t");
			for(int j=0;j<100;j++)
				System.out.print(labels[j]+"\t");
			System.out.println();
		}
	}
	/**
	 * @param arr
	 * @throws Throwable 
	 */
	public static String[] cluster(ArrayList<String> arr) throws Throwable {
		String[] labels=new String[arr.size()];
		int[] labels2=null;
		HashMap<Integer, Integer> orig2New =null;
		if(type.equals("DURATION"))
		{
			//clean up the durations vector.
			for(int i=0;i<arr.size();i++)
			{
//				System.out.print(arr.get(i));
				arr.set(i, cleanDuration(arr.get(i)));
//				System.out.println("###"+arr.get(i)+"###");
			}
			orig2New= new HashMap<Integer, Integer>();
			ArrayList<String> data = new ArrayList<String>();
			for(int i=0;i<arr.size();i++)
			{
				if(!arr.get(i).equals(""))
				{
					orig2New.put(i, data.size());
					data.add(arr.get(i));
				}
			}
			labels2=KMeansForDurations(data, numClusters);
			if(orig2New!=null)
			{
				for(int i=0;i<labels.length;i++)
				{
					if(orig2New.get(i)!=null)
						labels[i]=labels2[orig2New.get(i)]+":1.0";
					else
						labels[i]=-1+":1.0";
				}
			}
		}
		if(type.equals("NUMERIC"))
		{
			orig2New= new HashMap<Integer, Integer>();
			ArrayList<Double[]> data = new ArrayList<Double[]>();
			for(int i=0;i<arr.size();i++)
			{
				try
				{
					Double a[] = new Double[1];
					a[0]=Double.parseDouble(arr.get(i));
					orig2New.put(i, data.size());
					data.add(a);
				}
				catch(Exception e)
				{
					orig2New.put(i, -1);
					if(!arr.get(i).equals(""))
					System.err.println("[EXPECTED NUMBER BUT GOT]: "+arr.get(i));
				}
			}
			labels2=ClusteringUtils.KMeans(data, numClusters, GetInfoboxes.wekaDir);
			if(orig2New!=null)
			{
				for(int i=0;i<labels.length;i++)
				{
					if(orig2New.get(i)!=-1)
						labels[i]=labels2[orig2New.get(i)]+":1.0";
					else
						labels[i]=-1+":1.0";
				}
			}
		}
		if(type.equals("CATEGORY")||type.equals("URL"))
		{
			HashMap<String, Integer> cats = new HashMap<String, Integer>();
			for(int i=0;i<arr.size();i++)
			{
				if(!cats.containsKey(arr.get(i))&& !arr.get(i).equals(""))
					cats.put(arr.get(i), cats.size());
			}
//			System.out.println("#Categories: "+cats.size());
			for(int i=0;i<arr.size();i++)
			{
				if(cats.containsKey(arr.get(i)))
					labels[i]=cats.get(arr.get(i))+":1.0";
				else
					labels[i]=-1+":1.0";
			}
		}
		if(type.equals("SETOFSTRINGS"))
		{
			//create a network
			HashMap<String, Integer> nodeName2Id = new HashMap<String, Integer>();
			HashMap<Integer, String> nodeId2Name = new HashMap<Integer, String>();
			HashMap<Integer, HashSet<Integer>> nbrs = new HashMap<Integer, HashSet<Integer>>();
			int numEdges=0;
			for(int i=0;i<arr.size();i++)
			{
				String toks[]=arr.get(i).split("#@#");
				HashSet<Integer> set = new HashSet<Integer>();
				for(String t:toks)
				{
					if(!t.trim().equals(""))
					{
						if(!nodeName2Id.containsKey(t))
						{
							int size=nodeName2Id.size();
							nodeName2Id.put(t,size);
							nodeId2Name.put(size, t);
						}
						set.add(nodeName2Id.get(t));
					}
				}
				for(int a:set)
				{
					for(int b:set)
					{
						if(a!=b)
						{
							HashSet<Integer> s = new HashSet<Integer>();
							if(nbrs.containsKey(a))
								s=nbrs.get(a);
							s.add(b);
							nbrs.put(a,s);
							s = new HashSet<Integer>();
							if(nbrs.containsKey(b))
								s=nbrs.get(b);
							s.add(a);
							nbrs.put(b,s);
						}
					}
				}
			}
			for(int i:nbrs.keySet())
				numEdges+=nbrs.get(i).size();
			//convert network to kmetis form
			HashMap<Integer, Integer> old2NewNodeMap = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> new2OldNodeMap = new HashMap<Integer, Integer>();
			ArrayList<HashSet<Integer>> clusterList = new ArrayList<HashSet<Integer>>();
			int count=0;
			for(int i=0;i<nodeName2Id.size();i++)
			{
				if(!nbrs.containsKey(i))
				{
					HashSet<Integer> set1 = new HashSet<Integer>();
					set1.add(i);
					clusterList.add(set1);
				}
				else
				{
					count++;
					old2NewNodeMap.put(i, count);
					new2OldNodeMap.put(count, i);
				}
			}
			
			BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, "tmp.metis")));
			out.write(new2OldNodeMap.size()+" "+(numEdges/2)+"\n");
			
			for(int i=0;i<nodeName2Id.size();i++)
			{
				if(nbrs.containsKey(i))
				{
					for(int k:nbrs.get(i))
						out.write(old2NewNodeMap.get(k)+" ");
					out.write("\n");
				}
			}
			out.close();
			//run kmetis
			new File(baseDir, "tmp.metis.part.10").delete();
			try{
			Runtime rt2 = Runtime.getRuntime();
			Process pr2 =null;
			String processString[]=new String[3];
			processString[0]="sh";
			processString[1]="-c";
			processString[2]=baseDir+"kmetis tmp.metis 10";
			pr2 = rt2.exec(processString);
		    BufferedReader input2 = new BufferedReader(new InputStreamReader(pr2.getErrorStream()));
		    String line2="";
		    while((line2=input2.readLine()) != null) 
		    	System.err.println(line2);
		    pr2.waitFor();
		    input2.close();
			}
			catch(Exception e)
			{
				System.err.println("Could not perform kmetis");
				e.printStackTrace();
			}
		    //read the kmetis results
		    if(new File(baseDir, "tmp.metis.part.10").exists())
		    {
			    BufferedReader in3 = new BufferedReader(new FileReader(new File(baseDir, "tmp.metis.part.10")));
			    String str3="";
			    int labels3[]= new int[new2OldNodeMap.size()];
			    int f=0;
			    while((str3=in3.readLine())!=null)
			    {
			    	labels3[f]=Integer.parseInt(str3);
	    	        f++;
			    }
			    in3.close();
				//do soft clustering based on kmetis results
			    HashMap<Integer, HashSet<Integer>> tmp = new HashMap<Integer, HashSet<Integer>>();
			    for(int i=1;i<labels3.length+1;i++)
			    {
			    	HashSet<Integer> tmp1 = new HashSet<Integer>();
			    	if(tmp.containsKey(labels3[i-1]))
			    		tmp1=tmp.get(labels3[i-1]);
			    	tmp1.add(new2OldNodeMap.get(i));
			    	if(new2OldNodeMap.get(i)==null)
			    		System.out.println();
			    	tmp.put(labels3[i-1], tmp1);
			    }
			    for(int i:tmp.keySet())
			    	clusterList.add(tmp.get(i));
			    HashMap<Integer, Integer> node2ClusterLabel = new HashMap<Integer, Integer>();
			    for(int i=0;i<clusterList.size();i++)
			    {
			    	HashSet<Integer> s = clusterList.get(i);
			    	for(int p:s)
				    	node2ClusterLabel.put(p, i);
			    }
			    for(int i=0;i<arr.size();i++)
			    	labels[i]="";
			    for(int i=0;i<arr.size();i++)
			    {
			    	HashMap<Integer, Integer> iMap = new HashMap<Integer, Integer>();
			    	String toks[]=arr.get(i).split("#@#");
			    	double size=toks.length;
					for(String t:toks)
					{
						if(!t.trim().equals(""))
						{
							int id=nodeName2Id.get(t);
							int clus=node2ClusterLabel.get(id);
							if(iMap.containsKey(clus))
								iMap.put(clus, iMap.get(clus)+1);
							else
								iMap.put(clus, 1);
						}
					}
					for(int j:iMap.keySet())
						labels[i]+=(j)+":"+(iMap.get(j)/size)+";";
					if(labels[i].equals(""))
						labels[i]="-1:0.0";
					else
						labels[i]=labels[i].substring(0, labels[i].length()-1);
			    }
			}
			else
			{
			    for(int i=0;i<arr.size();i++)
			    	labels[i]="-1:1.0";
			}
		}
		return labels;
	}
	
	private static int[] KMeansForDurations(ArrayList<String> arr, int K) throws Throwable {
		int currLabels[] = new int[arr.size()];
		ArrayList<Double[]> data = new ArrayList<Double[]>();
		for(int i=0;i<arr.size();i++)
		{
			Double a[] = new Double[1];
			int y1=Integer.parseInt(arr.get(i).split("-")[0]);
			int y2=Integer.parseInt(arr.get(i).split("-")[1]);
			a[0]=(y1+y2)/2.0;
			data.add(a);
		}
		System.err.println(data.size()+" "+GetInfoboxes.wekaDir);
		currLabels=ClusteringUtils.KMeans(data, numClusters, GetInfoboxes.wekaDir);
		return currLabels;
	}
	
	/**
	 * @param arr
	 * @param K
	 * @return
	 */
//	private static int[] KMeansForDurationsBasedOnOverLap(ArrayList<String> arr, int K) {
//		int rows = arr.size();
//		int maxIterations=100;
//		int currLabels[] = new int[rows];
//		String clusterCentroids[]= new String[K];
//		int changed=1;
//		//randomly assign points to clusters.
//		int pointsPerCluster=rows/K;
//		int cluster=-1;
//		for(int i=0;i<rows;i++)
//		{
//			if(i%pointsPerCluster==0)
//				cluster++;
//			if(cluster>=K)
//				cluster--;
//			currLabels[i]=cluster;
//		}
//		HashMap<Integer, Integer> pointsPerClusterMap = new HashMap<Integer, Integer>();
//		int iter=0;
//		while(changed!=0 &&iter<maxIterations)
//		{
//			iter++;
////			System.out.println("Iter: "+iter+" Points changed clusters: "+changed);
//			changed=0;
//			for(int i=0;i<K;i++)
//				clusterCentroids[i]="";
//			for(int i=0;i<K;i++)
//				pointsPerClusterMap.put(i,0);
//			//compute current cluster centroids
//			for(int i=0;i<rows;i++)
//			{
//				cluster=currLabels[i];
//				String dur=arr.get(i);
//				int y1=Integer.parseInt(dur.split("-")[0]);
//				int y2=Integer.parseInt(dur.split("-")[1]);
////				double centerOfDur=(y2-y1)/2.;
//				if(clusterCentroids[cluster].equals(""))
//				{
//					clusterCentroids[cluster]=y1+"-"+y2;
//				}
//				else
//				{
//					int y1cc=Integer.parseInt(clusterCentroids[cluster].split("-")[0]);
//					int y2cc=Integer.parseInt(clusterCentroids[cluster].split("-")[1]);
//					int newY1=y1cc;
//					int newY2=y2cc;
//					if(y1<y1cc)
//						newY1=y1;
//					if(y2>y2cc)
//						newY2=y2;
//					if(newY2<newY1)
//						System.out.println();
//					clusterCentroids[cluster]=newY1+"-"+newY2;
//				}
//				pointsPerClusterMap.put(cluster, pointsPerClusterMap.get(cluster)+1);
//			}
//			for(int i=0;i<K;i++)
//				System.out.println("Iter: "+iter+" "+clusterCentroids[i]);
//			//recompute distance of one point from all cluster centroids and assign it to nearest cluster, maintain "changed"
//			for(int i=0;i<rows;i++)
//			{
//				double min=Double.MAX_VALUE;
//				int mini=0;
//				for(int j=0;j<K;j++)
//				{
//					double dist= 1-getDurSim(arr.get(i), clusterCentroids[j]);
//					if(dist<min)
//					{
//						min=dist;
//						mini=j;
//					}
//				}
//				if(currLabels[i]!=mini)
//					changed++;
//				currLabels[i]=mini;
//			}
//		}
//		return currLabels;
//	}
//	private static double getDurSim(String d1, String d2)
//	{
//		int year11=Integer.parseInt(d1.split("-")[0]);
//		int year12=Integer.parseInt(d1.split("-")[1]);
//		int year21=Integer.parseInt(d2.split("-")[0]);
//		int year22=Integer.parseInt(d2.split("-")[1]);
//		double dur1=year12-year11;
//		double dur2=year22-year21;
//		if(year11>year21)
//		{
//			//swap the durations
//			int tmp=year21;
//			year21=year11;
//			year11=tmp;
//			tmp=year22;
//			year22=year12;
//			year12=tmp;
//		}
//		if(year12<year21)//no overlap
//			return 0;
//		if(year12>year21)//overlap
//		{
//			if(year12>year22)//containment
//				return dur2/(dur1+dur2);
//			else //no containment
//				return ((double)(year12-year21))/(year22-year11);
//		}
//		return -1;
//	}
	
	/**
	 * @param string
	 * @return
	 */
	private static String cleanDuration(String value) {
		String tokens[]=value.split("#@#");
		if(tokens.length>1)
		{
			//if tokens[0] endswith [0-9a-z] and tokens[1] starts with [0-9a-z], then keep only tokens[0], else replace #@# by ""
			if(tokens[0].substring(tokens[0].length()-1).matches("[0-9a-z]")&&tokens[1].substring(0,1).matches("[0-9a-z]"))
				value=tokens[0];
			else
				value=value.replaceAll("#@#", "");
		}
		value=" "+value+" ";
		String v2="";
		if(value.trim().equals(""))
			return "";
		String remaining=value;
		String orig=value;
		Matcher m = fourNosYearPattern.matcher(value);
		int year1=0;
		if (m.find())
		{
			String match=value.substring(m.start(), m.end());
			year1=Integer.parseInt(match.substring(1,5));
			v2+= match.substring(1,5)+"-";
			remaining=remaining.replaceAll(match, "      ");
		}
		m = fourNosYearPattern.matcher(orig);
		value=remaining;
		Matcher m1 = twoNosYearPattern.matcher(value);
		int year2=0;
		while (m1.find())
		{
			String match=value.substring(m1.start(), m1.end());
			year2=Integer.parseInt(match.substring(1,3));
			if(m.find()&& m.start()>m1.start())
				year2=0;
			else
				break;
		}
		Matcher m2 = fourNosYearPattern.matcher(value);
		int year3=0;
		if (m2.find())
		{
			String match=value.substring(m2.start(), m2.end());
			year3=Integer.parseInt(match.substring(1,5));
		}
		if(year2==0&&year3==0)
		{
			if((v2+2012).length()==9)
				return v2+2012;
			else
				return "";
		}
		if(year2==0&&year3!=0)
		{
			if((v2+year3).length()==9)
				return v2+year3;
			else
				return "";
		}
		if(year2!=0&&year3!=0)
		{
			if(m1.start()>m2.start())
			{
				if((v2+year3).length()==9)
					return v2+year3;
				else
					return "";
			}
			else 
			{
				int century=year1/100;
				int twoDigitYear1=year1%100;
				if(twoDigitYear1<year2)
					year2=century*100+year2;
				if(twoDigitYear1>year2)
					year2=(century+1)*100+year2;
				if((v2+year2).length()==9)
					return v2+year2;
				else
					return "";
			}
		}
		if(year2!=0&&year3==0)
		{
			int century=year1/100;
			int twoDigitYear1=year1%100;
			if(twoDigitYear1<year2)
				year2=century*100+year2;
			if(twoDigitYear1>year2)
				year2=(century+1)*100+year2;
			if((v2+year2).length()==9)
				return v2+year2;
			else
				return "";
		}
		return "";
	}
	public static void guessType(ArrayList<String> arr)
	{
		int numNumeric=0;
		int numSetString=0;
		int numString=0;
		int numUrls=0;
		int numDurations=0;
		int c=-1;
		int i=0;
		for(i=0;i<tests;i++)
		{
			c++;
			if(c>=arr.size())
				break;
			if(arr.get(c).trim().equals(""))
			{
				i--;
				continue;
			}
			sample+=arr.get(c)+"\t";
			String value=arr.get(c);
			Matcher m = numberPattern.matcher(value);
			if (m.find())
				value = value.substring(m.start(), m.end());
			else
				value = "";
			m = numberPattern.matcher(arr.get(c));
			int c2=0;
			while(m.find())
				c2++;
			if(arr.get(c).contains("http"))
				numUrls++;
			else
			{
				if(value.length()==arr.get(c).length())
					numNumeric++;
				else
				{
					value=arr.get(c);
					if((value.contains("present")||value.contains("-")||value.contains("–"))&&(c2>=1))//contains at least one year or contains "present"
					{
						numDurations++;
					}
					else
					{
						if(arr.get(c).contains("#@#"))
							numSetString++;
						else
							numString++;
					}
				}
			}
			System.out.println("GuessTypes: "+value+"#"+numDurations+"#"+numSetString+"#"+numString+"#"+numNumeric+"#");
		}
		if(numUrls>0.5*i)
		{
			type="URL";
			typeSupporters=numUrls;
		}
		else
		{
			if(numNumeric>0.5*i)
			{
				type="NUMERIC";
				typeSupporters=numNumeric;
			}
			else 
			{
				if(numDurations>0.5*i)
				{
					type="DURATION";
					typeSupporters=numDurations;
				}
				else
				{
					if(numSetString>0.02*i)
					{
						type="SETOFSTRINGS";
						typeSupporters=numSetString;
					}
					else
					{
						type="CATEGORY";
						typeSupporters=numString;
					}
				}
			}
		}
	}
}

