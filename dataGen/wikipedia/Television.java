package dataGen.wikipedia;

/**
 * Represents the television type in Wikipedia dataset.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class Television {
	public String show_name;
	public String country;
	public String first_aired;
	public String runtime;
	public String last_aired;
	public String num_episodes;
	public String network;
	public String language;
	public String starring;
	public String genre;
	public String format;
	public String creator;
	public String num_seasons;
	public String website;
	public String producer;
	public String channel;
	public String picture_format;
	public String executive_producer;
	public String director;
	public String company;
	public String presenter;
	public String location;
	public String status;
	public String writer;
	public String opentheme;
	public String theme_music_composer;
	public String composer;
	public String camera;
	public String preceded_by;
	public String related;
	public String followed_by;
	public String audio_format;
	public String distributor;
	public String num_series;
	public String narrated;
	public String developer;
	public String editor;
	public int instance;
	public Television() {
		show_name=country=first_aired=runtime=last_aired=num_episodes=network=language=starring=genre=format=creator=num_seasons=website=producer=channel=picture_format=executive_producer=director=company=presenter=location=status=writer=opentheme=theme_music_composer=composer=camera=preceded_by=related=followed_by=audio_format=distributor=num_series=narrated=developer=editor="";
	}
	@Override
	public String toString() {
		String str= (show_name+"\t"+country+"\t"+first_aired+"\t"+runtime+"\t"+last_aired+"\t"+num_episodes+"\t"+network+"\t"+language+"\t"+starring+"\t"+genre+"\t"+format+"\t"+creator+"\t"+num_seasons+"\t"+website+"\t"+producer+"\t"+channel+"\t"+picture_format+"\t"+executive_producer+"\t"+director+"\t"+company+"\t"+presenter+"\t"+location+"\t"+status+"\t"+writer+"\t"+opentheme+"\t"+theme_music_composer+"\t"+composer+"\t"+camera+"\t"+preceded_by+"\t"+related+"\t"+followed_by+"\t"+audio_format+"\t"+distributor+"\t"+num_series+"\t"+narrated+"\t"+developer+"\t"+editor).toLowerCase();
		str=Film.process(str);
		str="insert into television values("+instance+","+str+");";
		return str;
	}
}

