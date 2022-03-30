package com.codingwasabi.howtodo.web.exam.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;
import com.codingwasabi.howtodo.web.subject.entity.Subject;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Exam {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private double hour;

	@OneToOne(fetch = FetchType.LAZY)
	private Subject subject;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	private DailyPlan dailyPlan;

	@Builder
	private Exam(double hour, Subject subject) {
		this.hour = hour;
		this.subject = subject;
	}
}