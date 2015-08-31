package dataGen.wikipedia;
import java.io.*;
import java.util.*;

/**
 * Generates the network from the sets.txt
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class GenerateNetwork {
	public static String baseDir = "";
	public static HashSet<String> edges = new HashSet<String>();
	public static void main(String[] args) throws Throwable {
		if(System.getProperty("os.name").contains("Windows"))
			baseDir = GetInfoboxes.windowsBaseDir;
		else
			baseDir = GetInfoboxes.linuxBaseDir;
		BufferedReader in = new BufferedReader(new FileReader(new File(baseDir, "sets.txt")));
		String str = "";
		ArrayList<Integer> list = new ArrayList<Integer>();
		int lc=0;
		while ((str = in.readLine()) != null) {
			if(lc%100==0)
				System.err.println("Lines processed: "+lc+" EdgeList Size: "+edges.size());
			lc++;
			list = new ArrayList<Integer>();
			String tokens[] = str.split("\\t");
			for(String t:tokens)
				list.add(Integer.parseInt(t));
			/**
			 * COLING submission was made with this code.
			Collections.sort(list);
//			for(int i=0;i<list.size();i++)
//				for(int j=i+1;j<list.size();j++)
//					edges.add(list.get(i)+"#"+list.get(j));
			for(int i=1;i<list.size();i++)
				edges.add(list.get(i)+"#"+list.get(i-1));
				**/
			/**** This code was written after coling 2012 submission START *****/
//			for(int i=0;i<list.size();i++)
//				for(int j=i+1;j<list.size();j++)
//					edges.add(list.get(i)+"#"+list.get(j));
			for(int i=1;i<list.size();i++)
				edges.add(list.get(0)+"#"+list.get(i));
			/**** This code was written after coling 2012 submission END *****/
		}
		in.close();
		System.err.println("Sets Processing Finished");
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, "edges.txt")));
		for(String e:edges)
			out.write(e+"\n");
		out.close();
	}
}

