package uk.co.stircomp.emojemap.data;

import java.util.ListIterator;

public class BloombergRefresh {
	
	public BloombergRefresh(DataManager dm) {
		
		refreshDebt(dm);
		refreshEmployment(dm);
		
	}
	
	private void refreshDebt(DataManager dm) {
		
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
			
			double debt = Double.parseDouble(s.getFieldValue("PX_LAST"));
			
			if (debt >= 1500000.0) {			
				dm.modifyIndex(Region.getRegionIndex(region), Emotion.SAD, 1.5f);
				dm.modifyIndex(Region.getRegionIndex(region), Emotion.FEAR, 1.6f);
				dm.modifyIndex(Region.getRegionIndex(region), Emotion.HAPPY, 0.6f);
			} else if (debt >= 100000.0 && debt < 1500000.0) {
				dm.modifyIndex(Region.getRegionIndex(region), Emotion.SAD, 1.1f);
				dm.modifyIndex(Region.getRegionIndex(region), Emotion.FEAR, 1.2f);
				dm.modifyIndex(Region.getRegionIndex(region), Emotion.HAPPY, 0.8f);
			} else {
				dm.modifyIndex(Region.getRegionIndex(region), Emotion.SAD, 0.8f);
				dm.modifyIndex(Region.getRegionIndex(region), Emotion.FEAR, 0.8f);
				dm.modifyIndex(Region.getRegionIndex(region), Emotion.HAPPY, 1.2f);
			}
			
		}
		
		System.out.println("COUNTRY DEBT WAS REFRESHED.");
		
	}
	
	private void refreshEmployment(DataManager dm) {
		
		String[] eulCodes = {
				"UMRTES Index",
				"UMRTIT Index",
				"UMRTFR Index",
				"UMRTDE Index",
				"UMRT27 Index",
				"UMRTGR Index",
				"UMRTPT Index",
				"UMRTAT Index",
				"UMRTSE Index",
				"UMRTIE Index",
				"UMRTNL Index",
				"UMRTDK Index"
		};
		String[] fields = { "PX_LAST", "REGION_OR_COUNTRY",  "SECURITY_DES" };
		
		BloombergRequest r = new BloombergRequest(eulCodes, fields);
		BloombergResponse response = r.make();
		
		
		ListIterator<BlSecurity> li = response.getSecurities().listIterator();
		while (li.hasNext()) {
			
			BlSecurity s = li.next();
			
			String region = s.getFieldValue("REGION_OR_COUNTRY");			
			double rate = 1- (Double.parseDouble(s.getFieldValue("PX_LAST")) / 50);
			
			dm.modifyIndex(Region.getRegionIndex(region), Emotion.HAPPY, (float) rate);
			dm.modifyIndex(Region.getRegionIndex(region), Emotion.SAD, (float) rate);
			dm.modifyIndex(Region.getRegionIndex(region), Emotion.ANGRY, (float) rate);
			dm.modifyIndex(Region.getRegionIndex(region), Emotion.FEAR, (float) rate);
			
			System.out.println(region + " = " + rate);
			
		}
		
		System.out.println("COUNTRY UNEMPLOYMENT WAS REFRESHED.");
		
	}
	

}