package com.codingwasabi.howtodo.unit.domain.dailyplan;

import static com.codingwasabi.howtodo.unit.utils.EntityFactory.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;
import com.codingwasabi.howtodo.web.exam.entity.Exam;
import com.codingwasabi.howtodo.web.todo.entity.ToDo;

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
		ToDo todo = ToDo_생성("물리");

		// when
		dailyPlan.addToDo(todo);

		// then
		assertThat(dailyPlan.getToDos()).containsOnly(todo);
	}

	@DisplayName("중복되지 않게 과목 채우기")
	@Test
	void 과목_채우기() {
		// given
		Set<Exam> exams = new HashSet<>();
		ToDo 물리 = ToDo_생성("물리");
		ToDo 수학 = ToDo_생성("수학");
		dailyPlan.addToDo(물리);
		dailyPlan.addToDo(수학);

		// when
		dailyPlan.fillExams(exams);

		// then
		assertThat(exams).containsOnly(물리.getExam(), 수학.getExam());
	}
}
