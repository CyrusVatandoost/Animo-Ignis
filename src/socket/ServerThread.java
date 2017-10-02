package socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import game.Event;
import game.GameServer;
import gui.GUIActivity;

public class ServerThread extends Thread {

	private GUIActivity activity;
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private GameServer game;
	private Event event;
	
	public ServerThread(Socket socket, GameServer game) {
		this.socket = socket;
		this.game = game;
		start();
	}
	
	public void run() {
		connect();
		while(socket.isConnected()) {
			try {
				event = (Event) inputStream.readObject();
				
				if(event.getAction().equalsIgnoreCase("set account")) {
					event.accept();
					send(event);
					System.out.println("event sent back");
				}
				
				game.decodeEvent(event);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void connect() {
		try {
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(Event event) {
		try {
			outputStream.writeObject(event);
			outputStream.reset();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void setActivity(GUIActivity activity) {
		this.activity = activity;
	}
	
}
