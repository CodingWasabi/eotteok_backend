package com.codingwasabi.howtodo.web.exam;

import static com.codingwasabi.howtodo.web.exam.dto.GetMyExamResponse.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingwasabi.howtodo.web.exam.dto.GetMyExamResponse;

@RestController
public class ExamController {
	
	@GetMapping("/my/exams")
	public GetMyExamResponse getMyExam() {
		List<SubjectResponse> subjectResponses = new ArrayList<>();
		subjectResponses.add(
			new SubjectResponse("인간컴퓨터상호작용",
								LocalDateTime.of(2022, 3, 24, 0, 0, 0),
								3)
		);
		subjectResponses.add(
			new SubjectResponse("데이터 분석 및 활용",
								LocalDateTime.of(2022, 3, 25, 0, 0, 0),
								4)
		);
		
		return new GetMyExamResponse(subjectResponses);
	}
	
}
