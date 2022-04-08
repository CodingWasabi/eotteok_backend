package com.codingwasabi.howtodo.web.policy.tendency;

import java.time.LocalDate;
import java.util.List;

import com.codingwasabi.howtodo.web.exam.entity.Exam;

public interface TendencyPolicy {
	int setUp(List<Integer> answers, int DailyQuota, List<Exam> exams, LocalDate date);
}
