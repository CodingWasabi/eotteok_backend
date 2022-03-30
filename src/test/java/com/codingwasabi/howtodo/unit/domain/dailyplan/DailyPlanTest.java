package com.codingwasabi.howtodo.unit.domain.dailyplan;

import static com.codingwasabi.howtodo.unit.domain.utils.EntityFactory.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.comment.entity.Comment;
import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;
import com.codingwasabi.howtodo.web.exam.entity.Exam;

@DisplayName("DailyPlan, 도메인 단위 테스트")
public class DailyPlanTest {

	private Account account;

	@BeforeEach
	void init() {
		account = Account_생성();
	}

	@DisplayName("DailyPlan 인스턴스 생성")
	@Test
	void DailyPlan_생성() {
		// when
		DailyPlan dailyPlan = DailyPlan.builder()
									   .date(LocalDate.now())
									   .account(account)
									   .build();

		// then
		assertThat(dailyPlan.getAccount()).isEqualTo(account);
	}

	@DisplayName("시험 추가")
	@Test
	void 시험_추가() {
		// given
		DailyPlan dailyPlan = DailyPlan.builder()
									   .date(LocalDate.now())
									   .account(account)
									   .build();
		Exam exam = Exam_생성("물리");

		// when
		dailyPlan.addExam(exam);

		// then
		assertThat(dailyPlan.getExams()).containsOnly(exam);
	}

	@DisplayName("댓글 추가")
	@Test
	void 댓글_추가() {
		// given
		DailyPlan dailyPlan = DailyPlan.builder()
									   .date(LocalDate.now())
									   .account(account)
									   .build();
		Comment comment = Comment_생성();

		// when
		dailyPlan.addComment(comment);

		// then
		assertThat(dailyPlan.getComments()).containsOnly(comment);
	}
}
