package com.codingwasabi.howtodo.web.exam.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.codingwasabi.howtodo.web.exam.entity.Exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetMyExamResponse {

	List<ExamResponse> subjects;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class ExamResponse {
		private String name;
		
		private int year;
		private int month;
		private int date;
		private int hour;
		private int minute;
		private int prepareTime;
		
		public ExamResponse(Exam exam) {
			this.name = exam.getName();
			this.year = exam.getDueDateTime().getYear();
			this.month = exam.getDueDateTime().getMonthValue();
			this.date = exam.getDueDateTime().getDayOfMonth();
			this.hour = exam.getDueDateTime().getHour();
			this.minute = exam.getDueDateTime().getMinute();
			this.prepareTime = exam.getStudyDegree();
		}
	}
}
