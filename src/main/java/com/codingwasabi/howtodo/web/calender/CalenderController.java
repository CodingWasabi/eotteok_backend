package com.codingwasabi.howtodo.web.calender;

import static com.codingwasabi.howtodo.web.calender.utils.CalenderResponseConverter.*;
import static org.springframework.http.MediaType.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingwasabi.howtodo.security.resolver.LoginAccount;
import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calender.dto.CalenderResponse;
import com.codingwasabi.howtodo.web.calender.dto.CreateCalenderRequest;
import com.codingwasabi.howtodo.web.calender.entity.Calender;
import com.codingwasabi.howtodo.web.calender.service.CalenderService;
import com.codingwasabi.howtodo.web.exam.entity.Exam;
import com.codingwasabi.howtodo.web.policies.TendencyPolicy;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class CalenderController {
	private final CalenderService calenderService;
	private final TendencyPolicy tendencyPolicy;

	@PostMapping(value = "/calender", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<CalenderResponse> createCalender(@LoginAccount Account account,
														   @RequestBody CreateCalenderRequest createCalenderRequest) {

		int tendency = tendencyPolicy.setUp(createCalenderRequest.getAnswers());

		Calender calender = calenderService.create(account,
												   tendency,
												   createCalenderRequest.getNickname(),
												   extractSubjects(account, createCalenderRequest));

		return ResponseEntity.ok(CalenderResponse.builder()
												 .nickname(createCalenderRequest.getNickname())
												 .tendency(tendency)
												 .exams(convertExamInfos(calender))
												 .calendar(convertMonthToDoInfo(calender))
												 .build());
	}

	private List<Exam> extractSubjects(Account account, CreateCalenderRequest createCalenderRequest) {
		return createCalenderRequest.getExams()
									.stream()
									.map(dto -> Exam.builder()
													.dateTime(dto.getLocalDateTime())
													.name(dto.getName())
													.studyDegree(dto.getPrepareTime())
													.account(account)
													.build())
									.collect(Collectors.toList());
	}

	@GetMapping(value = "/my/calender/result", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<CalenderResponse> getMyCalenderResponse(@LoginAccount Account account) {
		Calender calender = calenderService.findMine(account);

		return null;
	}
}
