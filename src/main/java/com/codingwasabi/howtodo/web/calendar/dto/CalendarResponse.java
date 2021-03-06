package com.codingwasabi.howtodo.web.calendar.dto;

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
public class CalendarResponse {

	private String nickname;

	private int tendency;

	private Long accountId;

	private List<ExamInfo> exams;

	private List<MonthToDoInfo> calendar;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ExamInfo {
		private String name;
		private int color;
		private int year;
		private int month;
		private int date;
		private int hour;
		private int minute;
		private int d_day;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class MonthToDoInfo {
		private int month;
		private int commentCount;
		private List<DailyCommentCount> dailyCommentsCount;
		private List<DailyToDoInfo> toDos;

		@Getter
		@Setter
		@AllArgsConstructor
		@NoArgsConstructor
		public static class DailyCommentCount {
			private LocalDate date;
			private int count;
		}

		@Getter
		@Setter
		@AllArgsConstructor
		@NoArgsConstructor
		public static class DailyToDoInfo {
			private LocalDate date;
			private List<TodoInfo> toDos;

			@Getter
			@Setter
			@AllArgsConstructor
			@NoArgsConstructor
			public static class TodoInfo {
				private String name;
				private double hour;
				private int d_day;
				private int color;
				private int month;
				private int date;
			}
		}

	}
}
