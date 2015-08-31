package dataGen.wikipedia;

/**
 * Represents the film type in Wikipedia dataset.
 * create table film(instanceNum int,name_title varchar(512),language varchar(512),released_release varchar(512),director varchar(512),country varchar(512),starring varchar(512),runtime varchar(512),writer varchar(512),producer varchar(512),cinematography varchar(512),music_movie_music varchar(512),distributor varchar(512),editing_editor varchar(512),caption varchar(512),budget varchar(512),studio varchar(512),gross varchar(512),screenplay varchar(512),basedOn varchar(512),story varchar(512),narrator varchar(512),awards varchar(512),lyrics varchar(512),genre varchar(512),writers varchar(512),rating varchar(512));
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class Film {
	public  String name_title;
	public  String language;
	public  String released_release;
	public  String director;
	public  String country;
	public  String starring;
	public  String runtime;
	public  String writer_writers;
	public  String producer;
	public  String cinematography;
	public  String music_movie_music;
	public  String distributor;
	public  String editing_editor;
	public  String budget;
	public  String studio;
	public  String gross;
	public  String screenplay;
	public  String basedOn;
	public  String story;
	public  String narrator;
	public  String awards;
	public  String lyrics;
	public  String genre;
	public  String rating;
	public  int instance;
	public Film() {
		name_title=language=released_release=director=country=starring=runtime=writer_writers=producer=cinematography=music_movie_music=distributor=editing_editor=budget=studio=gross=screenplay=basedOn=story=narrator=awards=lyrics=genre=rating="";
	}
	@Override
	public String toString() {
		String str= (name_title+"\t"+language+"\t"+released_release+"\t"+director+"\t"+country+"\t"+starring+"\t"+runtime+"\t"+writer_writers+"\t"+producer+"\t"+cinematography+"\t"+music_movie_music+"\t"+distributor+"\t"+editing_editor+"\t"+budget+"\t"+studio+"\t"+gross+"\t"+screenplay+"\t"+basedOn+"\t"+story+"\t"+narrator+"\t"+awards+"\t"+lyrics+"\t"+genre+"\t"+rating).toLowerCase();
		str=process(str);
		str="insert into film values("+instance+","+str+");";
		return str;
	}
	public static String process(String str){
		str=str.replaceAll("\\{\\{[^\\t]*?\\}\\}", "").replace('|', ' ').replaceAll("\\[[^\\t]*?\\]", " ").replaceAll("\\[\\[", "").replaceAll("\\]\\]", "").replaceAll("\\{\\{", "").replaceAll("\\}\\}", "");
		str=("\'"+str.replaceAll("'","_").replaceAll("\t", "\',\'").replaceAll("((#@#)+\\s*)+","#@#")+"\'").replaceAll("\\\\'", "'").replaceAll("\\r", "");
		return str;
	}
}

