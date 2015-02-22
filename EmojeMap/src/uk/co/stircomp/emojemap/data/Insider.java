package uk.co.stircomp.emojemap.data;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Insider extends JFrame implements Observer {
	
	private DataManager dm;
	private JLabel message;

	public Insider(DataManager d) {
		super();
		
		dm = d;
		
		message = new JLabel("");		
		add(message);
		
		setTitle("Insider");
		setSize(400, 120);
		setVisible(true);
		
	}

	@Override
	public void update(Observable o, Object arg) {
		
		updateMessage();
		
	}
	
	private void updateMessage() {
		
		setTitle("UK Happy: " + dm.getRegionalIndex(Region.UNITED_KINGDOM, Emotion.HAPPY));
		
	}
	
}
