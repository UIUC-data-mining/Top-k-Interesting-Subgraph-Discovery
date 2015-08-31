package dataGen.wikipedia;

/**
 * Represents the single type in Wikipedia dataset.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class Single {
	public String artist;
	public String name;
	public String released;
	public String genre;
	public String label;
	public String next_single;
	public String last_single;
	public String length;
	public String format;
	public String writer;
	public String producer;
	public String recorded;
	public String from_album;
	public String album;
	public String b_side;
	public String type;
	public String chronology;
	public String track_no;
	public String a_side;
	public  int instance;
	public Single() {
		artist=name=released=genre=label=next_single=last_single=length=format=writer=producer=recorded=from_album=album=b_side=type=chronology=track_no=a_side="";
	}
	@Override
	public String toString() {
		String str= (artist+"\t"+name+"\t"+released+"\t"+genre+"\t"+label+"\t"+next_single+"\t"+last_single+"\t"+length+"\t"+format+"\t"+writer+"\t"+producer+"\t"+recorded+"\t"+from_album+"\t"+album+"\t"+b_side+"\t"+type+"\t"+chronology+"\t"+track_no+"\t"+a_side).toLowerCase();
		str=Film.process(str);
		str="insert into single values("+instance+","+str+");";
		return str;
	}
}

