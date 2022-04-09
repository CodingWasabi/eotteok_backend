package com.codingwasabi.howtodo.web.policy.planMaking;

import java.time.LocalDate;
import java.util.List;

import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;
import com.codingwasabi.howtodo.web.exam.entity.Exam;

public interface PlanMakingPolicy {
	List<DailyPlan> makeDailyPlans(List<Exam> exams, LocalDate today);
}
