package dataGen.wikipedia;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

/**
 * Finds the type and cleaned text related to every entity and puts these into 2 different files.
 * Reads infoboxesCorpus.txt.bz2
 * Writes to infoboxes.txt and infoboxText.txt
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class ComputeEntityDictAndInfoboxText {
	public static String baseDir = "";
	public static void main(String[] args) throws Throwable {
		if(System.getProperty("os.name").contains("Windows"))
			baseDir = GetInfoboxes.windowsBaseDir;
		else
			baseDir = GetInfoboxes.linuxBaseDir;
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, "infoboxes.txt")));
		BufferedWriter out2 = new BufferedWriter(new FileWriter(new File(baseDir, "infoboxText.txt")));
		FileInputStream in = new FileInputStream(baseDir+"infoboxesCorpus.txt.bz2");
		BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in);
		BufferedReader br = new BufferedReader(new InputStreamReader(bzIn));
		String str="";
		String title="";
		String type="";
		int lc=0;
		String infobox="";
		int iStart=0;
		int openLeft=0;
		while((str=br.readLine())!=null)
		{
			if(lc%1000==0)
				System.err.println("Lines read: "+lc);
			lc++;
			try{
				if(str.contains("<title>"))
					title=str.split(">")[1].split("<")[0].trim();
				if(str.contains("{{")&&iStart==1)
					openLeft+=("#"+str+"#").split("\\{\\{").length-1;
				if(str.contains("}}")&&iStart==1)
					openLeft-=("#"+str+"#").split("\\}\\}").length-1;
				if(openLeft==0)//(str.trim().startsWith("}}")&&iStart==1)||openLeft==0
					iStart=0;
				if(iStart==1)
					infobox+=str.trim().replaceAll("##", "__")+"##";//.substring(1).replaceAll("##", "__").trim()+"##";
				if(str.contains("{{Infobox") &&infobox.equals(""))
				{
					iStart=1;openLeft=1;
				}
				if(str.contains("{{Infobox") &&type.equals(""))
					type=str.split("\\{\\{Infobox")[1].split("&")[0].split("\\|")[0].split("\\}")[0].replaceAll("_", " ").trim().toLowerCase();
				if(str.contains("</page>"))
				{
					if(!title.equals("")&&!type.equals(""))
					{
						out.write(type+"\t"+title+"\n");
						out2.write(title+"##"+infobox+"\n");
					}
					type="";
					infobox="";
					iStart=0;
				}
			}
			catch(Exception e){}
		}
		br.close();
		bzIn.close();
		in.close();
		out.close();
		out2.close();
	}
}

