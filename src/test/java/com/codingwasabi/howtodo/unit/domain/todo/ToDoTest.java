package com.codingwasabi.howtodo.unit.domain.todo;

import static com.codingwasabi.howtodo.unit.utils.EntityFactory.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codingwasabi.howtodo.web.todo.entity.ToDo;
import com.codingwasabi.howtodo.web.subject.entity.Subject;

@DisplayName("ToDo, 도메인 단위 테스트")
public class ToDoTest {

	@DisplayName("ToDo 인스턴스 생성")
	@Test
	void ToDo_생성() {
		// given
		Subject subject = Subject_생성("물리");

		// when
		ToDo todo = ToDo.builder()
						.subject(subject)
						.hour(2.5)
						.build();

		// then
		assertThat(todo.getHour()).isEqualTo(2.5);
		assertThat(todo.getSubject()).isSameAs(subject);
	}
}
