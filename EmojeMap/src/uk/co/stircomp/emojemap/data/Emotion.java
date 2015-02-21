package uk.co.stircomp.emojemap.data;

public class Emotion {
	
	public static final int		ANGRY = 0,
								FEAR = 1,
								HAPPY = 2,
								SAD = 3;
	
	public static final String[] EMOTIONS = {
		"ANGRY", "FEAR", "HAPPY", "SAD"
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
