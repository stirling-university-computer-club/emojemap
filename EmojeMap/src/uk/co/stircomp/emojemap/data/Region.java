package uk.co.stircomp.emojemap.data;

public class Region {

	public static final int	EARTH = 0,
			EUROPE = 1,
			AMERICA = 2,
			ASIA = 3,
			AFRICA = 4;
	
	public static final String[] REGIONS = {
		"EARTH",
		"EUROPE",
		"AMERICA",
		"ASIA",
		"AFRICA"
	};

	public static int getRegionIndex(String region) {
		
		for (int i = 0; i < REGIONS.length; i++) {
			
			String x = REGIONS[i];
			
			if (x.equals(region.toUpperCase())) {
				
				return i;
				
			}
			
		}
		
		return -1;
		
	}
	
}
