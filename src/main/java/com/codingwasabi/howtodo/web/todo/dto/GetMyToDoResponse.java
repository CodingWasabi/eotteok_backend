package com.codingwasabi.howtodo.web.todo.dto;

import java.time.LocalDateTime;
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
public class GetMyToDoResponse {

	List<SubjectResponse> subjects;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class SubjectResponse {
		private String name;
		private LocalDateTime dueDateTime;
		private int delayed;
	}
}
