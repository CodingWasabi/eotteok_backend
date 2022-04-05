package com.codingwasabi.howtodo.web.calendar.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateCalendarRequest {
	private String nickname;
	private List<Integer> answers;
	private List<ExamInfo> exams;
	private int dailyQuota;

	@Setter
	@Getter
	@NoArgsConstructor
	public static class ExamInfo {
		private String name;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
		private LocalDateTime localDateTime;
		private int prepareTime;
	}
}
