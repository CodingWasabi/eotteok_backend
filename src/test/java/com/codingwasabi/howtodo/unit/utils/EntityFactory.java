package com.codingwasabi.howtodo.unit.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calendar.entity.Calendar;
import com.codingwasabi.howtodo.web.comment.entity.Comment;
import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;
import com.codingwasabi.howtodo.web.exam.entity.Exam;
import com.codingwasabi.howtodo.web.todo.entity.ToDo;

public class EntityFactory {
	public static Account Account_생성() {
		return Account.builder()
					  .ageRange("20~29")
					  .birthday("1997129")
					  .email("test@email.com")
					  .gender("mail")
					  .oauthId(123123L)
					  .provider("kakao")
					  .build();
	}

	public static ToDo ToDo_생성(String 과목명) {
		return ToDo.builder()
				   .hour(2.5)
				   .exam(Exam_생성(과목명, 2022, 4, 9))
				   .build();
	}

	public static Exam Exam_생성(String 과목명, int year, int month, int day) {
		return Exam.builder()
				   .account(Account_생성())
				   .dueDateTime(LocalDateTime.of(year, month, day, 20, 30))
				   .name(과목명)
				   .studyDegree(3)
				   .build();
	}

	public static Exam Exam_생성(String 과목명, int year, int month, int day, int degree) {
		return Exam.builder()
				   .account(Account_생성())
				   .dueDateTime(LocalDateTime.of(year, month, day, 20, 30))
				   .name(과목명)
				   .studyDegree(degree)
				   .build();
	}

	public static Comment Comment_생성(Account account) {
		return Comment.builder()
					  .account(account)
					  .profileNumber(3)
					  .body("댓글")
					  .build();
	}

	public static DailyPlan DailyPlan_생성(Account account) {
		return DailyPlan.builder()
						.date(LocalDate.now())
						.account(account)
						.build();
	}

	public static DailyPlan 특정_날_의_DailyPlan_생성(Account account, int year, int month, int day) {
		return DailyPlan.builder()
						.account(account)
						.date(LocalDate.of(year, month, day))
						.build();
	}

	public static Calendar Calendar_생성(Account account) {
		return new Calendar(account);
	}
}
