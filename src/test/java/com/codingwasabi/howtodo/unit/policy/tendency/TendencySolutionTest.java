package com.codingwasabi.howtodo.unit.policy.tendency;

import static com.codingwasabi.howtodo.unit.utils.EntityFactory.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.codingwasabi.howtodo.web.exam.entity.Exam;
import com.codingwasabi.howtodo.web.policy.tendency.MostChosenTendencySolution;
import com.codingwasabi.howtodo.web.policy.tendency.TendencySolution;

@DisplayName("TendencySolution, 비즈니스 로직 단위 테스트")
public class TendencySolutionTest {
	private TendencySolution tendencySolution;

	@BeforeEach
	void init() {
		tendencySolution = new MostChosenTendencySolution();
	}

	@DisplayName("가장 많이 선택된 번호 찾아내기")
	@ParameterizedTest
	@ValueSource(strings = "1 2 3 3 5")
	void 가장_많이_선택된_숫자_1개(String input) {
		// given
		List<Integer> inputs = Arrays.stream(input.split(" "))
									 .map(Integer::valueOf)
									 .collect(Collectors.toList());

		// when
		int mostChosen = tendencySolution.mostChosen(inputs);

		// then
		assertThat(mostChosen).isEqualTo(3);
	}

	@DisplayName("가장 많이 선택된 번호 찾아내기")
	@ParameterizedTest
	@ValueSource(strings = "1 5 5 3 3")
	void 가장_많이_선택된_숫자_2개(String input) {
		// given
		List<Integer> inputs = Arrays.stream(input.split(" "))
									 .map(Integer::valueOf)
									 .collect(Collectors.toList());

		// when
		int mostChosen = tendencySolution.mostChosen(inputs);

		// then
		assertThat(mostChosen).isEqualTo(3);
	}

	@DisplayName("가장 많이 선택된 번호 찾아내기")
	@ParameterizedTest
	@ValueSource(strings = "1 2 3 4 5")
	void 모두_같은_횟수(String input) {
		// given
		List<Integer> inputs = Arrays.stream(input.split(" "))
									 .map(Integer::valueOf)
									 .collect(Collectors.toList());

		// when
		int mostChosen = tendencySolution.mostChosen(inputs);

		// then
		assertThat(mostChosen).isEqualTo(1);
	}

	@DisplayName("예외 ) 입력된 시험 개수가 0개 인 경우")
	@Test
	void 시험_개수_0개() {
		// when/then
		assertThatIllegalArgumentException().isThrownBy(() -> tendencySolution.extractExamsInterval(6,
																									new ArrayList<>(),
																									LocalDate.of(2022,
																												 04,
																												 10)))
											.withMessage("exams size cannot be 0");
	}

	@DisplayName("예외 ) 입력된 시험 개수가 8개 이상인 경우")
	@Test
	void 시험_개수_초과() {
		// given
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 04, 20),
								   Exam_생성("알고리즘", 2022, 04, 23),
								   Exam_생성("객체지향프로그래밍", 2022, 04, 12),
								   Exam_생성("데이터통신및네트워크", 2022, 04, 13),
								   Exam_생성("네트워크통신및보안", 2022, 04, 25),
								   Exam_생성("소프트웨어공학", 2022, 04, 16),
								   Exam_생성("운영체제", 2022, 04, 18),
								   Exam_생성("데이터베이스", 2022, 04, 07));

		// when/then
		assertThatIllegalArgumentException().isThrownBy(() -> tendencySolution.extractExamsInterval(6,
																									exams,
																									LocalDate.of(2022,
																												 04,
																												 03)))
											.withMessage("exams size cannot be over 7");
	}

	@DisplayName("예외 ) 지난 날짜에 대한 시험 일정 입력 경우")
	@Test
	void 지난_날짜에대한_시험_입력_경우() {
		// given
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 03, 31));

		// when/then
		assertThatIllegalArgumentException().isThrownBy(() -> tendencySolution.extractExamsInterval(2,
																									exams,
																									LocalDate.of(2022,
																												 04,
																												 05)))
											.withMessage("there is a exam need over study time");
	}

	@DisplayName("입력된 시험 개수가 1개인 경우")
	@Test
	void 시험_1개() {
		// given
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 04, 20));

		// when
		int interval = tendencySolution.extractExamsInterval(2, exams, LocalDate.of(2022, 04, 07));

		// then
		assertThat(interval).isEqualTo(7);
	}

	@DisplayName("시험간 모든 격차가 하루인 경우")
	@Test
	void 모든_시험_격차_하루() {
		// given
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 04, 20),
								   Exam_생성("컴퓨터구조", 2022, 04, 19),
								   Exam_생성("알고리즘", 2022, 04, 21));

		// when
		int interval = tendencySolution.extractExamsInterval(2, exams, LocalDate.of(2022, 04, 07));

		// then
		assertThat(interval).isEqualTo(1);
	}

	@DisplayName("시험간 간격이 너무 먼 경우")
	@Test
	void 한시험이_월초_한시험이_월말_인경우() {
		// given
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 04, 05), Exam_생성("알고리즘", 2022, 04, 25));

		// when
		int interval = tendencySolution.extractExamsInterval(3, exams, LocalDate.of(2022, 04, 03));

		// then
		assertThat(interval).isEqualTo(4);
	}

	@DisplayName("모든 시험이 10일 안 인 경우 (월초)")
	@Test
	void 모든_시험이_월초() {
		// given
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 4, 5), Exam_생성("알고리즘", 2022, 4, 9));

		// when
		int interval = tendencySolution.extractExamsInterval(3, exams, LocalDate.of(2022, 4, 3));

		// then
		assertThat(interval).isEqualTo(5);
	}

	@DisplayName("모든 시험이 20일 이후 인 경우, (월말)")
	@Test
	void 모든_시험이_월말() {
		// given
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 4, 21), Exam_생성("알고리즘", 2022, 4, 25));

		// when
		int interval = tendencySolution.extractExamsInterval(3, exams, LocalDate.of(2022, 4, 3));

		// then
		assertThat(interval).isEqualTo(6);
	}

	@DisplayName("위의 모든 경우가 아닌 경우")
	@Test
	void 무난한_시험_간격() {
		// given
		List<Exam> exams = List.of(Exam_생성("자료구조", 2022, 04, 05),
								   Exam_생성("운영체제", 2022, 04, 13),
								   Exam_생성("운영체제", 2022, 04, 14),
								   Exam_생성("알고리즘", 2022, 04, 25));

		// when
		int interval = tendencySolution.extractExamsInterval(3, exams, LocalDate.of(2022, 04, 03));

		// then
		assertThat(interval).isEqualTo(3);
	}
}
