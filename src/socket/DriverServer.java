package socket;

import game.GameServer;
import gui.GUIServer;

public class DriverServer {

	public static void main(String[] args) {
		GameServer game = new GameServer();
		Server server = new Server();
		GUIServer gui = new GUIServer(server, game);
		gui.run();
	}

}
