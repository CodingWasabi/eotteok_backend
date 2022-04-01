package com.codingwasabi.howtodo.web.calender.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.codingwasabi.howtodo.web.exam.entity.Exam;
import com.codingwasabi.howtodo.web.todo.entity.ToDo;

import lombok.AccessLevel;
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
public class GetMyCalenderResponse {
	private String nickname;
	private int tendency;
	private List<SubjectInfo> subjects;
	private List<DateInfo> calendar;

	@Getter
	@Setter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@NoArgsConstructor
	public static class SubjectInfo {
		private String name;
		private LocalDateTime date;
		private int d_day;

		public static SubjectInfo from(Exam exam) {
			return new SubjectInfo(exam.getName(), exam.getDateTime(), exam.getDDay(LocalDate.now()));
		}
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class DateInfo {
		private LocalDate date;
		private int commentCount;
		private List<ToDoInfo> subjects;

		@Getter
		@Setter
		@AllArgsConstructor(access = AccessLevel.PRIVATE)
		@NoArgsConstructor
		public static class ToDoInfo {
			private String name;
			private double hour;

			public static ToDoInfo from(ToDo toDo) {
				return new ToDoInfo(toDo.getExam()
										.getName(), toDo.getHour());
			}
		}
	}
}
