package game;

import java.io.Serializable;

public class Entity implements Serializable {

	private static final long serialVersionUID = 1L;
	protected int x;
	protected int y;
	
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
