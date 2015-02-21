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

	public SearchTwitter() throws TwitterException {
		
		// The factory instance is re-useable and thread safe.
	    Twitter twitter = TwitterFactory.getSingleton();
	    Query query = new Query("angry");
	    QueryResult result = twitter.search(query);
	    for (twitter4j.Status status : result.getTweets()) {
	    	
	        System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
	        
	    }	
		
	}
	
	
	@Override
	public QueryResult search(Query q) throws TwitterException {
		return null;
	}

	public static void query(String q) {
		// TODO Auto-generated method stub
		
	}
	
}