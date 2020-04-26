package com.coronakiller.ui.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameSession {
	private Integer currentLevel;
	private Long sessionScore;
	private Integer shipHealth;
	private ShipType shipType;
}
