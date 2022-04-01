package com.codingwasabi.howtodo.web.dailyplan.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calendar.entity.Calendar;
import com.codingwasabi.howtodo.web.comment.entity.Comment;
import com.codingwasabi.howtodo.web.todo.entity.ToDo;
import com.codingwasabi.howtodo.web.exam.entity.Exam;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class DailyPlan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate date;

	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;

	@OneToMany(mappedBy = "dailyPlan", cascade = CascadeType.ALL)
	private List<ToDo> toDos = new ArrayList<>();

	@OneToMany(mappedBy = "dailyPlan", cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	private Calendar calendar;

	@Builder
	private DailyPlan(Account account, LocalDate date) {
		this.account = account;
		this.date = date;
	}

	public void addToDo(ToDo todo) {
		toDos.add(todo);
		todo.setDailyPlan(this);
	}

	public void addComment(Comment comment) {
		comments.add(comment);
		comment.setDailyPlan(this);
	}

	public void fillExams(Set<Exam> exams) {
		toDos.forEach(toDo -> exams.add(toDo.getExam()));
	}
}
