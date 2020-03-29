package com.coronakiller.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDTO {
	private String username;
	private String password;
	private Long totalScore;
	private Long gameSessionId; /* Could be null,
							       in that case player does not have any ongoing gameSession to continue */
}
