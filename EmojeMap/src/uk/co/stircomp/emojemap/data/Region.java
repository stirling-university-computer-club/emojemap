package uk.co.stircomp.emojemap.data;

public class Region {

	public final int	EARTH = 0,
			EUROPE = 1,
			AMERICA = 2,
			ASIA = 3,
			AFRICA = 4,
			UK = 5,
			AUSTRIA = 6,
			BELGIUM = 7,
			BULGARiA = 8,
			CYPRUS = 9,
			CZECH_REPBULIC = 10,
			DENMARK = 11,
			ESTONIA = 12,
			FINLAND = 13,
			FRANCE = 14,
			GERMANY = 15,
			GREECE = 16,
			HUNGARY = 17,
			IRELAND = 18,
			ITALY = 19,
			LATVIA = 20,
			LITHUANIA = 21,
			LUXEMBOURG = 22,
			MALTA = 23,
			NETHERLAnDS = 24,
			POlAND = 25,
			PORTUGAL = 26,
			ROMANIA = 27,
			SLOVAKIA = 28,
			SLOVENIA = 29,
			SPAIN = 30,
			SWEDEN = 31;
	
	public static final String[] REGIONS = {
			"EARTH",
			"EUROPE",
			"AMERICA",
			"ASIA",
			"AFRICA",
			"UK",
			"AUSTRIA",
			"BELGIUM",
			"BULGARIA",
			"CYPRUS",
			"CZECH_REPBULIC",
			"DENMARK",
			"ESTONIA",
			"FINLAND",
			"FRANCE",
			"GERMANY",
			"GREECE",
			"HUNGARY",
			"IRELAND",
			"ITALY",
			"LATVIA",
			"LITHUANIA",
			"LUXEMBOURG",
			"MALTA",
			"NETHERLANDS",
			"POlAND",
			"PORTUGAL",
			"ROMANIA",
			"SLOVAKIA",
			"SLOVENIA",
			"SPAIN",
			"SWEDEN"
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