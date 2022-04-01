package com.codingwasabi.howtodo.unit.domain.todo;

import static com.codingwasabi.howtodo.unit.utils.EntityFactory.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codingwasabi.howtodo.web.exam.entity.Exam;
import com.codingwasabi.howtodo.web.todo.entity.ToDo;

@DisplayName("ToDo, 도메인 단위 테스트")
public class ToDoTest {

	@DisplayName("ToDo 인스턴스 생성")
	@Test
	void ToDo_생성() {
		// given
		Exam exam = Exam_생성("물리");

		// when
		ToDo todo = ToDo.builder()
						.exam(exam)
						.hour(2.5)
						.build();

		// then
		assertThat(todo.getHour()).isEqualTo(2.5);
		assertThat(todo.getExam()).isSameAs(exam);
	}
}
