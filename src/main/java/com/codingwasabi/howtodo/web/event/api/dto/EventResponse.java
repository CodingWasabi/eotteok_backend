package com.codingwasabi.howtodo.web.event.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class EventResponse {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Image {
		private String link;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Attend {
		private int status;
	}
}
