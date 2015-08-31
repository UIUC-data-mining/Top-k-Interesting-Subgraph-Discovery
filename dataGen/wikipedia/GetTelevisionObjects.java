package dataGen.wikipedia;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.regex.Matcher;

/**
 * Parses the infobox text to get television objects that can inserted into the database.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class GetTelevisionObjects {
	public static String baseDir = "";
	public static HashMap<String, String> typeMap = new HashMap<String, String>();
	
	public static void main(String[] args) throws Throwable {
		if(System.getProperty("os.name").contains("Windows"))
			baseDir = GetInfoboxes.windowsBaseDir;
		else
			baseDir = GetInfoboxes.linuxBaseDir;
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, "television.sql")));
		out.write("use gupta58;drop table television;create table television(instanceNum int,show_name  varchar(512), country  varchar(512), first_aired  varchar(512), runtime  varchar(512), last_aired  varchar(512), num_episodes  varchar(512), network  varchar(512), language  varchar(512), starring  varchar(512), genre  varchar(512), format  varchar(512), creator  varchar(512), num_seasons  varchar(512), website  varchar(512), producer  varchar(512), channel  varchar(512), picture_format  varchar(512), executive_producer  varchar(512), director  varchar(512), company  varchar(512), presenter  varchar(512), location  varchar(512), status  varchar(512), writer  varchar(512), opentheme  varchar(512), theme_music_composer  varchar(512), composer  varchar(512), camera  varchar(512), preceded_by  varchar(512), related  varchar(512), followed_by  varchar(512), audio_format  varchar(512), distributor  varchar(512), num_series  varchar(512), narrated  varchar(512), developer  varchar(512), editor  varchar(512));\n");
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
//			System.out.println("Before: "+str);
			String orig=str;
			str=GetFilmObjects.treatTextWithinDoubleBraces(str);
//			System.out.println("After: "+str);
			if(lc%10000==0)
				System.err.println("Read lines: "+lc);
			String tokens[]=str.split("##");
			String type=typeMap.get(orig.split("##")[0].toLowerCase());
			if(type==null||!type.equals("television"))
			{
				lc++;
				continue;
			}
			Television a= new Television();
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
							a.show_name=value;
						if (prop.equals("show_name")) {
							value = value.replaceAll(",", "#@#");
							a.show_name = value;
						}
						if (prop.equals("country")) {
							if(value.contains("{{"))
								value.replaceAll("\\{\\{", "");
							if(value.contains("}}"))
								value.replaceAll("\\}\\}", "");
							value = value.replaceAll(",", "#@#");
							a.country = value;
						}
						if (prop.equals("first_aired")) {
							Matcher m = GetFilmObjects.p3.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.first_aired = value;
						}
						if (prop.equals("runtime")) {
							value=value.toLowerCase();
							if(value.contains("min"))
								value=value.split("min")[0];
							String toks2[]=value.split("\\b");
							for(int i=toks2.length-1;i>=0;i--)
								if(toks2[i].matches("[0-9]+"))
								{
									value=toks2[i];
									break;
								}
							a.runtime = value;
						}
						if (prop.equals("last_aired")) {
							Matcher m = GetFilmObjects.p3.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.last_aired = value;
						}
						if (prop.equals("num_episodes")) {
							Matcher m = GetFilmObjects.p4.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.num_episodes = value;
						}
						if (prop.equals("network")) {
							value = value.replaceAll(",", "#@#");
							a.network = value;
						}
						if (prop.equals("language")) {
							value = value.replaceAll(",", "#@#");
							a.language = value;
						}
						if (prop.equals("starring")) {
							value = value.replaceAll(",", "#@#");
							a.starring = value;
						}
						if (prop.equals("genre")) {
							value = value.replaceAll(",", "#@#");
							a.genre = value;
						}
						if (prop.equals("format")) {
							value = value.replaceAll(",", "#@#");
							a.format = value;
						}
						if (prop.equals("creator")) {
							value = value.replaceAll(",", "#@#");
							a.creator = value;
						}
						if (prop.equals("num_seasons")) {
							Matcher m = GetFilmObjects.p4.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.num_seasons = value;
						}
						if (prop.equals("website")) {
							if(value.contains("{{URL|"))
								value=value.substring(6).replaceAll("}}", "");
							if(value.contains("["))
								value=value.split("\\s+")[0].replace('[', ' ').replace(']', ' ').trim();
							value = value.replaceAll(",", "#@#");
							a.website = value;
						}
						if (prop.equals("producer")) {
							value = value.replaceAll(",", "#@#");
							a.producer = value;
						}
						if (prop.equals("channel")) {
							value = value.replaceAll(",", "#@#");
							a.channel = value;
						}
						if (prop.equals("picture_format")) {
							value = value.replaceAll(",", "#@#");
							a.picture_format = value;
						}
						if (prop.equals("executive_producer")) {
							value = value.replaceAll(",", "#@#");
							a.executive_producer = value;
						}
						if (prop.equals("director")) {
							value = value.replaceAll(",", "#@#");
							a.director = value;
						}
						if (prop.equals("company")) {
							value = value.replaceAll(",", "#@#");
							a.company = value;
						}
						if (prop.equals("presenter")) {
							value = value.replaceAll(",", "#@#");
							a.presenter = value;
						}
						if (prop.equals("location")) {
							value = value.replaceAll(",", "#@#");
							a.location = value;
						}
						if (prop.equals("status")) {
							value = value.replaceAll(",", "#@#");
							a.status = value;
						}
						if (prop.equals("writer")) {
							value = value.replaceAll(",", "#@#");
							a.writer = value;
						}
						if (prop.equals("opentheme")) {
							value = value.replaceAll(",", "#@#");
							a.opentheme = value;
						}
						if (prop.equals("theme_music_composer")) {
							value = value.replaceAll(",", "#@#");
							a.theme_music_composer = value;
						}
						if (prop.equals("composer")) {
							value = value.replaceAll(",", "#@#");
							a.composer = value;
						}
						if (prop.equals("camera")) {
							value = value.replaceAll(",", "#@#");
							a.camera = value;
						}
						if (prop.equals("preceded_by")) {
							value = value.replaceAll(",", "#@#");
							a.preceded_by = value;
						}
						if (prop.equals("related")) {
							value = value.replaceAll(",", "#@#");
							a.related = value;
						}
						if (prop.equals("followed_by")) {
							value = value.replaceAll(",", "#@#");
							a.followed_by = value;
						}
						if (prop.equals("audio_format")) {
							value = value.replaceAll(",", "#@#");
							a.audio_format = value;
						}
						if (prop.equals("distributor")) {
							value = value.replaceAll(",", "#@#");
							a.distributor = value;
						}
						if (prop.equals("num_series")) {
							Matcher m = GetFilmObjects.p4.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.num_series = value;
						}
						if (prop.equals("narrated")) {
							value = value.replaceAll(",", "#@#");
							a.narrated = value;
						}
						if (prop.equals("developer")) {
							value = value.replaceAll(",", "#@#");
							a.developer = value;
						}
						if (prop.equals("editor")) {
							value = value.replaceAll(",", "#@#");
							a.editor = value;
						}
					}
				}
				catch(Exception e){e.printStackTrace();System.err.println(str);}
			}
			a.instance=lc;
			a.show_name=orig.split("##")[0];
			out.write(a+"\n");
			lc++;
		}
		in.close();
		out.write("commit;\n");
		out.close();
	}
}

