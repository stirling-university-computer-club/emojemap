package uk.co.stircomp.emojemap;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.TwitterException;
import twitter4j.api.SearchResource;

public class SearchTwitter implements SearchResource
{

	String[] angryWords = {"angry", "raging", "pissed", "furious", "fuming", "lazy", "outrageous", "asshole", "idiot", "stupid", "annoyed", "enraged", "irritating"};
	String[] happyWords = {"happy", "the best", "awesome", "nice", "cheerful", "delighted", "elated", "glad", "joyful", "joyous", "nice", "pleasant"};
	
	@Override
	public QueryResult search(Query arg0) throws TwitterException {
		// TODO Auto-generated method stub
		return null;
	}
	
}