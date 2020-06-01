package com.coronakiller.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameDataDTO {
	private PlayerDTO playerDTO;
	private GameSessionDTO gameSessionDTO;
}
