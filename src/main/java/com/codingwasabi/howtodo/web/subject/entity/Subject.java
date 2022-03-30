package com.codingwasabi.howtodo.web.subject.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.codingwasabi.howtodo.web.account.entity.Account;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Subject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	private Account account;

	private String name;
	private LocalDateTime dateTime;
	private int studyDegree;

	@Builder
	private Subject(Account account, String name, LocalDateTime dateTime, int studyDegree) {
		this.account = account;
		this.name = name;
		this.dateTime = dateTime;
		this.studyDegree = studyDegree;
	}
}
