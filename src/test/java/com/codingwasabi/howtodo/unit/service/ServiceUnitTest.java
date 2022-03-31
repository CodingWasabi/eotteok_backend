package com.codingwasabi.howtodo.unit.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.codingwasabi.howtodo.web.calender.CalenderRepository;
import com.codingwasabi.howtodo.web.dailyplan.DailyPlanRepository;
import com.codingwasabi.howtodo.web.policies.PlanMakingPolicy;
import com.codingwasabi.howtodo.web.subject.ExamRepository;

@ExtendWith(MockitoExtension.class)
public class ServiceUnitTest {
	@Mock
	protected CalenderRepository calenderRepository;

	@Mock
	protected ExamRepository examRepository;

	@Mock
	protected DailyPlanRepository dailyPlanRepository;

	@Mock
	protected PlanMakingPolicy planMakingPolicy;
}
