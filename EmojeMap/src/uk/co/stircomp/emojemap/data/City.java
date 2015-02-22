package uk.co.stircomp.emojemap.data;

public class City {
	
	public City(String country, String name, double lng, double lat) {
		
		this.country = country;
		this.name = name;
		this.lng = lng;
		this.lat = lat;
		
	}
	
	private String country, name;
	private double lng, lat;

	public String getName() {
		
		return name;
		
	}
	
	public int getCountry() {
		
		return Region.getRegionIndex(country);
		
	}
	
	public double distanceTo(double x, double y) {
		
		double deltaX = x - lng;
	    double deltaY = y - lat;
	    double result = Math.sqrt(deltaX*deltaX + deltaY*deltaY);
	    return result; 		
		
	}
	
}
