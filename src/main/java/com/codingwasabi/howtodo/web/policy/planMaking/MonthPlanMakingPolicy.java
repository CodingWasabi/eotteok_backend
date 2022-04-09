package com.codingwasabi.howtodo.web.policy.planMaking;

import static com.codingwasabi.howtodo.web.policy.util.ExamDateSorting.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;
import com.codingwasabi.howtodo.web.exam.entity.Exam;

@Component
public class MonthPlanMakingPolicy implements PlanMakingPolicy {
	@Override
	public List<DailyPlan> makeDailyPlans(List<Exam> exams, LocalDate today) {
		List<Exam> sortedExam = sort(exams);

		sortedExam.forEach(e -> System.out.println(e.getDueDateTime()));

		return new ArrayList<>();
	}
}
