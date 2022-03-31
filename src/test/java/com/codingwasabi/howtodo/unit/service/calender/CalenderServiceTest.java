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
import com.codingwasabi.howtodo.web.calender.entity.Calender;
import com.codingwasabi.howtodo.web.calender.service.CalenderService;
import com.codingwasabi.howtodo.web.calender.service.CalenderServiceImpl;
import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;

@DisplayName("Calender, 비즈니스 로직 단위 테스트")
public class CalenderServiceTest extends ServiceUnitTest {

	private CalenderService calenderService;

	@BeforeEach
	void init() {
		this.calenderService = new CalenderServiceImpl(calenderRepository,
													   subjectRepository,
													   dailyPlanRepository,
													   planMakingPolicy);
	}

	@DisplayName("비회원 Calender 생성 로직")
	@Test
	void create_비회원_Calender() {
		// given
		Account account = Account.Anonymous;
		List<DailyPlan> dailyPlans = List.of(DailyPlan_생성(account), DailyPlan_생성(account));
		willReturn(dailyPlans).given(planMakingPolicy)
							  .makeDailyPlans(any());
		// when
		Calender calender = calenderService.create(account, 3, "daehwan", List.of(Subject_생성("물리")));
		// then
		assertThat(calender.getDailyPlans()).hasSize(2);
		assertThat(calender.getAccount()
						   .getNickname()).isNull();
	}

	@DisplayName("회원 Calender 저장 로직")
	@Test
	void create_회원_Calender() {
		// given
		Account account = Account_생성();
		List<DailyPlan> dailyPlans = List.of(DailyPlan_생성(account), DailyPlan_생성(account));
		willReturn(dailyPlans).given(planMakingPolicy)
							  .makeDailyPlans(any());

		// when
		Calender calender = calenderService.create(account, 3, "daehwan", List.of(Subject_생성("물리")));

		// then
		assertThat(calender.getAccount()
						   .getNickname()).isEqualTo("daehwan");
	}

	@DisplayName("내 Calender 정보 조회")
	@Test
	void findMine_회원_Calender() {
		// given
		Account account = Account_생성();
		willReturn(Optional.ofNullable(new Calender(account))).given(calenderRepository)
										 .findByAccount(any());

		// when
		Calender calender = calenderService.findMine(account);

		// then
		assertThat(calender.getAccount()).isSameAs(account);
	}
}
