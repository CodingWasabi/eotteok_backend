package com.codingwasabi.howtodo.web.policy.planMaking;

import static com.codingwasabi.howtodo.web.policy.util.DateProcessor.*;

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

		for (Exam exam : sortedExams) {
			LocalDate examDate = exam.getDueDateTime()
									 .toLocalDate();

			DailyPlan examDayPlan = dailyPlans.stream()
											  .filter(dailyPlan -> dailyPlan.getDate()
																			.equals(examDate))
											  .findAny()
											  .orElseGet(() -> {
												  DailyPlan dailyPlan = DailyPlan.builder()
																				 .account(account)
																				 .date(examDate)
																				 .build();
												  dailyPlans.add(dailyPlan);
												  return dailyPlan;
											  });

			examDayPlan.addToDo(ToDo.builder()
									.hour(0)
									.exam(exam)
									.build());
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
			// ????????? ??????
			examDate = examDate.minusDays(1);

			// ?????? ??? ????????? ?????? ?????? ??????
			if (examDate.isEqual(today) || examDate.isBefore(today)) {
				throw new IllegalArgumentException("no remain time to plan");
			}

			// ?????? ????????? ??????????????? ?????? ?????? ????????? ??????
			if (dailyHours.get(examDate) == dailyQuota) {
				continue;
			}

			// ??????????????? ?????? ??????
			if (dailyHours.get(examDate) > dailyQuota) {
				throw new IllegalStateException("internal error : plan algorithm, over daily study time");
			}

			// ?????? ????????? ??????????????? ?????? ????????? ?????? ??????
			if (dailyHours.get(examDate) == 0) {
				// ?????? ?????? ??????
				DailyPlan dailyPlan = DailyPlan.builder()
											   .account(account)
											   .date(examDate)
											   .build();

				// ?????? ??????????????? ????????? ?????? ?????????????????? ?????????
				if (remainHour >= dailyQuota) {
					dailyHours.put(examDate, dailyQuota);

					dailyPlan.addToDo(ToDo.builder()
										  .exam(exam)
										  .hour(dailyQuota)
										  .build());
				}

				// ?????????
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

			// ?????? ????????? ??????????????? ????????? ?????? ??????
			if (dailyHours.get(examDate) > 0) {
				// ?????? ?????? ?????? ????????????
				DailyPlan targetDatePlan = null;
				for (DailyPlan dailyPlan : dailyPlans) {
					if (dailyPlan.getDate()
								 .isEqual(examDate)) {
						targetDatePlan = dailyPlan;
						break;
					}
				}

				// ????????? ?????? 2????????? ??????
				if (targetDatePlan.getToDos()
								  .size() == 2) {
					continue;
				}

				// ?????? ?????? ?????????????????? ?????? ?????????
				int gap = dailyQuota - dailyHours.get(examDate);
				// ?????? ??????????????? ????????? ??? ??????
				if (remainHour >= gap) {
					remainHour -= gap;
					dailyHours.replace(examDate, dailyQuota);

					targetDatePlan.addToDo(ToDo.builder()
											   .exam(exam)
											   .hour(gap)
											   .build());
					continue;
				}
				// ?????????
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
