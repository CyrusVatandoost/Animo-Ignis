package socket;

import gui.GUIClient;
	
public class DriverClient {

	public static void main(String[] args) {
		Client client = new Client();
		GUIClient gui = new GUIClient(client);
		gui.run();
	}
	
}
