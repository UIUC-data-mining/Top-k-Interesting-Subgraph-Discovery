package dataGen.wikipedia;

/**
 * Represents the footballBiography type in Wikipedia dataset.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class FootballBiography {
	public String position;
	public String fullname_name_playername;
	public String years1;
	public String years2;
	public String years3;
	public String years4;
	public String years5;
	public String years6;
	public String years7;
	public String clubs1;
	public String clubs2;
	public String clubs3;
	public String clubs4;
	public String clubs5;
	public String height;
	public String currentclub;
	public String cityofbirth_birth_place;
	public String countryofbirth;
	public String caps1;
	public String caps2;
	public String caps3;
	public String caps4;
	public String caps5;
	public String birth_date_dateofbirth;
	public String pcupdate;
	public String nationalyears1;
	public String goals1;
	public String goals2;
	public String goals3;
	public String goals4;
	public String nationalcaps1;
	public String clubnumber;
	public String nationalteam1;
	public String youthyears1;
	public String youthclubs1;
	public String nationalgoals1;
	public int instance;
	public FootballBiography() {
		position=fullname_name_playername=years1=years2=years3=years4=years5=years6=years7=clubs1=clubs2=clubs3=clubs4=clubs5=height=currentclub=cityofbirth_birth_place=countryofbirth=caps1=caps2=caps3=caps4=caps5=birth_date_dateofbirth=pcupdate=nationalyears1=goals1=goals2=goals3=goals4=nationalcaps1=clubnumber=nationalteam1=youthyears1=youthclubs1=nationalgoals1="";
	}
	@Override
	public String toString() {
		String str= (position+"\t"+fullname_name_playername+"\t"+years1+"\t"+years2+"\t"+years3+"\t"+years4+"\t"+years5+"\t"+years6+"\t"+years7+"\t"+clubs1+"\t"+clubs2+"\t"+clubs3+"\t"+clubs4+"\t"+clubs5+"\t"+height+"\t"+currentclub+"\t"+cityofbirth_birth_place+"\t"+countryofbirth+"\t"+caps1+"\t"+caps2+"\t"+caps3+"\t"+caps4+"\t"+caps5+"\t"+birth_date_dateofbirth+"\t"+pcupdate+"\t"+nationalyears1+"\t"+goals1+"\t"+goals2+"\t"+goals3+"\t"+goals4+"\t"+nationalcaps1+"\t"+clubnumber+"\t"+nationalteam1+"\t"+youthyears1+"\t"+youthclubs1+"\t"+nationalgoals1).toLowerCase();
		str=Film.process(str);
		str="insert into footballBiography values("+instance+","+str+");";
		return str;
	}
}

