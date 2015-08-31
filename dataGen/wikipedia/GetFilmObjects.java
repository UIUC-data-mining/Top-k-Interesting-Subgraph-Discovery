/**
 * 
 */
package dataGen.wikipedia;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses the infobox text to get film objects that can inserted into the database.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class GetFilmObjects {
	public static String baseDir = "";
	public static HashMap<String, String> typeMap = new HashMap<String, String>();
	public static Pattern p = Pattern.compile("\\{\\{.*?\\}\\}");
	public static Pattern p2 = Pattern.compile("\\[\\[.*?\\]\\]");
	public static Pattern p3 = Pattern.compile("\\b[1-2][0-9][0-9][0-9]\\b");
	public static Pattern p4 = Pattern.compile("\\b[0-9]+\\b");
	public static Pattern p5 = Pattern.compile("([0-9]+\\.*)+");
	public static Pattern p7 = Pattern.compile("([0-9]+:*)+");
	public static void main(String[] args) throws Throwable {
		
		if(System.getProperty("os.name").contains("Windows"))
			baseDir = GetInfoboxes.windowsBaseDir;
		else
			baseDir = GetInfoboxes.linuxBaseDir;
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(baseDir, "film.sql")));
		out.write("use gupta58;drop table film;create table film(instanceNum int,name_title varchar(512),language varchar(512),released_release varchar(512),director varchar(512),country varchar(512),starring varchar(512),runtime varchar(512),writer_writers varchar(512),producer varchar(512),cinematography varchar(512),music_movie_music varchar(512),distributor varchar(512),editing_editor varchar(512),budget varchar(512),studio varchar(512),gross varchar(512),screenplay varchar(512),basedOn varchar(512),story varchar(512),narrator varchar(512),awards varchar(512),lyrics varchar(512),genre varchar(512),rating varchar(512));\n");
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
			str=treatTextWithinDoubleBraces(str);
//			System.out.println("After: "+str);
			if(lc%10000==0)
				System.err.println("Read lines: "+lc);
			String tokens[]=str.split("##");
			String type=typeMap.get(orig.split("##")[0].toLowerCase());
			if(type==null||!type.equals("film"))
			{
				lc++;
				continue;
			}
			Film a= new Film();
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
						value=clean(value.trim());
						if(prop.equals("name")||prop.equals("title"))
							a.name_title=value;
						if(prop.equals("language"))
						{
							value=value.replace(',', ' ').replaceAll("\\s+", "#@#");
							a.language=value;
						}
						if(prop.equals("released")||prop.equals("release"))
						{
							Matcher m = p3.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.released_release=value;
						}
						if(prop.equals("director"))
						{
							value=value.replaceAll(",", "#@#");
							a.director=value;
						}
						if(prop.equals("country"))
						{
							value=value.replace("{{Film","").replace("{{film","").replace("}}","").replace("{{","").replace("United States","US").replace("United Kingdom","UK").replaceAll(",", "#@#");
							a.country=value;
						}
						if(prop.equals("starring"))
						{
							value=value.replaceAll(",", "#@#");
							a.starring=value;
						}
						if(prop.equals("runtime"))
						{
							value=value.replaceAll(",", "#@#");
							Matcher m = p4.matcher(value);
						       if(m.find()) {
						    	   value=value.substring(m.start(), m.end());
						       }
						       else
						    	   value="";
							a.runtime=value;
						}
						if(prop.equals("writer")||prop.equals("writers"))
						{
							value=value.replaceAll(",", "#@#");
							a.writer_writers=value;
						}
						if(prop.equals("producer"))
						{
							value=value.replaceAll(",", "#@#");
							a.producer=value;
						}
						if(prop.equals("cinematography"))
						{
							value=value.replaceAll(",", "#@#");
							a.cinematography=value;
						}
						if(prop.equals("music")||prop.equals("movie_music"))
						{
							value=value.replaceAll(",", "#@#");
							a.music_movie_music=value;
						}
						if(prop.equals("distributor"))
						{
							value=value.replaceAll(",", "#@#");
							a.distributor=value;
						}
						if(prop.equals("editing")||prop.equals("editor"))
						{
							value=value.replaceAll(",", "#@#");
							a.editing_editor=value;
						}
						if(prop.equals("budget"))
						{
//							String orig=value;
//							if(value.contains("$"))
//								value=value.substring(value.indexOf('$'));
//							value=value.replaceAll(",", "");//.replaceAll("\\s+million", "000000").replaceAll("&amp;nbsp;million;", "000000");
//							Matcher m = p5.matcher(value);
//						       if(m.find()) {
//						    	   value=value.substring(m.start(), m.end());
//						       }
//						       else
//						    	   value="";
//						       if(value.split("\\.").length>2)
//						    	   value=value.replaceAll("\\.", "");
//						       if(!value.equals(""))
//						       {
//							       int mul=1;
//							       double v=Double.parseDouble(value);
//							       if(orig.contains("million")&&v<1000)
//							    	   mul=1000000;
//							       value=((int)(v*mul))+"";
//						       }
							if(value.contains("{"))
								value=value.substring(0, value.indexOf('{'));
							a.budget=value;
						}
						if(prop.equals("studio"))
						{
							value=value.replaceAll(",", "#@#");
							a.studio=value;
						}
						if(prop.equals("gross"))
						{
//							String orig=value;
//							if(value.contains("$"))
//								value=value.substring(value.indexOf('$'));
//							value=value.replaceAll(",", "");//.replaceAll("\\s+million", "000000").replaceAll("&amp;nbsp;million;", "000000");
//							Matcher m = p5.matcher(value);
//						       if(m.find()) {
//						    	   value=value.substring(m.start(), m.end());
//						       }
//						       else
//						    	   value="";
//						       if(value.split("\\.").length>2)
//						    	   value=value.replaceAll("\\.", "");
//						       if(!value.equals(""))
//						       {
//							       int mul=1;
//							       if(orig.contains("million"))
//							    	   mul=1000000;
//							       value=((int)(Double.parseDouble(value)*mul))+"";
//						       }
							if(value.contains("{"))
								value=value.substring(0, value.indexOf('{'));
							a.gross=value;
						}
						if(prop.equals("screenplay"))
						{
							value=value.replaceAll(",", "#@#");
							a.screenplay=value;
						}
						if(prop.equals("based on"))
						{
							value=value.replaceAll(",", "#@#");
							a.basedOn=value;
						}
						if(prop.equals("story"))
						{
							value=value.replaceAll(",", "#@#");
							a.story=value;
						}
						if(prop.equals("narrator"))
						{
							value=value.replaceAll(",", "#@#");
							a.narrator=value;
						}
						if(prop.equals("awards"))
						{
							value=value.replaceAll(",", "#@#");
							a.awards=value;
						}
						if(prop.equals("lyrics"))
						{
							value=value.replaceAll(",", "#@#");
							a.lyrics=value;
						}
						if(prop.equals("genre"))
						{
							value=value.replaceAll(",", "#@#");
							a.genre=value;
						}
						if(prop.equals("rating"))
						{
							value=value.replaceAll(",", "#@#");
							a.rating=value;
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
	/**
	 * @param string
	 * @return
	 */
	public static String clean(String str) {
		Matcher m = p2.matcher(str);
		String newStr="";
		int old=0;
	       while(m.find()) {
	    	   newStr+=str.substring(old,m.start());
	    	   String tmp=str.substring(m.start()+2,m.end()-2);
	    	   if(tmp.contains("|")&&tmp.split("\\|").length>1)
	    		   newStr+=tmp.split("\\|")[1]+",";
	    	   else if(tmp.contains(",")&&tmp.split(",").length>1)
	    		   newStr+=tmp.split(",")[1]+",";
	    	   else
	    		   newStr+=tmp+",";
	    	   old=m.end();
	       }
	       newStr+=str.substring(old);
	    return newStr.trim();
	}
	public static String treatTextWithinDoubleBraces(String str) {
		str=str.replaceAll("\\t", "");
		str=str.replaceAll("id=\\{\\{.*?\\}\\}", "");
		str=str.replaceAll("\\{\\{cite.*?\\}\\}", "");
		str=str.replaceAll("\\{\\{Cite.*?\\}\\}", "");
		str=str.replaceAll("\\{\\{small.*?\\}\\}", "");
		str=str.replaceAll("\\{\\{Small.*?\\}\\}", "");
		str=str.replaceAll("\\{\\{dn.*?\\}\\}", "");
		str=str.replaceAll("\\{\\{Dn.*?\\}\\}", "");
		str=str.replaceAll("\\{\\{nowrap(.*?)\\}\\}", "$1");
		str=str.replaceAll("'''(.*?)'''", "$1");
		str=str.replaceAll("''(.*?)''", "$1");
		str=str.replaceAll("\\(.*?\\)", "");
		str=str.replaceAll("'''", "");
		while(str.contains("{{Unbulleted list")||str.contains("{{unbulleted list"))
		{
			int index1=str.indexOf("{{Unbulleted list");
			if(index1==-1)
				index1=str.indexOf("{{unbulleted list");
			int index=str.indexOf("}}",index1);
			int index2=str.indexOf("li_style");
			try{
				String tmp=str.substring(index1+17, index);
				if(index2!=-1)
					 tmp=str.substring(index1+17, index2);//http://en.wikipedia.org/wiki/Template:Unbulleted_list
				tmp=tmp.replaceAll("##", "").replaceFirst("\\|"," ").replace("|", "&lt;br /&gt;");
				str=((index1!=0)?str.substring(0, index1-1):"")+tmp+str.substring(index+2);
			}
			catch(Exception e){e.printStackTrace();System.err.println(str);break;};
		}
		while(str.contains("{{ubl"))
		{
			int index1=str.indexOf("{{ubl");
			int index=str.indexOf("}}",index1);
			try{
				String tmp=str.substring(index1+5, index);
				tmp=tmp.replaceAll("##", "").replaceFirst("\\|"," ").replace("|", "&lt;br /&gt;");
				str=((index1!=0)?str.substring(0, index1-1):"")+tmp+str.substring(index+2);
			}
			catch(Exception e){e.printStackTrace();System.err.println(str);break;};
		}
		while(str.contains("{{Plainlist")||str.contains("{{plainlist"))
		{
			int index1=str.indexOf("{{Plainlist");
			if(index1==-1)
				index1=str.indexOf("{{plainlist");
			int index=str.indexOf("}}",index1);
			try{
				str=((index1!=0)?str.substring(0, index1-1):"")+str.substring(index1+11, index).replaceAll("##", "").replaceFirst("\\*"," ").replace("*", "&lt;br /&gt;")+str.substring(index+2);
			}
			catch(Exception e){System.err.println(index+" "+index1+" "+ str);e.printStackTrace();break;};
		}
	       //  get a matcher object
	       Matcher m = p.matcher(str);
	       String newStr="";
	       int old=0;
	       while(m.find()) {
	    	   newStr+=str.substring(old,m.start());
	    	   newStr+=str.substring(m.start(),m.end()).replaceAll("##", "");
	    	   old=m.end();
	       }
	       newStr+=str.substring(old);
	       
	       m = p2.matcher(str);
	       newStr="";
	       old=0;
	       while(m.find()) {
	    	   newStr+=str.substring(old,m.start());
	    	   newStr+=str.substring(m.start(),m.end()).replaceAll("##", "");
	    	   old=m.end();
	       }
	       newStr+=str.substring(old);
	       return newStr.replaceAll("&lt;ref[^(&lt;ref)]*?ref&gt;", ",").replaceAll("&lt;ref.*?&gt;", ",").replaceAll("&lt;br&gt;", ",").replaceAll("&lt;br /&gt;", ",").replaceAll("&lt;.*?&gt;", " ").replaceAll("&lt;!--.*?--&gt;", " ").replaceAll("&[a-z0-9]*;", " ").replaceAll("nbsp;", " ").replaceAll("ndash;", "-").replaceAll("minus;", "-");
	}
}

