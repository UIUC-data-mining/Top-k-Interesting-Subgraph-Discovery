package dataGen.wikipedia;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.regex.Matcher;

/**
 * Parses the infobox text to get musicalArtist objects that can inserted into the database.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class GetMusicalArtistObjects {
	public static String baseDir = "";
	public static HashMap<String, String> typeMap = new HashMap<String, String>();
	
	public static void main(String[] args) throws Throwable {
		
		if(System.getProperty("os.name").contains("Windows"))
			baseDir = GetInfoboxes.windowsBaseDir;
		else
			baseDir = GetInfoboxes.linuxBaseDir;
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, "musicalArtist.sql")));
		out.write("use gupta58;drop table musicalArtist;create table musicalArtist(instanceNum int,background varchar(512),name varchar(512),genre varchar(512),years_active varchar(512),origin varchar(512),label varchar(512),website_url varchar(512),associated_acts_associated_acts varchar(512),occupation_occupations varchar(512),instrument_instruments varchar(512),birth_name_birth_name varchar(512),current_members_members varchar(512),birth_date_born varchar(512),past_members_former_members varchar(512),alias varchar(512),birth_place varchar(512),death_date varchar(512),death_place varchar(512),spouse varchar(512),labels varchar(512),nationality varchar(512),influences varchar(512));\n");
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
			if(type==null||!type.equals("musical artist"))
			{
				lc++;
				continue;
			}
			MusicalArtist a= new MusicalArtist();
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
						if (prop.equals("background")) {
							value = value.replaceAll(",", "#@#");
							a.background = value;
						}
						if (prop.equals("name")) {
							value = value.replaceAll(",", "#@#");
							a.name = value;
						}
						if (prop.equals("genre")) {
							value = value.replaceAll(",", "#@#");
							a.genre = value;
						}
						if (prop.equals("years_active")) {
							value=value.replaceAll(",", "#@#").replaceAll("â", "-");
							if(value.contains("|"))
								value=value.substring(0, value.indexOf('|'));
							a.years_active = value;
						}
						if (prop.equals("origin")) {
							value = value.replaceAll(",", "#@#");
							a.origin = value;
						}
						if (prop.equals("label")) {
							value = value.replaceAll(",", "#@#");
							a.label = value;
						}
						if (prop.equals("website")||prop.equals("url")) {
							if(value.contains("{{URL|"))
								value=value.substring(6).replaceAll("}}", "");
							if(value.contains("["))
								value=value.split("\\s+")[0].replace('[', ' ').replace(']', ' ').trim();
							value = value.replaceAll(",", "#@#");
							a.website_url = value;
						}
						if (prop.equals("associated_acts")|| prop.equals("associated acts")) {
							value = value.replaceAll(",", "#@#");
							a.associated_acts_associated_acts = value;
						}
						if (prop.equals("occupation")|| prop.equals("occupations")) {
							value = value.replaceAll(",", "#@#");
							a.occupation_occupations = value;
						}
						if (prop.equals("instrument") || prop.equals("instruments")) {
							value = value.replaceAll(",", "#@#");
							a.instrument_instruments = value;
						}
						if (prop.equals("birth_name") || prop.equals("birth name")) {
							value = value.replaceAll(",", "#@#");
							a.birth_name_birth_name = value;
						}
						if (prop.equals("current_members")|| prop.equals("members")) {
							value = value.replaceAll(",", "#@#");
							a.current_members_members = value;
						}
						if (prop.equals("birth_date") || prop.equals("born")) {
							Matcher m = GetFilmObjects.p3.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.birth_date_born = value;
						}
						if (prop.equals("past_members")|| prop.equals("former_members")) {
							value = value.replaceAll(",", "#@#");
							a.past_members_former_members = value;
						}
						if (prop.equals("alias")) {
							value = value.replaceAll(",", "#@#");
							a.alias = value;
						}
						if (prop.equals("birth_place")) {
							value = value.replaceAll(",", "#@#");
							a.birth_place = value;
						}
						if (prop.equals("death_date")) {
							Matcher m = GetFilmObjects.p3.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.death_date = value;
						}
						if (prop.equals("death_place")) {
							value = value.replaceAll(",", "#@#");
							a.death_place = value;
						}
						if (prop.equals("spouse")) {
							value = value.replaceAll(",", "#@#");
							a.spouse = value;
						}
						if (prop.equals("labels")) {
							value = value.replaceAll(",", "#@#");
							a.labels = value;
						}
						if (prop.equals("nationality")) {
							value = value.replaceAll(",", "#@#");
							a.nationality = value;
						}
						if (prop.equals("influences")) {
							value = value.replaceAll(",", "#@#");
							a.influences = value;
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

