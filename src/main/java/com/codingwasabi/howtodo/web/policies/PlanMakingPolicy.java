package com.codingwasabi.howtodo.web.policies;

import java.util.List;

import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;
import com.codingwasabi.howtodo.web.subject.entity.Subject;

public interface PlanMakingPolicy {
	List<DailyPlan> makeDailyPlans(List<Subject> subjects);
}
