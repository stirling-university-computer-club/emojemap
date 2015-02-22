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
		locs[0] = new GeoLocation(55.984, -3.8788);
		locs[1] = new GeoLocation(46.33277, 2.2803);
		
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
