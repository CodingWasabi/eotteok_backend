package com.codingwasabi.howtodo.unit.domain.subject;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codingwasabi.howtodo.unit.utils.EntityFactory;
import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.subject.entity.Subject;

@DisplayName("Subject, 도메인 단위 테스트")
public class SubjectTest {

	private Account account;

	@BeforeEach
	void init() {
		account = EntityFactory.Account_생성();
	}

	@DisplayName("Subject 인스턴스 생성")
	@Test
	void Subject_생성() {
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

	@DisplayName("과목 d-day 구하기")
	@Test
	void D_Day_계산() {
		// given
		Subject subject = Subject.builder()
								 .name("과목")
								 .account(account)
								 .dateTime(LocalDateTime.of(2022, 04, 20, 14, 30))
								 .studyDegree(3)
								 .build();

		// when
		int dDay = subject.getDDay(LocalDate.of(2022, 04, 15));

		// then
		assertThat(dDay).isEqualTo(5);
	}
}
