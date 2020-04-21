package com.coronakiller.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "GameSession")
@Table(name = "game_session")
@EntityListeners(AuditingEntityListener.class)
public class GameSession {
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

	@Column(name = "current_level", nullable = false)
	private Integer currentLevel;

	@Column(name = "session_score", nullable = false)
	private Long sessionScore;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ship_id", nullable = false)
	private Spaceship spaceship;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "gameSession")
	private Player player;

	@Override
	public String toString() {
		return String.format("GameSessionEntityModel with id %d and player id %s", id, player.getId());
	}
}
