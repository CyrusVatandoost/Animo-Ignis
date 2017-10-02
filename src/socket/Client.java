package socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import game.Account;
import game.Character;
import game.Event;
import game.GameClient;
import gui.GUIActivity;

public class Client {
	
	private GUIActivity activity;
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private String serverIP = "localhost";
	private int port = 4444;
	private GameClient game;
	private boolean ack = false;
	private Thread stopAndWaitThread;

	public Client() {
		game = new GameClient(this);
	}
	
	public Client(String serverIP, int port) {
		this.serverIP = serverIP;
		this.port = port;
	}
	
	public void connect(String serverIP, int port) {
		this.serverIP = serverIP;
		this.port = port;
		try {
			socket = new Socket(serverIP, port);
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
			game.setOutputStream(outputStream);
			activity.print("Connected to Server.");
			startInputThread();
		} catch (UnknownHostException e) {
			activity.print("Connection refused.");
			e.printStackTrace();
		} catch (IOException e) {
			activity.print("Connection refused.");
			e.printStackTrace();	
		}
	}
	
	public void startInputThread() {
		Thread inputThread = new Thread() {
			public void run() {
				while(socket.isConnected()) {
					try {
						
						Event event = (Event) inputStream.readObject();
						
						if(event.getAction().equalsIgnoreCase("set account")) {
							if(event.isAccepted()) {
								ack = true;
								activity.print("Ack received!");
							}
						}
						
						game.decodeEvent(event);
						
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		inputThread.setPriority(Thread.MAX_PRIORITY);
		inputThread.start();
	}
	
	public void send(Event event) {
		try {
			outputStream.writeObject(event);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendAccount(Account account) {
		stopAndWaitThread = new Thread() {
			public void run() {
				ack = false;
				while(!ack) {
					try {
						System.out.print("Sending Account.");
						activity.print("Sending Account.");
						send(new Event(account, "set account"));
						sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("StopAndWait Thread ended.");
			}
		};
		stopAndWaitThread.start();
	}
	
	public void setActivity(GUIActivity activity) {
		this.activity = activity;
	}
	
	public boolean isConnected() {
		if(socket != null)
			return socket.isConnected();
		return false;
	}
	
	public GameClient getGame() {
		return game;
	}
	
	public Account getAccount() {
		return game.getAccount();
	}
	
	public int getPlayerNum() {
		return game.getPlayerNum();
	}
	
}
