package uk.co.stircomp.emojemap.data;

import twitter4j.Query;
import uk.co.stircomp.emojemap.SearchTwitter;

public class TwitterFetch {
	
	public TwitterFetch() {
		
		try {		
			
			String[] angryWords = {"angry", "raging", "pissed", "furious", "fuming", "outrageous", "asshole", "idiot", "stupid", "annoyed", "enraged", "irritating"};
			String[] happyWords = {"happy", "the best", "awesome", "nice", "cheerful", "delighted", "elated", "glad", "joyful", "joyous", "nice", "pleasant"};
			
			SearchTwitter searchTwitter = new SearchTwitter();
			
			Query q = new Query();
			
			for (int i=0 ; i < happyWords.length ; i++){
				SearchTwitter.query(happyWords[i]);
				System.out.println(searchTwitter.search(q));
			}
			
			for (int i=0 ; i < angryWords.length ; i++){
				SearchTwitter.query(angryWords[i]);
				System.out.println(searchTwitter.search(q));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
