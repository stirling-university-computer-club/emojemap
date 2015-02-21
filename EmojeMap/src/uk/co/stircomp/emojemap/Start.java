package uk.co.stircomp.emojemap;

import twitter4j.Query;
import twitter4j.TwitterException;

public class Start {
	
	public static void main(String[] args) throws TwitterException {
		
		System.out.println("-- A map of world emotions --");
		SearchTwitter searchTwitter = new SearchTwitter();
		Query q = new Query(); 
		
		searchTwitter.search(q);
		System.out.println(q);
	}

}
