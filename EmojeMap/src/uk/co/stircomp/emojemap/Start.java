package uk.co.stircomp.emojemap;

import twitter4j.Query;
import twitter4j.TwitterException;

import uk.co.stircomp.emojemap.data.DataManager;
import uk.co.stircomp.emojemap.UI.WorldUI;



public class Start {

	public static void main(String[] args) throws TwitterException {
		

		String[] angryWords = {"angry", "raging", "pissed", "furious", "fuming", "outrageous", "asshole", "idiot", "stupid", "annoyed", "enraged", "irritating"};
		String[] happyWords = {"happy", "the best", "awesome", "nice", "cheerful", "delighted", "elated", "glad", "joyful", "joyous", "nice", "pleasant"};
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("-- A map of world emotions --");
		SearchTwitter searchTwitter = new SearchTwitter();
		Query q = new Query();
		
		
		for (int i=0 ; i < happyWords.length ; i++){
			SearchTwitter.query(happyWords[i]);
			System.out.println(searchTwitter.search(q));
		WorldUI worldUI = new WorldUI();
		
		// Create the Data Manager
		DataManager data = new DataManager();
		Thread t = new Thread(data);
		t.start();
		
		// Data can now be accessed from data.
		
		}
		for (int i=0 ; i < angryWords.length ; i++){
			SearchTwitter.query(angryWords[i]);
			System.out.println(searchTwitter.search(q));
	}
	}
}
