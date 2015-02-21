package uk.co.stircomp.emojemap;

import uk.co.stircomp.emojemap.data.DataManager;
import uk.co.stircomp.emojemap.UI.WorldUI;

public class Start {

	public static void main(String[] args) {
	
		System.out.println("-- A map of world emotions --");		
		
		WorldUI worldUI = new WorldUI();
		
		// Create the Data Manager
		DataManager data = new DataManager();
		Thread t = new Thread(data);
		t.start();		
		// Data can now be accessed from data.		
				
		
	}
	
	
	
}
