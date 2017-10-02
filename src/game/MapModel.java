package game;

import java.io.Serializable;
import java.util.ArrayList;

public class MapModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private int height = 15;
	private int width = 15;
	private ArrayList <Character> players = new ArrayList <Character> ();
	private ArrayList <Wall> walls = new ArrayList <Wall> ();
	private ArrayList <Brick> bricks = new ArrayList <Brick> ();
	private ArrayList <Bomb> bombs = new ArrayList <Bomb> ();
	private ArrayList <Explosion> explosions = new ArrayList <Explosion> ();
	private ArrayList <PowerUp> powerups = new ArrayList <PowerUp> ();
	private String displayText = "";
	
	public MapModel() {
		
	}
	
	public MapModel(Map map) {
		height = map.getHeight();
		width = map.getWidth();
		players = new ArrayList <Character> (map.getPlayers());
		walls = new ArrayList <Wall> (map.getWalls());
		bricks = new ArrayList <Brick> (map.getBricks());
		bombs = new ArrayList <Bomb> (map.getBombs());
		explosions = new ArrayList <Explosion> (map.getExplosions());
		powerups = new ArrayList <PowerUp> (map.getPowerUps());
		displayText = map.getdisplayText();
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setPlayers(ArrayList <Character> players) {
		this.players = players;
	}
	
	public void setWalls(ArrayList <Wall> walls) {
		this.walls = walls;
	}
	
	public void setBricks(ArrayList <Brick> bricks) {
		this.bricks = bricks;
	}
	
	public void setBombs(ArrayList <Bomb> bombs) {
		this.bombs = bombs;
	}

	public void setExplosions(ArrayList <Explosion> explosions) {
		this.explosions = explosions;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}

	public String getDisplayText() {
		return displayText;
	}
	
	public ArrayList <Explosion> getExplosions() {
		return explosions;
	}
	
	public ArrayList <Character> getPlayers() {
		return players;
	}
	
	public ArrayList <Wall> getWalls() {
		return walls;
	}
	
	public ArrayList <Brick> getBricks() {
		return bricks;
	}
	
	public ArrayList <Bomb> getBombs() {
		return bombs;
	}
	
	public ArrayList <PowerUp> getPowerUps() {
		return powerups;
	}
	
	public Map getMap() {
		return new Map(this);
	}
	
}
