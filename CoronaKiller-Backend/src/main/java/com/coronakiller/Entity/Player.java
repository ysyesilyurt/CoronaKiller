package com.coronakiller.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "Player")
@Table(name = "player")
@EntityListeners(AuditingEntityListener.class)
public class Player {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private Date createdAt;

	@LastModifiedDate
	@Column(name = "last_modified_at", nullable = false)
	private Date lastModifiedAt;

	@Column(name = "username", length = 255, nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password; // TODO: check later for hashed values

	@Column(name = "total_score", nullable = false)
	private Long totalScore;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "player")
	// a player may have more than one score
	@OrderBy("createdAt DESC")
	private List<Score> scoreList;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "session_id")
	private GameSession gameSession; // if game session is null then newgame

	@Override
	public String toString() {
		return String.format("PlayerEntityModel with id %d and username %s", id, username);
	}
}
