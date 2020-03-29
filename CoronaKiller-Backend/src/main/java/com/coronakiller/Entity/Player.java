package com.coronakiller.Entity;

import com.coronakiller.Enum.PlayerRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

	@Column(name = "username", length = 255, nullable = false, unique = true)
	private String username;

	/* Password will be kept as hashed string into db */
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "total_score", nullable = false)
	private Long totalScore;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private PlayerRole role;

	/* a player may have more than one score */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "player")
	@OrderBy("createdAt DESC")
	private List<Score> scoreList;

	/* if game session is null then newgame */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "session_id")
	private GameSession gameSession;

	@Override
	public String toString() {
		return String.format("PlayerEntityModel with id %d and username %s", id, username);
	}
}
