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
	private String password; /* In Bcrypt encoded form */
	private Long totalScore;
	private Long gameSessionId;
}
