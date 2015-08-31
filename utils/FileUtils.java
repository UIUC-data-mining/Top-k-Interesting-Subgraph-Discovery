package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;

public class FileUtils {
	public static BufferedReader getFileReader(String file) throws Throwable
	{
		return new BufferedReader(new FileReader(new File(file)));
	}
	
	public static BufferedWriter getFileWriter(String file) throws Throwable
	{
		return new BufferedWriter(new FileWriter(new File(file)));
	}
	/**
	 * Finds the lines present in one file but missing in other.
	 * @param baseDir
	 * @param file1
	 * @param file2
	 * @throws Throwable
	 */
	public static void getDiffBetweenFiles(String baseDir, String file1, String file2) throws Throwable
	{
	        BufferedReader in1= new BufferedReader(new FileReader(new File(baseDir, file1)));
	        BufferedReader in2= new BufferedReader(new FileReader(new File(baseDir, file2)));
	        BufferedWriter out1 = new BufferedWriter(new FileWriter(new File(baseDir, file1+"Minus"+file2)));
	        BufferedWriter out2 = new BufferedWriter(new FileWriter(new File(baseDir, file2+"Minus"+file1)));
	        HashSet<String> hash=new HashSet<String>();
	        String str="";
	        while((str=in1.readLine())!=null)
	            hash.add(str.trim());
	        in1.close();
	        int lc=0;
	        while((str=in2.readLine())!=null)
	        {
	        	lc++;
	            if(!hash.contains(str))
	                out2.write(lc+"\t"+str+"\n");
	        }
	        in2.close();
	        in1= new BufferedReader(new FileReader(new File(baseDir, file1)));
	        in2= new BufferedReader(new FileReader(new File(baseDir, file2)));
	        hash= new HashSet<String>();
	        while((str=in2.readLine())!=null)
	            hash.add(str.trim());
	        in2.close();
	        lc=0;
	        while((str=in1.readLine())!=null)
	        {
	        	lc++;
	            if(!hash.contains(str))
	                out1.write(lc+"\t"+str+"\n");
	        }
	        out1.close();
	        out2.close();
	}	
	public static void copyFile(String file1, String file2) throws Throwable
	{
		   File inputFile = new File(file1);
		    File outputFile = new File(file2);

		    FileReader in = new FileReader(inputFile);
		    FileWriter out = new FileWriter(outputFile);
		    int c;

		    while ((c = in.read()) != -1)
		      out.write(c);

		    in.close();
		    out.close();
	}

}
