package com.codingwasabi.howtodo.web.calendar.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.codingwasabi.howtodo.web.calendar.dto.CalendarResponse;
import com.codingwasabi.howtodo.web.calendar.entity.Calendar;
import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;
import com.codingwasabi.howtodo.web.todo.entity.ToDo;

public class CalenderResponseConverter {
	public static List<CalendarResponse.ExamInfo> convertExamInfos(Calendar calendar) {
		return calendar.getExams()
					   .stream()
					   .map(exam -> new CalendarResponse.ExamInfo(exam.getName(),
																  exam.getColor(),
																  exam.getDueDateTime()
																	  .getYear(),
																  exam.getDueDateTime()
																	  .getMonthValue(),
																  exam.getDueDateTime()
																	  .getDayOfMonth(),
																  exam.getDueDateTime()
																	  .getHour(),
																  exam.getDueDateTime()
																	  .getMinute(),
																  exam.getDDay(LocalDate.now())))
					   .collect(Collectors.toList());
	}

	public static List<CalendarResponse.MonthToDoInfo> convertMonthToDoInfo(Calendar calendar) {
		List<CalendarResponse.MonthToDoInfo> monthToDoInfos = new ArrayList<>();

		calendar.getMonthPlan()
				.entrySet()
				.forEach(entry -> {
					monthToDoInfos.add(new CalendarResponse.MonthToDoInfo(entry.getKey(),
																		  calendar.getCommentCount(entry.getKey()),
																		  getDailyCommentsCount(calendar),
																		  getDailyToDoInfoList(calendar,
																							   entry.getValue())));
				});

		return monthToDoInfos;
	}

	private static List<CalendarResponse.MonthToDoInfo.DailyCommentCount> getDailyCommentsCount(Calendar calendar) {
		return calendar.getDailyPlans()
					   .stream()
					   .map(dailyPlan -> new CalendarResponse.MonthToDoInfo.DailyCommentCount(dailyPlan.getDate(),
																							  calendar.getCommentCount(
																								  dailyPlan)))
					   .collect(Collectors.toList());
	}

	private static List<CalendarResponse.MonthToDoInfo.DailyToDoInfo> getDailyToDoInfoList(Calendar calendar,
																						   List<DailyPlan> dailyPlans) {
		return dailyPlans.stream()
						 .map(dailyPlan -> new CalendarResponse.MonthToDoInfo.DailyToDoInfo(dailyPlan.getDate(),
																							getToDoInfoList(dailyPlan.getToDos())))
						 .collect(Collectors.toList());
	}

	private static List<CalendarResponse.MonthToDoInfo.DailyToDoInfo.TodoInfo> getToDoInfoList(List<ToDo> toDos) {
		return toDos.stream()
					.map(toDo -> new CalendarResponse.MonthToDoInfo.DailyToDoInfo.TodoInfo(toDo.getExam()
																							   .getName(),
																						   toDo.getHour(),
																						   toDo.getDDay(),
																						   toDo.getExam()
																							   .getColor(),
																						   toDo.getExam()
																							   .getDueDateTime()
																							   .getMonth()
																							   .getValue(),
																						   toDo.getExam()
																							   .getDueDateTime()
																							   .getDayOfMonth()))
					.collect(Collectors.toList());
	}
}
