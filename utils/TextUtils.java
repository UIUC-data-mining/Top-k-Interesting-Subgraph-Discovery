package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;

public class TextUtils {
	public static HashMap<String, String> entityNos2Char=null;
	public static HashMap<String, String> entityNames2Char=null;
	/**
	 * Given an ARFF file file1, one can use the command similar to the one below to generate a file2.
	 * This function does the matching of the instances and gets the vector of predictions of instances in the correct order as in file1.
	 * java -Xmx5g -cp ~/jars/weka.jar weka.classifiers.functions.SMO -t  D2010/0to20/Pop3_RT_RTToPopRatio_PopToRTRatio_PopToPopRatio_RTToRTRatio.arff  -x 10 -i -k -p 1-214 > D2010/0to20/results/Pop3_RT_RTToPopRatio_PopToRTRatio_PopToPopRatio_RTToRTRatio.arff.details.SMO
	 * @param file1
	 * @param file2
	 * @return
	 * @throws Throwable
	 */
	public static String[] getPredictionsUsingWekaInputOutput(String file1, String file2) throws Throwable {
		int numInstances=0;
		HashMap<String, Integer> instance2Num = new HashMap<String, Integer>();
	    BufferedReader in = new BufferedReader(new FileReader(new File(file1)));
	    int inst=0;
	    int start=0;
	    String str="";
	    HashMap<Integer, HashSet<Integer>> collisionSets = new HashMap<Integer, HashSet<Integer>>();
	    while((str=in.readLine())!=null)
	    {
	    	if(start==1)
	    	{
	    		numInstances++;
	    		str=str.replaceAll("\\t",",");
	    		str=str.replace(".0,",",");//.replace(",DECGENTLE", "").replace(",DECSTEEP", "").replace(",FLAT", "").replace(",INCSTEEP", "").replace(",INCGENTLE", "");
	    		String tokens[]=str.split(",");
	    		String finalStr="";
	    		for(int i=0;i<tokens.length-1;i++)
	    		{
	    			if(tokens[i].indexOf(".")+7<tokens[i].length())
	    			{
	    				if(tokens[i].indexOf(".")+8==tokens[i].length()&&tokens[i].charAt(tokens[i].length()-1)=='5')
	    				{
	    					//weird rounding off issues with weka versus conventional double formatter class
	    					tokens[i]=tokens[i].substring(0,tokens[i].length()-1)+"6";
	    				}
	    				Double d = Double.parseDouble(tokens[i]);
	    				DecimalFormat df = new DecimalFormat("#.######");
	    				tokens[i]=df.format(d);
	    			}
	    		}
	    		for(int i=0;i<tokens.length-2;i++)
	    			finalStr+=tokens[i]+",";
	    		finalStr+=tokens[tokens.length-2];	
	    		if(instance2Num.containsKey(finalStr))
	    		{
	    			int oldinst=instance2Num.get(finalStr);
	    			HashSet<Integer> set = new HashSet<Integer>();
	    			if(collisionSets.containsKey(oldinst))
	    				set=collisionSets.get(oldinst);
	    			set.add(inst);
	    			collisionSets.put(oldinst, set);
	    		}
	    		else
	    			instance2Num.put(finalStr, inst);
	    		inst++;
	    	}
	    	if(start==0 && str.contains("@data"))
	    		start=1;
	    }
	    in.close();
		in = new BufferedReader(new FileReader(new File(file2)));
		String[] result = new String[numInstances];
		start=0;
		while((str=in.readLine())!=null)
		{
			if(start==1)
			{
				str=str.replace("+", "").trim();
				if(str.equals(""))
					continue;
				String tokens[]=str.split("\\s+");
				int instance=instance2Num.get(tokens[4].replace("(","").replace(")",""));
				String pred=tokens[2].split(":")[1];
				result[instance]=pred;
			}
			if(start==0 &&str.contains("inst#"))
			{
				start=1;
			}
		}
		in.close();
		for(Integer i: collisionSets.keySet())
		{
			HashSet<Integer> set = collisionSets.get(i);
			for(int j:set)
				result[j]=result[i];
		}
		return result;
	}
	/**
	 * Returns a string representation of a double rounded off up to the specified precision.
	 * @param d
	 * @param precision
	 * @return
	 */
	public static String reduceDoublePrecision(String s, int precision)
	{
		Double d = Double.parseDouble(s);
		String tmp="";
		for(int i=0;i<precision;i++)
			tmp+="#";
		DecimalFormat df = new DecimalFormat("."+tmp);
	    return ""+Double.valueOf(df.format(d));
	}
	/**
	 * Get the character representation for HTML entities in number form
	 * @return
	 */
	public static HashMap<String, String> nosHTMLEntities2Chars()
	{
		HashMap<String, String> entityNos2Char = new HashMap<String, String>();
		entityNos2Char.put("&#192;","a");
		entityNos2Char.put("&#193;","a");
		entityNos2Char.put("&#194;","a");
		entityNos2Char.put("&#195;","a");
		entityNos2Char.put("&#196;","a");
		entityNos2Char.put("&#197;","a");
		entityNos2Char.put("&#198;","ae");
		entityNos2Char.put("&#199;","c");
		entityNos2Char.put("&#200;","e");
		entityNos2Char.put("&#201;","e");
		entityNos2Char.put("&#202;","e");
		entityNos2Char.put("&#203;","e");
		entityNos2Char.put("&#204;","i");
		entityNos2Char.put("&#205;","i");
		entityNos2Char.put("&#206;","i");
		entityNos2Char.put("&#207;","i");
		entityNos2Char.put("&#208;","eth");
		entityNos2Char.put("&#209;","n");
		entityNos2Char.put("&#210;","o");
		entityNos2Char.put("&#211;","o");
		entityNos2Char.put("&#212;","o");
		entityNos2Char.put("&#213;","o");
		entityNos2Char.put("&#214;","o");
		entityNos2Char.put("&#216;","o");
		entityNos2Char.put("&#217;","u");
		entityNos2Char.put("&#218;","u");
		entityNos2Char.put("&#219;","u");
		entityNos2Char.put("&#220;","u");
		entityNos2Char.put("&#221;","y");
		entityNos2Char.put("&#222;","th");
		entityNos2Char.put("&#223;","s");
		entityNos2Char.put("&#224;","a");
		entityNos2Char.put("&#225;","a");
		entityNos2Char.put("&#226;","a");
		entityNos2Char.put("&#227;","a");
		entityNos2Char.put("&#228;","a");
		entityNos2Char.put("&#229;","a");
		entityNos2Char.put("&#230;","ae");
		entityNos2Char.put("&#231;","c");
		entityNos2Char.put("&#232;","e");
		entityNos2Char.put("&#233;","e");
		entityNos2Char.put("&#234;","e");
		entityNos2Char.put("&#235;","e");
		entityNos2Char.put("&#236;","i");
		entityNos2Char.put("&#237;","i");
		entityNos2Char.put("&#238;","i");
		entityNos2Char.put("&#239;","i");
		entityNos2Char.put("&#240;","eth");
		entityNos2Char.put("&#241;","n");
		entityNos2Char.put("&#242;","o");
		entityNos2Char.put("&#243;","o");
		entityNos2Char.put("&#244;","o");
		entityNos2Char.put("&#245;","o");
		entityNos2Char.put("&#246;","o");
		entityNos2Char.put("&#248;","o");
		entityNos2Char.put("&#249;","u");
		entityNos2Char.put("&#250;","u");
		entityNos2Char.put("&#251;","u");
		entityNos2Char.put("&#252;","u");
		entityNos2Char.put("&#253;","y");
		entityNos2Char.put("&#254;","th");
		entityNos2Char.put("&#255;","y");
		return entityNos2Char;
	}
	/**
	 * Get the character representation for HTML entities in name form
	 * @return
	 */
	public static HashMap<String, String> namesHTMLEntities2Chars()
	{
		HashMap<String, String> entityName2Char = new HashMap<String, String>();
		entityName2Char.put("&agrave;","a");
		entityName2Char.put("&aacute;","a");
		entityName2Char.put("&acirc;","a");
		entityName2Char.put("&atilde;","a");
		entityName2Char.put("&auml;","a");
		entityName2Char.put("&aring;","a");
		entityName2Char.put("&aelig;","ae");
		entityName2Char.put("&ccedil;","c");
		entityName2Char.put("&egrave;","e");
		entityName2Char.put("&eacute;","e");
		entityName2Char.put("&ecirc;","e");
		entityName2Char.put("&euml;","e");
		entityName2Char.put("&igrave;","i");
		entityName2Char.put("&iacute;","i");
		entityName2Char.put("&icirc;","i");
		entityName2Char.put("&iuml;","i");
		entityName2Char.put("&eth;","eth");
		entityName2Char.put("&ntilde;","n");
		entityName2Char.put("&ograve;","o");
		entityName2Char.put("&oacute;","o");
		entityName2Char.put("&ocirc;","o");
		entityName2Char.put("&otilde;","o");
		entityName2Char.put("&ouml;","o");
		entityName2Char.put("&oslash;","o");
		entityName2Char.put("&ugrave;","u");
		entityName2Char.put("&uacute;","u");
		entityName2Char.put("&ucirc;","u");
		entityName2Char.put("&uuml;","u");
		entityName2Char.put("&yacute;","y");
		entityName2Char.put("&thorn;","th");
		entityName2Char.put("&szlig;","s");
		entityName2Char.put("&agrave;","a");
		entityName2Char.put("&aacute;","a");
		entityName2Char.put("&acirc;","a");
		entityName2Char.put("&atilde;","a");
		entityName2Char.put("&auml;","a");
		entityName2Char.put("&aring;","a");
		entityName2Char.put("&aelig;","ae");
		entityName2Char.put("&ccedil;","c");
		entityName2Char.put("&egrave;","e");
		entityName2Char.put("&eacute;","e");
		entityName2Char.put("&ecirc;","e");
		entityName2Char.put("&euml;","e");
		entityName2Char.put("&igrave;","i");
		entityName2Char.put("&iacute;","i");
		entityName2Char.put("&icirc;","i");
		entityName2Char.put("&iuml;","i");
		entityName2Char.put("&eth;","eth");
		entityName2Char.put("&ntilde;","n");
		entityName2Char.put("&ograve;","o");
		entityName2Char.put("&oacute;","o");
		entityName2Char.put("&ocirc;","o");
		entityName2Char.put("&otilde;","o");
		entityName2Char.put("&ouml;","o");
		entityName2Char.put("&oslash;","o");
		entityName2Char.put("&ugrave;","u");
		entityName2Char.put("&uacute;","u");
		entityName2Char.put("&ucirc;","u");
		entityName2Char.put("&uuml;","u");
		entityName2Char.put("&yacute;","y");
		entityName2Char.put("&thorn;","th");
		entityName2Char.put("&yuml;","y");
		return entityName2Char;
	}
	/**
	 * Clean the string as follows: lowercase, remove all HTML entities, remove tabs, replace multi-character white space by single space and trim.
	 * @param s
	 * @return
	 */
	public static String cleanString(String s)
	{
		if(entityNos2Char==null)
			entityNos2Char = nosHTMLEntities2Chars();
		if(entityNames2Char==null)
			entityNames2Char = namesHTMLEntities2Chars();
		int index=s.indexOf("&");
		int index2=s.indexOf(";");
		while(index!=-1 && index2!=-1)
		{
			if(index2<index)
			{
				s=s.replaceFirst(";", " ");
				index2=s.indexOf(";");
				continue;
			}
			String entity=s.substring(index, index2+1);
			String translation="";
			if(entityNos2Char.containsKey(entity))
				translation=entityNos2Char.get(entity);
			if(entityNames2Char.containsKey(entity))
				translation=entityNames2Char.get(entity);
			String s1=s.substring(0, index)+translation+s.substring(index2+1);
			s=s1;
			index=s.indexOf("&");
			index2=s.indexOf(";");
		}
		s=s.replaceAll("[^a-z0-9 ]"," ").replaceAll("\\t", "").replaceAll("\\s+"," ").trim();
		return s;
	}
	
	public static void removeAuthorsWithFreqLessThanX(String inFile, String outFile, int x) throws Throwable
	{
		BufferedReader in = FileUtils.getFileReader(inFile);
		BufferedWriter out = FileUtils.getFileWriter(outFile);
		String str="";
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		while((str=in.readLine())!=null)
		{
			String tokens[]=str.split("\\t");
			tokens=tokens[3].split("#");
			for(String t:tokens)
			{
				if(map.containsKey(t))
					map.put(t, map.get(t)+1);
				else
					map.put(t,1);
			}
		}
		in.close();
		in = FileUtils.getFileReader(inFile);
		while((str=in.readLine())!=null)
		{
			String toks1[]=str.split("\\t");
			String auths="";
			String tokens[]=toks1[3].split("#");
			for(String t:tokens)
				if(map.get(t)>=x)
					auths+=t+"#";
			if(!auths.equals(""))
				out.write(toks1[0]+"\t"+toks1[1]+"\t"+toks1[2]+"\t"+auths+"\n");
		}
		in.close();
		out.close();
	}
	
	public static void convertDBLPText2NetClusFormat(String inFile) throws Throwable
	{
		BufferedReader in = FileUtils.getFileReader(inFile);
		int lastSlash = inFile.lastIndexOf(File.separator);
		String baseDir=inFile.substring(0, lastSlash)+File.separator;
		new File(baseDir+"netclus").mkdir();
		baseDir+="netclus"+File.separator;
		String str="";
		HashMap<String, Integer> globalTermFreq = new HashMap<String, Integer>();
		ArrayList<HashMap<String, Integer>> clusterWiseTermFreq = new ArrayList<HashMap<String,Integer>>();
		HashMap<Integer, HashSet<String>> clusterID2ConfSet = new HashMap<Integer, HashSet<String>>();
		
		BufferedReader clus = FileUtils.getFileReader("bin/utils/confClusters.txt");
		while((str=clus.readLine())!=null)
		{
			String tokens[]=str.split("\\t");
			int id = Integer.parseInt(tokens[0]);
			String conf=tokens[1].toLowerCase();
			HashSet<String> set = new HashSet<String>();
			if(clusterID2ConfSet.containsKey(id))
				set = clusterID2ConfSet.get(id);
			set.add(conf);
			clusterID2ConfSet.put(id, set);
		}
		clus.close();
		
		for(int i=0;i<clusterID2ConfSet.size();i++)
			clusterWiseTermFreq.add(new HashMap<String, Integer>());
		
		BufferedWriter out = FileUtils.getFileWriter(baseDir+"README");
		out.write("General description:\nThis is a bibliographic information network data set extracted from DBLP data. \n\nFile description:\nconf.txt: dictionary for conferences. Format: ID \t Conference name\nauthor.txt: dictionary for authors. Format: ID \t Author name\nterm.txt: dictionary for terms. Format: ID \t Term\npaper.txt: dictionary for papers. Format: ID \t paper title\n\npaper_conf.txt: relation file. Format: paperID \t confID\npaper_author.txt: relation file. Format: paperID \t authorID\npaper_term.txt: relation file. Format: paperID \t termID\npaper_year.txt: relation file. Format: paperID \t year\n\nprior_term.txt: Priors for the clusters in terms of term weights.");
		out.close();
		BufferedWriter paper = FileUtils.getFileWriter(baseDir+"paper.txt");
		BufferedWriter author = FileUtils.getFileWriter(baseDir+"author.txt");
		BufferedWriter term = FileUtils.getFileWriter(baseDir+"term.txt");
		BufferedWriter conf = FileUtils.getFileWriter(baseDir+"conf.txt");
		BufferedWriter paperAuthor = FileUtils.getFileWriter(baseDir+"paper_author.txt");
		BufferedWriter paperTerm = FileUtils.getFileWriter(baseDir+"paper_term.txt");
		BufferedWriter paperConf = FileUtils.getFileWriter(baseDir+"paper_conf.txt");
		BufferedWriter paperYear = FileUtils.getFileWriter(baseDir+"paper_year.txt");
		BufferedWriter priorTerm = FileUtils.getFileWriter(baseDir+"prior_term.txt");
		
		LinkedHashMap<String, Integer> paperName2IDMap = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> authorName2IDMap = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> confName2IDMap = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> termName2IDMap = new LinkedHashMap<String, Integer>();
		int paperCount=1;
		int authorCount=1;
		int confCount=1;
		int termCount=1;
		while((str=in.readLine())!=null)
		{
			String tokens[] =str.split("\\t");
			if(!paperName2IDMap.containsKey(tokens[0]))
			{
				paperName2IDMap.put(tokens[0], paperCount);
				paperYear.write(paperCount+"\t"+tokens[1]+"\n");
				paper.write(paperCount+"\t"+tokens[0]+"\n");
				paperCount++;
			}
			else
				continue;
			if(!confName2IDMap.containsKey(tokens[2]))
			{
				confName2IDMap.put(tokens[2], confCount);
				conf.write(confCount+"\t"+tokens[2]+"\n");
				confCount++;
			}
			paperConf.write((paperCount-1)+"\t"+confName2IDMap.get(tokens[2])+"\n");
			
			String titleTokens[]=tokens[0].split("\\s+");
			for(String t:titleTokens)
			{
				if(!termName2IDMap.containsKey(t))
				{
					termName2IDMap.put(t, termCount);
					term.write(termCount+"\t"+t+"\n");
					termCount++;
				}
				paperTerm.write((paperCount-1)+"\t"+termName2IDMap.get(t)+"\n");
				if(globalTermFreq.containsKey(t))
					globalTermFreq.put(t, globalTermFreq.get(t)+1);
				else
					globalTermFreq.put(t, 1);
				for(int i=1;i<=clusterID2ConfSet.size();i++)
				{
					if(clusterID2ConfSet.get(i).contains(tokens[2]))
					{
						if(clusterWiseTermFreq.get(i-1).containsKey(t))
							clusterWiseTermFreq.get(i-1).put(t, clusterWiseTermFreq.get(i-1).get(t)+1);
						else
							clusterWiseTermFreq.get(i-1).put(t, 1);
					}
				}
			}

			String authTokens[] =tokens[3].split("#");
			for(String a:authTokens)
			{
				if(!authorName2IDMap.containsKey(a))
				{
					authorName2IDMap.put(a, authorCount);
					author.write(authorCount+"\t"+a+"\n");
					authorCount++;
				}
				paperAuthor.write((paperCount-1)+"\t"+authorName2IDMap.get(a)+"\n");
			}
		}
		for(int i=0;i<clusterID2ConfSet.size();i++)
		{
			FibonacciHeap<String> heap = new FibonacciHeap<String>();
			HashMap<String, Integer> local = clusterWiseTermFreq.get(i);
			for(String s:local.keySet())
			{
				double score=local.get(s)*(local.get(s)/globalTermFreq.get(s)); //TF log IDF
				FibonacciHeapNode<String> node = new FibonacciHeapNode<String>(s, -score);
				heap.insert(node, node.getKey());
			}
			HashMap<String, Double> tmp = new HashMap<String, Double>();
			double sum=0;
			for(int j=0;j<100&&!heap.isEmpty();j++)
			{
				FibonacciHeapNode<String> node = heap.removeMin();
				tmp.put(node.getData(), -node.getKey());
				sum+=-node.getKey();
			}
			for(String s:tmp.keySet())
				priorTerm.write(i+" "+s+" "+(tmp.get(s)/sum)+"\n");
		}
		in.close();
		paper.close();
		author.close();
		term.close();
		conf.close();
		paperAuthor.close();
		paperConf.close();
		paperTerm.close();
		paperYear.close();
		priorTerm.close();
	}
	public static void cleanNetClusDir(String baseDir) throws Throwable
	{
		String args1[]={baseDir+File.separator};
		AssertNetClusDataCleanliness.main(args1);
		String str=baseDir+File.separator+"cleaned";
		String files[]={"paper.txt","author.txt","term.txt","conf.txt","paper_author.txt","paper_conf.txt","paper_term.txt","paper_year.txt"};
		for(String f:files)
		{
			new File(baseDir+File.separator+f).delete();
			new File(str+File.separator+f).renameTo(new File(baseDir+File.separator+f));
		}
		new File(str).delete();
	}
	public static void convertNetClusFormat2TabbedText(String baseDir) throws Throwable
	{
		
	}
	
	public static ArrayList<String> getDBLPSubset(String baseDir, int minYear, int maxYear) throws Throwable
	{
		ArrayList<String> tmp = new ArrayList<String>();
		if(!new File(baseDir, "dblp.txt").exists())
			convertDBLPXMLToTabbedText(baseDir);
		if(!new File(baseDir, "dblp.txt").exists())
		{
			System.err.println("Failed to create the "+baseDir+"/dblp.txt.");
			return null;
		}
		BufferedReader in = new BufferedReader(new FileReader(new File(baseDir, "dblp.txt")));
		String str="";
		while((str=in.readLine())!=null)
		{
			String tokens[]=str.split("\\t");
			int year = Integer.parseInt(tokens[1]);
			if(minYear<=year && maxYear>=year)
				tmp.add(str);
		}
		in.close();
		return tmp;
	}
	public static void getDBLPTemporalDataset(String baseDir, int minYear, int maxYear) throws Throwable
	{
		if(!new File(baseDir, "dblp.txt").exists())
			convertDBLPXMLToTabbedText(baseDir);
		if(!new File(baseDir, "dblp.txt").exists())
		{
			System.err.println("Failed to create the "+baseDir+"/dblp.txt.");
			return;
		}
		BufferedWriter out[]= new BufferedWriter[maxYear-minYear+1];
		for(int i=0;i<out.length;i++)
			out[i]= FileUtils.getFileWriter(baseDir+"/"+(minYear+i)+".txt");
		BufferedReader in = FileUtils.getFileReader(baseDir+"/dblp.txt");
		String str="";
		while((str=in.readLine())!=null)
		{
			String tokens[]=str.split("\\t");
			int year = Integer.parseInt(tokens[1]);
			if(minYear<=year && maxYear>=year)
				out[year-minYear].write(str+"\n");
		}
		in.close();
		for(int i=0;i<out.length;i++)
			out[i].close();
	}
	/**
	 * 
	 * @param baseDir
	 * @param type -- could be author or conf
	 * @param minYear
	 * @param maxYear
	 * @throws Throwable
	 */
//	public static void getDBLPTemporalDataset(String baseDir, String type, int minYear, int maxYear) throws Throwable
//	{
//		ArrayList<String> subset = getDBLPSubset(baseDir, minYear, maxYear);
//		HashMap<String, Integer> type2Freq = new HashMap<String, Integer>();
//		if(type.equals("conf"))
//		{
//			for(String s:subset)
//			{
//				String tokens[]=s.split("\\t");
//				if(type2Freq.containsKey(tokens[2]))
//					type2Freq.put(tokens[2], type2Freq.get(tokens[2])+1);
//				else
//					type2Freq.put(tokens[2], 1);
//			}
//		}
//		if(type.equals("author"))
//		{
//			for(String s:subset)
//			{
//				String tokens[]=s.split("\\t")[3].split("#");
//				for(String a:tokens)
//				{
//					if(type2Freq.containsKey(a))
//						type2Freq.put(a, type2Freq.get(a)+1);
//					else
//						type2Freq.put(a, 1);
//				}
//			}
//		}
//	}
	
	/**
	 * Generates the coactorship graph from IMDB text file.
	 * Note that such graphs can be very huge.
	 * E.g. Processing for years from 2003 to 2011 takes around 20GB of RAM.
	 * So, plan to put an appropriate parameter value for the minYear.
	 */
	public static void getCoActorshipGraphFromIMDB(String baseDir, int minYear) throws Throwable{
		TextUtils.convertIMDBListFilestoTabbedText(baseDir);
		HashMap<String, Integer> actor2ID= new HashMap<String, Integer>();
		BufferedReader in = FileUtils.getFileReader(baseDir+"/imdb.txt");
		String str="";
		int totlc=0;
		while((str=in.readLine())!=null)
		{
			totlc++;
			if(str.split("\\t").length<3)
				continue;
			if(str.split("\\t")[2].equals(""))
				continue;
			if(Integer.parseInt(str.split("\\t")[1])<minYear)
				continue;
			String actors[]= str.split("\\t")[2].split("#");
			for(String a:actors)
			{
				if(!actor2ID.containsKey(a)&&!a.equals(""))
					actor2ID.put(a, actor2ID.size());
			}
		}
		in.close();
		BufferedWriter out =FileUtils.getFileWriter(baseDir+"/actorIds.txt");
		for(String s:actor2ID.keySet())
			out.write(s+"\t"+actor2ID.get(s)+"\n");
		out.close();
		HashMap<String, Integer> authPair2Freq = new HashMap<String, Integer>();
		in = FileUtils.getFileReader(baseDir+"/imdb.txt");
		int lc=0;
		while((str=in.readLine())!=null)
		{
			lc++;
			if(lc%1000==0)
				System.out.println("Lines "+lc+" done out of "+totlc);
			if(str.split("\\t").length<3)
				continue;
			if(str.split("\\t")[2].equals(""))
				continue;
			if(Integer.parseInt(str.split("\\t")[1])<minYear)
				continue;
			String actors[]= str.split("\\t")[2].split("#");
			for(String a1:actors)
			{
				for(String a2:actors)
				{
					if(!a1.equals(a2)&&!a1.equals("")&&!a2.equals(""))
					{
						int id1=actor2ID.get(a1);
						int id2=actor2ID.get(a2);
						if(id1>id2)
						{
							int tmp=id1;
							id1=id2;
							id2=tmp;
						}
						String key=id1+"#"+id2;
						if(authPair2Freq.containsKey(key))
							authPair2Freq.put(key, authPair2Freq.get(key)+1);
						else
							authPair2Freq.put(key, 1);
					}
				}
			}
		}
		in.close();
		out =FileUtils.getFileWriter(baseDir+"/IMDBCoStarringGraph.txt");
		for(String s:authPair2Freq.keySet())
			out.write(s+"#"+authPair2Freq.get(s)+"\n");
		out.close();
	}

	/**
	 * Generates the coauthorship graph from DBLP text file.
	 * @param baseDir
	 * @throws Throwable
	 */
	public static void getCoAuthorshipGraphFromDBLPText(String baseDir) throws Throwable
	{
		TextUtils.convertDBLPXMLToTabbedText(baseDir);
		HashMap<String, Integer> author2ID= new HashMap<String, Integer>();
		BufferedReader in = FileUtils.getFileReader(baseDir+"/dblp.txt");
		String str="";
		while((str=in.readLine())!=null)
		{
			String authors[]= str.split("\\t")[3].split("#");
			for(String a:authors)
			{
				if(!author2ID.containsKey(a))
					author2ID.put(a, author2ID.size());
			}
		}
		in.close();
		BufferedWriter out =FileUtils.getFileWriter(baseDir+"/authorIds.txt");
		for(String s:author2ID.keySet())
			out.write(s+"\t"+author2ID.get(s)+"\n");
		out.close();
		HashMap<String, Integer> authPair2Freq = new HashMap<String, Integer>();
		in = FileUtils.getFileReader(baseDir+"/dblp.txt");
		while((str=in.readLine())!=null)
		{
			String authors[]= str.split("\\t")[3].split("#");
			for(String a1:authors)
			{
				for(String a2:authors)
				{
					if(!a1.equals(a2))
					{
						int id1=author2ID.get(a1);
						int id2=author2ID.get(a2);
						if(id1>id2)
						{
							int tmp=id1;
							id1=id2;
							id2=tmp;
						}
						String key=id1+"#"+id2;
						if(authPair2Freq.containsKey(key))
							authPair2Freq.put(key, authPair2Freq.get(key)+1);
						else
							authPair2Freq.put(key, 1);
					}
				}
			}
		}
		in.close();
		out =FileUtils.getFileWriter(baseDir+"/DBLPCoAuthorshipGraph.txt");
		for(String s:authPair2Freq.keySet())
			out.write(s+"#"+authPair2Freq.get(s)+"\n");
		out.close();
	}
	public static void convertIMDBListFilestoTabbedText(String baseDir) throws Throwable
	{
		if(!new File(baseDir, "actors.list").exists()||!new File(baseDir, "actresses.list").exists()||!new File(baseDir, "genres.list").exists())
		{
			System.err.println("List Files (either or all of actors.list, actresses.list, genres.list "+baseDir+" do not exist.");
			return;
		}
		if(new File(baseDir, "imdb.txt").exists())
		{
			System.err.println("File "+baseDir+"/imdb.txt already exists. Delete that file first.");
			return;
		}
		String year = "";
		HashMap<String, Integer> genres2IdsHashMap  =new HashMap<String, Integer>();
		HashMap<Integer,String> genreId2NameHashMap  =new HashMap<Integer, String>();
		HashMap<String, Integer> movies2IdsHashMap  =new HashMap<String, Integer>();
		HashMap<Integer, HashSet<Integer>> movieId2GenreIds = new HashMap<Integer, HashSet<Integer>>(); 
		BufferedReader in = new BufferedReader(new FileReader(new File(baseDir, "genres.list")));
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, "imdb.txt")));
		String str="";
		int start=0;
		Pattern pattern = Pattern.compile("\\(\\d{4}\\)");
		
		while((str=in.readLine())!=null)
		{
			if(start==1)
			{
				String tokens[]=str.split("\\t");
				String movie= tokens[0];
				Matcher matcher = pattern.matcher(movie);
				if(!matcher.find())continue;
				year=matcher.group();
				movie=movie.split("\\("+year+"\\)")[0];
				if(movie.length()==0) continue;
				movie=movie.trim()+"\t"+year.replace('(', ' ').replace(')',' ').trim();
				String genre = tokens[tokens.length-1];
				int movieId=movieId2GenreIds.size();
				if(movies2IdsHashMap.containsKey(movie))
					movieId=movies2IdsHashMap.get(movie);
				else
					movies2IdsHashMap.put(movie, movieId);
				int genreId = genres2IdsHashMap.size();
				if(genres2IdsHashMap.containsKey(genre))
					genreId= genres2IdsHashMap.get(genre);
				else
				{
					genres2IdsHashMap.put(genre, genreId);
					genreId2NameHashMap.put(genreId, genre);
				}
				HashSet<Integer> set = new HashSet<Integer>();
				if(movieId2GenreIds.containsKey(movieId))
					set = movieId2GenreIds.get(movieId);
				set.add(genreId);
				movieId2GenreIds.put(movieId, set);
			}
			if(start==0 && str.equals("8: THE GENRES LIST"))
			{
				in.readLine();in.readLine();
				start=1;
			}
		}
		in.close();
		String actor="";
		HashMap<String, HashSet<String>> movie2Actors= new HashMap<String, HashSet<String>>();
		start=0;
		in = new BufferedReader(new FileReader(new File(baseDir,"actors.list")));
		while((str=in.readLine())!=null)
		{
			if(start==1)
			{
				str=str.trim();
				if(str.equals(""))
					actor="";
				String tokens[]=str.split("\\t");
				if(tokens.length>1)
					actor=tokens[0];
				String movie=tokens[tokens.length-1];
				if(movie.contains("<"))
					movie=movie.substring(0, movie.indexOf('<')).trim();
				if(movie.contains("["))
					movie=movie.substring(0, movie.indexOf('[')).trim();
				Matcher matcher = pattern.matcher(movie);
				if(!matcher.find())continue;
				year=matcher.group();
				movie=movie.split("\\("+year+"\\)")[0];
				if(movie.length()==0) continue;
				movie=movie.trim()+"\t"+year.replace('(', ' ').replace(')',' ').trim();
				HashSet<String> set = new HashSet<String>();
				if(movie2Actors.containsKey(movie))
					set = movie2Actors.get(movie);
				set.add(actor);
				movie2Actors.put(movie, set);
			}
			if(start==0 && str.contains("THE ACTORS LIST"))
			{
				in.readLine();in.readLine();in.readLine();in.readLine();
				start=1;
			}
		}	
		in.close();
		start=0;
		in = new BufferedReader(new FileReader(new File(baseDir,"actresses.list")));
		while((str=in.readLine())!=null)
		{
			if(start==1)
			{
				str=str.trim();
				if(str.equals(""))
					actor="";
				String tokens[]=str.split("\\t");
				if(tokens.length>1)
					actor=tokens[0];
				String movie=tokens[tokens.length-1];
				if(movie.contains("<"))
					movie=movie.substring(0, movie.indexOf('<')).trim();
				if(movie.contains("["))
					movie=movie.substring(0, movie.indexOf('[')).trim();
				Matcher matcher = pattern.matcher(movie);
				if(!matcher.find())continue;
				year=matcher.group();
				movie=movie.split("\\("+year+"\\)")[0];
				if(movie.length()==0) continue;
				movie=movie.trim()+"\t"+year.replace('(', ' ').replace(')',' ').trim();
				HashSet<String> set = new HashSet<String>();
				if(movie2Actors.containsKey(movie))
					set = movie2Actors.get(movie);
				set.add(actor);
				movie2Actors.put(movie, set);
			}
			if(start==0 && str.contains("THE ACTRESSES LIST"))
			{
				in.readLine();in.readLine();in.readLine();in.readLine();
				start=1;
			}
		}	
		in.close();
		for(String m:movie2Actors.keySet())
		{
			if(m.equals(""))
				continue;
			HashSet<String> actors = movie2Actors.get(m);
			HashSet<Integer> genres = movieId2GenreIds.get(movies2IdsHashMap.get(m));
			out.write(m+"\t");
			for(String a:actors)
				if(!a.equals(""))
					out.write(a+"#");
			out.write("\t");
			if(genres!=null)
				for(int g:genres)
					out.write(genreId2NameHashMap.get(g)+"#");
			out.write("\t");
			out.write("\n");
		}
		out.close();
	}
	/**
	 * First get XML from wget http://dblp.uni-trier.de/xml/dblp.xml.gz
	 */
	public static void convertDBLPXMLToTabbedText(String baseDir) throws Throwable
	{
		if(!new File(baseDir, "dblp.xml").exists())
		{
			System.err.println("File "+baseDir+"/dblp.xml does not exist.");
			return;
		}
		if(new File(baseDir, "dblp.txt").exists())
		{
			System.err.println("File "+baseDir+"/dblp.txt already exists. Delete that file first.");
			return;
		}
		BufferedReader in = new BufferedReader(new FileReader(new File(baseDir, "dblp.xml")));
		BufferedWriter out= new BufferedWriter(new FileWriter(new File(baseDir, "dblp.txt")));
		String str="";
		HashSet<String> authors = new HashSet<String>(); 
		String year="";
		String title="";
		String journalConf="";
//		String type="";
		while((str=in.readLine())!=null)
		{
			try
			{
				if(str.contains("key="))
				{
					year="";
//					type=str.split("\\s+")[0].split("<")[1];
					authors= new HashSet<String>();
					title="";
					journalConf="";
				}
				str=str.toLowerCase();
				if(str.contains("<author>"))
					authors.add(cleanString(str.replace("<author>","").replace("</author>", "")));
				if(str.contains("<title>"))
				{
					title=cleanString(str.replace("<title>","").replace("</title>", ""));
					title=removeOneLetterWords(title);
				}
				if(str.contains("<year>"))
					year=cleanString(str.replace("<year>","").replace("</year>", "").replace("#",""));
//				if(str.contains("<journal>"))
//					journalConf=cleanString(str.replace("<journal>","").replace("</journal>", ""));
				if(str.contains("<booktitle>"))
				{
					journalConf=cleanString(str.replace("<booktitle>","").replace("</booktitle>", ""));
					journalConf=removeOneLetterWords(journalConf);
				}
				if(str.startsWith("</"))
				{
					if(!journalConf.equals("")&&!year.equals("")&&authors.size()!=0 &&!title.equals(""))
					{
						out.write(title+"\t"+year+"\t"+journalConf+"\t");
						for(String a:authors)
							out.write(a+"#");
						out.write("\n");
					}
				}
			}
			catch(Exception e)
			{
				System.err.println("Could not handle: "+str);
				e.printStackTrace();
			}
		}
		in.close();
		out.close();
	}
	private static String removeOneLetterWords(String str) {
		String t2="";
		String toks2[]=str.split("\\s+");
		for(String s:toks2)
		{
			if(s.length()>1)
				t2+=s+" ";
		}
		str=t2.trim();
		return str;
	}
	/**
	 *  * Given the dblp.xml file as the input, this class would create coauthorship graphs cumulative year over year.
	 * Good headers are printed for every graph.
	 * Note that this code is not optimized and needs a huge RAM (I ran using 8GB RAM in 2010).
	 * Usage: java -Xmx8g -XX:-UseGCOverheadLimit GraphDistance/CoAuthorshipCreator
	 * @param initialNodeId - 0 or 1
	 * @param dir
	 * @param dblpXMLFileName
	 * @param unitWeighted - should the graph have unit weight edges or any weight between 0 or 1.
	 * @throws Throwable 
	 */
	public static void coAuthorShipGraphCreator(int initialNodeId, String dir, String dblpXMLFileName, boolean unitWeighted) throws Throwable
	{
		int yearMax=2008;
		int yearMin=1936;
		int nodes=initialNodeId;
		HashMap<String, Integer> authors = new HashMap<String, Integer>();
		HashMap<String, HashMap<Integer, Integer>> coauthorMap = new HashMap<String, HashMap<Integer,Integer>>();
		BufferedReader in = new BufferedReader(new FileReader(new File(dir, dblpXMLFileName)));
		BufferedWriter out1[] = new BufferedWriter[yearMax-yearMin+1];
		for(int i=yearMax;i>=yearMin;i--)
			out1[i-yearMin]= new BufferedWriter(new FileWriter(new File(dir,i+"")));
		BufferedWriter out2= new BufferedWriter(new FileWriter(new File(dir, "authorIds2Names.txt")));
		String str="";
		int maxWeight[] =new int[yearMax-yearMin+1];
		HashSet<String> currAuthors = new HashSet<String>(); 
		int authorStart=0;
		int count=0;
		while((str=in.readLine())!=null)
		{
			count++;
			if(count%100000==0)
				System.out.println(count);
			try
			{
			if(str.contains("<author>"))
			{
				if(authorStart==0)
					currAuthors = new HashSet<String>();
				authorStart=1;
				String author=str.replaceAll("<author>","").replaceAll("</author>", "").trim();
				currAuthors.add(author);
			}
			else
				authorStart=0;
			if(str.contains("<year>"))
			{
				int year=Integer.parseInt(str.replaceAll("<year>","").replaceAll("</year>", "").trim());
				if(year>=yearMin && year <=yearMax && currAuthors.size()>1)
				{
					for (String string : currAuthors) {
						if(!authors.containsKey(string))
						{
							authors.put(string, nodes);
							nodes++;
						}
					}
					for (Iterator<String> iterator = currAuthors.iterator(); iterator.hasNext();) {
						String one =  iterator.next();
						for (Iterator<String> iterator2 = currAuthors.iterator(); iterator2.hasNext();) {
							String two =  iterator2.next();
							if(authors.get(one)!=authors.get(two))
							{
								if(coauthorMap.containsKey(authors.get(one)+"#"+authors.get(two)))
								{
									HashMap<Integer, Integer> tempMap = coauthorMap.get(authors.get(one)+"#"+authors.get(two));
									for(int y=year;y<=yearMax;y++)
									{
										if(tempMap.containsKey(y))
											tempMap.put(y,tempMap.get(y)+1);
										else
											tempMap.put(y,1);
										if(coauthorMap.get(authors.get(one)+"#"+authors.get(two)).get(y)>maxWeight[y-yearMin])
										{
											maxWeight[y-yearMin]=coauthorMap.get(authors.get(one)+"#"+authors.get(two)).get(y);
										}
									}
								}
								else
								{
									HashMap<Integer, Integer> temp = new HashMap<Integer, Integer>();
									for(int y=year;y<=yearMax;y++)
									{
										temp.put(y,1);
									}
									coauthorMap.put(authors.get(one)+"#"+authors.get(two), temp);
								}
							}
						}
					}
				}
			}
			}
			catch(Exception e){e.printStackTrace();}
		}
		nodes--;
		for(int i=0;i<=yearMax-yearMin;i++)
		{
			HashSet<String> thisyearnodes= new HashSet<String>();
			int thisyearedges=0;
			for (Iterator<String> iterator = coauthorMap.keySet().iterator(); iterator.hasNext();) {
				String au = iterator.next();
				if(coauthorMap.get(au).containsKey(i+yearMin))
				{
					thisyearedges++;
					String tok[]= au.split("#");
					thisyearnodes.add(tok[0]);
					thisyearnodes.add(tok[1]);
				}
			}
			
			out1[i].write("#DBLP coauthorship graph:\n");
			out1[i].write("#From year "+yearMin+" to year "+(i+yearMin)+"\n");
			Date d = new Date(); 
			out1[i].write("#Time: "+ d+"\n");
			out1[i].write("# Nodes:"+thisyearnodes.size()+"\t");
			out1[i].write("# Edges: "+thisyearedges+"\n");
			out1[i].write("#Max coauthorship freq: "+maxWeight[i]+"\n");
			out1[i].write("#Undirected graph (each pair of nodes is saved twice) -- contains no self loops"+"\n");
			out1[i].write("#Nodeid starts from "+initialNodeId+"\n");
			out1[i].write("#Weights represent 1/coauthorShip freq\n");
			out1[i].write("#Unweighted graph: "+unitWeighted+"\n");
			//Adjust weight of the coauthorship edge as 1/coauthorShip freq
			for (Iterator<String> iterator = coauthorMap.keySet().iterator(); iterator.hasNext();) {
				String au = iterator.next();
				if(coauthorMap.get(au).containsKey(i+yearMin))
				{
				if(unitWeighted==false)
					out1[i].write(au+"#"+(1.0/coauthorMap.get(au).get(i+yearMin))+"\n");
				else
					out1[i].write(au+"#1"+"\n");
				}
			}
			out1[i].close();
		}
		for (Iterator<String> iterator = authors.keySet().iterator(); iterator.hasNext();) {
			String au = iterator.next();
			out2.write(authors.get(au)+"#"+au+"\n");
		}
		in.close();
		out2.close();
	}

}
