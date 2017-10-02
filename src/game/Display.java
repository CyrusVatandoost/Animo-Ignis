package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class Display extends JComponent {
	
	private static final long serialVersionUID = 1L;
	private final String DEFAULT		= "lib/sprites/default_001.png";
	private final String SHIELD			= "lib/sprites/shield_001.png";
	private final String FLOOR1			= "lib/sprites/floor_001.png";
	private final String FLOOR2			= "lib/sprites/floor_002.png";
	private final String WALL			= "lib/sprites/wall_001.png";
	private final String BRICK			= "lib/sprites/brick_001.png";
	private final String BOMB0			= "lib/sprites/bomb_000.png";
	private final String BOMB1			= "lib/sprites/bomb_001.png";
	private final String BOMB2			= "lib/sprites/bomb_002.png";
	private final String BOMB3			= "lib/sprites/bomb_003.png";
	private final String EXPLOSION		= "lib/sprites/explosion_001.png";
	private final String UP_BOMB		= "lib/sprites/powerup_bomb_001.png";
	private final String UP_STRENGTH	= "lib/sprites/powerup_strength_001.png";
	private final String UP_SHIELD		= "lib/sprites/powerup_shield_001.png";
	private final String PLAYER1		= "lib/sprites/player_001.png";
	private final String PLAYER2		= "lib/sprites/player_002.png";
	private final String PLAYER3		= "lib/sprites/player_003.png";
	private final String PLAYER4		= "lib/sprites/player_004.png";
	private final String PLAYER5		= "lib/sprites/player_005.png";
	private final String PLAYER6		= "lib/sprites/player_006.png";
	private final String PLAYER7		= "lib/sprites/player_007.png";
	private final String PLAYER8		= "lib/sprites/player_008.png";
	private final String PLAYER9		= "lib/sprites/player_009.png";
	private final String PLAYER10		= "lib/sprites/player_010.png";
	private final String DEAD			= "lib/sprites/dead_001.png";
	private final String WIN			= "lib/sprites/text/win_001.png";
	private final String LOSE			= "lib/sprites/text/lose_001.png";
	private final String GAMEOVER		= "lib/sprites/text/gameover_001.png";
	private final String TITLE			= "lib/sprites/text/screen_title_001.png";	
	
	private Graphics g;

	private int windowHeight;
	private int windowWidth;

	private Map map;
	private MapModel model;
	
	private int mapHeight;
	private int mapWidth;
	private ArrayList <Integer> widthList;
	private ArrayList <Integer> heightList;
	private int yHeight;
	private int xWidth;
	private JFrame frame;
	
	private ArrayList <Character> players = new ArrayList <Character> ();
	private ArrayList <Wall> walls = new ArrayList <Wall> ();
	private ArrayList <Brick> bricks = new ArrayList <Brick> ();
	private ArrayList <Bomb> bombs = new ArrayList <Bomb> ();
	private ArrayList <Explosion> explosions = new ArrayList <Explosion> ();
	private ArrayList <PowerUp> powerups = new ArrayList <PowerUp> ();
	
	private int fps = 20;
	private double dfpms = (1 / fps) * 1000;
	private int fpms = (int) dfpms;
	
	public Display() {
		
	}
	
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
	/**
	 * This function initializes a thread for Display.
	 */
	public void startAnimationThread() {
		Thread animationThread = new Thread(new Runnable() {
			public void run() {
				while(true) {
					repaint();
					try {
						Thread.sleep(fpms);	// Repaint every 0.050 seconds, 20 fps
					}
					catch(Exception ex) {
				
					}
				}
			}
		});
		animationThread.start();
	}
	
	public void setFPS(int fps) {
		System.out.println(fps);
		if(fps < 1)
			fps = 1;
		this.fps = fps;
		this.dfpms = (1 / (double) fps) * 1000;
		this.fpms = (int) dfpms;
/*		System.out.println("FPS: "  + fps);
		System.out.println("DFMPS: "  + dfpms);
		System.out.println("FPMS: "  + fpms);
		System.out.println();*/
	}
	
	public void paintComponent(Graphics g) {
		
		this.g = g;
		super.paintComponent(g);
		update();
		setExtras();
		drawFloor();
		drawWalls();
		drawPowerUps();
		drawBricks();
		drawPlayers();
		drawBombs();
		drawExplosions();
		drawText();
		
		//drawStats();
		//drawGrid();
		//repaint();
		
/*		System.out.println("Window height:\t\t" + this.getHeight());
		System.out.println("mapHeight:\t\t" + mapHeight);
		System.out.println("yHeight:\t\t" + this.getHeight() / mapHeight);
		System.out.println("mapHeight * yheight:\t" + (this.getHeight() / mapHeight) * mapHeight );
		System.out.println();
		
		System.out.println("Window width:\t\t" + this.getWidth());
		System.out.println("mapWidth:\t\t" + mapWidth);
		System.out.println("yWidth:\t\t\t" + this.getWidth() / mapWidth);
		System.out.println("mapWidth * yWidth:\t" + (this.getWidth() / mapWidth) * mapWidth );
		System.out.println();
		System.out.println();
		System.out.println();*/
	}
	
	public void setFrameSize(int size) {
		int frameWidth = 0;
		int frameHeight = 0;
		
		switch(size) {
		case 1:
			frameWidth = 541;
			frameHeight = 564;
			break;
		case 2:
			frameWidth = 691;
			frameHeight = 714;
			break;
		case 3:
			frameWidth = 766;
			frameHeight = 789;
			break;
		case 4:
			frameWidth = 1036;
			frameHeight = 1059;
			break;
		case 5:
			frameWidth = 1366;
			frameHeight = 1059;	
			break;
		}
		
		frame.setSize(frameWidth, frameHeight);
	}
	
	/**
	 * This function updates the length values of the component to display properly.
	 */
	public void update() {
		windowHeight = this.getHeight();
		windowWidth = this.getWidth();
		yHeight = windowHeight/mapHeight;
		xWidth = windowWidth/mapWidth;
		
		players = model.getPlayers();
		walls = model.getWalls();
		bricks = model.getBricks();
		bombs = model.getBombs();
		explosions = model.getExplosions();
		powerups = model.getPowerUps();
	
/*		players = new ArrayList <Character> (map.getPlayers());
		walls = new ArrayList <Wall> (map.getWalls());
		bricks = new ArrayList <Brick> (map.getBricks());
		bombs = new ArrayList <Bomb> (map.getBombs());
		explosions = new ArrayList <Explosion> (map.getExplosions());
		powerups = new ArrayList <PowerUp> (map.getPowerUps());*/
		
/*		players = new ArrayList <Character> (model.getPlayers());
		walls = new ArrayList <Wall> (model.getWalls());
		bricks = new ArrayList <Brick> (model.getBricks());
		bombs = new ArrayList <Bomb> (model.getBombs());
		explosions = new ArrayList <Explosion> (model.getExplosions());
		powerups = new ArrayList <PowerUp> (model.getPowerUps());*/
	}
	
	public void drawPlayers() {
		for(Character c : players) {
			if(c.isAlive()) {
				switch(c.getNum()) {
				case 0:
					fillCell(c.getX(), c.getY(), PLAYER1);
					break;
				case 1:
					fillCell(c.getX(), c.getY(), PLAYER2);
					break;
				case 2:
					fillCell(c.getX(), c.getY(), PLAYER3);
					break;
				case 3:
					fillCell(c.getX(), c.getY(), PLAYER4);
					break;
				case 4:
					fillCell(c.getX(), c.getY(), PLAYER4);
					break;
				case 5:
					fillCell(c.getX(), c.getY(), PLAYER5);
					break;
				case 6:
					fillCell(c.getX(), c.getY(), PLAYER6);
					break;
				case 7:
					fillCell(c.getX(), c.getY(), PLAYER7);
					break;
				case 8:
					fillCell(c.getX(), c.getY(), PLAYER8);
					break;
				case 9:
					fillCell(c.getX(), c.getY(), PLAYER9);
					break;
				case 10:
					fillCell(c.getX(), c.getY(), PLAYER10);
					break;
				}
				if(c.hasShield())
					fillCell(c.getX(), c.getY(), SHIELD);
			}
			else
				fillCell(c.getX(), c.getY(), DEAD);
		}
	}
	
	public void drawBombs() {
		for(Bomb b : bombs) {
			if(b.getTimer() == 0)
				fillCell(b.getX(), b.getY(), BOMB0);
			else if(b.getTimer() == 1)
				fillCell(b.getX(), b.getY(), BOMB1);
			else if(b.getTimer() == 2)
				fillCell(b.getX(), b.getY(), BOMB2);
			else if(b.getTimer() == 3)
				fillCell(b.getX(), b.getY(), BOMB3);
		}
	}
	
	public void drawBricks() {
		for(Brick b : bricks) {
			fillCell(b.getX(), b.getY(), BRICK);
		}
	}

	public void drawExplosions() {
		for(Explosion b : explosions) {
			fillCell(b.getX(), b.getY(), EXPLOSION);
		}
	}

	public void drawWalls() {
		for(Wall w : walls) {
			fillCell(w.getX(), w.getY(), WALL);
		}
	}
	
	public void drawPowerUps() {
		for(PowerUp p : powerups) {
			if(p instanceof UpBomb) {
				fillCell(p.getX(), p.getY(), UP_BOMB);
			}
			else if(p instanceof UpShield) {
				fillCell(p.getX(), p.getY(), UP_SHIELD);
			}
			else if(p instanceof UpStrength) {
				fillCell(p.getX(), p.getY(), UP_STRENGTH);
			}
		}
	}
	
	/**
	 * This function draws a grid according to the grid of Map.
	 */
	public void drawGrid() {
		g.setColor(Color.BLACK);
		// This paints vertical lines.
		for(int i = 0; i < mapWidth; i++) {
			int xStart = (i * xWidth) + widthList.get(i);
			g.drawLine(xStart, 0, xStart, windowHeight);
		}
		g.setColor(Color.BLACK);
		// This paints horizontal lines.
		for(int i = 0; i < mapHeight; i++) {
			int yStart = (i * yHeight) + heightList.get(i);
			g.drawLine(0, yStart, windowWidth, yStart);
		}
	}
	
	/**
	 * This function draws the floor of Map.
	 */
	public void drawFloor() {
		g.setColor(Color.BLUE);
		for(int x = 0; x < mapWidth; x++) {
			for(int y = 0; y < mapHeight; y++) {
				if(x % 2 == 0) {
					if(y % 2 == 0)
						fillCell(x, y, FLOOR1);
					else
						fillCell(x, y, FLOOR2);
				}
				else {
					if(y % 2 == 0)
						fillCell(x, y, FLOOR2);
					else
						fillCell(x, y, FLOOR1);
				}
			}
		}
	}
	
	public void drawStats() {
/*		for(Character c : map.getPlayers()) {
			g.setColor(Color.BLUE);
		}*/
	}
	
	public void drawMessages() {
		
	}
	
	public void drawText() {
		if(model.getDisplayText().equalsIgnoreCase("gameover"))
			fillScreen(GAMEOVER);
		else if(model.getDisplayText().equalsIgnoreCase("title"))
			fillScreen(TITLE);
	}
	
	/**
	 * This function paints a cell with color.
	 * @param x This is the x-coordinate of the cell.
	 * @param y This is the y-coordinate of the cell.
	 */
	public void fillCell(int x, int y) {
		g.setColor(Color.BLUE);
		int x1 = (x * xWidth) + widthList.get(x);
		int y1 = (y * yHeight) + heightList.get(y);
		int x2 = xWidth + widthList.get(x);
		int y2 = yHeight + heightList.get(y);
		g.fillRect(x1, y1, x2, y2);
	}
	
	/**
	 * This function paints a cell with an image.
	 * @param x	This is the x-coordinate of the cell.
	 * @param y This is the y-coordinate of the cell.
	 * @param fileName	This is the location of the image file to be drawn.
	 */
	public void fillCell(int x, int y, String fileName) {
		int x1 = (x * xWidth) + widthList.get(x);
		int y1 = (y * yHeight) + heightList.get(y);
		int x2 = xWidth + widthList.get(x);
		int y2 = yHeight + heightList.get(y);
		
		Image img = null;
		try {
			img = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		g.drawImage(img, x1, y1, x2, y2, null);
	}
	
	public void fillScreen(String fileName) {
		Image img = null;
		try {
			img = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(img, 0, 0, windowWidth, windowHeight, null);
	}
	
	/**
	 * This funcion calculates for the lacking length of every cell.
	 */
	public void setExtras() {
		widthList = new ArrayList <Integer> ();
		heightList = new ArrayList <Integer> ();
		
		int extraHeight = windowHeight - (yHeight * mapHeight);
		int extraWidth = windowWidth - (xWidth * mapWidth);
		
		if(extraHeight < 0)
			extraHeight = mapHeight - 1;
		if(extraWidth < 0)
			extraWidth = mapWidth - 1;
		
		widthList.add(0);
		heightList.add(0);
		
		int num1 = 0;
		for(int i = 0; i < mapHeight; i++) {
			if(extraHeight > 0)
				num1 = num1 + 1;
			//heightList.add(num1);
			heightList.add(0);
			if(extraHeight > 0)
				extraHeight--;
		}
		num1 = 0;
		for(int i = 0; i <= mapWidth; i++) {
			if(extraWidth > 0)
				num1 = num1 + 1;
			//widthList.add(num1);
			widthList.add(0);
			if(extraWidth > 0)
				extraWidth--;
		}
	}
	
	public void setMap(Map map) {
		this.map = map;
		mapHeight = map.getHeight();
		mapWidth = map.getWidth();
	}
	
	public void setMapModel(MapModel model) {
		this.model = model;
		mapHeight = model.getHeight();
		mapWidth = model.getWidth();
	}
	
}
