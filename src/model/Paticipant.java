package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tblPaticipant")
public class Paticipant {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
	private long id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "nickName")
	private String nickName;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "lastTimeIn",nullable = true)
	private String lastTimeIn;
	
	@Column(name = "lastTimeOut",nullable = true)
	private String lastTimeOut;
	
	
	@ManyToOne
	@JoinColumn(name="clubID",nullable = true)
	private Club club;
//	
	@Column(name = "tounamentID",nullable = true)
	private long tounamentID;
	
	public Paticipant() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Paticipant(long id, String username, String password, String nickName, String status, String lastTimeIn,
			String lastTimeOut, Club club, long tounamentID) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.nickName = nickName;
		this.status = status;
		this.lastTimeIn = lastTimeIn;
		this.lastTimeOut = lastTimeOut;
		this.club = club;
		this.tounamentID = tounamentID;
	}


	public Paticipant(String username, String password, String nickName, String status, String lastTimeIn,
			String lastTimeOut, Club club, long tounamentID) {
		super();
		this.username = username;
		this.password = password;
		this.nickName = nickName;
		this.status = status;
		this.lastTimeIn = lastTimeIn;
		this.lastTimeOut = lastTimeOut;
		this.club = club;
		this.tounamentID = tounamentID;
	}

	public Paticipant(String username, String password, String nickName) {
		super();
		this.username = username;
		this.password = password;
		this.nickName = nickName;
		
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLastTimeIn() {
		return lastTimeIn;
	}

	public void setLastTimeIn(String lastTimeIn) {
		this.lastTimeIn = lastTimeIn;
	}

	public String getLastTimeOut() {
		return lastTimeOut;
	}

	public void setLastTimeOut(String lastTimeOut) {
		this.lastTimeOut = lastTimeOut;
	}

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	public long getTounamentID() {
		return tounamentID;
	}

	public void setTounamentID(long tounamentID) {
		this.tounamentID = tounamentID;
	}
	
}
