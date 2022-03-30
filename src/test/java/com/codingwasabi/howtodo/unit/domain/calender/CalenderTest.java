package com.codingwasabi.howtodo.unit.domain.calender;

import static com.codingwasabi.howtodo.unit.domain.utils.EntityFactory.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calender.entity.Calender;
import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;

@DisplayName("Calender, 도메인 단위 테스트")
public class CalenderTest {

	@DisplayName("Calender 인스턴스 생성")
	@Test
	void Calender_생성() {
		// given
		Account account = Account_생성();

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
		Account account = Account_생성();
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
}
