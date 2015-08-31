package dataGen.wikipedia;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.regex.Matcher;

/**
 * Parses the infobox text to get footballbiography objects that can inserted into the database.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class GetFootballBiographyObjects {
	public static String baseDir = "";
	public static HashMap<String, String> typeMap = new HashMap<String, String>();
	
	public static void main(String[] args) throws Throwable {
		if(System.getProperty("os.name").contains("Windows"))
			baseDir = GetInfoboxes.windowsBaseDir;
		else
			baseDir = GetInfoboxes.linuxBaseDir;
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, "footballBiography.sql")));
		out.write("use gupta58;drop table footballBiography;create table footballBiography(instanceNum int,position varchar(512),fullname_name_playername varchar(512),years1 varchar(512),years2 varchar(512),years3 varchar(512),years4 varchar(512),years5 varchar(512),years6 varchar(512),years7 varchar(512),clubs1 varchar(512),clubs2 varchar(512),clubs3 varchar(512),clubs4 varchar(512),clubs5 varchar(512),height varchar(512),currentclub varchar(512),cityofbirth_birth_place varchar(512),countryofbirth varchar(512),caps1 varchar(512),caps2 varchar(512),caps3 varchar(512),caps4 varchar(512),caps5 varchar(512),birth_date_dateofbirth varchar(512),pcupdate varchar(512),nationalyears1 varchar(512),goals1 varchar(512),goals2 varchar(512),goals3 varchar(512),goals4 varchar(512),nationalcaps1 varchar(512),clubnumber varchar(512),nationalteam1 varchar(512),youthyears1 varchar(512),youthclubs1 varchar(512),nationalgoals1 varchar(512));\n");
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
			if(type==null||!type.equals("football biography"))
			{
				lc++;
				continue;
			}
			FootballBiography a= new FootballBiography();
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
						if(prop.equals("name")||prop.equals("fullname")||prop.equals("playername"))
							a.fullname_name_playername=value;
						if (prop.equals("position")) {
							value = value.replaceAll(",", "#@#");
							a.position = value;
						}
						if (prop.equals("years1")) {
							if(value.contains("|"))
								value=value.split("\\|")[0];
							value = value.replaceAll(",", "#@#");
							a.years1 = value;
						}
						if (prop.equals("years2")) {
							if(value.contains("|"))
								value=value.split("\\|")[0];
							value = value.replaceAll(",", "#@#");
							a.years2 = value;
						}
						if (prop.equals("years3")) {
							if(value.contains("|"))
								value=value.split("\\|")[0];
							value = value.replaceAll(",", "#@#");
							a.years3 = value;
						}
						if (prop.equals("years4")) {
							if(value.contains("|"))
								value=value.split("\\|")[0];
							value = value.replaceAll(",", "#@#");
							a.years4 = value;
						}
						if (prop.equals("years5")) {
							if(value.contains("|"))
								value=value.split("\\|")[0];
							value = value.replaceAll(",", "#@#");
							a.years5 = value;
						}
						if (prop.equals("years6")) {
							if(value.contains("|"))
								value=value.split("\\|")[0];
							value = value.replaceAll(",", "#@#");
							a.years6 = value;
						}
						if (prop.equals("years7")) {
							if(value.contains("|"))
								value=value.split("\\|")[0];
							value = value.replaceAll(",", "#@#");
							a.years7 = value;
						}
						if (prop.equals("clubs1")) {
							value = value.replaceAll(",", "#@#");
							a.clubs1 = value;
						}
						if (prop.equals("clubs2")) {
							value = value.replaceAll(",", "#@#");
							a.clubs2 = value;
						}
						if (prop.equals("clubs3")) {
							value = value.replaceAll(",", "#@#");
							a.clubs3 = value;
						}
						if (prop.equals("clubs4")) {
							value = value.replaceAll(",", "#@#");
							a.clubs4 = value;
						}
						if (prop.equals("clubs5")) {
							value = value.replaceAll(",", "#@#");
							a.clubs5 = value;
						}
						if (prop.equals("height")) {
							value = value.replaceAll(",", "#@#");
							a.height = value;
						}
						if (prop.equals("currentclub")) {
							value = value.replaceAll(",", "#@#");
							a.currentclub = value;
						}
						if (prop.equals("cityofbirth")||prop.equals("birth_place")) {
							value = value.replaceAll(",", "#@#");
							a.cityofbirth_birth_place = value;
						}
						if (prop.equals("countryofbirth")) {
							value = value.replaceAll(",", "#@#");
							a.countryofbirth = value;
						}
						if (prop.equals("caps1")) {
							if(value.contains("|"))
								value=value.split("\\|")[0];
							Matcher m = GetFilmObjects.p4.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.caps1 = value;
						}
						if (prop.equals("caps2")) {
							if(value.contains("|"))
								value=value.split("\\|")[0];
							Matcher m = GetFilmObjects.p4.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.caps2 = value;
						}
						if (prop.equals("caps3")) {
							if(value.contains("|"))
								value=value.split("\\|")[0];
							Matcher m = GetFilmObjects.p4.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.caps3 = value;
						}
						if (prop.equals("caps4")) {
							if(value.contains("|"))
								value=value.split("\\|")[0];
							Matcher m = GetFilmObjects.p4.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.caps4 = value;
						}
						if (prop.equals("caps5")) {
							if(value.contains("|"))
								value=value.split("\\|")[0];
							Matcher m = GetFilmObjects.p4.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.caps5 = value;
						}
						if (prop.equals("birth_date")||prop.equals("dateofbirth")) {
							Matcher m = GetFilmObjects.p3.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.birth_date_dateofbirth = value;
						}
						if (prop.equals("pcupdate")) {
							Matcher m = GetFilmObjects.p3.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.pcupdate = value;
						}
						if (prop.equals("nationalyears1")) {
							if(value.contains("|"))
								value=value.split("\\|")[0];
							value = value.replaceAll(",", "#@#");
							a.nationalyears1 = value;
						}
						if (prop.equals("goals1")) {
							Matcher m = GetFilmObjects.p4.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.goals1 = value;
						}
						if (prop.equals("goals2")) {
							Matcher m = GetFilmObjects.p4.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.goals2 = value;
						}
						if (prop.equals("goals3")) {
							Matcher m = GetFilmObjects.p4.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.goals3 = value;
						}
						if (prop.equals("goals4")) {
							Matcher m = GetFilmObjects.p4.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.goals4 = value;
						}
						if (prop.equals("nationalcaps1")) {
							if(value.contains("|"))
								value=value.split("\\|")[0];
							Matcher m = GetFilmObjects.p4.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.nationalcaps1 = value;
						}
						if (prop.equals("clubnumber")) {
							Matcher m = GetFilmObjects.p4.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.clubnumber = value;
						}
						if (prop.equals("nationalteam1")) {
							value = value.replaceAll(",", "#@#");
							a.nationalteam1 = value;
						}
						if (prop.equals("youthyears1")) {
							if(value.contains("|"))
								value=value.split("\\|")[0];
							value = value.replaceAll(",", "#@#");
							a.youthyears1 = value;
						}
						if (prop.equals("youthclubs1")) {
							value = value.replaceAll(",", "#@#");
							a.youthclubs1 = value;
						}
						if (prop.equals("nationalgoals1")) {
							Matcher m = GetFilmObjects.p4.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.nationalgoals1 = value;
						}
					}
				}
				catch(Exception e){e.printStackTrace();System.err.println(str);}
			}
			a.instance=lc;
			a.fullname_name_playername=orig.split("##")[0];
			out.write(a+"\n");
			lc++;
		}
		in.close();
		out.write("commit;\n");
		out.close();
	}
}

