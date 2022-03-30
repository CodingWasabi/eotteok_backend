package com.codingwasabi.howtodo.unit.domain.subject;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codingwasabi.howtodo.unit.domain.utils.EntityFactory;
import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.subject.entity.Subject;

@DisplayName("Subject, 도메인 단위 테스트")
public class SubjectTest {
	@DisplayName("Subject 인스턴스 생성")
	@Test
	void Subject_생성() {
		// given
		Account account = EntityFactory.Account_생성();

		// when
		Subject subject = Subject.builder()
								 .name("과목")
								 .account(account)
								 .dateTime(LocalDateTime.now())
								 .studyDegree(3)
								 .build();

		// then
		assertThat(subject.getName()).isEqualTo("과목");
		assertThat(subject.getStudyDegree()).isEqualTo(3);
	}
}
