package dataGen.wikipedia;

/**
 * Represents the person type in Wikipedia dataset.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class Person {
	public String name_title;
	public String birth_date;
	public String birth_place;
	public String occupation;
	public String death_date;
	public String death_place;
	public String spouse;
	public String nationality;
	public String yearsactive_years_active;
	public String known_for;
	public String website;
	public String children;
	public String residence;
	public String parents;
	public String alma_mater;
	public String religion;
	public String employer;
	public String ethnicity;
	public String home_town;
	public String notable_role;
	public String relatives;
	public  int instance;
	
	public Person() {
		name_title=birth_date=birth_place=occupation=death_date=death_place=spouse=nationality=yearsactive_years_active=known_for=website=children=residence=parents=alma_mater=religion=employer=ethnicity=home_town=notable_role=relatives="";
	}
	@Override
	public String toString() {
		String str= (name_title+"\t"+birth_date+"\t"+birth_place+"\t"+occupation+"\t"+death_date+"\t"+death_place+"\t"+spouse+"\t"+nationality+"\t"+yearsactive_years_active+"\t"+known_for+"\t"+website+"\t"+children+"\t"+residence+"\t"+parents+"\t"+alma_mater+"\t"+religion+"\t"+employer+"\t"+ethnicity+"\t"+home_town+"\t"+notable_role+"\t"+relatives).toLowerCase();
		str=Film.process(str);
		str="insert into person values("+instance+","+str+");";
		return str;
	}
}

