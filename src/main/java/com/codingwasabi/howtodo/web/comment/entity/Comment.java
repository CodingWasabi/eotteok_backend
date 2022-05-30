package com.codingwasabi.howtodo.web.comment.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calendar.entity.Calendar;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Calendar calendar;

	private LocalDate date;

	private int profileNumber;

	private String body;

	@Builder
	private Comment(Account account, Calendar calendar, int profileNumber, String body, LocalDate date) {
		this.account = account;
		this.calendar = calendar;
		this.profileNumber = profileNumber;
		this.body = body;
		this.date = date;
	}

	public boolean isDate(LocalDate date) {
		return this.date.isEqual(date);
	}

	public boolean isMonth(int month) {
		return date.getMonthValue() == month;
	}
}
