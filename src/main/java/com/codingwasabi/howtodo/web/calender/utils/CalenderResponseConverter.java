package com.codingwasabi.howtodo.web.calender.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.codingwasabi.howtodo.web.calender.dto.CalenderResponse;
import com.codingwasabi.howtodo.web.calender.entity.Calender;
import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;
import com.codingwasabi.howtodo.web.todo.entity.ToDo;

public class CalenderResponseConverter {
	public static List<CalenderResponse.ExamInfo> convertExamInfos(Calender calender) {
		return calender.getExams()
					   .stream()
					   .map(exam -> new CalenderResponse.ExamInfo(exam.getName(),
																  exam.getColor(),
																  exam.getDateTime()
																	  .getYear(),
																  exam.getDateTime()
																	  .getMonthValue(),
																  exam.getDateTime()
																	  .getDayOfMonth(),
																  exam.getDateTime()
																	  .getHour(),
																  exam.getDateTime()
																	  .getMinute(),
																  exam.getDDay(LocalDate.now())))
					   .collect(Collectors.toList());
	}

	public static List<CalenderResponse.MonthToDoInfo> convertMonthToDoInfo(Calender calender) {
		List<CalenderResponse.MonthToDoInfo> monthToDoInfos = new ArrayList<>();

		calender.getMonthPlan()
				.entrySet()
				.forEach(entry -> {
					monthToDoInfos.add(new CalenderResponse.MonthToDoInfo(entry.getKey(),
																		  getMonthCommentCount(entry.getValue()),
																		  getDailyToDoInfoList(entry.getValue())));
				});

		return monthToDoInfos;
	}

	private static Integer getMonthCommentCount(List<DailyPlan> dailyPlans) {
		int commentCount = 0;
		for (DailyPlan dailyPlan : dailyPlans) {
			commentCount += dailyPlan.getComments()
									 .size();
		}
		return commentCount;
	}

	private static List<CalenderResponse.MonthToDoInfo.DailyToDoInfo> getDailyToDoInfoList(List<DailyPlan> dailyPlans) {
		return dailyPlans.stream()
						 .map(dailyPlan -> new CalenderResponse.MonthToDoInfo.DailyToDoInfo(dailyPlan.getDate(),
																							dailyPlan.getComments()
																									 .size(),
																							getToDoInfoList(dailyPlan.getToDos())))
						 .collect(Collectors.toList());
	}

	private static List<CalenderResponse.MonthToDoInfo.DailyToDoInfo.TodoInfo> getToDoInfoList(List<ToDo> toDos) {
		return toDos.stream()
					.map(toDo -> new CalenderResponse.MonthToDoInfo.DailyToDoInfo.TodoInfo(toDo.getExam()
																							   .getName(),
																						   toDo.getHour(),
																						   toDo.getDDay(),
																						   toDo.getExam()
																							   .getColor()))
					.collect(Collectors.toList());
	}
}
