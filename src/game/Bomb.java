package game;

public class Bomb extends Entity {

	private static final long serialVersionUID = 1L;
	private int num;
	private int timer = 3;
	private int strength;
	private boolean explode = false;
	
	public Bomb(int num, int strength, int x, int y) {
		super(x, y);
		this.num = num;
		this.strength = strength;
	}
	
	public void tick() {
		timer--;
		if(timer == 0)
			explode = true;
	}
	
	public boolean isDone() {
		return explode;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public int getNum() {
		return num;
	}
	
	public int getTimer() {
		return timer;
	}
	
}
