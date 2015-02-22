package uk.co.stircomp.emojemap.data;

public class DataPointUpdate {
	
	public DataPointUpdate(int region, int emotion) {
		
		this.region = region;
		this.emotion = emotion;
		
	}
	
	private int region, emotion;
	
	public int getRegion() {
		
		return this.region;
		
	}
	
	public int getEmotion() {
		
		return this.emotion;
		
	}

}
