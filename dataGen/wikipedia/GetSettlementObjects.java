package dataGen.wikipedia;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.regex.Matcher;

/**
 * Parses the infobox text to get settlement objects that can inserted into the database.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class GetSettlementObjects {
	public static String baseDir = "";
	public static HashMap<String, String> typeMap = new HashMap<String, String>();
	
	public static void main(String[] args) throws Throwable {
		if(System.getProperty("os.name").contains("Windows"))
			baseDir = GetInfoboxes.windowsBaseDir;
		else
			baseDir = GetInfoboxes.linuxBaseDir;
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, "settlement.sql")));
		out.write("use gupta58;drop table settlement;create table settlement(instanceNum int,subdivision_name varchar(512),subdivision_type varchar(512),subdivision_type1 varchar(512),subdivision_name1 varchar(512),latd varchar(512),settlement_type varchar(512),longd varchar(512),subdivision_type2 varchar(512),subdivision_name2 varchar(512),coordinates_region varchar(512),pushpin_map varchar(512),timezone varchar(512),utc_offset varchar(512),population_total varchar(512),name varchar(512),official_name varchar(512),subdivision_type3 varchar(512),area_total_km2 varchar(512),subdivision_name3 varchar(512),unit_pref varchar(512),postal_code_type varchar(512),population_density_km2 varchar(512),area_land_km2 varchar(512),elevation_m varchar(512),total_type varchar(512),latns varchar(512),longew varchar(512),longm varchar(512),latm varchar(512),elevation_ft  varchar(512),lats varchar(512),longs varchar(512));\n");
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
			if(type==null||!type.equals("settlement"))
			{
				lc++;
				continue;
			}
			Settlement a= new Settlement();
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
						if (prop.equals("subdivision_name")) {
							value = value.replaceAll(",", "#@#");
							a.subdivision_name = value;
						}
						if (prop.equals("subdivision_type")) {
							value = value.replaceAll(",", "#@#");
							a.subdivision_type = value;
						}
						if (prop.equals("subdivision_type1")) {
							value = value.replaceAll(",", "#@#");
							a.subdivision_type1 = value;
						}
						if (prop.equals("subdivision_name1")) {
							value = value.replaceAll(",", "#@#");
							a.subdivision_name1 = value;
						}
						if (prop.equals("latd")) {
							a.latd = value;
							if(value.contains("|"))
							{
								if(value.split("\\|").length>0)
									a.latd = value.split("\\|")[0];
								String toks2[]=value.split("\\|");
								for(String t2:toks2)
								{
									String toks3[]=t2.split("\\s+");
									if(toks3.length==2)
									{
										if(toks3[0].trim().equals("latm"))
											a.latm=toks3[1].trim();
										if(toks3[0].trim().equals("lats"))
											a.lats=toks3[1].trim();
										if(toks3[0].trim().equals("latns"))
											a.latns=toks3[1].trim();
										if(toks3[0].trim().equals("longd"))
											a.longd=toks3[1].trim();
										if(toks3[0].trim().equals("longm"))
											a.longm=toks3[1].trim();
										if(toks3[0].trim().equals("longs"))
											a.longs=toks3[1].trim();
										if(toks3[0].trim().equals("longew"))
											a.longew=toks3[1].trim();
									}
								}
							}
							if(a.latd.indexOf('.')!=-1)
								a.latd=a.latd.substring(0, a.latd.indexOf('.'));
						}
						if (prop.equals("settlement_type")) {
							value = value.replaceAll(",", "#@#");
							a.settlement_type = value;
						}
						if (prop.equals("longd")) {
							a.longd = value;
							if(value.contains("|"))
							{
								if(value.split("\\|").length>0)
									a.longd = value.split("\\|")[0];
								String toks2[]=value.split("\\|");
								for(String t2:toks2)
								{
									String toks3[]=t2.split("\\s+");
									if(toks3.length==2)
									{
										if(toks3[0].trim().equals("latm"))
											a.latm=toks3[1].trim();
										if(toks3[0].trim().equals("lats"))
											a.lats=toks3[1].trim();
										if(toks3[0].trim().equals("latns"))
											a.latns=toks3[1].trim();
										if(toks3[0].trim().equals("latd"))
											a.latd=toks3[1].trim();
										if(toks3[0].trim().equals("longm"))
											a.longm=toks3[1].trim();
										if(toks3[0].trim().equals("longs"))
											a.longs=toks3[1].trim();
										if(toks3[0].trim().equals("longew"))
											a.longew=toks3[1].trim();
									}
								}
							}
							if(a.longd.indexOf('.')!=-1)
								a.longd=a.longd.substring(0, a.longd.indexOf('.'));
						}
						if (prop.equals("subdivision_type2")) {
							value = value.replaceAll(",", "#@#");
							a.subdivision_type2 = value;
						}
						if (prop.equals("subdivision_name2")) {
							value = value.replaceAll(",", "#@#");
							a.subdivision_name2 = value;
						}
						if (prop.equals("coordinates_region")) {
							value = value.replaceAll(",", "#@#");
							a.coordinates_region = value;
						}
						if (prop.equals("pushpin_map")) {
							value = value.replaceAll(",", "#@#");
							a.pushpin_map = value;
						}
						if (prop.equals("timezone")) {
							value = value.replaceAll(",", "#@#");
							a.timezone = value;
						}
						if (prop.equals("utc_offset")) {
							value = value.replaceAll(",", "#@#");
							a.utc_offset = value;
						}
						if (prop.equals("population_total")) {
							value = value.replaceAll(",", "");
							Matcher m = GetFilmObjects.p4.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.population_total = value;
						}
						if (prop.equals("name")) {
							value = value.replaceAll(",", "#@#");
							a.name = value;
						}
						if (prop.equals("official_name")) {
							value = value.replaceAll(",", "#@#");
							a.official_name = value;
						}
						if (prop.equals("subdivision_type3")) {
							value = value.replaceAll(",", "#@#");
							a.subdivision_type3 = value;
						}
						if (prop.equals("area_total_km2")) {
							value = value.replaceAll(",", "");
							Matcher m = GetFilmObjects.p4.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.area_total_km2 = value;
						}
						if (prop.equals("subdivision_name3")) {
							value = value.replaceAll(",", "#@#");
							a.subdivision_name3 = value;
						}
						if (prop.equals("unit_pref")) {
							value = value.replaceAll(",", "#@#");
							a.unit_pref = value;
						}
						if (prop.equals("postal_code_type")) {
							value = value.replaceAll(",", "#@#").replaceAll("#@#s", "#@#");
							a.postal_code_type = value;
						}
						if (prop.equals("population_density_km2")) {
							value = value.replaceAll(",", "");
							a.population_density_km2 = value;
						}
						if (prop.equals("area_land_km2")) {
							value = value.replaceAll(",", "");
							a.area_land_km2 = value;
						}
						if (prop.equals("elevation_m")) {
							value = value.replaceAll(",", "");
							a.elevation_m = value;
						}
						if (prop.equals("total_type")) {
							value = value.replaceAll(",", "#@#");
							a.total_type = value;
						}
						if (prop.equals("latns")) {
							a.latns = value;
							if(value.contains("|"))
							{
								if(value.split("\\|").length>0)
									a.latns = value.split("\\|")[0];
								String toks2[]=value.split("\\|");
								for(String t2:toks2)
								{
									String toks3[]=t2.split("\\s+");
									if(toks3.length==2)
									{
										if(toks3[0].trim().equals("latm"))
											a.latm=toks3[1].trim();
										if(toks3[0].trim().equals("lats"))
											a.lats=toks3[1].trim();
										if(toks3[0].trim().equals("longd"))
											a.longd=toks3[1].trim();
										if(toks3[0].trim().equals("latd"))
											a.latd=toks3[1].trim();
										if(toks3[0].trim().equals("longm"))
											a.longm=toks3[1].trim();
										if(toks3[0].trim().equals("longs"))
											a.longs=toks3[1].trim();
										if(toks3[0].trim().equals("longew"))
											a.longew=toks3[1].trim();
									}
								}
							}
						}
						if (prop.equals("longew")) {
							a.longew = value;
							if(value.contains("|"))
							{
								if(value.split("\\|").length>0)
									a.longew = value.split("\\|")[0];
								String toks2[]=value.split("\\|");
								for(String t2:toks2)
								{
									String toks3[]=t2.split("\\s+");
									if(toks3.length==2)
									{
										if(toks3[0].trim().equals("latm"))
											a.latm=toks3[1].trim();
										if(toks3[0].trim().equals("lats"))
											a.lats=toks3[1].trim();
										if(toks3[0].trim().equals("latns"))
											a.latns=toks3[1].trim();
										if(toks3[0].trim().equals("latd"))
											a.latd=toks3[1].trim();
										if(toks3[0].trim().equals("longm"))
											a.longm=toks3[1].trim();
										if(toks3[0].trim().equals("longs"))
											a.longs=toks3[1].trim();
										if(toks3[0].trim().equals("longd"))
											a.longd=toks3[1].trim();
									}
								}
							}
						}
						if (prop.equals("longm")) {
							a.longm=value;
							if(value.contains("|"))
							{
								if(value.split("\\|").length>0)
									a.longm = value.split("\\|")[0];
								String toks2[]=value.split("\\|");
								for(String t2:toks2)
								{
									String toks3[]=t2.split("\\s+");
									if(toks3.length==2)
									{
										if(toks3[0].trim().equals("latm"))
											a.latm=toks3[1].trim();
										if(toks3[0].trim().equals("lats"))
											a.lats=toks3[1].trim();
										if(toks3[0].trim().equals("latns"))
											a.latns=toks3[1].trim();
										if(toks3[0].trim().equals("latd"))
											a.latd=toks3[1].trim();
										if(toks3[0].trim().equals("longew"))
											a.longew=toks3[1].trim();
										if(toks3[0].trim().equals("longs"))
											a.longs=toks3[1].trim();
										if(toks3[0].trim().equals("longd"))
											a.longd=toks3[1].trim();
									}
								}
							}
							if(a.longm.indexOf('.')!=-1)
								a.longm=a.longm.substring(0, a.longm.indexOf('.'));
						}
						if (prop.equals("latm")) {
							a.latm = value;
							if(value.contains("|"))
							{
								if(value.split("\\|").length>0)
									a.latm = value.split("\\|")[0];
								String toks2[]=value.split("\\|");
								for(String t2:toks2)
								{
									String toks3[]=t2.split("\\s+");
									if(toks3.length==2)
									{
										if(toks3[0].trim().equals("longew"))
											a.longew=toks3[1].trim();
										if(toks3[0].trim().equals("lats"))
											a.lats=toks3[1].trim();
										if(toks3[0].trim().equals("latns"))
											a.latns=toks3[1].trim();
										if(toks3[0].trim().equals("latd"))
											a.latd=toks3[1].trim();
										if(toks3[0].trim().equals("longm"))
											a.longm=toks3[1].trim();
										if(toks3[0].trim().equals("longs"))
											a.longs=toks3[1].trim();
										if(toks3[0].trim().equals("longd"))
											a.longd=toks3[1].trim();
									}
								}
							}
							if(a.latm.indexOf('.')!=-1)
								a.latm=a.latm.substring(0, a.latm.indexOf('.'));
						}
						if (prop.equals("elevation_ft")) {
							value = value.replaceAll(",", "#@#");
							a.elevation_ft = value;
						}
					}
				}
				catch(Exception e){e.printStackTrace();System.err.println(str);}
			}
			a.instance=lc;
			if(a.longs.indexOf('.')!=-1)
				a.longs=a.longs.substring(0, a.longs.indexOf('.'));
			if(a.lats.indexOf('.')!=-1)
				a.lats=a.lats.substring(0, a.lats.indexOf('.'));
			
			a.name=orig.split("##")[0];
			out.write(a+"\n");
			lc++;
		}
		in.close();
		out.write("commit;\n");
		out.close();
	}
}

