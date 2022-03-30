package com.codingwasabi.howtodo.web.comment.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	private DailyPlan dailyPlan;

	private int profileNumber;
	private String body;

	@Builder
	private Comment(Account account, int profileNumber, String body) {
		this.account = account;
		this.profileNumber = profileNumber;
		this.body = body;
	}
}