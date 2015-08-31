package dataGen.wikipedia;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.regex.Matcher;

/**
 * Parses the infobox text to get album objects that can inserted into the database.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class GetAlbumObjects {
	public static String baseDir = "";
	public static HashMap<String, String> typeMap = new HashMap<String, String>();
	
	public static void main(String[] args) throws Throwable {
		if(System.getProperty("os.name").contains("Windows"))
			baseDir = GetInfoboxes.windowsBaseDir;
		else
			baseDir = GetInfoboxes.linuxBaseDir;
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, "album.sql")));
		out.write("use gupta58;drop table album;create table album(instanceNum int,type varchar(512),name varchar(512),artist varchar(512),released varchar(512),genre varchar(512),label varchar(512),last_album varchar(512),next_album varchar(512),producer varchar(512),length varchar(512),recorded varchar(512),single_1 varchar(512),single_1_date varchar(512),single_2 varchar(512),single_2_date varchar(512),single_3 varchar(512),single_3_date varchar(512),single_4 varchar(512),single_4_date varchar(512),single_5 varchar(512),single_5_date varchar(512),chronology varchar(512),upper_caption varchar(512),lower_caption varchar(512),language varchar(512),background varchar(512),longtype varchar(512),format varchar(512));\n");
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
			if(type==null||!type.equals("album"))
			{
				lc++;
				continue;
			}
			Album a= new Album();
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
						if(prop.equals("label"))
						{
							value=value.replaceAll(",", "#@#");
							a.label=value;
						}
						if(prop.equals("next album"))
						{
							value=value.replaceAll(",", "#@#");
							a.next_album=value;
						}
						if(prop.equals("last album"))
						{
							value=value.replaceAll(",", "#@#");
							a.last_album=value;
						}
						if(prop.equals("format"))
						{
							value=value.replaceAll(",", "#@#");
							a.format=value;
						}
						if(prop.equals("language"))
						{
							value=value.replaceAll(",", "#@#");
							a.language=value;
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
						if(prop.equals("single 1 date"))
						{
							Matcher m = GetFilmObjects.p3.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.single_1_date=value;
						}
						if(prop.equals("single 2 date"))
						{
							Matcher m = GetFilmObjects.p3.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.single_2_date=value;
						}
						if(prop.equals("single 3 date"))
						{
							Matcher m = GetFilmObjects.p3.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.single_3_date=value;
						}
						if(prop.equals("single 4 date"))
						{
							Matcher m = GetFilmObjects.p3.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.single_4_date=value;
						}
						if(prop.equals("single 5 date"))
						{
							Matcher m = GetFilmObjects.p3.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.single_5_date=value;
						}
						
						if(prop.equals("single 1"))
						{
							value=value.replaceAll(",", "#@#");
							a.single_1=value;
						}
						if(prop.equals("single 2"))
						{
							value=value.replaceAll(",", "#@#");
							a.single_2=value;
						}
						if(prop.equals("single 3"))
						{
							value=value.replaceAll(",", "#@#");
							a.single_3=value;
						}
						if(prop.equals("single 4"))
						{
							value=value.replaceAll(",", "#@#");
							a.single_4=value;
						}
						if(prop.equals("single 5"))
						{
							value=value.replaceAll(",", "#@#");
							a.single_5=value;
						}
						if(prop.equals("upper caption"))
						{
							value=value.replaceAll(",", "#@#");
							a.upper_caption=value;
						}
						if(prop.equals("lower caption"))
						{
							value=value.replaceAll(",", "#@#");
							a.lower_caption=value;
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
						if(prop.equals("background"))
						{
							value=value.replaceAll(",", "#@#");
							a.background=value;
						}
						if(prop.equals("longtype"))
						{
							value=value.replaceAll(",", "#@#");
							a.longtype=value;
						}
						if(prop.equals("format"))
						{
							value=value.replaceAll(",", "#@#");
							a.format=value;
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

