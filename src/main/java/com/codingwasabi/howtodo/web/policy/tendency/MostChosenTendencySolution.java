package com.codingwasabi.howtodo.web.policy.tendency;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.codingwasabi.howtodo.web.exam.entity.Exam;

public class MostChosenTendencySolution implements TendencySolution {
	@Override
	public int mostChosen(List<Integer> answers) {
		Collections.sort(answers);

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
	public int extractExamsInterval(int dailyQuota, List<Exam> exams, LocalDate date) {
		Validator.validate(dailyQuota, exams, date);

		List<Exam> sortedExams = exams.stream()
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

		// [7] 시험 수가 1개인 경우
		if (exams.size() == 1) {
			return 7;
		}

		// [2] 시험 수가 6개 이상 경우
		if (exams.size() >= 6) {
			return 2;
		}

		// [1] 모든 시험의 격차가 하루 간격
		if (everyExamHasIntervalOnlyOneDay(sortedExams)) {
			return 1;
		}

		// [4] 두 시험의 간격이 너무 먼 경우
		if (allExamLocatedFrontOrBackDayOfMonth(sortedExams)) {
			return 4;
		}

		// [5] 모든 시험이 월 초인 경우
		if (allExamLocatedFrontOfMonth(sortedExams)) {
			return 5;
		}

		// [6] 모든 시험이 월 말인 경우
		if (allExamLocatedBackOfMonth(sortedExams)) {
			return 6;
		}

		return 3;
	}

	private boolean allExamLocatedBackOfMonth(List<Exam> sortedExams) {
		for (Exam exam : sortedExams) {
			if (exam.isFront() || exam.isMid()) {
				return false;
			}
		}
		return true;
	}

	private boolean allExamLocatedFrontOfMonth(List<Exam> sortedExams) {
		for (Exam exam : sortedExams) {
			if (exam.isBack() || exam.isMid()) {
				return false;
			}
		}
		return true;
	}

	private boolean allExamLocatedFrontOrBackDayOfMonth(List<Exam> sortedExams) {
		boolean hasFront = false;
		boolean hasBack = false;

		for (Exam exam : sortedExams) {
			if (exam.isMid()) {
				return false;
			}
			if (exam.isFront()) {
				hasFront = true;
			}
			if (exam.isBack()) {
				hasBack = true;
			}
		}

		return hasFront && hasBack;
	}

	private boolean everyExamHasIntervalOnlyOneDay(List<Exam> sortedExams) {
		LocalDate beforeDay = null;

		for (Exam exam : sortedExams) {
			if (beforeDay == null) {
				beforeDay = exam.getDueDateTime()
								.toLocalDate();
				continue;
			}

			if (Math.abs(exam.getDueDateTime()
							 .toLocalDate()
							 .until(beforeDay)
							 .getDays()) != 1) {
				return false;
			}
			beforeDay = exam.getDueDateTime()
							.toLocalDate();
		}
		return true;
	}

	private static class Validator {
		static void validate(int dailyQuota, List<Exam> exams, LocalDate date) {
			// 예외
			// - 시험 수가 0개인 경우
			if (exams.isEmpty()) {
				throw new IllegalArgumentException("exams size cannot be 0");
			}

			// - 시험 수가 8개 이상인 경우
			if (exams.size() >= 8) {
				throw new IllegalArgumentException("exams size cannot be over 7");
			}

			// 시험 필요 공부시간이 공부할 시간보다 큰 경우
			if (exams.stream()
					 .anyMatch(exam -> exam.getStudyDegree() > dailyQuota * getRemainDays(exam, date))) {
				throw new IllegalArgumentException("there is a exam need over study time");
			}
		}

		private static int getRemainDays(Exam exam, LocalDate date) {
			return exam.getDDay(date) - 1;
		}
	}
}