package com.codingwasabi.howtodo.web.policy.tendency;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.codingwasabi.howtodo.web.exam.entity.Exam;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MostChosenTendencyPolicy implements TendencyPolicy {
	private static final int QUESTION_COUNT = 4;
	private final TendencySolution tendencySolution;

	@Override
	// 완성되지않음, 배포 테스트 용으로 우선 커밋, 추후에 수정되어 재 배포 -> 기능이 실행되지 않을것이다.
	public int setUp(List<Integer> answers, int dailyQuota, List<Exam> exams, LocalDate date) {
		int mostOne = tendencySolution.mostChosen(answers);
		int interval = tendencySolution.extractExamsInterval(dailyQuota, exams, date);

		return getTendency(mostOne, interval);
	}

	private int getTendency(int mostOne, int interval) {
		return 0;
	}
}
