package com.coronakiller.Entity;

import com.coronakiller.Enum.ShipType;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Entity(name = "Spaceship")
@Table(name = "spaceship")
@EntityListeners(AuditingEntityListener.class)
public class Spaceship {
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

	@Column(name = "health", nullable = false)
	private Integer health;

	@NotNull
	@Enumerated(EnumType.ORDINAL) // TODO: check
	@Column(name = "type", nullable = false)
	private ShipType type;

	@NotNull // A ship can not exist without a session
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "spaceship")
	private GameSession gameSession;

	@Override
	public String toString() {
		return String.format("SpaceshipEntityModel with id %d and session id %s", id, gameSession.getId());
	}
}
