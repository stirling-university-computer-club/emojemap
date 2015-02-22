package uk.co.stircomp.emojemap.data;

import java.util.Random;

public class RandomChanges implements Runnable {
	
	private DataManager dm;
	private Random rand;
	
	public RandomChanges(DataManager dm) {
		
		rand = new Random();
		this.dm = dm;
		
	}
	
	public void run() {
		
		while(true) {
			
			dm.updateIndex(rand.nextInt(Region.REGIONS.length), Emotion.FEAR, rand.nextFloat());

			try {
				Thread.sleep(rand.nextInt(500));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
