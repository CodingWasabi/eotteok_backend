package com.codingwasabi.howtodo.web.policy.tendency;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.codingwasabi.howtodo.web.exam.entity.Exam;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MostChosenTendencyPolicy implements TendencyPolicy {
	private final TendencySolution tendencySolution;

	@Override
	public int setUp(List<Integer> answers, int dailyQuota, List<Exam> exams, LocalDate date) {
		int mostOne = tendencySolution.mostChosen(answers);
		int interval = tendencySolution.extractExamsInterval(dailyQuota, exams, date);

		return getTendency(mostOne, interval);
	}

	private int getTendency(int mostOne, int interval) {
		return interval + ((mostOne - 1) * 7);
	}
}
