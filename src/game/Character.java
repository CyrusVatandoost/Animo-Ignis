package game;

public class Character extends Entity {

	private static final long serialVersionUID = 1L;
	private int num;
	private int strength = 1;
	private boolean isAlive = true;
	private boolean deadBody = false;
	private boolean canRemove = false;
	private boolean hasShield = false;
	private int points;
	private int bombLimit = 1;
	private int bombs = bombLimit;
	
	public Character(int num, int x, int y) {
		super(x, y);
		this.num = num;
	}
	
	public void moveUP() {
		if(isAlive)
			y--;
	}
	
	public void moveDOWN() {
		if(isAlive)
			y++;
	}
	
	public void moveLEFT() {
		if(isAlive)
			x--;
	}
	
	public void moveRIGHT() {
		if(isAlive)
			x++;
	}
	
	public void upgradeStrength() {
		strength++;
	}
	
	public void kill() {
		if(hasShield) {
			hasShield = false;
			isAlive = true;
		}
		else
			isAlive = false;
	}
	
	public void remove() {
		this.canRemove = true;
	}

	public void beACorpse() {
		deadBody = true;
	}
	
	public void plantBomb() {
		bombs--;
	}
	
	public void addBomb() {
		if(bombs < bombLimit)
			bombs++;
	}
	
	public void addBombLimit() {
		bombLimit++;
		bombs++;
	}
	
	public void addShield() {
		hasShield = true;
	}
	
	public void addPoints(int points) {
		points += points;
	}
	
	public void usePowerUp(PowerUp powerup) {
		if(powerup instanceof UpBomb) {
			addBombLimit();
		}
		else if(powerup instanceof UpShield) {
			addShield();
		}
		else if(powerup instanceof UpStrength) {
			upgradeStrength();
		}
	}
	
	public boolean isDead() {
		return !isAlive;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public boolean hasShield() {
		return hasShield;
	}
	
	public boolean canPlantBomb() {
		if(bombs > 0)
			return true;
		return false;
	}
	
	public boolean isCorpse() {
		return deadBody;
	}
	
	public boolean canRemove() {
		return canRemove;
	}
	
	public int getNum() {
		return num;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public int getPoints() {
		return points;
	}
	
}
