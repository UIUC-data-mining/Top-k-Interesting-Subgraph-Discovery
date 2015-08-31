package dataGen.wikipedia;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.regex.Matcher;

/**
 * Parses the infobox text to get single objects that can inserted into the database.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class GetSingleObjects {
	public static String baseDir = "";
	public static HashMap<String, String> typeMap = new HashMap<String, String>();
	
	public static void main(String[] args) throws Throwable {
		if(System.getProperty("os.name").contains("Windows"))
			baseDir = GetInfoboxes.windowsBaseDir;
		else
			baseDir = GetInfoboxes.linuxBaseDir;
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, "single.sql")));
		out.write("use gupta58;drop table single;create table single(instanceNum int,artist varchar(512),name varchar(512),released varchar(512),genre varchar(512),label varchar(512),next_single varchar(512),last_single varchar(512),length varchar(512),format varchar(512),writer varchar(512),producer varchar(512),recorded varchar(512),from_album varchar(512),album varchar(512),b_side varchar(512),type varchar(512),chronology varchar(512),track_no varchar(512),a_side varchar(512));\n");		
		BufferedReader in = new BufferedReader(new FileReader(new File(baseDir, "infoboxes.txt")));
		String str = "";
		while ((str = in.readLine()) != null) {
			String tokens[] = str.split("\\t");
			typeMap.put(tokens[1].toLowerCase(), tokens[0]);
		}
		in.close();
		in = new BufferedReader(new FileReader(new File(baseDir, "infoboxText.txt")));
		int lc=0;
		while((str=in.readLine())!=null)
		{
			String orig=str;
//			System.out.println("Before: "+str);
			str=GetFilmObjects.treatTextWithinDoubleBraces(str);
//			System.out.println("After: "+str);
			if(lc%10000==0)
				System.err.println("Read lines: "+lc);
			String tokens[]=str.split("##");
			String type=typeMap.get(orig.split("##")[0].toLowerCase());
			if(type==null||!type.equals("single"))
			{
				lc++;
				continue;
			}
			Single a= new Single();
			for(String t:tokens)
			{
				try
				{
					String toks[]=t.split("=");
					if(toks.length>1)
					{
						String prop=toks[0].toLowerCase().replaceAll("\\|", "").trim();
						String value="";
						for(int i=1;i<toks.length;i++)
							value+=toks[i]+" ";
						value=GetFilmObjects.clean(value.trim());
						if(prop.equals("name"))
							a.name=value;
						if(prop.equals("released"))
						{
							Matcher m = GetFilmObjects.p3.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.released=value;
						}
						if(prop.equals("artist"))
						{
							value=value.replaceAll(",", "#@#");
							a.artist=value;
						}
						if(prop.equals("genre"))
						{
							value=value.replaceAll(",", "#@#");
							a.genre=value;
						}
						if(prop.equals("length"))
						{
							value=value.replaceAll(",", "#@#");
							Matcher m = GetFilmObjects.p7.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.length=value;
						}
						if(prop.equals("track_no"))
						{
							value=value.replaceAll(",", "#@#");
							Matcher m = GetFilmObjects.p4.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.track_no=value;
						}
						if(prop.equals("label"))
						{
							value=value.replaceAll(",", "#@#");
							a.label=value;
						}
						if(prop.equals("next single"))
						{
							value=value.replaceAll(",", "#@#");
							a.next_single=value;
						}
						if(prop.equals("last single"))
						{
							value=value.replaceAll(",", "#@#");
							a.last_single=value;
						}
						if(prop.equals("format"))
						{
							value=value.replaceAll(",", "#@#");
							a.format=value;
						}
						if(prop.equals("writer"))
						{
							value=value.replaceAll(",", "#@#");
							a.writer=value;
						}
						if(prop.equals("producer"))
						{
							value=value.replaceAll(",", "#@#");
							a.producer=value;
						}
						if(prop.equals("recorded"))
						{
							Matcher m = GetFilmObjects.p3.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.recorded=value;
						}
						if(prop.equals("from album"))
						{
							value=value.replaceAll(",", "#@#");
							a.from_album=value;
						}
						if(prop.equals("album"))
						{
							value=value.replaceAll(",", "#@#");
							a.album=value;
						}
						if(prop.equals("b-side"))
						{
							value=value.replaceAll(",", "#@#");
							a.b_side=value;
						}
						if(prop.equals("type"))
						{
							value=value.replaceAll(",", "#@#");
							a.type=value;
						}
						if(prop.equals("chronology"))
						{
							value=value.replaceAll(",", "#@#");
							a.chronology=value;
						}
						if(prop.equals("a-side"))
						{
							value=value.replaceAll(",", "#@#");
							a.a_side=value;
						}
					}
				}
				catch(Exception e){e.printStackTrace();System.err.println(str);}
			}
			a.instance=lc;
			a.name=orig.split("##")[0];
			out.write(a+"\n");
			lc++;
		}
		in.close();
		out.write("commit;\n");
		out.close();
	}
}

