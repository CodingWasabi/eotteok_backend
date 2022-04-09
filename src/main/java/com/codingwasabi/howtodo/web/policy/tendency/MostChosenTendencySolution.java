package com.codingwasabi.howtodo.web.policy.tendency;

import static com.codingwasabi.howtodo.web.policy.util.ExamDateSorting.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.codingwasabi.howtodo.web.exam.entity.Exam;

@Component
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
	public int extractExamsInterval(int dailyQuota, List<Exam> exams, LocalDate today) {
		List<Exam> sortedExams = sortExams(exams);

		Validator.validate(dailyQuota, sortedExams, today);

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
		static void validate(int dailyQuota, List<Exam> exams, LocalDate today) {
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
					 .anyMatch(exam -> exam.getStudyDegree() > dailyQuota * getRemainDays(exam, today))) {
				throw new IllegalArgumentException("there is a exam need over study time");
			}

			// 공부할 시간으로 모든 시험을 공부 할 수없는 경우 (각 시험의 공부할당량을 비교하여)
			if (noRemainTime(exams, dailyQuota, today)) {
				throw new IllegalArgumentException("it cannot make plan because no remain time");
			}
		}

		private static boolean noRemainTime(List<Exam> exams, int dailyQuota, LocalDate today) {
			int allOfStudyTime = getRemainDays(exams.get(exams.size() - 1), today) * dailyQuota;

			for (Exam exam : exams) {
				if (allOfStudyTime < exam.getStudyDegree()) {
					return true;
				}
				allOfStudyTime -= exam.getStudyDegree();
			}
			if (allOfStudyTime < 0) {
				return true;
			}
			return false;
		}

		private static int getRemainDays(Exam exam, LocalDate date) {
			return exam.getDDay(date) - 1;
		}
	}
}
