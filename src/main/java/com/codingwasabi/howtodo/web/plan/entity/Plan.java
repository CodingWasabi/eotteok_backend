package com.codingwasabi.howtodo.web.plan.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.comment.entity.Comment;
import com.codingwasabi.howtodo.web.exam.entity.Exam;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Plan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate date;

	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;

	@OneToMany(mappedBy = "plan")
	private List<Exam> exams = new ArrayList<>();

	@OneToMany(mappedBy = "plan")
	private List<Comment> comments = new ArrayList<>();
}
