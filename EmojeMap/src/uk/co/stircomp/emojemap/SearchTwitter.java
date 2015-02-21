package uk.co.stircomp.emojemap;

import javax.net.ssl.SSLEngineResult.Status;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.api.SearchResource;

public class SearchTwitter implements SearchResource
{
	
	static Twitter twitter = TwitterFactory.getSingleton();
	
	public static QueryResult search(String word) throws TwitterException{
		
		// The factory instance is re-useable and thread safe.
	    
	    Query query = new Query(word);
	    QueryResult result = twitter.search(query);
		
		return result;
	}

	public static void query(String q) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public QueryResult search(Query arg0) throws TwitterException {
		// TODO Auto-generated method stub
		return null;
	}


	
}