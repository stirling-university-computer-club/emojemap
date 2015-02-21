package uk.co.stircomp.emojemap;

import twitter4j.Query;
import twitter4j.TwitterException;

public class Start {

	public static void main(String[] args) throws TwitterException {
		

		String[] angryWords = {"angry", "raging", "pissed", "furious", "fuming", "outrageous", "asshole", "idiot", "stupid", "annoyed", "enraged", "irritating"};
		String[] happyWords = {"happy", "the best", "awesome", "nice", "cheerful", "delighted", "elated", "glad", "joyful", "joyous", "nice", "pleasant"};
		
		System.out.println("-- A map of world emotions --");
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
	}
}
