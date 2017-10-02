package game;

public class Explosion extends Entity {

	public boolean isDone = false;
	
	public Explosion(int x, int y) {
		super(x, y);
	}

	public void tick() {
		isDone = true;
	}
	
	public boolean isDone() {
		return isDone;
	}

}
