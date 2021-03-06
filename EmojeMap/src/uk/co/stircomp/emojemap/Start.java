package uk.co.stircomp.emojemap;

import uk.co.stircomp.emojemap.data.DataManager;
import uk.co.stircomp.emojemap.data.Emotion;
import uk.co.stircomp.emojemap.data.Insider;
import uk.co.stircomp.emojemap.data.Region;
import uk.co.stircomp.emojemap.UI.MiseryMap;
import uk.co.stircomp.emojemap.UI.WorldUI;

public class Start {

	public static void main(String[] args) {
	
		System.out.println("-- A map of world emotions --");	
		
		// Create the Data Manager
		DataManager data = new DataManager();
		Thread t = new Thread(data);
		t.start();		
		// Data can now be accessed from data.		
		new MiseryMap(data).setVisible(true);		
		
		//Insider debugView = new Insider(data);
		//data.addObserver(debugView);
		
		data.getRegionalIndex(Region.getRegionIndex("africa"), Emotion.getEmotionIndex("anger"));
		
	}
	
	
	
}
