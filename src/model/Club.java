package model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tblClub")
public class Club {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="createdAt")
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name = "createdBy")
	private Paticipant createdBy;
	
	@OneToMany(mappedBy = "club")
	private List<Paticipant> listPaticipant;
	
	public Club() {
		// TODO Auto-generated constructor stub
	}

	
	public Club(long id, String name, LocalDateTime createdAt, Paticipant createdBy, List<Paticipant> listPaticipant) {
		super();
		this.id = id;
		this.name = name;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.listPaticipant = listPaticipant;
	}

	public Club(String name, LocalDateTime createdAt, Paticipant createdBy, List<Paticipant> listPaticipant) {
		super();
		this.name = name;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.listPaticipant = listPaticipant;
	}


	public List<Paticipant> getListPaticipant() {
		return listPaticipant;
	}


	public void setListPaticipant(List<Paticipant> listPaticipant) {
		this.listPaticipant = listPaticipant;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Paticipant getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Paticipant createdBy) {
		this.createdBy = createdBy;
	}
}
