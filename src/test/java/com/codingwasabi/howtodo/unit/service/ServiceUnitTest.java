package com.codingwasabi.howtodo.unit.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.codingwasabi.howtodo.web.account.AccountRepository;
import com.codingwasabi.howtodo.web.calendar.CalendarRepository;
import com.codingwasabi.howtodo.web.dailyplan.DailyPlanRepository;
import com.codingwasabi.howtodo.web.policy.planMaking.PlanMakingPolicy;
import com.codingwasabi.howtodo.web.exam.ExamRepository;

@ExtendWith(MockitoExtension.class)
public class ServiceUnitTest {
	@Mock
	protected CalendarRepository calendarRepository;

	@Mock
	protected ExamRepository examRepository;

	@Mock
	protected DailyPlanRepository dailyPlanRepository;

	@Mock
	protected PlanMakingPolicy planMakingPolicy;

	@Mock
	protected AccountRepository accountRepository;
}
