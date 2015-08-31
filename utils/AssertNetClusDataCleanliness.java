package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class AssertNetClusDataCleanliness {
	public static LinkedHashMap<String, String> paperID2NameMap = new LinkedHashMap<String, String>();
	public static LinkedHashMap<String, String> ID2NameMap = new LinkedHashMap<String, String>();
	public static LinkedHashSet<String> paper2XSet = new LinkedHashSet<String>(); 
public static void main(String[] args) throws Throwable {
	String outDir=args[0]+"\\cleaned\\";
	new File(outDir).mkdir();
	String baseDir=args[0];
	loadMap(paperID2NameMap, baseDir+"paper.txt");
	writeMap(paperID2NameMap, outDir+"paper.txt");
	
	ID2NameMap = new LinkedHashMap<String, String>();
	paper2XSet = new LinkedHashSet<String>();
	loadPaperXSet(paper2XSet, baseDir+"paper_term.txt");
	writeSet(paper2XSet, outDir+"paper_term.txt");
	loadID2NameMap(baseDir+"term.txt");
	writeMap(ID2NameMap, outDir+"term.txt");
	
	ID2NameMap = new LinkedHashMap<String, String>();
	paper2XSet = new LinkedHashSet<String>();
	loadPaperXSet(paper2XSet, baseDir+"paper_conf.txt");
	writeSet(paper2XSet, outDir+"paper_conf.txt");
	loadID2NameMap(baseDir+"conf.txt");
	writeMap(ID2NameMap, outDir+"conf.txt");

	ID2NameMap = new LinkedHashMap<String, String>();
	paper2XSet = new LinkedHashSet<String>();
	loadPaperXSet(paper2XSet, baseDir+"paper_author.txt");
	writeSet(paper2XSet, outDir+"paper_author.txt");
	loadID2NameMap(baseDir+"author.txt");
	writeMap(ID2NameMap, outDir+"author.txt");

	ID2NameMap = new LinkedHashMap<String, String>();
	paper2XSet = new LinkedHashSet<String>();
	loadPaperXSet(paper2XSet, baseDir+"paper_year.txt");
	writeSet(paper2XSet, outDir+"paper_year.txt");
}
private static void loadID2NameMap(String file) throws Throwable {
    BufferedReader in = new BufferedReader(new FileReader(new File(file)));
    String line = "";
    while ((line = in.readLine()) != null)
    {
        String[] tokens = line.split("\\t");
        if(ID2NameMap.containsKey(tokens[0]))
        	ID2NameMap.put(tokens[0], tokens[1]);
    }
    in.close();	
}
public static void loadMap(LinkedHashMap<String, String> map, String file) throws Throwable
{
	HashSet<String> set = new HashSet<String>();
    BufferedReader in = new BufferedReader(new FileReader(new File(file)));
    String line = "";
    while ((line = in.readLine()) != null)
    {
        String[] tokens = line.split("\\t");
        if(!set.contains(tokens[1]))
        {
        	map.put(tokens[0], tokens[1]);
        	set.add(tokens[1]);
        }
    }
    in.close();
}
public static void loadPaperXSet(LinkedHashSet<String> set, String file) throws Throwable
{
    BufferedReader in = new BufferedReader(new FileReader(new File(file)));
    String line = "";
    while ((line = in.readLine()) != null)
    {
        String[] tokens = line.split("\\t");
        if(paperID2NameMap.containsKey(tokens[0]))
        {
        	set.add(line);
        	ID2NameMap.put(tokens[1], "");
        }
    }
    in.close();
}
public static void writeMap(LinkedHashMap<String, String> map, String file) throws Throwable
{
	BufferedWriter out = new BufferedWriter(new FileWriter(new File(file)));
    for(String s:map.keySet())
    	out.write(s+"\t"+map.get(s)+"\n");
    out.close();
}
public static void writeSet(LinkedHashSet<String> set, String file) throws Throwable
{
	BufferedWriter out = new BufferedWriter(new FileWriter(new File(file)));
    for(String s:set)
    	out.write(s+"\n");
    out.close();
}
}
