package com.coronakiller.ui.model;

import lombok.*;

/**
 * Model for the cookie of application
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameData {
	private Player playerDTO;
	private GameSession gameSessionDTO; /* May be null if player does not have any ongoing game session */
}
