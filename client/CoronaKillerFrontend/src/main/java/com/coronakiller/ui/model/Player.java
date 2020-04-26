package com.coronakiller.ui.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {
	private Long id;
	private String username;
	private String password; /* In BCrpyt encoded form */
	private Long totalScore;
	private Boolean hasOngoingSession;
	private Long gameSessionId; /* Refers to the ongoing game session of the player if hasOngoingSession is true,
	 							otherwise refers to the last (finished) gamesession of the player */
}
