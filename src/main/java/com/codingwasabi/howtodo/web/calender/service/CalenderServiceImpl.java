package com.codingwasabi.howtodo.web.calender.service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calender.CalenderRepository;
import com.codingwasabi.howtodo.web.calender.entity.Calender;
import com.codingwasabi.howtodo.web.dailyplan.DailyPlanRepository;
import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;
import com.codingwasabi.howtodo.web.policies.PlanMakingPolicy;
import com.codingwasabi.howtodo.web.subject.SubjectRepository;
import com.codingwasabi.howtodo.web.subject.entity.Subject;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CalenderServiceImpl implements CalenderService {
	private final CalenderRepository calenderRepository;
	private final SubjectRepository subjectRepository;
	private final DailyPlanRepository dailyPlanRepository;
	private final PlanMakingPolicy planMakingPolicy;

	@Override
	public Calender create(Account account, int tendency, String nickname, List<Subject> subjects) {
		Calender calender = new Calender(account);
		List<DailyPlan> dailyPlans = addDailyPlans(subjects, calender);

		if (account.isAnonymous()) {
			return calender;
		}

		account.setNickname(nickname);
		account.setTendency(tendency);
		dailyPlanRepository.saveAll(dailyPlans);
		subjectRepository.saveAll(subjects);
		calenderRepository.save(calender);
		return calender;
	}

	private List<DailyPlan> addDailyPlans(List<Subject> subjects, Calender calender) {
		List<DailyPlan> dailyPlans = planMakingPolicy.makeDailyPlans(subjects);
		dailyPlans.forEach(dailyPlan -> calender.addPlan(dailyPlan));

		return dailyPlans;
	}

	@Override
	@Transactional(readOnly = true)
	public Calender findMine(Account account) {
		return calenderRepository.findByAccount(account)
								 .orElseThrow(() -> new AccessDeniedException("no data"));
	}
}
