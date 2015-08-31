/**
 * 
 */
package dataGen.wikipedia;
import java.io.*;
import java.util.*;

/**
 * Create a file containing node ids which are part of 10EntityType network. 
 * Create a file containing edges which are part of 10EntityType network.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class Create10EntityTypeNetwork {
	public static String baseDir = GetInfoboxes.baseDir;
	public static HashSet<String> set = new HashSet<String>();
	public static HashMap<Integer, String> typeMap = new HashMap<Integer, String>();
	public static HashMap<Integer, String> idMap = new HashMap<Integer, String>();
	
	public static void main(String[] args) throws Throwable {
		if(System.getProperty("os.name").contains("Windows"))
			baseDir = GetInfoboxes.windowsBaseDir;
		else
			baseDir = GetInfoboxes.linuxBaseDir;

		String list[] = new File(baseDir).list();
		String str="";
		for(String f:list)
		{
			if(f.endsWith(".sql"))
			{
				System.out.println("Starting to read: "+f);
				BufferedReader in = new BufferedReader(new FileReader(new File(baseDir, f)));
				while ((str = in.readLine()) != null) {
					if(str.startsWith("insert"))
					{
						String inst= str.split("\\(")[1].split(",")[0];
						set.add(inst);
					}
				}
				in.close();
			}
		}
		BufferedReader in = new BufferedReader(new FileReader(new File(baseDir, "infoboxes.txt")));
		int count=0;
		while ((str = in.readLine()) != null) {
			String tokens[] = str.split("\\t");
			if(set.contains(count+""))
			{
				idMap.put(count, tokens[1].toLowerCase());
				typeMap.put(count, tokens[0]);
			}
			count++;
		}
		in.close();
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, "10EntityTypeNetwork.nodes")));
		for(int i=0;i<count;i++)
		{
			if(set.contains(i+""))
			{
				out.write(i+"\t"+idMap.get(i)+"\t"+typeMap.get(i)+"\n");
			}
		}
		out.close();
		idMap=null;
		typeMap=null;
		out = new BufferedWriter(new FileWriter(new File(baseDir, "10EntityTypeNetwork.edges")));
		in = new BufferedReader(new FileReader(new File(baseDir, "edges.txt")));
		while((str=in.readLine())!=null)
		{
			String tokens[]=str.split("#");
			int num1=Integer.parseInt(tokens[0]);
			int num2=Integer.parseInt(tokens[1]);
			if(set.contains(num1+"")&&set.contains(num2+""))
				out.write(str+"\n");
		}
		in.close();
		out.close();
	}
}

