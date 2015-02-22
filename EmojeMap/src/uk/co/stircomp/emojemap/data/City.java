package uk.co.stircomp.emojemap.data;

public class City {
	
	public City(String country, String name, double lng, double lat) {
		
		this.country = correctCountryCode(country);
		this.name = name;
		this.lng = lng;
		this.lat = lat;
		
	}
	
	private String correctCountryCode(String code) {
		
		code = code.toUpperCase();
		
		if (code.equals("AD")) {
			return "Andorra";
		}
		if (code.equals("AE")) {
			return "United Arab Emirates";
		}
		if (code.equals("AT")) {
			return "Austria";
		}
		if (code.equals("DE")) {
			return "Germany";
		}
		if (code.equals("DK")) {
			return "Denmark";
		}
		if (code.equals("ES")) {
			return "Spain";
		}
		if (code.equals("FI")) {
			return "Finland";
		}
		if (code.equals("FR")) {
			return "France";
		}
		if (code.equals("GR")) {
			return "Greece";
		}
		if (code.equals("IE")) {
			return "Ireland";
		}
		if (code.equals("HU")) {
			return "Hungary";
		}
		if (code.equals("GB")) {
			return "United Kingdom";
		}
		if (code.equals("IT")) {
			return "Italy";
		}
		if (code.equals("SO")) {
			return "Somalia";
		}
		if (code.equals("SC")) {
			return "Seychelles";
		}
		
		return code;
		
	}
	
	private String country, name;
	private double lng, lat;

	public String getName() {
		
		return name;
		
	}
	
	public String getCountry() {
		
		return this.country;
		
	}
	
	public double distanceTo(double x, double y) {
		
		double deltaX = lng - x;
	    double deltaY = lat - y;
	    double result = Math.sqrt((deltaX*deltaX) + (deltaY*deltaY));
	    return result; 		
		
	}
	
}
