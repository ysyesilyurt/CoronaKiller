package com.coronakiller.Dto;

import com.coronakiller.Enum.ShipType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameSessionDTO {
	private Integer currentLevel;
	private Integer sessionScore;
	private Integer shipHealth;
	private ShipType shipType;
}
