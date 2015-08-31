package dataGen.wikipedia;

/**
 * Represents the album type in Wikipedia dataset.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class Album {
	public String type;
	public String name;
	public String artist;
	public String released;
	public String genre;
	public String label;
	public String last_album;
	public String next_album;
	public String producer;
	public String length;
	public String recorded;
	public String single_1;
	public String single_1_date;
	public String single_2;
	public String single_2_date;
	public String single_3;
	public String single_3_date;
	public String single_4;
	public String single_4_date;
	public String single_5;
	public String single_5_date;
	public String chronology;
	public String upper_caption;
	public String lower_caption;
	public String language;
	public String background;
	public String longtype;
	public String format;
	public  int instance;
	public Album() {
		type=name=artist=released=genre=label=last_album=next_album=producer=length=recorded=single_1=single_1_date=single_2=single_2_date=single_3=single_3_date=single_4=single_4_date=single_5=single_5_date=chronology=upper_caption=lower_caption=language=background=longtype=format="";
	}
	@Override
	public String toString() {
		String str= (type+"\t"+name+"\t"+artist+"\t"+released+"\t"+genre+"\t"+label+"\t"+last_album+"\t"+next_album+"\t"+producer+"\t"+length+"\t"+recorded+"\t"+single_1+"\t"+single_1_date+"\t"+single_2+"\t"+single_2_date+"\t"+single_3+"\t"+single_3_date+"\t"+single_4+"\t"+single_4_date+"\t"+single_5+"\t"+single_5_date+"\t"+chronology+"\t"+upper_caption+"\t"+lower_caption+"\t"+language+"\t"+background+"\t"+longtype+"\t"+format).toLowerCase();
		str=Film.process(str);
		str="insert into album values("+instance+","+str+");";
		return str;
	}
}

