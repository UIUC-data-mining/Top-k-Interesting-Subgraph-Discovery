package dataGen.wikipedia;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

/**
 * Gets all the pages from the Wikipedia dump, which contain infoboxes in them.
 * Reads enwiki-20120104-pages-articles.xml.bz2 and writes to infoboxesCorpus.txt.bz2
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class GetInfoboxes {
	public static String linuxBaseDir = "/home/cs/gupta58/2012/HNODA3/wikipedia/";
	public static String windowsBaseDir = "C:\\Users\\manish\\Desktop\\tmpData\\";
	public static String baseDir = "";
	public static String wekaDir="C:\\Users\\manish\\Desktop\\workspace\\jars\\";
//	public static String wekaDir="/home/cs/gupta58/jars/";
	
	public static void main(String[] args) throws Throwable {
		if(System.getProperty("os.name").contains("Windows"))
			baseDir = GetInfoboxes.windowsBaseDir;
		else
			baseDir = GetInfoboxes.linuxBaseDir;
//		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, "infoboxesCorpus.txt")));
		BZip2CompressorOutputStream bzOut = new BZip2CompressorOutputStream(new FileOutputStream(new File(baseDir, "infoboxesCorpus.txt.bz2"))); 
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(bzOut));
		int start=0;
		int containsInfobox=0;
		FileInputStream in = new FileInputStream(baseDir+"enwiki-20120104-pages-articles.xml.bz2");
		BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in);
		BufferedReader br = new BufferedReader(new InputStreamReader(bzIn));
		ArrayList<String> data = new ArrayList<String>();
		String str="";
		while((str=br.readLine())!=null)
		{
			if(str.contains("<page>"))
			{
				start=1;
			}
			if(str.contains("{{Infobox"))
				containsInfobox=1;
			if(start==1)
			{
				data.add(str);
			}
			if(str.contains("</page>"))
			{
				if(containsInfobox==1)
				{
					for(String s:data)
					{
						out.write(s+"\n");
					}
				}
				containsInfobox=0;
				data = new ArrayList<String>();
				start=0;
			}
		}
		br.close();
		bzIn.close();
		in.close();
		out.close();
		bzOut.close();
	}
}

