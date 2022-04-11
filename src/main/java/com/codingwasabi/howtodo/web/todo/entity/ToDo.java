package com.codingwasabi.howtodo.web.todo.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;
import com.codingwasabi.howtodo.web.exam.entity.Exam;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class ToDo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private double hour;

	@OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.REMOVE)
	private Exam exam;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	private DailyPlan dailyPlan;

	@Builder
	private ToDo(double hour, Exam exam) {
		this.hour = hour;
		this.exam = exam;
	}

	public int getDDay() {
		LocalDate toDoDate = dailyPlan.getDate();
		LocalDate examDate = exam.getDueDateTime()
								 .toLocalDate();

		return toDoDate.until(examDate).getDays();
	}
}