package uk.co.stircomp.emojemap.data;

public class DataManager implements Runnable {
	
	private String message;
	
	public String getMessage() {
		
		return message;
		
	}
	
	private void setMessage(String m) {
		
		this.message = m;
		System.out.println("DMSG: " + m);
		
	}

	@Override
	public void run() {
		
		setMessage("Starting data.");
		
		setMessage("Fetching Twitter.");
		new TwitterFetch();
		
		
	}
	
	public float getRegionalIndex(int region, int emotion) {
		
		return 0.5f;
		
	}

}
