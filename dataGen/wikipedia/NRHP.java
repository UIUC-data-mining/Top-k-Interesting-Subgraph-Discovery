package dataGen.wikipedia;

/**
 * Represents the nrhp type in Wikipedia dataset.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class NRHP {
	public String added;
	public String refnum;
	public String governing_body;
	public String lat_degrees_latitude;
	public String long_degrees;
	public String locmapin;
	public String long_direction;
	public String lat_direction;
	public String location;
	public String long_minutes;
	public String lat_minutes;
	public String long_seconds;
	public String lat_seconds;
	public String built;
	public String architecture;
	public String name;
	public String architect_architect_or_builder;
	public String area;
	public String nrhp_type;
	public String mpsub;
	public String nearest_city;
	public String coord_parameters_type;
	public String coord_parameters_region;
	public String designated_nrhp_type;
	public String designated_other1_name;
	public String designated_other1_abbr;
	public String designated_other1_date;
	public String partof;
	public int instance;
	public NRHP() {
		added=refnum=governing_body=lat_degrees_latitude=long_degrees=locmapin=long_direction=lat_direction=location=long_minutes=lat_minutes=long_seconds=lat_seconds=built=architecture=name=architect_architect_or_builder=area=nrhp_type=mpsub=nearest_city=coord_parameters_type=coord_parameters_region=designated_nrhp_type=designated_other1_name=designated_other1_abbr=designated_other1_date=partof="";
	}
	@Override
	public String toString() {
		String str= (added+"\t"+refnum+"\t"+governing_body+"\t"+lat_degrees_latitude+"\t"+long_degrees+"\t"+locmapin+"\t"+long_direction+"\t"+lat_direction+"\t"+location+"\t"+long_minutes+"\t"+lat_minutes+"\t"+long_seconds+"\t"+lat_seconds+"\t"+built+"\t"+architecture+"\t"+name+"\t"+architect_architect_or_builder+"\t"+area+"\t"+nrhp_type+"\t"+mpsub+"\t"+nearest_city+"\t"+coord_parameters_type+"\t"+coord_parameters_region+"\t"+designated_nrhp_type+"\t"+designated_other1_name+"\t"+designated_other1_abbr+"\t"+designated_other1_date+"\t"+partof).toLowerCase();
		str=Film.process(str);
		str="insert into nrhp values("+instance+","+str+");";
		return str;
	}
}

