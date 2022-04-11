package com.codingwasabi.howtodo.web.policy.planMaking;

import static com.codingwasabi.howtodo.web.policy.util.ExamDateSorting.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;
import com.codingwasabi.howtodo.web.exam.entity.Exam;
import com.codingwasabi.howtodo.web.todo.entity.ToDo;

@Component
public class MonthPlanMakingPolicy implements PlanMakingPolicy {
	@Override
	public List<DailyPlan> makeDailyPlans(Account account, List<Exam> exams, LocalDate today, int dailyQuota) {
		List<Exam> sortedExams = sortExams(exams);
		Map<LocalDate, Integer> dailyHours = initDailyHours(sortedExams, today);
		List<DailyPlan> dailyPlans = new ArrayList<>();

		for (Exam exam : sortedExams) {
			LocalDate examDate = exam.getDueDateTime()
									 .toLocalDate();
			fillPlan(account, dailyHours, dailyPlans, examDate, exam, today, dailyQuota);
		}

		return sortDailyPlans(dailyPlans);
	}

	private void fillPlan(Account account,
						  Map<LocalDate, Integer> dailyHours,
						  List<DailyPlan> dailyPlans,
						  LocalDate examDate,
						  Exam exam,
						  LocalDate today,
						  int dailyQuota) {
		int remainHour = exam.getStudyDegree();

		while (remainHour > 0) {
			// 하루를 빼고
			examDate = examDate.minusDays(1);

			// 당일 과 같거나 전이 되면 아웃
			if (examDate.isEqual(today) || examDate.isBefore(today)) {
				throw new IllegalArgumentException("no remain time to plan");
			}

			// 해당 날짜의 공부시간이 이미 가득 찼다면 패스
			if (dailyHours.get(examDate) == dailyQuota) {
				continue;
			}

			// 잘못계산된 경우 발견
			if (dailyHours.get(examDate) > dailyQuota) {
				throw new IllegalStateException("internal error : plan algorithm, over daily study time");
			}

			// 해당 날짜의 공부시간이 아예 비워져 있는 경우
			if (dailyHours.get(examDate) == 0) {
				// 당일 플랜 생성
				DailyPlan dailyPlan = DailyPlan.builder()
											   .account(account)
											   .date(examDate)
											   .build();

				// 과목 공부해야할 시간이 당일 공부시간보다 크다면
				if (remainHour >= dailyQuota) {
					dailyHours.put(examDate, dailyQuota);

					dailyPlan.addToDo(ToDo.builder()
										  .exam(exam)
										  .hour(dailyQuota)
										  .build());
				}

				// 작다면
				if (remainHour < dailyQuota) {
					dailyHours.put(examDate, remainHour);
					dailyPlan.addToDo(ToDo.builder()
										  .exam(exam)
										  .hour(remainHour)
										  .build());
				}

				dailyPlans.add(dailyPlan);
				remainHour -= dailyQuota;
				continue;
			}

			// 해당 날짜의 공부시간이 채워져 있는 경우
			if (dailyHours.get(examDate) > 0) {
				// 해당 날짜 플랜 가져오기
				DailyPlan targetDatePlan = null;
				for (DailyPlan dailyPlan : dailyPlans) {
					if (dailyPlan.getDate()
								 .isEqual(examDate)) {
						targetDatePlan = dailyPlan;
						break;
					}
				}

				// 하루에 최대 2과목만 가능
				if (targetDatePlan.getToDos()
								  .size() == 2) {
					continue;
				}

				// 해당 날짜 공부시간과의 갭을 구한후
				int gap = dailyQuota - dailyHours.get(examDate);
				// 남은 공부시간이 갭보다 큰 경우
				if (remainHour >= gap) {
					remainHour -= gap;
					dailyHours.replace(examDate, dailyQuota);

					targetDatePlan.addToDo(ToDo.builder()
											   .exam(exam)
											   .hour(gap)
											   .build());
					continue;
				}
				// 작다면
				if (remainHour < gap) {
					dailyHours.replace(examDate, dailyHours.get(examDate) + remainHour);
					targetDatePlan.addToDo(ToDo.builder()
											   .exam(exam)
											   .hour(remainHour)
											   .build());
					remainHour = 0;
				}
			}
		}
	}

	private Map<LocalDate, Integer> initDailyHours(List<Exam> exams, LocalDate today) {
		Map<LocalDate, Integer> dailyHours = new HashMap<>();
		LocalDate nextDay = today;

		LocalDate endDate = exams.get(exams.size() - 1)
								 .getDueDateTime()
								 .toLocalDate();
		int days = getDays(today, endDate);

		for (int day = 1; day < days; day++) {
			nextDay = nextDay.plusDays(1);
			dailyHours.put(nextDay, 0);
		}

		return dailyHours;
	}

	private int getDays(LocalDate start, LocalDate end) {
		return (int)ChronoUnit.DAYS.between(start, end);
	}
}
