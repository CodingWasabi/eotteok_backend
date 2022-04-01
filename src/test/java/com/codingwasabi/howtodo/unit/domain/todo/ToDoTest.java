package com.codingwasabi.howtodo.unit.domain.todo;

import static com.codingwasabi.howtodo.unit.utils.EntityFactory.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;
import com.codingwasabi.howtodo.web.exam.entity.Exam;
import com.codingwasabi.howtodo.web.todo.entity.ToDo;

@DisplayName("ToDo, 도메인 단위 테스트")
public class ToDoTest {

	@DisplayName("ToDo 인스턴스 생성")
	@Test
	void ToDo_생성() {
		// given
		Exam exam = Exam_생성("물리", 2022, 4, 9);

		// when
		ToDo todo = ToDo.builder()
						.exam(exam)
						.hour(2.5)
						.build();

		// then
		assertThat(todo.getHour()).isEqualTo(2.5);
		assertThat(todo.getExam()).isSameAs(exam);
	}

	@DisplayName("해당 날짜의 todo의 시험까지 d-day 조회")
	@Test
	void ToDo_dDay_조회() {
		// given
		Account account = Account_생성();
		Exam exam = Exam_생성("물리", 2022, 4, 9);
		DailyPlan dailyPlan = 특정_날_의_DailyPlan_생성(account, 2022, 4, 5);

		ToDo toDo = ToDo.builder()
						 .exam(exam)
						 .hour(2.5)
						 .build();
		dailyPlan.addToDo(toDo);

		// when
		int dDay = toDo.getDDay();

		// then
		assertThat(dDay).isEqualTo(4);
	}
}
