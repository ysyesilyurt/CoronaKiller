package com.coronakiller.entity;

import com.coronakiller.enums.PlayerRole;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/* Note: Lombok's @Builder conflicts with Mapstruct's AfterMapping */
@AllArgsConstructor
@NoArgsConstructor
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
	private long createdAt;

	@LastModifiedDate
	@Column(name = "last_modified_at", nullable = false)
	private long lastModifiedAt;

	@Column(name = "username", length = 255, nullable = false, unique = true)
	private String username;

	/* Password will be kept as hashed string into db */
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "total_score", nullable = false)
	private Long totalScore;

	@Column(name = "has_ongoing_session", nullable = false)
	private Boolean hasOngoingSession;

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
