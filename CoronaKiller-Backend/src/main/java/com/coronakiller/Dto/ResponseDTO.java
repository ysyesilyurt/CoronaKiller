package com.coronakiller.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
	private Object data;
	private String message;
	private String result;
}
