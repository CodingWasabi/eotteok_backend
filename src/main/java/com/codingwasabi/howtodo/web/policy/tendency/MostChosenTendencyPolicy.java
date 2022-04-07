package com.codingwasabi.howtodo.web.policy.tendency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.codingwasabi.howtodo.web.exam.entity.Exam;

@Component
public class MostChosenTendencyPolicy implements TendencyPolicy {
	private static final int QUESTION_COUNT = 4;

	@Override
	// 완성되지않음, 배포 테스트 용으로 우선 커밋, 추후에 수정되어 재 배포 -> 기능이 실행되지 않을것이다.
	public int setUp(List<Integer> answers, int dailyQuota, List<Exam> exams) {
		int mostOne = mostChosen(answers);
		int apart = extractApart(dailyQuota, exams);

		// return getTendency(mostOne, apart);
		return 1;
	}

	private int mostChosen(List<Integer> answers) {
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

	private int extractApart(int dailyQuota, List<Exam> exams) {
		return 1;
	}
}
