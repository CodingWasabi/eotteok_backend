package com.codingwasabi.howtodo.unit.service.calender;

import static com.codingwasabi.howtodo.unit.utils.EntityFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codingwasabi.howtodo.unit.service.ServiceUnitTest;
import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calendar.entity.Calendar;
import com.codingwasabi.howtodo.web.calendar.service.CalendarService;
import com.codingwasabi.howtodo.web.calendar.service.CalendarServiceImpl;
import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;

@DisplayName("Calender, 비즈니스 로직 단위 테스트")
public class CalendarServiceTest extends ServiceUnitTest {

	private CalendarService calendarService;

	@BeforeEach
	void init() {
		this.calendarService = new CalendarServiceImpl(calendarRepository,
													   examRepository,
													   dailyPlanRepository,
													   planMakingPolicy,
														accountRepository);
	}

	@DisplayName("비회원 Calender 생성 로직")
	@Test
	void create_비회원_정상생성() {
		// given
		Account account = Account.Anonymous;
		List<DailyPlan> dailyPlans = List.of(DailyPlan_생성(account), DailyPlan_생성(account));
		willReturn(dailyPlans).given(planMakingPolicy)
							  .makeDailyPlans(any(), any(), any(), any(Integer.class));

		// when
		Calendar calendar = calendarService.create(account, 3, "daehwan", 10, List.of(Exam_생성("물리", 2022, 4, 9)));
		// then
		assertThat(calendar.getDailyPlans()).hasSize(2);
		assertThat(calendar.getAccount()
						   .getNickname()).isNull();
	}

	@DisplayName("회원 Calender 저장 로직")
	@Test
	void create_회원_정상저장() {
		// given
		Account account = Account_생성();
		List<DailyPlan> dailyPlans = List.of(DailyPlan_생성(account), DailyPlan_생성(account));
		willReturn(dailyPlans).given(planMakingPolicy)
							  .makeDailyPlans(any(), any(), any(), any(Integer.class));

		// when
		Calendar calendar = calendarService.create(account, 3, "daehwan", 10, List.of(Exam_생성("물리", 2022, 4, 9)));

		// then
		assertThat(calendar.getAccount()
						   .getNickname()).isEqualTo("daehwan");
	}

	@DisplayName("내 Calender 정보 조회")
	@Test
	void findMine_회원_정상조회() {
		// given
		Account account = Account_생성();
		willReturn(Optional.ofNullable(new Calendar(account))).given(calendarRepository)
															  .findByAccount(any());

		// when
		Calendar calendar = calendarService.find(account);

		// then
		assertThat(calendar.getAccount()).isSameAs(account);
	}
}
