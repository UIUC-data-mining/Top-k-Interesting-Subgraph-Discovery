package dataGen.wikipedia;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.regex.Matcher;

/**
 * Parses the infobox text to get company objects that can inserted into the database.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class GetCompanyObjects {
	public static String baseDir = "";
	public static HashMap<String, String> typeMap = new HashMap<String, String>();
	
	public static void main(String[] args) throws Throwable {
		if(System.getProperty("os.name").contains("Windows"))
			baseDir = GetInfoboxes.windowsBaseDir;
		else
			baseDir = GetInfoboxes.linuxBaseDir;
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, "company.sql")));
		out.write("use gupta58;drop table company;create table company(instanceNum int,foundation_year varchar(512),industry varchar(512),homepage varchar(512),location varchar(512),key_people varchar(512),company_type varchar(512),products varchar(512),num_employees varchar(512),location_city varchar(512),location_country varchar(512),revenue varchar(512),name varchar(512),area_served varchar(512),founder varchar(512),parent varchar(512),company_slogan varchar(512),net_income varchar(512),operating_income varchar(512),intl varchar(512),assets varchar(512),owner varchar(512),services varchar(512),defunct varchar(512),traded_as varchar(512),subsid varchar(512),slogan varchar(512),genre varchar(512),equity varchar(512),fate varchar(512),predecessor varchar(512),divisions varchar(512),successor varchar(512),aum varchar(512));\n");
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
			if(type==null||!type.equals("company"))
			{
				lc++;
				continue;
			}
			Company a= new Company();
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
						if(prop.equals("name")||prop.equals("company_name"))
							a.name=value;
						if(prop.equals("foundation"))
						{
							Matcher m = GetFilmObjects.p3.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.foundation_year=value;
						}
						if(prop.equals("genre"))
						{
							value=value.replaceAll(",", "#@#");
							a.genre=value;
						}
						if (prop.equals("industry")) {
							value = value.replaceAll(",", "#@#");
							a.industry = value;
						}
						if (prop.equals("homepage")) {
							if(value.contains("{{URL|"))
								value=value.substring(6).replaceAll("}}", "");
							if(value.contains("["))
								value=value.split("\\s+")[0].replace('[', ' ').replace(']', ' ').trim();
							value = value.replaceAll(",", "#@#");
							a.homepage = value;
						}
						if (prop.equals("location")||prop.equals("locations")) {
							value += value.replaceAll(",", "#@#")+"#@#";
							a.location = value;
						}
						if (prop.equals("key_people")) {
							value = value.replaceAll(",", "#@#");
							a.key_people = value;
						}
						if (prop.equals("company_type")||prop.equals("type")) {
							value+= value.replaceAll(",", "#@#")+"#@#";
							a.company_type = value;
						}
						if (prop.equals("products")) {
							value = value.replaceAll(",", "#@#");
							a.products = value;
						}
						if (prop.equals("location_city")) {
							value = value.replaceAll(",", "#@#");
							a.location_city = value;
						}
						if (prop.equals("location_country")) {
							value = value.replaceAll(",", "#@#");
							a.location_country = value;
						}
						if (prop.equals("area_served")) {
							value = value.replaceAll(",", "#@#");
							a.area_served = value;
						}
						if (prop.equals("founder")) {
							value = value.replaceAll(",", "#@#");
							a.founder = value;
						}
						if (prop.equals("parent")) {
							value = value.replaceAll(",", "#@#");
							a.parent = value;
						}
						if (prop.equals("company_slogan")) {
							value = value.replaceAll(",", "#@#");
							a.company_slogan = value;
						}
						if (prop.equals("intl")) {
							value = value.replaceAll(",", "#@#");
							a.intl = value;
						}
						if (prop.equals("assets")) {
							value = value.replaceAll(",", "#@#");
							a.assets = value;
						}
						if (prop.equals("owner")) {
							value = value.replaceAll(",", "#@#");
							a.owner = value;
						}
						if (prop.equals("services")) {
							value = value.replaceAll(",", "#@#");
							a.services = value;
						}
						if (prop.equals("defunct")) {
							Matcher m = GetFilmObjects.p3.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.defunct = value;
						}
						if (prop.equals("traded_as")) {
							value = value.replaceAll(",", "#@#");
							a.traded_as = value;
						}
						if (prop.equals("subsid")) {
							value = value.replaceAll(",", "#@#");
							a.subsid = value;
						}
						if (prop.equals("slogan")) {
							value = value.replaceAll(",", "#@#");
							a.slogan = value;
						}
						if (prop.equals("genre")) {
							value = value.replaceAll(",", "#@#");
							a.genre = value;
						}
						if (prop.equals("equity")) {
							value = value.replaceAll(",", "#@#");
							a.equity = value;
						}
						if (prop.equals("fate")) {
							value = value.replaceAll(",", "#@#");
							a.fate = value;
						}
						if (prop.equals("predecessor")) {
							value = value.replaceAll(",", "#@#");
							a.predecessor = value;
						}
						if (prop.equals("divisions")) {
							value = value.replaceAll(",", "#@#");
							a.divisions = value;
						}
						if (prop.equals("successor")) {
							value = value.replaceAll(",", "#@#");
							a.successor = value;
						}
						if (prop.equals("aum")) {
							value = value.replaceAll(",", "#@#");
							a.aum = value;
						}
						if (prop.equals("num_employees")) {
							value = value.replaceAll(",", "");
							Matcher m = GetFilmObjects.p4.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.num_employees = value;
						}
						if (prop.equals("revenue")) {
							value = value.replaceAll(",", "#@#");
							a.revenue = value;
						}
						if (prop.equals("net_income")) {
							value = value.replaceAll(",", "#@#");
							a.net_income = value;
						}
						if (prop.equals("operating_income")) {
							value = value.replaceAll(",", "#@#");
							a.operating_income = value;
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

