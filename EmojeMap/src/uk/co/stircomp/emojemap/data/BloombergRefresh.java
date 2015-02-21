package uk.co.stircomp.emojemap.data;

public class BloombergRefresh {
	
	public BloombergRefresh(DataManager dm) {
		
		String[] eulCodes = {
				"EULD60AT Index",
				"EULD60BE Index",
				"EULDBP Index",
				"EULDCY Index",
				"EULDCZ Index",
				"EULD60DK Index",
				"EULD6015 Index",
				"EULDEU25 Index",
				"EULDEE Index",
				"EULDEU27 Index",
				"EULDEURO Index",
				"EULD60FI Index",
				"EULD60FR Index",
				"EULD60DE Index",
				"EULD60GR Index",
				"EULDHU Index",
				"EULD60IE Index",
				"EULD60IT Index",
				"EULDLV Index",
				"EULDLT Index",
				"EULD60LU Index",
				"EULDMT Index",
				"EULD60NL Index",
				"EULDPL Index",
				"EULD60PT Index",
				"EULDRO Index",
				"EULDSK Index",
				"EULDSI Index",
				"EULD60ES Index",
				"EULD60SE Index",
				"EULD60UK Index"
				};
		String[] fields = { "PX_LAST" };
		
		BloombergRequest r = new BloombergRequest(eulCodes, fields);
		BloombergResponse response = r.make();
		
	}

}