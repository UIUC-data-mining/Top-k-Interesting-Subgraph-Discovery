package dataGen.wikipedia;

/**
 * Represents the company type in Wikipedia dataset.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class Company {
	public String foundation_year;
	public String industry;
	public String homepage;
	public String location;
	public String key_people;
	public String company_type;
	public String products;
	public String num_employees;
	public String location_city;
	public String location_country;
	public String revenue;
	public String name;
	public String area_served;
	public String founder;
	public String parent;
	public String company_slogan;
	public String net_income;
	public String operating_income;
	public String intl;
	public String assets;
	public String owner;
	public String services;
	public String defunct;
	public String traded_as;
	public String subsid;
	public String slogan;
	public String genre;
	public String equity;
	public String fate;
	public String predecessor;
	public String divisions;
	public String successor;
	public String aum;
	public  int instance;
	public Company() {
		foundation_year=industry=homepage=location=key_people=company_type=products=num_employees=location_city=location_country=revenue=name=area_served=founder=parent=company_slogan=net_income=operating_income=intl=assets=owner=services=defunct=traded_as=subsid=slogan=genre=equity=fate=predecessor=divisions=successor=aum="";
	}
	@Override
	public String toString() {
		String str= (foundation_year+"\t"+industry+"\t"+homepage+"\t"+location+"\t"+key_people+"\t"+company_type+"\t"+products+"\t"+num_employees+"\t"+location_city+"\t"+location_country+"\t"+revenue+"\t"+name+"\t"+area_served+"\t"+founder+"\t"+parent+"\t"+company_slogan+"\t"+net_income+"\t"+operating_income+"\t"+intl+"\t"+assets+"\t"+owner+"\t"+services+"\t"+defunct+"\t"+traded_as+"\t"+subsid+"\t"+slogan+"\t"+genre+"\t"+equity+"\t"+fate+"\t"+predecessor+"\t"+divisions+"\t"+successor+"\t"+aum).toLowerCase();
		str=Film.process(str);
		str="insert into company values("+instance+","+str+");";
		return str;
	}
}

