package dataGen.wikipedia;

/**
 * Represents the musicalArtist type in Wikipedia dataset.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class MusicalArtist {
	public String background;
	public String name;
	public String genre;
	public String years_active;
	public String origin;
	public String label;
	public String website_url;
	public String associated_acts_associated_acts;
	public String occupation_occupations;
	public String instrument_instruments;
	public String birth_name_birth_name;
	public String current_members_members;
	public String birth_date_born;
	public String past_members_former_members;
	public String alias;
	public String birth_place;
	public String death_date;
	public String death_place;
	public String spouse;
	public String labels;
	public String nationality;
	public String influences;
	public int instance;
	public MusicalArtist() {
		background=name=genre=years_active=origin=label=website_url=associated_acts_associated_acts=occupation_occupations=instrument_instruments=birth_name_birth_name=current_members_members=birth_date_born=past_members_former_members=alias=birth_place=death_date=death_place=spouse=labels=nationality=influences="";
	}
	@Override
	public String toString() {
		String str= (background+"\t"+name+"\t"+genre+"\t"+years_active+"\t"+origin+"\t"+label+"\t"+website_url+"\t"+associated_acts_associated_acts+"\t"+occupation_occupations+"\t"+instrument_instruments+"\t"+birth_name_birth_name+"\t"+current_members_members+"\t"+birth_date_born+"\t"+past_members_former_members+"\t"+alias+"\t"+birth_place+"\t"+death_date+"\t"+death_place+"\t"+spouse+"\t"+labels+"\t"+nationality+"\t"+influences).toLowerCase();
		str=Film.process(str);
		str="insert into musicalArtist values("+instance+","+str+");";
		return str;
	}
}

