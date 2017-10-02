package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import game.Account;
import game.Event;
import game.GameServer;
import gui.GUIActivity;

public class Server {

	private GUIActivity activity = new GUIActivity(null);
	private ArrayList <ServerThread> serverList = new ArrayList <ServerThread> ();
	private ServerSocket serverSocket;
	private Thread waitingThread;
	private Thread updateThread;
	private GameServer game;
	private boolean isConnected = false;
	
	public Server() {
		game = new GameServer();
	}
	
	public void waitForConnection(int port) {
		waitingThread = new Thread() {
			public void run() {
				try {
					serverSocket = new ServerSocket(port);
					isConnected = true;
					while(true) {
						activity.print("Server is waiting for Clients to connect.");
						addClient(serverSocket.accept());
					}
				} catch (IOException e) {
					activity.print("Could not listen on port: " + port);
					e.printStackTrace();
				}
			}
		};
		waitingThread.start();
	}
	
	public void startGame() {
		activity.print("Starting Game.");
		game.start();
		activity.print("Starting Game in Clients.");
		for(Account a : game.getAccounts()) {
			game.decodeEvent(new Event(a.getNum(), "add player"));
		}
		sendAll(new Event(game.getMapModel(), "set map"));
		startUpdateThread();
		activity.print("Game has started.");
	}
	
	public void askForAccounts() {
		sendAll(new Event("ask account"));
		System.out.println("Asking for Accounts.");
		while(true) {
			System.out.println(game.getAccounts().size() + " + " + serverList.size());
			if(game.getAccounts().size() == serverList.size())
				break;
		}
		System.out.println("Game has started.");
		startGame();
	}
	
	public void startUpdateThread() {
		updateThread = new Thread() {
			public void run() {
				try {
					while(true) {
						sleep(100);
						sendAll(new Event(game.getMapModel(), "update map"));
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		updateThread.start();
	}
	
	public void addClient(Socket socket) {
		activity.print("A new Client has connected.");
		serverList.add(new ServerThread(socket, game));
		serverList.get(serverList.size() - 1).setActivity(activity);
		activity.print("Number of Clients connected: " + serverList.size());
		System.out.println("Serverlist: " + serverList.size());
	}
	
	public void sendAll(Event event) {
		for(ServerThread st : serverList) {
			st.send(event);
		}
	}
	
	public void setActivity(GUIActivity activity) {
		this.activity = activity;
	}
	
	public boolean isConnected() {
		return isConnected;
	}
	
	public GameServer getGame() {
		return game;
	}
	
}
