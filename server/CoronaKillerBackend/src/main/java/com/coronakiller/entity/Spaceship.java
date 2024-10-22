package com.coronakiller.entity;

import com.coronakiller.enums.ShipType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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
	@Temporal(TemporalType.DATE)
	@Column(name = "created_at", nullable = false, updatable = false)
	private Date createdAt;

	@LastModifiedDate
	@Temporal(TemporalType.DATE)
	@Column(name = "last_modified_at", nullable = false)
	private Date lastModifiedAt;

	@Column(name = "health", nullable = false)
	private Integer health;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "type", nullable = false)
	private ShipType type;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "spaceship")
	private GameSession gameSession;

	@Override
	public String toString() {
		return String.format("SpaceshipEntityModel with id %d and session id %s", id, gameSession.getId());
	}
}
