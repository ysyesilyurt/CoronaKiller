package com.coronakiller.ui.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Score {
	private String username;
	private Long score;
}
