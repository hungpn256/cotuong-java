package model;

public class User {
	private String username;
	private int win;
	public User(String username, int win){
		this.username  = username;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getWin() {
		return win;
	}
	public void setWin(int win) {
		this.win = win;
	}
}
