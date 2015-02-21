package uk.co.stircomp.emojemap.data;

public class DataManager implements Runnable {
	
	private String message;
	private float[][] index;
	private boolean initialised = false;
	
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
		
		setMessage("Initialising indexes to zero");
		initialiseIndexes();
		
		setMessage("Fetching Twitter.");
		new TwitterFetch();		
		
		setMessage("Refreshing Bloomberg data");
		try {
			new BloombergRefresh(this);
		} catch (Exception e) {
			
		}
		
	}
	
	public float getRegionalIndex(int region, int emotion) {
		
		// Make sure the indexes exist.
		if (!initialised) {
			return 0.0f;
		}
		
		if (region > index.length || emotion > index[0].length || region < 0 || emotion < 0) {
			return 0.0f;
		}
		
		return index[region][emotion];
		
	}
	
	private void initialiseIndexes() {
		
		int regionCount = Region.REGIONS.length;
		int emotionCount = Emotion.EMOTIONS.length;
		
		index = new float[regionCount][emotionCount];
		
		for (int x = 0; x < regionCount; x++) {
			for (int y = 0; y < emotionCount; y++) {
				
				index[x][y] = 0.0f;
				
			}
		}
		
		initialised = true;
		
	}
	
	public void updateIndex(int region, int emotion, float value) {
		
		if (region > index.length || emotion > index[0].length || region < 0 || emotion < 0) {
			return;
		}
		
		index[region][emotion] = value;
		
	}

}
