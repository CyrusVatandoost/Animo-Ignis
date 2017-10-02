package gui;

import java.time.LocalTime;

import javax.swing.JTextArea;

public class GUIActivity {

	private JTextArea txtActivity = new JTextArea();
	
	public GUIActivity(JTextArea txtActivity) {
		if(txtActivity == null)
			this.txtActivity = new JTextArea();
		else
			this.txtActivity = txtActivity;
	}
	
	public void print(String message) {
		
		LocalTime time = LocalTime.now();
		
		txtActivity.append(time.getHour() + ":" + time.getMinute() +  " " + message + "\n");
	}
	
}
