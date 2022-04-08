package com.codingwasabi.howtodo.web.calendar.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;
import com.codingwasabi.howtodo.web.exam.entity.Exam;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Calendar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DailyPlan> dailyPlans = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY)
	private Account account;

	public Calendar(Account account) {
		this.account = account;
	}

	public void addPlan(DailyPlan dailyPlan) {
		dailyPlans.add(dailyPlan);
		dailyPlan.setCalendar(this);
	}

	public Set<Exam> getExams() {
		Set<Exam> exams = new HashSet<>();
		dailyPlans.forEach(plan -> plan.fillExams(exams));

		return exams;
	}

	public Map<Integer, List<DailyPlan>> getMonthPlan() {
		Map<Integer, List<DailyPlan>> monthPlans = new HashMap<>();

		dailyPlans.forEach(plan -> {
			int month = plan.getDate()
							.getMonthValue();
			if (!monthPlans.containsKey(month)) {
				monthPlans.put(month, new ArrayList<>());
			}
			monthPlans.get(month)
					  .add(plan);
		});

		return monthPlans;
	}
}