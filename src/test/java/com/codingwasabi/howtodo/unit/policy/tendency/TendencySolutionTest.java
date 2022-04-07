package com.codingwasabi.howtodo.unit.policy.tendency;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
}
