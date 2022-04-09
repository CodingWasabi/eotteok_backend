package com.codingwasabi.howtodo.web.policy.util;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.codingwasabi.howtodo.web.exam.entity.Exam;

public class ExamDateSorting {
	public static List<Exam> sort(List<Exam> exams) {
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
}
