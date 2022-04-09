package com.codingwasabi.howtodo.web.calendar.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingwasabi.howtodo.web.policy.planMaking.PlanMakingPolicy;
import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calendar.CalendarRepository;
import com.codingwasabi.howtodo.web.calendar.entity.Calendar;
import com.codingwasabi.howtodo.web.dailyplan.DailyPlanRepository;
import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;
import com.codingwasabi.howtodo.web.exam.ExamRepository;
import com.codingwasabi.howtodo.web.exam.entity.Exam;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {
	private final CalendarRepository calendarRepository;
	private final ExamRepository subjectRepository;
	private final DailyPlanRepository dailyPlanRepository;
	private final PlanMakingPolicy planMakingPolicy;

	@Override
	public Calendar create(Account account, int tendency, String nickname, List<Exam> exams) {
		Calendar calendar = new Calendar(account);
		List<DailyPlan> dailyPlans = addDailyPlans(exams, calendar);

		if (account.isAnonymous()) {
			return calendar;
		}

		account.setNickname(nickname);
		account.setTendency(tendency);
		dailyPlanRepository.saveAll(dailyPlans);
		subjectRepository.saveAll(exams);
		calendarRepository.save(calendar);
		return calendar;
	}

	private List<DailyPlan> addDailyPlans(List<Exam> exams, Calendar calendar) {
		List<DailyPlan> dailyPlans = planMakingPolicy.makeDailyPlans(exams, LocalDate.now());
		dailyPlans.forEach(dailyPlan -> calendar.addPlan(dailyPlan));

		return dailyPlans;
	}

	@Override
	@Transactional(readOnly = true)
	public Calendar find(Account account) {
		return calendarRepository.findByAccount(account)
								 .orElseThrow(() -> new AccessDeniedException("no data"));
	}
}
