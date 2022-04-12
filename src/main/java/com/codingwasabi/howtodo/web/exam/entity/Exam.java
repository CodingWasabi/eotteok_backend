package com.codingwasabi.howtodo.web.exam.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.policy.util.DateProcessor;

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

	@OneToOne(fetch = FetchType.LAZY)
	private Account account;

	private String name;
	private LocalDateTime dueDateTime;
	private int studyDegree;

	@Setter
	private int color;

	@Builder
	private Exam(Account account, String name, LocalDateTime dueDateTime, int studyDegree) {
		this.account = account;
		this.name = name;
		this.dueDateTime = dueDateTime;
		this.studyDegree = studyDegree;
	}

	public int getDDay(LocalDate today) {
		return DateProcessor.calculateDDay(today, dueDateTime.toLocalDate());
	}

	public boolean isMid() {
		int day = this.dueDateTime.getDayOfMonth();
		return 10 <= day && 20 >= day;
	}

	public boolean isFront() {
		return 10 > this.dueDateTime.getDayOfMonth();
	}

	public boolean isBack() {
		return 20 < this.dueDateTime.getDayOfMonth();
	}
}
