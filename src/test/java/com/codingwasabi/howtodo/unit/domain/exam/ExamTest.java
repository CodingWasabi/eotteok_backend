package com.codingwasabi.howtodo.unit.domain.exam;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codingwasabi.howtodo.unit.domain.utils.EntityFactory;
import com.codingwasabi.howtodo.web.exam.entity.Exam;
import com.codingwasabi.howtodo.web.subject.entity.Subject;

@DisplayName("Exam, 도메인 단위 테스트")
public class ExamTest {

	@DisplayName("Exam 인스턴스 생성")
	@Test
	void Exam_생성() {
		// given
		Subject subject = EntityFactory.Subject_생성("물리");

		// when
		Exam exam = Exam.builder()
						.subject(subject)
						.hour(2.5)
						.build();

		// then
		assertThat(exam.getHour()).isEqualTo(2.5);
		assertThat(exam.getSubject()).isSameAs(subject);
	}
}
