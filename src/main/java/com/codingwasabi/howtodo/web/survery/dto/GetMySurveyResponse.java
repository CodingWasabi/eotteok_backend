package com.codingwasabi.howtodo.web.survery.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetMySurveyResponse {
	private int tendency;
	private List<DateInfoResponse> calendar;
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class DateInfoResponse {
		private LocalDate date;
		private int commentCount;
		private List<SubjectResponse> subjects;
		
		@Getter
		@Setter
		@AllArgsConstructor
		@NoArgsConstructor
		public static class SubjectResponse {
			private String name;
			private int hour;
		}
	}
}
