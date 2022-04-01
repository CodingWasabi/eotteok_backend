package com.codingwasabi.howtodo.unit.domain.calendar;

import static com.codingwasabi.howtodo.unit.utils.EntityFactory.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calendar.entity.Calendar;
import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;
import com.codingwasabi.howtodo.web.exam.entity.Exam;

@DisplayName("Calender, 도메인 단위 테스트")
public class CalendarTest {

	private Account account;

	@BeforeEach
	void init() {
		account = Account_생성();
	}

	@DisplayName("Calender 인스턴스 생성")
	@Test
	void Calender_생성() {
		// when
		Calendar calendar = new Calendar(account);

		// then
		assertThat(calendar.getAccount()
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
		Calendar calendar = new Calendar(account);

		// when
		calendar.addPlan(dailyPlan);

		// then
		assertThat(calendar.getDailyPlans()).containsOnly(dailyPlan);
	}

	@DisplayName("모든 Exam 조회")
	@Test
	void Exam_모두_조회() {
		// given
		DailyPlan dailyPlan = DailyPlan.builder()
									   .account(account)
									   .date(LocalDate.now())
									   .build();
		dailyPlan.addToDo(ToDo_생성("물리"));
		dailyPlan.addToDo(ToDo_생성("수학"));

		Calendar calendar = new Calendar(account);
		calendar.addPlan(dailyPlan);

		// when
		Set<Exam> exams = calendar.getExams();

		// then
		List<String> subjectNames = exams.stream()
										 .map(subject -> subject.getName())
										 .collect(Collectors.toList());
		assertThat(subjectNames).containsOnly("물리", "수학");
	}

	@DisplayName("월별 plan 조회")
	@Test
	void MonthPlans_조회() {
		// given
		Calendar calendar = new Calendar(account);
		DailyPlan dailyPlan_3월_20 = 특정_날_의_DailyPlan_생성(account, 2022, 03, 20);
		DailyPlan dailyPlan_3월_30 = 특정_날_의_DailyPlan_생성(account, 2022, 03, 30);
		DailyPlan dailyPlan_4월_15 = 특정_날_의_DailyPlan_생성(account, 2022, 04, 15);
		DailyPlan dailyPlan_5월_05 = 특정_날_의_DailyPlan_생성(account, 2022, 05, 05);

		calendar.addPlan(dailyPlan_3월_20);
		calendar.addPlan(dailyPlan_3월_30);
		calendar.addPlan(dailyPlan_4월_15);
		calendar.addPlan(dailyPlan_5월_05);

		// when
		Map<Integer, List<DailyPlan>> monthPlan = calendar.getMonthPlan();

		// then
		assertThat(monthPlan.get(3)).hasSize(2);
		assertThat(monthPlan.get(4)).hasSize(1);
		assertThat(monthPlan.get(5)).hasSize(1);
	}
}
