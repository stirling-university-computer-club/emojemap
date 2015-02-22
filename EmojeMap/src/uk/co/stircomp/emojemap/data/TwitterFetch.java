package uk.co.stircomp.emojemap.data;

import java.util.Iterator;

import twitter4j.GeoLocation;
import twitter4j.Status;
import twitter4j.TwitterException;
import uk.co.stircomp.emojemap.SearchTwitter;

public class TwitterFetch {
	
	private final String[] dictAngry = {"angry", "pissed", "annoyed", "irritated", "fuck", "shit", "asshole", "bastard"};
	private final String[] dictHappy = {"happy", "cheerful", "amazing", "awesome", "smile", "smiling", "pleased", "thank", "yay", "good"};
	private final String[] dictSad = {"poor", "hurt", "sad", "unhappy", "unfair", "broke", "hospital", "death", "cry", "cried"};

	public TwitterFetch(DataManager dm) {
		
		GeoLocation[] locs = new GeoLocation[2];
		// United Kingdom
		locs[0]  = new GeoLocation(51.479114, -0.344507);
		locs[1]  = new GeoLocation(53.642478, -1.443140);
		locs[2]  = new GeoLocation(52.312968, -3.640405);
		locs[3]  = new GeoLocation(55.520349, -3.684351);
		locs[4]  = new GeoLocation(57.813563, -4.717065);
		locs[5]  = new GeoLocation(54.614507, -6.584741);
		// Austria
		locs[6]  = new GeoLocation(47.179770, 10.883520);
		locs[7]  = new GeoLocation(47.105043, 13.421362);
		locs[8]  = new GeoLocation(47.187236, 15.190161);
		locs[9]  = new GeoLocation(48.178309, 15.882299);
		locs[10] = new GeoLocation(48.068304, 13.860815);
		// Belgium
		locs[11] = new GeoLocation(50.311810, 5.647682);
		locs[12] = new GeoLocation(50.584646, 4.955544);
		locs[13] = new GeoLocation(50.810809, 4.071144);
		locs[14] = new GeoLocation(51.094568, 3.274635);
		// Bulgaria
		locs[15] = new GeoLocation(43.146300, 24.010447);
		locs[16] = new GeoLocation(41.972980, 24.405955);
		locs[17] = new GeoLocation(42.711918, 25.889109);
		locs[18] = new GeoLocation(43.362341, 27.185496);
		// Cyprus
		locs[19] = new GeoLocation(34.900812, 32.947825);
		locs[20] = new GeoLocation(35.215568, 33.667429);
		// Czech Republic
		locs[21] = new GeoLocation(50.233787, 13.684490);
		locs[22] = new GeoLocation(49.167988, 14.266766);
		locs[23] = new GeoLocation(49.987191, 15.563152);
		locs[24] = new GeoLocation(49.525853, 17.200115);
		// Denmark
		locs[25] = new GeoLocation(57.174257, 9.935044);
		locs[27] = new GeoLocation(56.367564, 9.341782);
		locs[28] = new GeoLocation(55.506116, 9.056137);
		locs[29] = new GeoLocation(55.362755, 10.330551);
		locs[30] = new GeoLocation(55.428267, 11.962021);
		// Estonia
		locs[31] = new GeoLocation(57.984111, 26.826522);
		locs[32] = new GeoLocation(58.498748, 25.315902);
		locs[33] = new GeoLocation(59.437601, 24.802653);
		locs[34] = new GeoLocation(59.265209, 26.793563);
		locs[35] = new GeoLocation(58.854462, 25.516765);
		// Finland
		locs[36] = new GeoLocation(62.110607, 25.140876);
		locs[37] = new GeoLocation(64.107284, 27.294197);
		locs[38] = new GeoLocation(66.745577, 26.942634);
		locs[39] = new GeoLocation(68.258435, 26.481208);
		// France
		locs[40] = new GeoLocation(44.797641, 1.231944);
		locs[41] = new GeoLocation(45.740902, 4.967295);
		locs[42] = new GeoLocation(48.140631, 5.648448);
		locs[43] = new GeoLocation(48.868524, 2.418467);
		locs[44] = new GeoLocation(47.905496, -0.965321);
		// Germany
		locs[45] = new GeoLocation(48.411170, 9.570567);
		locs[46] = new GeoLocation(48.846840, 11.844737);
		locs[47] = new GeoLocation(49.650041, 9.933116);
		locs[48] = new GeoLocation(50.117247, 8.460948);
		locs[49] = new GeoLocation(51.251788, 12.405039);
		locs[50] = new GeoLocation(51.423375, 7.439219);
		locs[51] = new GeoLocation(52.238075, 10.833994);
		locs[52] = new GeoLocation(52.499685, 13.415781);
		locs[53] = new GeoLocation(53.654582, 10.515391);
		// Greece
		locs[54] = new GeoLocation(39.102816, 22.130719);
		locs[55] = new GeoLocation(40.737300, 22.888776);
		locs[56] = new GeoLocation(41.086008, 25.360700);
		// Hungary
		locs[57] = new GeoLocation(47.248415, 17.350716);
		locs[58] = new GeoLocation(46.463343, 17.987923);
		locs[59] = new GeoLocation(47.531051, 19.064583);
		locs[60] = new GeoLocation(47.686595, 21.217903);
		// Ireland
		locs[61] = new GeoLocation(52.188952, -8.924238);
		locs[62] = new GeoLocation(52.577884, -7.188398);
		locs[63] = new GeoLocation(53.424132, -6.770918);
		locs[64] = new GeoLocation(53.866974, -8.594648);
		// Italy
		locs[65] = new GeoLocation(46.001021, 11.791484);
		locs[66] = new GeoLocation(45.433399, 8.869120);
		locs[67] = new GeoLocation(43.345410, 11.989237);
		locs[68] = new GeoLocation(40.585023, 16.317851);
		// Latvia
		locs[69] = new GeoLocation(56.896384, 22.351146);
		locs[70] = new GeoLocation(56.655603, 26.822581);
		locs[71] = new GeoLocation(57.081929, 25.086741);
		// Lithuania
		locs[72] = new GeoLocation(57.081929, 25.086741);
		locs[73] = new GeoLocation(54.819404, 24.508179);
		locs[74] = new GeoLocation(55.671121, 24.387329);
		// Luxemburg
		locs[75] = new GeoLocation(49.672327, 6.124948);
		// Malta
		locs[76] = new GeoLocation(35.896627, 14.412535);
		// Netherlands
		locs[77] = new GeoLocation(52.368225, 4.864127);
		locs[78] = new GeoLocation(51.811336, 4.567496);
		locs[79] = new GeoLocation(51.620749, 5.589225);
		locs[80] = new GeoLocation(52.458691, 6.138541);
		locs[81] = new GeoLocation(53.123046, 6.292350);
		// Poland
		locs[82] = new GeoLocation(50.307645, 19.476077);
		locs[83] = new GeoLocation(50.830981, 22.420412);
		locs[84] = new GeoLocation(52.184767, 20.937258);
		locs[85] = new GeoLocation(51.478704, 17.740237);
		locs[86] = new GeoLocation(53.459329, 20.212161);
		locs[87] = new GeoLocation(53.210036, 16.828372);
		// Portugal
		locs[88] = new GeoLocation(53.210036, 16.828372);
		locs[89] = new GeoLocation(41.117468, -7.958790);
		locs[90] = new GeoLocation(39.838945, -7.673145);
		// Romania
		locs[91] = new GeoLocation(44.678628, 28.283095);
		locs[92] = new GeoLocation(44.522178, 25.997939);
		locs[93] = new GeoLocation(45.999074, 26.437392);
		locs[94] = new GeoLocation(45.354282, 23.240371);
		locs[95] = new GeoLocation(46.113434, 21.548477);
		locs[96] = new GeoLocation(46.832214, 24.064346);
		locs[97] = new GeoLocation(47.390050, 26.336996);
		// Slovakia
		locs[98]  = new GeoLocation(48.217173, 17.264880);
		locs[99]  = new GeoLocation(48.399853, 18.467883);
		locs[100] = new GeoLocation(48.629100, 19.599475);
		locs[101] = new GeoLocation(48.784974, 21.318835);
		locs[102] = new GeoLocation(49.088081, 19.275378);
		// Slovenia
		locs[103] = new GeoLocation(46.205147, 14.137271);
		locs[104] = new GeoLocation(46.391114, 15.560000);
		locs[105] = new GeoLocation(45.865754, 14.703066);
		// Spain
		locs[106] = new GeoLocation(42.768888, -7.676614);
		locs[107] = new GeoLocation(42.542655, -3.018411);
		locs[108] = new GeoLocation(41.564695, 0.276310);
		locs[109] = new GeoLocation(40.687503, -3.612850);
		locs[110] = new GeoLocation(38.434865, -2.338436);
		locs[111] = new GeoLocation(38.158956, -5.524471);
		// Sweden
		locs[112] = new GeoLocation(57.192143, 14.180188);
		locs[113] = new GeoLocation(59.564296, 17.300305);
		locs[114] = new GeoLocation(60.916466, 14.795423);
		locs[115] = new GeoLocation(63.979702, 16.545803);
		locs[116] = new GeoLocation(65.985740, 20.171291);
		locs[117] = new GeoLocation(67.746045, 20.226291);
		// Croatia
		locs[118] = new GeoLocation(44.228954, 15.750099);
		locs[119] = new GeoLocation(45.498155, 17.980324);
		locs[120] = new GeoLocation(45.828310, 16.376320);
		locs[121] = new GeoLocation(45.119558, 14.387795);
		// Norway
		locs[122] = new GeoLocation(59.632641, 9.877080);
		locs[123] = new GeoLocation(61.468572, 7.504033);
		locs[124] = new GeoLocation(63.371573, 10.510002);
		locs[125] = new GeoLocation(66.062398, 13.827873);
		locs[126] = new GeoLocation(69.060505, 18.749748);
		locs[127] = new GeoLocation(69.633957, 23.891349);
		locs[128] = new GeoLocation(70.125233, 29.406485);
		// Ukraine
		locs[129] = new GeoLocation(49.926425, 24.242031);
		locs[130] = new GeoLocation(49.199606, 27.669765);
		locs[131] = new GeoLocation(50.600627, 30.636074);
		locs[132] = new GeoLocation(47.595002, 32.635585);
		locs[133] = new GeoLocation(48.998193, 36.612636);
		// Andorra
		locs[134] = new GeoLocation(42.543404, 1.567198);		
		
		for (int l = 0; l < locs.length; l++) {
			
			processRequest(dm, "", locs[l]);
			
		}

	}

	private void processRequest(DataManager dm, String query, GeoLocation geo) {

		try {

			Iterator<Status> iter = SearchTwitter.search(query, geo)
					.getTweets().iterator();
			while (iter.hasNext()) {
				Status status = iter.next();
				
				// Get the location and add to the map.
				if (status.getGeoLocation() != null) {
					String city = dm.getGeoResolver().findCountry(
							status.getGeoLocation().getLatitude(),
							status.getGeoLocation().getLongitude());					
					
					int happiness = captureOccurrences(status.getText(), Emotion.HAPPY);
					int anger = captureOccurrences(status.getText(), Emotion.ANGRY);
					int sad = captureOccurrences(status.getText(), Emotion.SAD);
					
					// Since we are happy then let's improve regional happiness and decrease sadness
					dm.modifyIndex(Region.getRegionIndex(city), Emotion.HAPPY, (happiness / 100));
					dm.modifyIndex(Region.getRegionIndex(city), Emotion.ANGRY, (anger / 100));
					dm.updateIndex(Region.getRegionIndex(city), Emotion.SAD, (sad / 100));
					
				}

			}

		} catch (TwitterException e) {
			e.printStackTrace();
		}

	}
	
	private int captureOccurrences(String message, int emotion) {
				
		// Start the emotional switch		
		String[] dictionary = null;
		
		switch (emotion) {
		
		case Emotion.ANGRY:	
			dictionary = dictAngry;
			break;
			
		case Emotion.FEAR:	
			dictionary = dictAngry;
			break;
			
		case Emotion.HAPPY:	
			dictionary = dictHappy;
			break;
			
		case Emotion.SAD:	
			dictionary = dictSad;
			break;
			
		default:
			break;
		
		
		} // End emotion switch
		
		// Return the modifier.
		return countDictionaryOccurrences(message, dictionary);
		
	}
	
	private int countDictionaryOccurrences(String message, String[] dictionary) {
		
		int result = 0;
		message = message.toLowerCase();
		
		for (int i = 0; i < dictionary.length; i++) {
			
			if (message.matches(dictionary[i]))
				result++;
			
		}
		
		return result;
		
	}

}
