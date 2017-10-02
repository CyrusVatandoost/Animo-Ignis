package game;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;

import socket.Client;

public class GameClient {

	private JFrame frame;
	private Display display;
	private Map map;
	private MapModel model;
	private Thread gameThread;
	private int playerNum;
	private boolean isRunning = false;
	private Client client;
	private ObjectOutputStream outputStream;
	private Event event;
	private Account account;
	
	public GameClient(Client client) {
		this.client = client;
	}
	
	public void start(MapModel model) {
		this.model = model;
		display = new Display();
		//display.setMap(map);
		display.setMapModel(model);
		display.startAnimationThread();
		setDisplay();
		startGameThread();
		isRunning = true;
	}
	
	public void startGameThread() {
		gameThread = new Thread() {
			public void run() {
				try {
					while(true) {
						sleep(50);
						if(event != null) {
							outputStream.writeObject(event);
							event = null;
						}
					}
					
				} catch (InterruptedException | IOException e) {
					e.printStackTrace();
				}
			}
		};
		gameThread.start();
	}
	
	public void decodeEvent(Event event) {
		switch(event.getAction()) {
		case "set map" :
			start(event.getMapModel());
			break;
		case "set num" :
			playerNum = event.getPlayerNum();
			break;
		case "update map" :
			updateMap(event.getMapModel());
			break;
		case "ask account" :
			client.sendAccount(account);
			break;
		}
	}
	
	public void updateMap(MapModel model) {
		display.setMapModel(model);
	}
	
	public void setOutputStream(ObjectOutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public boolean isRunning() {
		return isRunning;
	}
	
	public Display getDisplay() {
		return display;
	}
	
	public int getPlayerNum() {
		return playerNum;
	}
	
	public void setDisplay() {
		frame = new JFrame("Client");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(541, 564);
		frame.setLocationRelativeTo(null);
		frame.add(display);
		frame.addKeyListener(new keyListener());
		frame.setVisible(true);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("lib\\sprites\\logo_002.png"));
		display.setFrame(frame);
	}
	
	public void setAccount(Account account) {
		this.account = account;
		playerNum = account.getNum();
	}
	
	public Account getAccount() {
		return account;
	}
	
	class keyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
		    if (e.getKeyCode() == KeyEvent.VK_UP) {
		    	event = new Event(playerNum, "move up");
		    }
		    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
		    	event = new Event(playerNum, "move down");
		    }
		    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
		    	event = new Event(playerNum, "move left");
		    }	
		    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
		    	event = new Event(playerNum, "move right");
		    }
		    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
		    	event = new Event(playerNum, "drop bomb");
		    }
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			
		}

		@Override
		public void keyTyped(KeyEvent event) {
			
		}
		
	}
	
}
