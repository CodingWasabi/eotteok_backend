package com.codingwasabi.howtodo.unit.domain.calender;

import static com.codingwasabi.howtodo.unit.domain.utils.EntityFactory.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calender.entity.Calender;
import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;
import com.codingwasabi.howtodo.web.subject.entity.Subject;

@DisplayName("Calender, 도메인 단위 테스트")
public class CalenderTest {

	private Account account;

	@BeforeEach
	void init() {
		account = Account_생성();
	}

	@DisplayName("Calender 인스턴스 생성")
	@Test
	void Calender_생성() {
		// when
		Calender calender = new Calender(account);

		// then
		assertThat(calender.getAccount()
						   .getEmail()).isEqualTo("test@email.com");
	}

	@DisplayName("dailyPlan 추가")
	@Test
	void DailyPlan_추가() {
		// given
		DailyPlan dailyPlan = DailyPlan.builder()
									   .account(account)
									   .date(LocalDate.now())
									   .build();
		Calender calender = new Calender(account);

		// when
		calender.addPlan(dailyPlan);

		// then
		assertThat(calender.getDailyPlans()).containsOnly(dailyPlan);
	}

	@DisplayName("모든 Subject 조회")
	@Test
	void Subject_모두_조회() {
		// given
		DailyPlan dailyPlan = DailyPlan.builder()
									   .account(account)
									   .date(LocalDate.now())
									   .build();
		dailyPlan.addExam(Exam_생성("물리"));
		dailyPlan.addExam(Exam_생성("수학"));

		Calender calender = new Calender(account);
		calender.addPlan(dailyPlan);

		// when
		Set<Subject> subjects = calender.getSubjects();

		// then
		List<String> subjectNames = subjects.stream()
											.map(subject -> subject.getName())
											.collect(Collectors.toList());
		assertThat(subjectNames).containsOnly("물리", "수학");
	}
}
