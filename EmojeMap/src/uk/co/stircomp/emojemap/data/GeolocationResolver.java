package uk.co.stircomp.emojemap.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GeolocationResolver {
	
	public final int MAX_CITIES = 1000000;
	private final String GEO_FILE = "worldcitiespop.txt", GEO_LITTLE_FILE = "smallworldcitiespop.txt";
	private City[] cities;
	
	public GeolocationResolver() {
		
		cities = new City[MAX_CITIES];
		try {
			loadCities();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void loadCities() throws IOException {
		
		System.out.println("Loading cities ...");
		
		String mapFile = GEO_FILE; 
		
		if (!new File(GEO_FILE).exists()) {
			System.out.println("Using petite cities index ...");
			mapFile = GEO_LITTLE_FILE;
		}
		
		BufferedReader br = new BufferedReader(new FileReader(mapFile));
		String line;
		int lineCount = 0;
		while ((line = br.readLine()) != null && (lineCount < MAX_CITIES)) {
			
			String[] s = line.split(",");
			cities[lineCount++] = new City(s[0], s[2], Double.parseDouble(s[5]), Double.parseDouble(s[6]));
			
		}
		br.close();
		
		System.out.println("Loaded " + cities.length + " cities.");
		
	}
	
	public int findCountry(double lng , double lat) {
		
		double shortest = 100000;
		double dd = 0;
		int index = 0;
		
		for (int i = 0; i < cities.length; i++) {
			
			if (cities[i] == null) continue;
			
			dd = cities[i].distanceTo(lng, lat);
			if (dd < shortest) {
				shortest = dd;
				index = i;
			}
			
		}
		
		return cities[index].getCountry();
		 
	}
	
	public String findCity(double lng , double lat) {
		
		double shortest = 100000;
		double dd = 0;
		int index = 0;
		
		for (int i = 0; i < cities.length; i++) {
			
			if (cities[i] == null) continue;
			
			dd = cities[i].distanceTo(lng, lat);
			if (dd < shortest) {
				shortest = dd;
				index = i;
			}
			
		}
		
		return cities[index].getName();
		 
	}

}
