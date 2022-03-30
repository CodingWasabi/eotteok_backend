package com.codingwasabi.howtodo.web.calender.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateCalenderRequest {
	private String nickname;
	private List<Integer> answers;
	private List<SubjectInfo> subjects;

	@Setter
	@Getter
	@NoArgsConstructor
	public static class SubjectInfo {
		private String name;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
		private LocalDateTime localDateTime;
		private int prepareTime;
	}
}
