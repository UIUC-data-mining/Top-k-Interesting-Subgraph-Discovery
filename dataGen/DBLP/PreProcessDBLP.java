package dataGen.DBLP;

import java.io.*;
import java.util.*;

import utils.FileUtils;
import utils.TextUtils;
/**
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 * Creates the basic DBLP dataset that can be used by NetClus for community detection.
 */
public class PreProcessDBLP {
	public static String baseDir="/home/gupta58/2012/VLDB12/data/DBLP/";
public static void main(String[] args) throws Throwable{
	TextUtils.convertDBLPXMLToTabbedText(baseDir);
	ArrayList<String> list = TextUtils.getDBLPSubset(baseDir, 2001, 2010);
	BufferedWriter out = FileUtils.getFileWriter(baseDir+"dblp.subsetY2001_2010.txt");
	for(String s:list)
		out.write(s+"\n");
	out.close();
	TextUtils.removeAuthorsWithFreqLessThanX(baseDir+"dblp.subsetY2001_2010.txt", baseDir+"dblp.subsetY2001_2010AGtEq10.txt", 10);
	TextUtils.convertDBLPText2NetClusFormat(baseDir+"dblp.subsetY2001_2010AGtEq10.txt");
	TextUtils.cleanNetClusDir(baseDir+File.separator+"netclus");
}
}