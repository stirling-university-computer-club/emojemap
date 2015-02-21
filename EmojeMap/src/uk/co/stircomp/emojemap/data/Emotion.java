package uk.co.stircomp.emojemap.data;

public class Emotion {
	
	public final int	ANGRY = 0;
	
	public static final String[] EMOTIONS = {
		"ANGRY"
	};

	public static int getEmotionIndex(String emotion) {
		
		for (int i = 0; i < EMOTIONS.length; i++) {
			
			String x = EMOTIONS[i];
			
			if (x.equals(emotion.toUpperCase())) {
				
				return i;
				
			}
			
		}
		
		return -1;
		
	}						

}
