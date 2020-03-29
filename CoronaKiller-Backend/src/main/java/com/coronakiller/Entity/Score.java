package com.coronakiller.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
@Entity(name = "Score")
@Table(name = "score")
@EntityListeners(AuditingEntityListener.class)
public class Score {
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

	@Column(name = "score", nullable = false)
	private Long score;

	@NotNull // A song can not exist without an album
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "player_id", nullable = false)
	private Player player;

	@Override
	public String toString() {
		return String.format("ScoreEntityModel with id %d and player id %s", id, player.getId());
	}
}
