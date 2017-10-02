package game;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Event implements Serializable {

	private static final long serialVersionUID = 1L;
	private static int NULL = -1;
	private String IP;
	private String action;
	private boolean accepted;
	private Account account;
	
	private int playerNum = NULL;
	private Map map;
	private MapModel model;
	
	public Event(int playerNum, String action) {
		this.playerNum = playerNum;
		this.action = action;
		//this.IP = getMachineIP();
		//this.accepted = false;
	}
	
	public Event(String action) {
		this.action = action;
		//this.IP = getMachineIP();
		//this.accepted = false;
	}
	
	public Event(Map map) {
		this.map = map;
		this.action = "set map";
		//this.IP = getMachineIP();
		//this.accepted = false;
	}
	
	public Event(MapModel model, String action) {
		this.model = model;
		this.action = action;
		//this.IP = getMachineIP();
		//this.accepted = false;
	}
	
	public Event(Account account, String action) {
		this.action = "set account";
		this.account = account;
		this.accepted = false;
	}
	
	public void accept() {
		this.accepted = true;
	}
	
	public boolean isAccepted() {
		return accepted;
	}
	
	public int getPlayerNum() {
		return playerNum;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public Map getMap() {
		return map;
	}
	
	public MapModel getMapModel() {		
		return model;
	}
	
	private String getMachineIP() {
		InetAddress inet = null;
		try {
			inet = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return inet.getHostAddress();
	}
	
	public String getAction() {
		return action;
	}
	
	public String getIP() {
		return IP;
	}
	
}
