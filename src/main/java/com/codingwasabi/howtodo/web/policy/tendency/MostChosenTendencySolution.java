package com.codingwasabi.howtodo.web.policy.tendency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codingwasabi.howtodo.web.exam.entity.Exam;

public class MostChosenTendencySolution implements TendencySolution {
	@Override
	public int mostChosen(List<Integer> answers) {
		answers.sort((a, b) -> a - b);

		int mostOne = 0;
		int maxCount = 0;

		Map<Integer, Integer> questionCount = new HashMap<>();

		answers.forEach(answer -> {
			if (questionCount.containsKey(answer)) {
				questionCount.replace(answer, questionCount.get(answer) + 1);
				return;
			}
			questionCount.put(answer, 1);
		});

		for (Map.Entry<Integer, Integer> entry : questionCount.entrySet()) {
			if (entry.getValue() > maxCount) {
				maxCount = entry.getValue();
				mostOne = entry.getKey();
			}
		}

		return mostOne;
	}

	@Override
	public int extractExamsInterval(int dailyQuota, List<Exam> exams) {
		// 예외
		// - 시험 수가 0개인 경우
		// - 시험 수가 8개 이상인 경우

		// 1. 시험 수가 2개 이하인 경우

		// 2. 시험 수가 3개 이상인 경우

		return 0;
	}
}
