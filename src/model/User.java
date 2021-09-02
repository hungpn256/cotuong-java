package model;

public class User {
	private long id;
	private String username;
	private int win;
	private String password;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(String username, int win, String password) {
		super();
		this.username = username;
		this.win = win;
		this.password = password;
	}
	
	public User(long id, String username, int win, String password) {
		super();
		this.id = id;
		this.username = username;
		this.win = win;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
