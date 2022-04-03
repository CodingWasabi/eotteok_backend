package com.codingwasabi.howtodo.web.policy.planMaking;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;
import com.codingwasabi.howtodo.web.exam.entity.Exam;

@Component
public class MonthPlanMakingPolicy implements PlanMakingPolicy {
	@Override
	public List<DailyPlan> makeDailyPlans(List<Exam> exams) {
		return new ArrayList<>();
	}
}
