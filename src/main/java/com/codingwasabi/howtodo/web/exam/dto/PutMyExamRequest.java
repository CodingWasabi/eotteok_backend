package com.codingwasabi.howtodo.web.exam.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.codingwasabi.howtodo.web.exam.entity.Exam;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PutMyExamRequest {
	
	List<ExamRequest> subjects;
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ExamRequest {
		private String name;
		
		@JsonFormat(pattern = "yyyy/MM/dd kk:mm")
		private LocalDateTime date;
		
		private int prepareTime;
	}
}