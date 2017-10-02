package game;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Map {

	private MapModel model;
	
	private int height = 15;
	private int width = 15;
	
	private ArrayList <Character> players = new ArrayList <Character> ();
	private ArrayList <Wall> walls = new ArrayList <Wall> ();
	private ArrayList <Brick> bricks = new ArrayList <Brick> ();
	private ArrayList <Bomb> bombs = new ArrayList <Bomb> ();
	private ArrayList <Explosion> explosions = new ArrayList <Explosion> ();
	private ArrayList <PowerUp> powerups = new ArrayList <PowerUp> ();
	
	private boolean isOn = false;
	private boolean isRunning = false;
	
	private String displayText = "";
	
	private MusicPlayer music = new MusicPlayer();
	
	/**
	 * The Server initializes Map through this.
	 */
	public Map() {
		setWalls();
		setBricks();
		setPowerUps();
		displayText = "title";
	}
	
	/**
	 * The Client initializes Map through a MapModel sent by Server.
	 * @param model
	 */
	public Map(MapModel model) {
		height = model.getHeight();
		width = model.getWidth();
		players = model.getPlayers();
	}
	
	public void beginGame() {
		isRunning = true;
		music.playBackgroundMusic();
		displayText = "";
	}
	
	public void playSound(String fileName) {
	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(fileName).getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
	
	public void tick() {
		if(isRunning) {
			tickBombs();
			tickExplosions();
			tickPlayers();
			checkWinner();
		}
	}
	
	public void tickBombs() {

		int dontexplodehorizontally = 0;
		int dontexplodevertically = 0;
		int stop = 0;
		int anotherstop = 0;
		
		ArrayList <Bomb> toRemove = new ArrayList <Bomb> ();
		
		for(Bomb b : bombs) {
			if(b.isDone()) {

				music.playExplosion();
				addExplosion(b.getX(), b.getY());
				
				for (Wall w : walls) {
					if (b.getX() == w.getX() && (b.getY() == w.getY() + 1 || b.getY() == w.getY() - 1) && w.getY() != 0 && w.getY() != height - 1)
						dontexplodevertically = 1;
					else if (b.getY() == w.getY() && (b.getX() == w.getX() + 1 || b.getX() == w.getX() - 1) && w.getX() != 0 && w.getX() != width - 1)
						dontexplodehorizontally = 1;
				}

				for(int x = 0; x <= b.getStrength() && dontexplodehorizontally == 0 && (stop == 0 || anotherstop == 0); x++) {
					if (stop == 0){
						if(isFreeToExplode(b.getX() + x, b.getY()))
							addExplosion(b.getX() + x, b.getY());
						for (Brick k : bricks) {
							if(b.getX() + x == k.getX() && b.getY() == k.getY()){
								stop = 1;
								break;
							}
						}
					}
					if (anotherstop == 0){
						if(isFreeToExplode(b.getX() - x, b.getY()))
							addExplosion(b.getX() - x, b.getY());
						for (Brick k : bricks) {
							if(b.getX() - x == k.getX() && b.getY() == k.getY()){
								anotherstop = 1;
								break;
							}
						}
					}
				}
				
				stop = 0;
				anotherstop = 0;
					
				for(int y = 0; y <= b.getStrength() && dontexplodevertically == 0 && (stop == 0 || anotherstop == 0); y++) {
					if (stop == 0){
						if(isFreeToExplode(b.getX(), b.getY() + y))
							addExplosion(b.getX(), b.getY() + y);
						for (Brick k : bricks) {
							if(b.getX() == k.getX() && b.getY() + y == k.getY()){
								stop = 1;
								break;
							}
						}
					}
					if (anotherstop == 0) {
						if(isFreeToExplode(b.getX(), b.getY() - y))
							addExplosion(b.getX(), b.getY() - y);
						for (Brick k : bricks){
							if(b.getX() == k.getX() && b.getY() - y == k.getY()){
								anotherstop = 1;
								break;
							}
						}
					}
				}
				
				toRemove.add(b);
				getPlayer(b.getNum()).addBomb();
				
			}
			else
				b.tick();
		}
		bombs.removeAll(toRemove);
	}
	
	public void tickExplosions() {
		ArrayList <Explosion> toRemove = new ArrayList <Explosion> ();
		ArrayList <Brick> toVanish = new ArrayList <Brick> ();
		
		for(Explosion e : explosions) {
			
			if(e.isDone)
				toRemove.add(e);
			else {
				e.tick();
				for(Character p : players) {
					if(e.getX() == p.getX() && e.getY() == p.getY()) {
						p.kill();
					}
				}
				for(Brick b : bricks) {
					if(e.getX() == b.getX() && e.getY() == b.getY()) {
						toVanish.add(b);
					}
				}
			}
			
		}
		explosions.removeAll(toRemove);
		bricks.removeAll(toVanish);
	}
	
	public void tickPlayers() {
		ArrayList <PowerUp> toRemove = new ArrayList <PowerUp> ();
		ArrayList <Character> toKill = new ArrayList <Character> ();
		for(Character p : players) {
			if(p.canRemove()) {
				toKill.add(p);
			}
			else {
				if(p.isCorpse()) {
					p.remove();
				}
				else {
					if(p.isAlive()) {
						for(PowerUp u : powerups) {
							if(p.getX() == u.getX() && p.getY() == u.getY()) {
								p.usePowerUp(u);
								toRemove.add(u);
								music.playCollection();
							}
						}
					}
					else {
						p.beACorpse();
						music.playDeath();
					}
				}
			}
		}
		powerups.removeAll(toRemove);
		players.removeAll(toKill);
	}
	
	public void checkWinner() {
		if(isOver()) {
			if(!displayText.equalsIgnoreCase("gameover"))
				displayText = "gameover";
		}
	}
	
	public void addPlayer(int x, int y) {
		players.add(new Character(players.size() -  1, x, y));
	}
	
	public void addPlayer(int num) {
		if(!isOccupied(1, 1))
			players.add(new Character(num, 1, 1));
		else if(!isOccupied(1, 13))
			players.add(new Character(num, 1, 13));
		else if(!isOccupied(13, 1))
			players.add(new Character(num, 13, 1));
		else if(!isOccupied(13, 13))
			players.add(new Character(num, 13, 13));		
	}
	
	public void addBomb(int x, int y) {
		bombs.add(new Bomb(0, 0, x, y));
	}
	
	public void dropBomb(int num) {
		if(isRunning) {
			Character p = getPlayer(num);
			if(p.canPlantBomb()) {
				bombs.add(new Bomb(p.getNum(), p.getStrength(), p.getX(), p.getY()));
				p.plantBomb();
			}
		}
	}
	
	public void addBrick(int x, int y) {
		bricks.add(new Brick(x, y));
	}
	
	public void addExplosion(int x, int y) {
		explosions.add(new Explosion(x, y));
	}
	
	public void addWall(int x, int y) {
		walls.add(new Wall(x, y));
	}
	
	public void addUpStrength(int x, int y) {
		powerups.add(new UpStrength(x, y));
	}
	
	public void addUpBomb(int x, int y) {
		powerups.add(new UpBomb(x, y));
	}
	
	public void addUpShield(int x, int y) {
		powerups.add(new UpShield(x, y));
	}
	
	public void setTestMap() {
		addPlayer(0);
		addPlayer(1);
		addPlayer(2);
		addPlayer(3);
		addExplosion(2, 1);
		addBomb(1, 2);
		addUpBomb(12, 1);
		addUpShield(13, 2);
		addUpStrength(13, 12);
	}
	
	public void setBricks() {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				if(isFreeForBricks(x, y))
					addBrick(x, y);
			}
		}
	}
	
	public void setWalls() {
		for (int i = 0; i < width; i++)
			for(int j = 0; j < height; j++)
				if (i % 2 == 0 && (i == 0 || i == height - 1 || j % 2 == 0))
						addWall(i, j);
				else if((i == 1 || i < height - 1) && (j == 0 || j == height - 1))
						addWall(i,j);
	}
	
	public void setPowerUps() {
		Random rand = new Random();
		
		for(Brick b : bricks) {
			if(rand.nextInt(5) == 0) {
				switch(rand.nextInt(5)) {
				case 0:
					addUpStrength(b.getX(), b.getY());
					break;
				case 1:
					addUpStrength(b.getX(), b.getY());
					break;
				case 2:
					addUpBomb(b.getX(), b.getY());
					break;
				case 3:
					addUpBomb(b.getX(), b.getY());
					break;
				case 4:
					addUpShield(b.getX(), b.getY());
					break;
				}
			}
		}
	}
	
	public void update(MapModel model) {
/*		height = model.getHeight();
		width = model.getWidth();
		players = model.getPlayers();
		walls = model.getWalls();
		bricks = model.getBricks();
		bombs = model.getBombs();
		explosions = model.getExplosions();
		powerups = model.getPowerUps();
		displayText = model.getDisplayText();*/
		
		this.model = model;
	}
	
	public void setPlayers(ArrayList <Character> players) {
		this.players = players;
	}
	
	public void movePlayer(int num, String direction) {
		if(isRunning) {
			Character player = getPlayer(num);
			int x = player.getX();
			int y = player.getY();
			
			switch(direction) {
			case "up" :			
				
				if(y > 0 && isFree(x, y - 1))
					player.moveUP();
				
				break;
			case "down" :
				
				if(y < height - 1 && isFree(x, y + 1))
					player.moveDOWN();
				
				break;
			case "left" :
				
				if(x > 0 && isFree(x - 1, y))
					player.moveLEFT();
				
				break;
			case "right" :
				
				if(x < width - 1 && isFree(x + 1, y))
					player.moveRIGHT();
				
				break;
			}
		}
	}
	
	public ArrayList <Bomb> getBombs() {
		return bombs;
	}
	
	public ArrayList <Brick> getBricks() {
		return bricks;
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
	
	public ArrayList <PowerUp> getPowerUps() {
		return powerups;
	}
	
	public boolean isFree(int x, int y) {
		return !isOccupied(x, y);
	}
	
	public boolean isOccupied(int x, int y) {
		for(Character i : players)
			if(i.getX() == x && i.getY() == y)
				return true;
		
		for(Wall i : walls)
			if(i.getX() == x && i.getY() == y)
				return true;
		
		for(Brick i : bricks)
			if(i.getX() == x && i.getY() == y)
				return true;
		
		for(Bomb i : bombs)
			if(i.getX() == x && i.getY() == y)
				return true;

		if(x < 0 || x >= width || y < 0 || y >= height)
			return true;
		
		return false;
	}
	
	public boolean isFreeToExplode(int x, int y) {
		
		for(Wall i : walls)
			if(i.getX() == x && i.getY() == y)
				return false;
		
		for(Bomb i : bombs)
			if(i.getX() == x && i.getY() == y)
				return false;

		if(x < 0 || x >= width || y < 0 || y >= height)
			return false;
		
		return true;
	}
	
	public boolean isFreeForBricks(int x, int y) {
		
		ArrayList <Character> temp = new ArrayList <Character> ();
		temp.add(new Character(0, 1, 1));
		temp.add(new Character(0, 1, 13));
		temp.add(new Character(0, 13, 1));
		temp.add(new Character(0, 13, 13));
		
		for(Character i : temp)
			if( (i.getX() == x && i.getY() == y) ||
				(i.getX() - 1 == x && i.getY() == y) ||
				(i.getX() + 1 == x && i.getY() == y) ||
				(i.getX() == x && i.getY() - 1 == y) ||
				(i.getX() == x && i.getY() + 1 == y) )
				return false;
		
		for(Wall i : walls)
			if(i.getX() == x && i.getY() == y)
				return false;

		if(x < 0 || x >= width || y < 0 || y >= height)
			return false;
		
		return true;
		
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public boolean isOver() {
		if(players.size() == 1)
			return true;
		return false;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public String getdisplayText() {
		return displayText;
	}
	
	public Character getPlayer(int num) {
		for(Character p : players) {
			if(p.getNum() == num)
				return p;
		}
		return null;
	}
	
	public MapModel getMapModel() {
		MapModel model = new MapModel(this);
		return model;
	}
	
}
