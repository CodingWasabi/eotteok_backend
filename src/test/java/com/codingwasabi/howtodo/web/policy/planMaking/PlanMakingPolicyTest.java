package com.codingwasabi.howtodo.web.policy.planMaking;

import static com.codingwasabi.howtodo.unit.utils.EntityFactory.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;
import com.codingwasabi.howtodo.web.exam.entity.Exam;

@DisplayName("PlanMakingPolicy, 비즈니스 로직 단위 테스트")
class PlanMakingPolicyTest {
	private PlanMakingPolicy planMakingPolicy;

	@BeforeEach
	void init() {
		planMakingPolicy = new MonthPlanMakingPolicy();
	}

	@DisplayName("시험 두개, 시험일이 곂치지 않는 경우")
	@Test
	void 시험두개_곂치지_않는_경우() {
		// given
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 4, 11, 10), Exam_생성("운영체제", 2022, 4, 13, 20));

		// when
		List<DailyPlan> dailyPlans = planMakingPolicy.makeDailyPlans(Account_생성(), exams, LocalDate.of(2022, 4, 7), 6);

		// then

		// 4/8 : B(6)
		assertThat(dailyPlans.get(0)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 0, 0, "운영체제", 6);

		// 4/9 : A(4), B(2)
		assertThat(dailyPlans.get(1)
							 .getToDos()).hasSize(2);
		assertThatToDo(dailyPlans, 1, 0, "자료구조", 4);
		assertThatToDo(dailyPlans, 1, 1, "운영체제", 2);

		// 4/10 : A(6)
		assertThat(dailyPlans.get(2)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 2, 0, "자료구조", 6);

		// 4/11 : B(6)
		assertThat(dailyPlans.get(3)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 3, 0, "운영체제", 6);

		// 4/12 : B(6)
		assertThat(dailyPlans.get(4)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 4, 0, "운영체제", 6);
	}

	@Test
	@DisplayName("시험 두개, 시험일이 곂치는 경우")
	void 시험두개_시험일이_곂치는_경우() {
		// given
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 4, 13, 10), Exam_생성("운영체제", 2022, 4, 13, 20));

		// when
		List<DailyPlan> dailyPlans = planMakingPolicy.makeDailyPlans(Account_생성(), exams, LocalDate.of(2022, 4, 7), 6);

		// then

		// 4/8 : B(6)
		assertThat(dailyPlans.get(0)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 0, 0, "운영체제", 6);

		// 4/9 : B(6)
		assertThat(dailyPlans.get(1)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 1, 0, "운영체제", 6);

		// 4/10 : B(6)
		assertThat(dailyPlans.get(2)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 2, 0, "운영체제", 6);

		// 4/11 : A(4), B(2)
		assertThat(dailyPlans.get(3)
							 .getToDos()).hasSize(2);
		assertThatToDo(dailyPlans, 3, 0, "자료구조", 4);
		assertThatToDo(dailyPlans, 3, 1, "운영체제", 2);

		// 4/12 : A(6)
		assertThat(dailyPlans.get(4)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 4, 0, "자료구조", 6);
	}

	@Test
	@DisplayName("시험 3개가 곂치고, 하루는 별도로 존재")
	void 시험_3개_곂치고_하루_추가로_존재() {
		// given
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 4, 17, 20),
								   Exam_생성("운영체제", 2022, 4, 15, 10),
								   Exam_생성("알고리즘", 2022, 4, 15, 20),
								   Exam_생성("소프트웨어공학", 2022, 4, 15, 10));

		// when
		List<DailyPlan> dailyPlans = planMakingPolicy.makeDailyPlans(Account_생성(),
																	 exams,
																	 LocalDate.of(2022, 4, 10),
																	 12);

		// then
		// 4/11 : 소공(4)
		assertThat(dailyPlans.get(0)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 0, 0, "소프트웨어공학", 4);

		// 4/12 : 알고리즘(6), 소공(6)
		assertThat(dailyPlans.get(1)
							 .getToDos()).hasSize(2);
		assertThatToDo(dailyPlans, 1, 0, "알고리즘", 6);
		assertThatToDo(dailyPlans, 1, 1, "소프트웨어공학", 6);

		// 4/13 : 알고리즘(12)
		assertThat(dailyPlans.get(2)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 2, 0, "알고리즘", 12);

		// 4/14 : 운영체제(10), 알고리즘(2)
		assertThat(dailyPlans.get(3)
							 .getToDos()).hasSize(2);
		assertThatToDo(dailyPlans, 3, 0, "운영체제", 10);
		assertThatToDo(dailyPlans, 3, 1, "알고리즘", 2);

		// 4/15 : 자료구조(8)
		assertThat(dailyPlans.get(4)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 4, 0, "자료구조", 8);

		// 4/16 : 자료구조(12)
		assertThat(dailyPlans.get(5)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 5, 0, "자료구조", 12);
	}

	@Test
	@DisplayName("시험 3개가 멀리떨어짐")
	void 시험_3개_멀리_떨어짐() {
		// given
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 4, 8, 20),
								   Exam_생성("운영체제", 2022, 4, 15, 10),
								   Exam_생성("알고리즘", 2022, 4, 25, 20));

		// when
		List<DailyPlan> dailyPlans = planMakingPolicy.makeDailyPlans(Account_생성(), exams, LocalDate.of(2022, 4, 5), 10);

		// then
		// 4/6 : 자료구조(10)
		assertThat(dailyPlans.get(0)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 0, 0, "자료구조", 10);

		// 4/7 : 자료구조(10)
		assertThat(dailyPlans.get(1)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 1, 0, "자료구조", 10);

		// 4/14 : 운영체제(10)
		assertThat(dailyPlans.get(2)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 2, 0, "운영체제", 10);

		// 4/23 : 알고리즘(10)
		assertThat(dailyPlans.get(3)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 3, 0, "알고리즘", 10);

		// 4/24 : 알고리즘(10)
		assertThat(dailyPlans.get(4)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 4, 0, "알고리즘", 10);
	}

	@Test
	@DisplayName("시험 2개가 월이 달라짐")
	void 시험_월이_달라짐() {
		// given
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 4, 30, 30),
								   Exam_생성("운영체제", 2022, 5, 3, 50),
								   Exam_생성("알고리즘", 2022, 4, 28, 20));

		// when
		List<DailyPlan> dailyPlans = planMakingPolicy.makeDailyPlans(Account_생성(), exams, LocalDate.of(2022, 4, 5), 12);

		// then
		// 4/24 : 운영체제(4)
		assertThat(dailyPlans.get(0)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 0, 0, "운영체제", 4);

		// 4/25 : 자료구조(2), 운영체제(10)
		assertThat(dailyPlans.get(1)
							 .getToDos()).hasSize(2);
		assertThatToDo(dailyPlans, 1, 0, "자료구조", 2);
		assertThatToDo(dailyPlans, 1, 1, "운영체제", 10);

		// 4/26 : 알고리즘(8), 자료구조(4)
		assertThat(dailyPlans.get(2)
							 .getToDos()).hasSize(2);
		assertThatToDo(dailyPlans, 2, 0, "알고리즘", 8);
		assertThatToDo(dailyPlans, 2, 1, "자료구조", 4);

		// 4/27 : 알고리즘(12)
		assertThat(dailyPlans.get(3)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 3, 0, "알고리즘", 12);

		// 4/28 : 자료구조(12)
		assertThat(dailyPlans.get(4)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 4, 0, "자료구조", 12);

		// 4/29 : 자료구조(12)
		assertThat(dailyPlans.get(5)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 5, 0, "자료구조", 12);

		// 4/30 : 운영체제(12)
		assertThat(dailyPlans.get(6)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 6, 0, "운영체제", 12);

		// 5/1 : 운영체제(12)
		assertThat(dailyPlans.get(7)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 7, 0, "운영체제", 12);

		// 5/2 : 운영체제(12)
		assertThat(dailyPlans.get(8)
							 .getToDos()).hasSize(1);
		assertThatToDo(dailyPlans, 8, 0, "운영체제", 12);
	}

	@Test
	@DisplayName("예외) 시험 공부 시간이 부족한 경우")
	void 시험_공부_시간_부족() {
		// given
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 4, 17, 20),
								   Exam_생성("운영체제", 2022, 4, 15, 10),
								   Exam_생성("알고리즘", 2022, 4, 15, 20),
								   Exam_생성("소프트웨어공학", 2022, 4, 15, 10));

		// when/then
		assertThatIllegalArgumentException().isThrownBy(() -> planMakingPolicy.makeDailyPlans(Account_생성(),
																							  exams,
																							  LocalDate.of(2022, 4, 10),
																							  6));
	}

	private void assertThatToDo(List<DailyPlan> dailyPlans,
								int planNumber,
								int toDoNumber,
								String subjectName,
								int hour) {

		assertThat(dailyPlans.get(planNumber)
							 .getToDos()
							 .get(toDoNumber)
							 .getExam()
							 .getName()).isEqualTo(subjectName);

		assertThat(dailyPlans.get(planNumber)
							 .getToDos()
							 .get(toDoNumber)
							 .getHour()).isEqualTo(hour);
	}

}