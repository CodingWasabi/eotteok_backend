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
		List<DailyPlan> dailyPlans = planMakingPolicy.makeDailyPlans(exams, LocalDate.of(2022, 4, 7));

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