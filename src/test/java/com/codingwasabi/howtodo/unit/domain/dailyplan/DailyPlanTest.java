package com.codingwasabi.howtodo.unit.domain.dailyplan;

import static com.codingwasabi.howtodo.unit.domain.utils.EntityFactory.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.comment.entity.Comment;
import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;
import com.codingwasabi.howtodo.web.exam.entity.Exam;
import com.codingwasabi.howtodo.web.subject.entity.Subject;

@DisplayName("DailyPlan, 도메인 단위 테스트")
public class DailyPlanTest {

	private Account account;
	private DailyPlan dailyPlan;

	@BeforeEach
	void init() {
		account = Account_생성();
		dailyPlan = DailyPlan.builder()
							 .date(LocalDate.now())
							 .account(account)
							 .build();
	}

	@DisplayName("DailyPlan 인스턴스 생성")
	@Test
	void DailyPlan_생성() {
		// then
		assertThat(dailyPlan.getAccount()).isEqualTo(account);
	}

	@DisplayName("시험 추가")
	@Test
	void 시험_추가() {
		// given
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
		Comment comment = Comment_생성();

		// when
		dailyPlan.addComment(comment);

		// then
		assertThat(dailyPlan.getComments()).containsOnly(comment);
	}

	@DisplayName("중복되지 않게 과목 채우기")
	@Test
	void 과목_채우기() {
		// given
		Set<Subject> subjects = new HashSet<>();
		Exam 물리 = Exam_생성("물리");
		Exam 수학 = Exam_생성("수학");
		dailyPlan.addExam(물리);
		dailyPlan.addExam(수학);

		// when
		dailyPlan.fillSubject(subjects);

		// then
		assertThat(subjects).containsOnly(물리.getSubject(), 수학.getSubject());
	}
}
