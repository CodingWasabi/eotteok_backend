package com.codingwasabi.howtodo.web.policy.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;
import com.codingwasabi.howtodo.web.exam.entity.Exam;

public class ExamDateSorting {
	public static List<Exam> sortExams(List<Exam> exams) {
		return exams.stream()
					.sorted((e1, e2) -> {
						LocalDateTime day1 = e1.getDueDateTime();
						LocalDateTime day2 = e2.getDueDateTime();
						if (day1.isBefore(day2)) {
							return -1;
						} else if (day2.isBefore(day1)) {
							return 1;
						}
						return 0;
					})
					.collect(Collectors.toList());
	}

	public static List<DailyPlan> sortDailyPlans(List<DailyPlan> dailyPlans) {
		return dailyPlans.stream()
						 .sorted((dp1, dp2) -> {
							 LocalDate day1 = dp1.getDate();
							 LocalDate day2 = dp2.getDate();
							 if (day1.isBefore(day2)) {
								 return -1;
							 } else if (day2.isBefore(day1)) {
								 return 1;
							 }
							 return 0;
						 })
						 .collect(Collectors.toList());
	}
}
