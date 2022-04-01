package com.codingwasabi.howtodo.web.calender.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
public class Calender {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "calender", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DailyPlan> dailyPlans = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY)
	private Account account;

	public Calender(Account account) {
		this.account = account;
	}

	public void addPlan(DailyPlan dailyPlan) {
		dailyPlans.add(dailyPlan);
		dailyPlan.setCalender(this);
	}

	public Set<Exam> getExams() {
		Set<Exam> exams = new HashSet<>();
		dailyPlans.forEach(plan -> plan.fillExams(exams));

		return exams;
	}
}
