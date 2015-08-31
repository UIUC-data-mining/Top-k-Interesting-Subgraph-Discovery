package dataGen.wikipedia;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.regex.Matcher;

/**
 * Parses the infobox text to get person objects that can inserted into the database.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class GetPersonObjects {
	public static String baseDir = "";
	public static HashMap<String, String> typeMap = new HashMap<String, String>();
	
	public static void main(String[] args) throws Throwable {
		if(System.getProperty("os.name").contains("Windows"))
			baseDir = GetInfoboxes.windowsBaseDir;
		else
			baseDir = GetInfoboxes.linuxBaseDir;
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, "person.sql")));
		out.write("use gupta58;drop table person;create table person(instanceNum int,name_title  varchar(512), birth_date  varchar(512), birth_place  varchar(512), occupation  varchar(512), death_date  varchar(512), death_place  varchar(512), spouse  varchar(512), nationality  varchar(512), yearsactive_years_active  varchar(512), known_for  varchar(512), website  varchar(512), children  varchar(512), residence  varchar(512), parents  varchar(512), alma_mater  varchar(512), religion  varchar(512), employer  varchar(512), ethnicity  varchar(512), home_town  varchar(512), notable_role  varchar(512), relatives  varchar(512));\n");
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
			if(type==null||!type.equals("person"))
			{
				lc++;
				continue;
			}
			Person a= new Person();
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
						if(prop.equals("name")||prop.equals("title"))
							a.name_title=value;
						if(prop.equals("birth_date"))
						{
							Matcher m = GetFilmObjects.p3.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.birth_date=value;
						}
						if(prop.equals("birth_place"))
						{
							value=value.replaceAll(",", "#@#");
							a.birth_place=value;
						}
						if(prop.equals("occupation"))
						{
							value=value.replaceAll(",", "#@#");
							a.occupation=value;
						}
						if(prop.equals("death_date"))
						{
							Matcher m = GetFilmObjects.p3.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.death_date=value;
						}
						if(prop.equals("death_place"))
						{
							value=value.replaceAll(",", "#@#");
							a.death_place=value;
						}
						if(prop.equals("spouse"))
						{
							value=value.replaceAll(",", "#@#");
							a.spouse=value;
						}
						if(prop.equals("nationality")||prop.equals("citizenship"))
						{
							value=value.replaceAll(",", "#@#");
							if(value.contains("{{flag"))
							{
								int index=value.indexOf("{{flag");
								int index2=value.indexOf("|", index);
								if(index2==-1)
									value=value.substring(index+5);
								else
									value=value.substring(index2+1);
							}
							a.nationality=value.replaceAll("}}", "");;
						}
						if(prop.equals("yearsactive")||prop.equals("years_active"))
						{
							value=value.replaceAll(",", "#@#").replaceAll("â", "-");
							if(value.contains("|"))
								value=value.substring(0, value.indexOf('|'));
							a.yearsactive_years_active=value;
						}
						if(prop.equals("known_for"))
						{
							value=value.replaceAll(",", "#@#");
							a.known_for=value;
						}
						if(prop.equals("website"))
						{
							if(value.contains("{{URL|"))
								value=value.substring(6).replaceAll("}}", "");
							if(value.contains("["))
								value=value.split("\\s+")[0].replace('[', ' ').replace(']', ' ').trim();
							value=value.replaceAll(",", "#@#");
							a.website=value;
						}
						if(prop.equals("children"))
						{
							value=value.replaceAll(",", "#@#");
							a.children=value;
						}
						if(prop.equals("residence"))
						{
							value=value.replaceAll(",", "#@#");
							a.residence=value;
						}
						if(prop.equals("parents"))
						{
							value=value.replaceAll(",", "#@#");
							a.parents=value;
						}
						if(prop.equals("alma_mater")||prop.equals("education"))
						{
							value=value.replaceAll(",", "#@#");
							a.alma_mater+=value+"#@#";
						}
						if(prop.equals("religion"))
						{
							value=value.replaceAll(",", "#@#");
							a.religion=value;
						}
						if(prop.equals("employer"))
						{
							value=value.replaceAll(",", "#@#");
							a.employer=value;
						}
						if(prop.equals("ethnicity"))
						{
							value=value.replaceAll(",", "#@#");
							a.ethnicity=value;
						}
						if(prop.equals("home_town"))
						{
							value=value.replaceAll(",", "#@#");
							a.home_town=value;
						}
						if(prop.equals("notable role"))
						{
							value=value.replaceAll(",", "#@#");
							a.notable_role=value;
						}
						if(prop.equals("relatives"))
						{
							value=value.replaceAll(",", "#@#");
							a.relatives=value;
						}
					}
				}
				catch(Exception e){e.printStackTrace();System.err.println(str);}
			}
			a.instance=lc;
			a.name_title=orig.split("##")[0];
			out.write(a+"\n");
			lc++;
		}
		in.close();
		out.write("commit;\n");
		out.close();
	}
}

