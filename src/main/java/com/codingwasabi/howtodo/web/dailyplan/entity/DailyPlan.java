package com.codingwasabi.howtodo.web.dailyplan.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calender.entity.Calender;
import com.codingwasabi.howtodo.web.comment.entity.Comment;
import com.codingwasabi.howtodo.web.exam.entity.Exam;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class DailyPlan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate date;

	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;

	@OneToMany(mappedBy = "dailyPlan", cascade = CascadeType.ALL)
	private List<Exam> exams = new ArrayList<>();

	@OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	private Calender calender;

	public void addExam(Exam exam) {
		exams.add(exam);
		exam.setDailyPlan(this);
	}

	public void addComment(Comment comment) {
		comments.add(comment);
		comment.setDailyPlan(this);
	}
}
