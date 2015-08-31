package dataGen.wikipedia;

/**
 * Represents the settlement type in Wikipedia dataset.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class Settlement {
	public String subdivision_name;
	public String subdivision_type;
	public String subdivision_type1;
	public String subdivision_name1;
	public String latd;
	public String settlement_type;
	public String longd;
	public String subdivision_type2;
	public String subdivision_name2;
	public String coordinates_region;
	public String pushpin_map;
	public String timezone;
	public String utc_offset;
	public String population_total;
	public String name;
	public String official_name;
	public String subdivision_type3;
	public String area_total_km2;
	public String subdivision_name3;
	public String unit_pref;
	public String postal_code_type;
	public String population_density_km2;
	public String area_land_km2;
	public String elevation_m;
	public String total_type;
	public String latns;
	public String longew;
	public String longm;
	public String latm;
	public String elevation_ft;
	public int instance;
	public String lats;
	public String longs;
	public Settlement() {
		subdivision_name=subdivision_type=subdivision_type1=subdivision_name1=latd=settlement_type=longd=subdivision_type2=subdivision_name2=coordinates_region=pushpin_map=timezone=utc_offset=population_total=name=official_name=subdivision_type3=area_total_km2=subdivision_name3=unit_pref=postal_code_type=population_density_km2=area_land_km2=elevation_m=total_type=latns=longew=longm=latm=elevation_ft=lats=longs="";
	}
	@Override
	public String toString() {
		String str= (subdivision_name+"\t"+subdivision_type+"\t"+subdivision_type1+"\t"+subdivision_name1+"\t"+latd+"\t"+settlement_type+"\t"+longd+"\t"+subdivision_type2+"\t"+subdivision_name2+"\t"+coordinates_region+"\t"+pushpin_map+"\t"+timezone+"\t"+utc_offset+"\t"+population_total+"\t"+name+"\t"+official_name+"\t"+subdivision_type3+"\t"+area_total_km2+"\t"+subdivision_name3+"\t"+unit_pref+"\t"+postal_code_type+"\t"+population_density_km2+"\t"+area_land_km2+"\t"+elevation_m+"\t"+total_type+"\t"+latns+"\t"+longew+"\t"+longm+"\t"+latm+"\t"+elevation_ft+"\t"+lats+"\t"+longs).toLowerCase();
		str=Film.process(str);
		str="insert into settlement values("+instance+","+str+");";
		return str;
	}
}

