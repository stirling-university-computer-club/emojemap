package uk.co.stircomp.emojemap.data;

import java.util.Iterator;

import twitter4j.GeoLocation;
import twitter4j.Status;
import twitter4j.TwitterException;
import uk.co.stircomp.emojemap.SearchTwitter;

public class TwitterFetch {

	public TwitterFetch(DataManager dm) {
		
		GeoLocation[] locs = new GeoLocation[2];
		locs[0] = new GeoLocation(55.984, -3.8788);
		locs[1] = new GeoLocation(46.33277, 2.2803);
		
		for (int l = 0; l < locs.length; l++) {
			processRequest(dm, "happy OR joyful or cheerful or elated or glad or nice or pleasant or awesome or smiling or smile", locs[l]);
			
		}

		/*
		 * 
		 * try {
		 * o
		 * String[] angryWords = {"angry", "raging", "pissed", "furious",
		 * "fuming", "outrageous", "asshole", "idiot", "stupid", "annoyed",
		 * "enraged", "irritating"}; String[] happyWords = {"happy", "the best",value
		 * "awesome", "nice", "cheerful", "delighted", "elated", "glad",
		 * "joyful", "joyous", "nice", "pleasant"};
		 * 
		 * 
		 * //Query q = new Query();
		 * 
		 * for (int i=0 ; i < angryWords.length ; i++){
		 * SearchTwitter.query(angryWords[i]);
		 * System.out.println(SearchTwitter.search(angryWords[i])); }
		 * 
		 * for (int i=0 ; i < happyWords.length ; i++){
		 * SearchTwitter.query(happyWords[i]);
		 * System.out.println(SearchTwitter.search(happyWords[i])); }
		 * 
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */

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
					
					// Since we are happy then let's improve regional happiness and decrease sadness
					dm.modifyIndex(Region.getRegionIndex(city), Emotion.HAPPY, 1.5f);
					
				}

			}

		} catch (TwitterException e) {
			e.printStackTrace();
		}

	}

}
