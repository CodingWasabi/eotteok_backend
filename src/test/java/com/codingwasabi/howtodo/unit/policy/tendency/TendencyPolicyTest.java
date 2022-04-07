package com.codingwasabi.howtodo.unit.policy.tendency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.codingwasabi.howtodo.web.policy.tendency.MostChosenTendencyPolicy;
import com.codingwasabi.howtodo.web.policy.tendency.MostChosenTendencySolution;
import com.codingwasabi.howtodo.web.policy.tendency.TendencyPolicy;

@Deprecated
@DisplayName("TendencyPolicy, 비즈니스 로직 단위 테스트 ")
public class TendencyPolicyTest {
	private TendencyPolicy tendencyPolicy;

	@BeforeEach
	void init() {
		tendencyPolicy = new MostChosenTendencyPolicy(new MostChosenTendencySolution());
	}

}