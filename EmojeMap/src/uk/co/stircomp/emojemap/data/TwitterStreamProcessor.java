package uk.co.stircomp.emojemap.data;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class TwitterStreamProcessor implements StatusListener {
	
	private DataManager dm;
	
	public TwitterStreamProcessor(DataManager d) {
		
		this.dm = d;
		
	}

	@Override
	public void onException(Exception e) {
		
		//e.printStackTrace();
		
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0) {
		
		System.err.println("Twitter Stream recieved delete notice.");
		
	}

	@Override
	public void onScrubGeo(long arg0, long arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStallWarning(StallWarning arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatus(Status status) {
		
		System.out.println(status.getUser().getName() + " : " + status.getText());
		
	}

	@Override
	public void onTrackLimitationNotice(int arg0) {
		// TODO Auto-generated method stub
		
	}

}
