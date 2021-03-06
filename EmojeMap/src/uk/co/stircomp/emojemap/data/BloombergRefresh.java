package uk.co.stircomp.emojemap.data;

import java.util.ListIterator;

public class BloombergRefresh {
	
	public BloombergRefresh(DataManager dm) {
		
		refreshDebt(dm);
		refreshEmployment(dm);
		refreshHousePrice(dm);
		
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
						
				dm.addIndex(Region.getRegionIndex(region), Emotion.SAD, 0.4f);
				dm.addIndex(Region.getRegionIndex(region), Emotion.ANGRY, 0.5f);
				dm.addIndex(Region.getRegionIndex(region), Emotion.HAPPY, -0.4f);
				
			} else if (debt >= 100000.0 && debt < 1500000.0) {
				dm.addIndex(Region.getRegionIndex(region), Emotion.SAD, 0.2f);
				dm.addIndex(Region.getRegionIndex(region), Emotion.ANGRY, 0.3f);
				dm.addIndex(Region.getRegionIndex(region), Emotion.HAPPY, -0.2f);
			} else {
				dm.addIndex(Region.getRegionIndex(region), Emotion.SAD, -0.2f);
				dm.addIndex(Region.getRegionIndex(region), Emotion.ANGRY, -0.3f);
				dm.addIndex(Region.getRegionIndex(region), Emotion.HAPPY, 0.4f);
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
				"UMRTDK Index",
				"UMRTUK Index",
				"UMRTFI Index",
				"UMRTEE Index",
				"UMRTCZ Index",
				"UMRTLU Index",
				"UMRTBE Index",
				"UMRTCY Index",
				"UMRTPL Index",
				"UMRTRO Index",
				"UMRTBG Index",
				"UMRTSK Index",
				"UMRT25 Index",
				"UMRTNO Index",
				"UMRTLT Index",
				"UMRTLV Index",
				"UMRTSI Index",
				"UMRTHR Index",
				"UMRTHU Index",
				"UMRTMT Index"
		};
		String[] fields = { "PX_LAST", "REGION_OR_COUNTRY",  "SECURITY_DES" };
		
		BloombergRequest r = new BloombergRequest(eulCodes, fields);
		BloombergResponse response = r.make();
		
		
		ListIterator<BlSecurity> li = response.getSecurities().listIterator();
		while (li.hasNext()) {
			
			BlSecurity s = li.next();
			
			String region = s.getFieldValue("REGION_OR_COUNTRY");	
			
			if (region == null) continue;
			
			double rate = Double.parseDouble(s.getFieldValue("PX_LAST")) / 500;
			
			System.out.println(region + " UNEMP " + rate);
			
			dm.addIndex(Region.getRegionIndex(region), Emotion.HAPPY, (float)rate);
			dm.addIndex(Region.getRegionIndex(region), Emotion.SAD, (float) rate);
			dm.addIndex(Region.getRegionIndex(region), Emotion.ANGRY, (float) rate);
			
		}
		
		System.out.println("COUNTRY UNEMPLOYMENT WAS REFRESHED.");
		
	}
	
	private void refreshHousePrice(DataManager dm) {
		
		String[] eulCodes = {
				"HOPIDEI Index",
				"HOPISEI Index",
				"HOPIIEI Index",
				"HOPIEUI Index",
				"HOPIGRI Index",
				"HOPIDKI Index",
				"HOPIITI Index",
				"HOPIEAI Index",
				"HOPINOI Index",
				"HOPINLI Index",
				"HOPIPTI Index",
				"HOPIFRI Index",
				"HOPIROI Index",
				"HOPICZI Index",
				"HOPISKI Index",
				"HOPICYI Index",
				"HOPIISI Index",
				"HOPIFII Index",
				"HOPIBGI Index",
				"HOPIESI Index",
				"HOPIHUI Index",
				"HOPIATI Index",
				"HOPISII Index",
				"HOPIEEI Index",
				"HOPILTI Index",
				"HOPILUI Index",
				"HOPILVI Index",
				"HOPIMTI Index",
				"HOPIUKI Index"
		};
		String[] fields = { "PX_LAST", "REGION_OR_COUNTRY",  "SECURITY_DES" };
		
		BloombergRequest r = new BloombergRequest(eulCodes, fields);
		BloombergResponse response = r.make();
		
		
		ListIterator<BlSecurity> li = response.getSecurities().listIterator();
		while (li.hasNext()) {			
			
			BlSecurity s = li.next();
			
			String region = s.getFieldValue("REGION_OR_COUNTRY");	
			
			if (region == null) continue;
			
			double rate = 1- (Double.parseDouble(s.getFieldValue("PX_LAST")) / 50);
			if (rate < 0.0f) rate *= (-1);
			if (rate < 1.0f) rate += 1;
			rate = rate * 0.5f;

			System.out.println(region + " HP " + rate);
			
			dm.modifyIndex(Region.getRegionIndex(region), Emotion.HAPPY, (float)rate);
			dm.modifyIndex(Region.getRegionIndex(region), Emotion.SAD, 2 * (float)rate);
			dm.modifyIndex(Region.getRegionIndex(region), Emotion.ANGRY, 2 * (float)rate);
			//dm.modifyIndex(Region.getRegionIndex(region), Emotion.FEAR, (float) rate);
			
		}
		
		System.out.println("COUNTRY HOUSE PRICE WAS REFRESHED.");
		
	}

}