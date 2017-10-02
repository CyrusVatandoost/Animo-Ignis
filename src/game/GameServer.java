package game;

import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;

public class GameServer {

	private ArrayList <Account> accounts = new ArrayList <Account> ();
	private JFrame frame;
	private Display display;
	private Thread gameThread;
	private Map map;
	private boolean isOnline = false;
	
	public GameServer() {
		frame = new JFrame("Server");
	}
	
	public void start() {
		map = new Map();
/*		display = new Display();
		display.setMap(map);
		display.startAnimationThread();
		setDisplay();*/
		startGameThread();
	}
	
	public void startGameThread() {
		gameThread = new Thread() {
			public void run() {
				try {
					while(true) {
						sleep(1000);
						map.tick();
						if(display != null)
							display.repaint();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		gameThread.start();
	}
	
	public void decodeEvent(Event event) {
		switch(event.getAction()) {
		case "add player" :
			map.addPlayer(event.getPlayerNum());
			break;
		case "move up" :
			map.movePlayer(event.getPlayerNum(), "up");
			break;
		case "move down" :
			map.movePlayer(event.getPlayerNum(), "down");
			break;
		case "move left" :
			map.movePlayer(event.getPlayerNum(), "left");
			break;
		case "move right" :
			map.movePlayer(event.getPlayerNum(), "right");
			break;
		case "drop bomb" :
			map.dropBomb(event.getPlayerNum());
			break;
		case "set account" :
			accounts.add(event.getAccount());
			System.out.println("Account added: " + event.getAccount().getName());
			break;
		}
	}
	
	public void setDisplay() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(766, 789);
		frame.setLocationRelativeTo(null);
		frame.add(display);
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("lib\\sprites\\logo_002.png"));
		display.setFrame(frame);
		isOnline = true;
	}
	
	public boolean isOnline() {
		return isOnline;
	}
	
	public ArrayList <Account> getAccounts() {
		return accounts;
	}
	
	public Display getDisplay() {
		return display;
	}
	
	public Map getMap() {
		return map;
	}
	
	public MapModel getMapModel() {
		return map.getMapModel();
	}
	
}
