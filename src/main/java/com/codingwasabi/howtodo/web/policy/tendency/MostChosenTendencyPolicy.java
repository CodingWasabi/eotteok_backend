package com.codingwasabi.howtodo.web.policy.tendency;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MostChosenTendencyPolicy implements TendencyPolicy {
	private static final int QUESTION_COUNT = 5;

	@Override
	// 완성되지않음, 배포 테스트 용으로 우선 커밋, 추후에 수정되어 재 배포 -> 기능이 실행되지 않을것이다.
	public int setUp(List<Integer> answers, int studyDegree) {

		return 0;
	}
}
