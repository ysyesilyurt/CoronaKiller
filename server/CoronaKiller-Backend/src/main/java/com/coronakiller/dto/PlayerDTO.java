package com.coronakiller.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO {
	private Long id;
	private String username;
	private String password;
	private Long totalScore;
	private Boolean hasOngoingSession;
	private Long gameSessionId; /* Refers to the ongoing game session of the player if hasOngoingSession is true,
	 							otherwise refers to the last (finished) gamesession of the player */
}
