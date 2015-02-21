package uk.co.stircomp.emojemap.data;

import java.util.ListIterator;

public class BloombergRefresh {
	
	public BloombergRefresh(DataManager dm) {
		
		String[] eulCodes = {
				"EULD60AT Index", // UK
				"EULD60BE Index", // Austria
				"EULDBP Index", // Belgium
				"EULDCY Index", // Bulgaria
				"EULDCZ Index", // Cyprus
				"EULD60DK Index", // CR
				"EULD6015 Index", // Denmark
				"EULDEU25 Index", // Eurozone
				"EULDEE Index", // Eurozone
				"EULDEU27 Index", // Estonia
				"EULDEURO Index", // EU
				"EULD60FI Index", // Eurozone
				"EULD60FR Index", // Finland
				"EULD60DE Index", // France
				"EULD60GR Index", // Germany
				"EULDHU Index", // GreeceANGRY
				"EULD60IE Index", // Hungary
				"EULD60IT Index", // Ireland
				"EULDLV Index", // Italy
				"EULDLT Index", // Latvia
				"EULD60LU Index", // Lithuania
				"EULDMT Index", // Luxembourg
				"EULD60NL Index", // Malta
				"EULDPL Index", // netherlands
				"EULD60PT Index", // Poland
				"EULDRO Index", // Portugal
				"EULDSK Index", // Romania
				"EULDSI Index", // Slovakia
				"EULD60ES Index", // Slovenia
				"EULD60SE Index", // Spain
				"EULD60UK Index" // Sweden
				};
		String[] fields = { "PX_LAST", "REGION_OR_COUNTRY",  "SECURITY_DES" };
		
		BloombergRequest r = new BloombergRequest(eulCodes, fields);
		BloombergResponse response = r.make();
		
		ListIterator<BlSecurity> li = response.getSecurities().listIterator();
		while (li.hasNext()) {
			
			BlSecurity s = li.next();
			
			String region = s.getFieldValue("REGION_OR_COUNTRY");
			
			// Update different emotions!
			dm.updateIndex(Region.getRegionIndex(region), Emotion.SAD, 0.5f);
			
		}
		
		System.out.println("COUNTRY DEBT WAS REFRESHED.");
		
	}

}