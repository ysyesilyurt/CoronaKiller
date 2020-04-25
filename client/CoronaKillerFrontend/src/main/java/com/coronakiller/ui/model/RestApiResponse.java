package com.coronakiller.ui.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestApiResponse {
	private Object data;
	private String message;
	private String result;
}
