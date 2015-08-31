package dataGen.wikipedia;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.regex.Matcher;

/**
 * Parses the infobox text to get nrhp objects that can inserted into the database.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class GetNRHPObjects {
	public static String baseDir = "";
	public static HashMap<String, String> typeMap = new HashMap<String, String>();
	
	public static void main(String[] args) throws Throwable {
		if(System.getProperty("os.name").contains("Windows"))
			baseDir = GetInfoboxes.windowsBaseDir;
		else
			baseDir = GetInfoboxes.linuxBaseDir;
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, "nrhp.sql")));
		out.write("use gupta58;drop table nrhp;create table nrhp(instanceNum int,added varchar(512),refnum varchar(512),governing_body varchar(512),lat_degrees_latitude varchar(512),long_degrees varchar(512),locmapin varchar(512),long_direction varchar(512),lat_direction varchar(512),location varchar(512),long_minutes varchar(512),lat_minutes varchar(512),long_seconds varchar(512),lat_seconds varchar(512),built varchar(512),architecture varchar(512),name varchar(512),architect_architect_or_builder varchar(512),area varchar(512),nrhp_type varchar(512),mpsub varchar(512),nearest_city varchar(512),coord_parameters_type varchar(512),coord_parameters_region varchar(512),designated_nrhp_type varchar(512),designated_other1_name varchar(512),designated_other1_abbr varchar(512),designated_other1_date varchar(512),partof  varchar(512));\n");
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
			if(type==null||!type.equals("nrhp"))
			{
				lc++;
				continue;
			}
			NRHP a= new NRHP();
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
						if (prop.equals("added")) {
							Matcher m = GetFilmObjects.p3.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.added = value;
						}
						if (prop.equals("refnum")) {
							Matcher m = GetFilmObjects.p4.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.refnum = value;
						}
						if (prop.equals("governing_body")) {
							value = value.replaceAll(",", "#@#");
							a.governing_body = value;
						}
						if (prop.equals("lat_degrees")||prop.equals("latitude")) {
							a.lat_degrees_latitude = value;
							if(prop.equals("lat_degrees") && value.contains("|"))
							{
								a.lat_degrees_latitude = value.split("\\|")[0];
								String toks2[]=value.split("\\|");
								for(String t2:toks2)
								{
									String toks3[]=t2.split("\\s+");
									if(toks3.length==2)
									{
										if(toks3[0].trim().equals("lat_minutes"))
											a.lat_minutes=toks3[1].trim();
										if(toks3[0].trim().equals("lat_seconds"))
											a.lat_seconds=toks3[1].trim();
										if(toks3[0].trim().equals("lat_direction"))
											a.lat_direction=toks3[1].trim();
									}
								}
							}
							if(a.lat_degrees_latitude.indexOf('.')!=-1)
								a.lat_degrees_latitude=a.lat_degrees_latitude.substring(0, a.lat_degrees_latitude.indexOf('.'));
						}
						if (prop.equals("long_degrees")) {
							a.long_degrees= value;
							if(value.contains("|"))
							{
								a.long_degrees = value.split("\\|")[0];
								String toks2[]=value.split("\\|");
								for(String t2:toks2)
								{
									String toks3[]=t2.split("\\s+");
									if(toks3.length==2)
									{
										if(toks3[0].trim().equals("long_minutes"))
											a.long_minutes=toks3[1].trim();
										if(toks3[0].trim().equals("long_seconds"))
											a.long_seconds=toks3[1].trim();
										if(toks3[0].trim().equals("long_direction"))
											a.long_direction=toks3[1].trim();
									}
								}
							}
							if(a.long_degrees.indexOf('.')!=-1)
								a.long_degrees=a.long_degrees.substring(0, a.long_degrees.indexOf('.'));
						}
						if (prop.equals("locmapin")) {
							value = value.replaceAll(",", "#@#");
							a.locmapin = value;
						}
						if (prop.equals("long_direction")) {
							value = value.replaceAll(",", "#@#");
							a.long_direction = value;
						}
						if (prop.equals("lat_direction")) {
							value = value.replaceAll(",", "#@#");
							a.lat_direction = value;
						}
						if (prop.equals("location")) {
							value = value.replaceAll(",", "#@#");
							a.location = value;
						}
						if (prop.equals("long_minutes")) {
							value = value.replaceAll(",", "#@#");
							a.long_minutes = value;
						}
						if (prop.equals("lat_minutes")) {
							value = value.replaceAll(",", "#@#");
							a.lat_minutes = value;
						}
						if (prop.equals("long_seconds")) {
							value = value.replaceAll(",", "#@#");
							a.long_seconds = value;
						}
						if (prop.equals("lat_seconds")) {
							value = value.replaceAll(",", "#@#");
							a.lat_seconds = value;
						}
						if (prop.equals("built")) {
							Matcher m = GetFilmObjects.p3.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.built = value;
						}
						if (prop.equals("architecture")) {
							value = value.replaceAll(",", "#@#");
							a.architecture = value;
						}
						if (prop.equals("architect")||prop.equals("architect or builder")) {
							value = value.replaceAll(",", "#@#");
							a.architect_architect_or_builder = value;
						}
						if (prop.equals("area")) {
							value=value.toLowerCase();
							if(value.contains("{{convert") && value.split("\\|").length>=3)
							{
								value=value.split("\\|")[1]+" "+value.split("\\|")[2];
								a.area = value;
							}
						}
						if (prop.equals("nrhp_type")) {
							if(value.contains("|"))
								value=value.split("\\|")[0];
							value = value.replaceAll(",", "#@#");
							a.nrhp_type = value;
						}
						if (prop.equals("mpsub")) {
							while(value.contains("["))
							{
								int index1=value.indexOf('[');
								int index=value.indexOf(']',index1);
								try{
									String tmp=value.substring(index1+1, index);
									String tokens2[]=tmp.split("\\s+");
									tmp="";
									for(int t2=1;t2<tokens2.length;t2++)
										tmp+=tokens2[t2]+" ";
									value=((index1!=0)?value.substring(0, index1-1):"")+tmp+value.substring(index+1);
								}
								catch(Exception e){e.printStackTrace();System.err.println(value);break;};
							}
							value = value.replaceAll(",", "#@#");
							a.mpsub = value;
						}
						if (prop.equals("nearest_city")) {
							value = value.replaceAll(",", "#@#");
							a.nearest_city = value;
						}
						if (prop.equals("coord_parameters")) {
							String tokens2[]=value.split("_");
							for(String t2:tokens2)
							{
								if(t2.contains("region:"))
									a.coord_parameters_region = t2.split(":")[1];
								if(t2.contains("type:"))
									a.coord_parameters_type = t2.split(":")[1];
							}
						}
						if (prop.equals("designated_nrhp_type")) {
							Matcher m = GetFilmObjects.p3.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.designated_nrhp_type = value;
						}
						if (prop.equals("designated_other1_name")) {
							value = value.replaceAll(",", "#@#");
							a.designated_other1_name = value;
						}
						if (prop.equals("designated_other1_abbr")) {
							value = value.replaceAll(",", "#@#");
							a.designated_other1_abbr = value;
						}
						if (prop.equals("designated_other1_date")) {
							Matcher m = GetFilmObjects.p3.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.designated_other1_date = value;
						}
						if (prop.equals("partof")) {
							value = value.replaceAll(",", "#@#");
							a.partof = value;
						}
					}
				}
				catch(Exception e){e.printStackTrace();System.err.println(str);}
			}
			if(a.lat_minutes.indexOf('.')!=-1)
				a.lat_minutes=a.lat_minutes.substring(0, a.lat_minutes.indexOf('.'));
			if(a.lat_seconds.indexOf('.')!=-1)
				a.lat_seconds=a.lat_seconds.substring(0, a.lat_seconds.indexOf('.'));
			if(a.long_minutes.indexOf('.')!=-1)
				a.long_minutes=a.long_minutes.substring(0, a.long_minutes.indexOf('.'));
			if(a.long_seconds.indexOf('.')!=-1)
				a.long_seconds=a.long_seconds.substring(0, a.long_seconds.indexOf('.'));
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

