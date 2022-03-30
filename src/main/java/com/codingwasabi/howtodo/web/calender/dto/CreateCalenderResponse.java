package com.codingwasabi.howtodo.web.calender.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.codingwasabi.howtodo.web.exam.entity.Exam;
import com.codingwasabi.howtodo.web.subject.entity.Subject;
import com.fasterxml.jackson.annotation.JsonFormat;

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
public class CreateCalenderResponse {

	private String nickname;

	private int tendency;

	private List<SubjectInfo> subjects;

	private List<DateInfo> calendar;

	@Getter
	@Setter
	@NoArgsConstructor
	public static class SubjectInfo {
		private String name;
		private LocalDateTime date;
		private int d_day;

		public static SubjectInfo from(Subject subject) {
			return new SubjectInfo(subject.getName(), subject.getDateTime(), subject.getDDay(LocalDate.now()));
		}

		private SubjectInfo(String name,
							@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
								LocalDateTime date,
							int d_day) {
			this.name = name;
			this.d_day = d_day;
			this.date = date;
		}
	}

	@Getter
	@Setter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@NoArgsConstructor
	public static class DateInfo {
		private LocalDate date;
		private List<TodoInfo> subjects;

		public static DateInfo of(
			@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm") LocalDate date,
			List<TodoInfo> subjects) {
			return new DateInfo(date, subjects);
		}

		@Getter
		@Setter
		@AllArgsConstructor(access = AccessLevel.PRIVATE)
		@NoArgsConstructor
		public static class TodoInfo {
			private String name;
			private double hour;

			public static TodoInfo from(Exam todo) {
				return new TodoInfo(todo.getSubject()
										.getName(), todo.getHour());
			}
		}
	}
}
