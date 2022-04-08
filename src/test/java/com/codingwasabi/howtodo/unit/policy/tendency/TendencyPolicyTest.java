package com.codingwasabi.howtodo.unit.policy.tendency;

import static com.codingwasabi.howtodo.unit.utils.EntityFactory.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codingwasabi.howtodo.web.exam.entity.Exam;
import com.codingwasabi.howtodo.web.policy.tendency.MostChosenTendencyPolicy;
import com.codingwasabi.howtodo.web.policy.tendency.MostChosenTendencySolution;
import com.codingwasabi.howtodo.web.policy.tendency.TendencyPolicy;

@DisplayName("TendencyPolicy, 비즈니스 로직 단위 테스트 ")
public class TendencyPolicyTest {
	private TendencyPolicy tendencyPolicy;

	@BeforeEach
	void init() {
		tendencyPolicy = new MostChosenTendencyPolicy(new MostChosenTendencySolution());
	}

	@Test
	@DisplayName("정상) 가혹한 일정에 절망하는 짱구, 1:1")
	void 짱구_1() {
		// given
		List<Integer> answers = new ArrayList<>(List.of(1, 1, 3, 1));
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 4, 15),
								   Exam_생성("운영체제", 2022, 4, 16),
								   Exam_생성("운영체제", 2022, 4, 17),
								   Exam_생성("알고리즘", 2022, 4, 18));

		// when
		int tendency = tendencyPolicy.setUp(answers, 6, exams, LocalDate.of(2022, 04, 05));

		// then
		assertThat(tendency).isEqualTo(1);
	}

	@Test
	@DisplayName("정상) 힘들지만 할만한거같은 맹구, 3:2")
	void 맹구_2() {
		// given
		List<Integer> answers = new ArrayList<>(List.of(3, 1, 2, 3));
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 4, 15),
								   Exam_생성("운영체제", 2022, 4, 16),
								   Exam_생성("운영체제", 2022, 4, 17),
								   Exam_생성("알고리즘", 2022, 4, 18),
								   Exam_생성("알고리즘", 2022, 4, 18),
								   Exam_생성("알고리즘", 2022, 4, 19),
								   Exam_생성("알고리즘", 2022, 4, 25));

		// when
		int tendency = tendencyPolicy.setUp(answers, 6, exams, LocalDate.of(2022, 04, 05));

		// then
		assertThat(tendency).isEqualTo(16);
	}

	@Test
	@DisplayName("꾸준히 딴짓하는 훈이, 5:3")
	void 훈이_3() {
		// given
		List<Integer> answers = new ArrayList<>(List.of(3, 4, 5, 5));
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 4, 13),
								   Exam_생성("운영체제", 2022, 4, 15),
								   Exam_생성("운영체제", 2022, 4, 17));

		// when
		int tendency = tendencyPolicy.setUp(answers, 6, exams, LocalDate.of(2022, 04, 05));

		// then
		assertThat(tendency).isEqualTo(31);
	}

	@Test
	@DisplayName("비어있는 일정도 계획적으로 보내는 철수, 2:4")
	void 철수_4() {
		// given
		List<Integer> answers = new ArrayList<>(List.of(2, 2, 4, 4));
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 4, 8),
								   Exam_생성("운영체제", 2022, 4, 24),
								   Exam_생성("운영체제", 2022, 4, 28));

		// when
		int tendency = tendencyPolicy.setUp(answers, 6, exams, LocalDate.of(2022, 04, 05));

		// then
		assertThat(tendency).isEqualTo(11);
	}

	@Test
	@DisplayName("시험 빨리보는 얼리버드 유리, 4:5")
	void 유리_5() {
		// given
		List<Integer> answers = new ArrayList<>(List.of(1, 4, 4, 4));
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 4, 7), Exam_생성("운영체제", 2022, 4, 9));

		// when
		int tendency = tendencyPolicy.setUp(answers, 6, exams, LocalDate.of(2022, 04, 05));

		// then
		assertThat(tendency).isEqualTo(26);
	}

	@Test
	@DisplayName(" 놀다가 뒤늦게 공부하는 짱구, 1:6")
	void 짱구_6() {
		// given
		List<Integer> answers = new ArrayList<>(List.of(4, 2, 1, 1));
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 4, 21),
								   Exam_생성("운영체제", 2022, 4, 22),
								   Exam_생성("운영체제", 2022, 4, 27));

		// when
		int tendency = tendencyPolicy.setUp(answers, 6, exams, LocalDate.of(2022, 04, 05));

		// then
		assertThat(tendency).isEqualTo(6);
	}

	@Test
	@DisplayName("시..험.. 하나뿐인.. 맹..구, 3:7")
	void 맹구_7() {
		// given
		List<Integer> answers = new ArrayList<>(List.of(3, 3, 3, 2));
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 4, 21));

		// when
		int tendency = tendencyPolicy.setUp(answers, 6, exams, LocalDate.of(2022, 04, 05));

		// then
		assertThat(tendency).isEqualTo(21);
	}

	@Test
	@DisplayName("예외) 당일 시험이 입력된 경우")
	void 당일_시험() {
		// given
		List<Integer> answers = new ArrayList<>(List.of(3, 3, 3, 2));
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 4, 05));

		// when/then
		assertThatIllegalArgumentException().isThrownBy(() -> tendencyPolicy.setUp(answers,
																				   6,
																				   exams,
																				   LocalDate.of(2022, 04, 05)));
	}

	@Test
	@DisplayName("예외) 매일 공부할 시간이 선택한 시험별 공부시간에 부족한경우")
	void 공부시간_부족() {
		// given
		List<Integer> answers = new ArrayList<>(List.of(3, 3, 3, 2));
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 4, 10, 10), Exam_생성("자료구조", 2022, 4, 13, 10));

		// when/then
		assertThatIllegalArgumentException().isThrownBy(() -> tendencyPolicy.setUp(answers,
																				   1,
																				   exams,
																				   LocalDate.of(2022, 04, 8)));
	}

	@Test
	@DisplayName("예외) 매일 공부할 시간이 선택한 시험별 공부시간에 부족한경우 2")
	void 공부시간_부족_2() {
		// given
		List<Integer> answers = new ArrayList<>(List.of(3, 3, 3, 2));
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 4, 10, 10), Exam_생성("자료구조", 2022, 4, 11, 10));

		// when/then
		assertThatIllegalArgumentException().isThrownBy(() -> tendencyPolicy.setUp(answers,
																				   5,
																				   exams,
																				   LocalDate.of(2022, 04, 7)));
	}
}