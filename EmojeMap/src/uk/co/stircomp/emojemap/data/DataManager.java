package uk.co.stircomp.emojemap.data;

public class DataManager implements Runnable {
	
	private String message;
	private float[][] index;
	private boolean initialised = false;
	private GeolocationResolver resolver;
	private DataStatusView window;
	
	public String getMessage() {
		
		return message;
		
	}
	
	public GeolocationResolver getGeoResolver() {
		
		return resolver;
		
	}
	
	private void setMessage(String m) {
		
		this.message = m;
		System.out.println("DMSG: " + m);
		window.setTitle(m);
		
	}

	@Override
	public void run() {
		
		window = new DataStatusView();
		window.setVisible(true);		
		
		setMessage("Starting data.");
		
		setMessage("Initialising indexes to zero");
		initialiseIndexes();
		
		setMessage("Initialising location resolver");
		resolver = new GeolocationResolver();
		
		setMessage("Fetching Twitter.");
		 new TwitterFetch(this);	
			setMessage("Refreshing Bloomberg data");
			try {
				new BloombergRefresh(this);
			} catch (Exception e) { e.printStackTrace(); }
			setMessage("Fetched.");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
						
			window.setVisible(false);
		
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
				
				index[x][y] = 0.5f;
				
			}
		}
		
		initialised = true;
		
	}
	
	public void updateIndex(int region, int emotion, float value) {
		
		if (region > index.length || emotion > index[0].length || region < 0 || emotion < 0) {
			return;
		}
		
		index[region][emotion] = value;
		normaliseIndex(region, emotion);
		
	}
	
	public void modifyIndex(int region, int emotion, float value) {
		
		if (region > index.length || emotion > index[0].length || region < 0 || emotion < 0) {
			return;
		}
		
		if (value == 0.0f) {
			return;
		}
		
		index[region][emotion] *= value;
		normaliseIndex(region, emotion);
		
	}
	
	private void normaliseIndex(int region, int emotion) {
		
		if (index[region][emotion] > 1.0f) index[region][emotion] = 1.0f;
		if (index[region][emotion] < 0.0f) index[region][emotion] = 0.0f;
		
	}

}
