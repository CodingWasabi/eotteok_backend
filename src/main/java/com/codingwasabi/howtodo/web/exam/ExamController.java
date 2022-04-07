package com.codingwasabi.howtodo.web.exam;

import static com.codingwasabi.howtodo.web.exam.dto.GetMyExamResponse.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codingwasabi.howtodo.security.resolver.LoginAccount;
import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.exam.dto.GetMyExamResponse;
import com.codingwasabi.howtodo.web.exam.dto.PutMyExamRequest;
import com.codingwasabi.howtodo.web.exam.dto.PutMyExamResponse;
import com.codingwasabi.howtodo.web.exam.entity.Exam;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ExamController {
	
	private final ExamService examService;
	
	@GetMapping("/my/exams")
	public GetMyExamResponse getMyExam(@LoginAccount Account account) {
		
		return new GetMyExamResponse(examService.getMyExam(account)
												.stream()
												.map(ExamResponse::new)
												.collect(Collectors.toList()));
	}
	
	@PutMapping("/my/exams")
	public void putMyExam(@LoginAccount Account account, @RequestBody PutMyExamRequest putMyExamRequest) {
		List<Exam> exams = new ArrayList<>();
		int index = 0;
		for (PutMyExamRequest.ExamRequest subjectRequest : putMyExamRequest.getSubjects()) {
			exams.add(Exam.builder()
						  .account(account)
						  .name(subjectRequest.getName())
						  .dueDateTime(subjectRequest.getDate())
						  .studyDegree(subjectRequest.getPrepareTime())
						  .color(++index)
						  .build());
		}
		
		examService.putExam(account, exams);
		
		return;
	}
	
}
