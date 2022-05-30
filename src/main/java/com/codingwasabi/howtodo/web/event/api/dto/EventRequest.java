package com.codingwasabi.howtodo.web.event.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class EventRequest {

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Attend {
		private String name;
		private String phoneNumber;
		private String img;
	}
}
