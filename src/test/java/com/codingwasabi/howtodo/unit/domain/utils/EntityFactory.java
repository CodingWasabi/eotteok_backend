package com.codingwasabi.howtodo.unit.domain.utils;

import java.time.LocalDateTime;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.comment.entity.Comment;
import com.codingwasabi.howtodo.web.exam.entity.Exam;
import com.codingwasabi.howtodo.web.subject.entity.Subject;

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

	public static Exam Exam_생성() {
		return Exam.builder()
				   .hour(2.5)
				   .subject(Subject_생성())
				   .build();
	}

	public static Subject Subject_생성() {
		return Subject.builder()
					  .account(Account_생성())
					  .dateTime(LocalDateTime.now())
					  .name("과목명")
					  .studyDegree(3)
					  .build();
	}

	public static Comment Comment_생성() {
		return Comment.builder()
					  .account(Account_생성())
					  .profileNumber(3)
					  .body("댓글")
					  .build();
	}
}