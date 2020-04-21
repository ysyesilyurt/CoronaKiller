package com.coronakiller.dto;

import com.coronakiller.enums.ShipType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameSessionDTO {
	private Integer currentLevel;
	private Long sessionScore;
	private Integer shipHealth;
	private ShipType shipType;
}
