package game;

import java.io.Serializable;

public class Account implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name = "user";
	private int num;
	
	public Account(String name, int num) {
		this.name = name;
		this.num = num;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setNum(int num) {
		this.num = num;
	}
	
	public int getNum() {
		return num;
	}
	
	public String getName() {
		return name;
	}
	
}
