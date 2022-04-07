package com.codingwasabi.howtodo.web.policy.tendency;

import java.util.List;

import com.codingwasabi.howtodo.web.exam.entity.Exam;

public interface TendencySolution {
	int mostChosen(List<Integer> answers);

	int extractExamsInterval(int dailyQuota, List<Exam> exams);
}
