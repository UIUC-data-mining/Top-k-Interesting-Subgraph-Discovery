package dataGen.wikipedia;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

/**
 * For every entity e, this class generates those entities that are mentioned on Wikipedia page for e.
 * each set contains all entities within the same sentence. First entity is the entity e and other entities are the ones mentioned in a sentence on Wikipedia page for e.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class GenerateSets {
	public static String baseDir = "";
	public static HashMap<String, Integer> map = new HashMap<String, Integer>();
	public static void main(String[] args) throws Throwable {
		if(System.getProperty("os.name").contains("Windows"))
			baseDir = GetInfoboxes.windowsBaseDir;
		else
			baseDir = GetInfoboxes.linuxBaseDir;
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, "sets.txt")));
		//load the map from entity to entity type
		BufferedReader in = new BufferedReader(new FileReader(new File(baseDir, "infoboxes.txt")));
		String str = "";
		int count=0;
		while ((str = in.readLine()) != null) {
			String tokens[] = str.split("\\t");
			map.put(tokens[1].toLowerCase(), count);
			count++;
		}
		in.close();
		//parse the data file.
		FileInputStream in2 = new FileInputStream(baseDir+"infoboxesCorpus.txt.bz2");
		BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in2);
		BufferedReader br = new BufferedReader(new InputStreamReader(bzIn));
		String title="";
		Pattern patt = Pattern.compile("\\[\\[.*?\\]\\]",Pattern.DOTALL);
		while((str=br.readLine())!=null)
		{
			if(str.contains("<title>"))
				title=str.split(">")[1].split("<")[0].trim().toLowerCase();
			if(map.containsKey(title))
			{
				if(str.contains("[["))
				{
					String sentences[]= str.split("\\.");
					for(String s:sentences)
					{
						HashSet<Integer> set = new HashSet<Integer>();
						set.add(map.get(title));
						Matcher m = patt.matcher(s);
						while(m.find())
						{
							 int start = m.start(0)+2;
							 int end = m.end(0)-2;
							 String tmp="";
							 try{
							 tmp=s.substring(start, end).split("\\|")[0].toLowerCase().trim();
							 }catch(Exception e){e.printStackTrace();System.err.println(str);};
							 if(map.containsKey(tmp))
								 set.add(map.get(tmp));
						}
						if(set.size()>1)
						{
							out.write(map.get(title)+"\t");
							for(int i:set)
								if(i!=map.get(title))
									out.write(i+"\t");
							out.write("\n");
						}
					}
				}
			}
		}
		br.close();
		in2.close();
		bzIn.close();
		out.close();
		out = new BufferedWriter(new FileWriter(new File(baseDir, "entityIDs.txt")));
		for(String e:map.keySet())
			out.write(e+"\t"+map.get(e)+"\n");
		out.close();
	}
}

