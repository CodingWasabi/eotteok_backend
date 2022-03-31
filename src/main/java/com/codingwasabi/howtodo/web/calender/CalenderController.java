package com.codingwasabi.howtodo.web.calender;

import static org.springframework.http.MediaType.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
import com.codingwasabi.howtodo.web.calender.dto.CreateCalenderRequest;
import com.codingwasabi.howtodo.web.calender.dto.CreateCalenderResponse;
import com.codingwasabi.howtodo.web.calender.dto.GetCalenderByUserIdResponse;
import com.codingwasabi.howtodo.web.calender.dto.GetMyCalenderResponse;
import com.codingwasabi.howtodo.web.calender.entity.Calender;
import com.codingwasabi.howtodo.web.calender.service.CalenderService;
import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;
import com.codingwasabi.howtodo.web.policies.TendencyPolicy;
import com.codingwasabi.howtodo.web.subject.entity.Exam;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class CalenderController {
	private final CalenderService calenderService;
	private final TendencyPolicy tendencyPolicy;

	@PostMapping(value = "/calender", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<CreateCalenderResponse> createCalender(@LoginAccount Account account,
																 @RequestBody
																	 CreateCalenderRequest createCalenderRequest) {

		int tendency = tendencyPolicy.setUp(createCalenderRequest.getAnswers());

		Calender calender = calenderService.create(account,
												   tendency,
												   createCalenderRequest.getNickname(),
												   extractSubjects(account, createCalenderRequest));

		return ResponseEntity.ok(CreateCalenderResponse.builder()
													   .nickname(createCalenderRequest.getNickname())
													   .tendency(tendency)
													   .subjects(convertCreateCalenderSubjectInfo(calender))
													   .calendar(convertCreateCalenderDateInfo(calender))
													   .build());
	}

	private List<Exam> extractSubjects(Account account, CreateCalenderRequest createCalenderRequest) {
		return createCalenderRequest.getSubjects()
									.stream()
									.map(dto -> Exam.builder()
													.dateTime(dto.getLocalDateTime())
													.name(dto.getName())
													.studyDegree(dto.getPrepareTime())
													.account(account)
													.build())
									.collect(Collectors.toList());
	}

	private List<CreateCalenderResponse.SubjectInfo> convertCreateCalenderSubjectInfo(Calender calender) {
		return calender.getExams()
					   .stream()
					   .map(CreateCalenderResponse.SubjectInfo::from)
					   .collect(Collectors.toList());
	}

	private List<CreateCalenderResponse.DateInfo> convertCreateCalenderDateInfo(Calender calender) {
		return calender.getDailyPlans()
					   .stream()
					   .map(plan -> CreateCalenderResponse.DateInfo.of(plan.getDate(), convertCreateCalenderTodo(plan)))
					   .collect(Collectors.toList());
	}

	private List<CreateCalenderResponse.DateInfo.TodoInfo> convertCreateCalenderTodo(DailyPlan plan) {
		return plan.getToDos()
				   .stream()
				   .map(CreateCalenderResponse.DateInfo.TodoInfo::from)
				   .collect(Collectors.toList());
	}

	@GetMapping(value = "/my/calender/result", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<GetMyCalenderResponse> getMyCalenderResponse(@LoginAccount Account account) {
		Calender calender = calenderService.findMine(account);

		return ResponseEntity.ok(GetMyCalenderResponse.builder()
													  .nickname(account.getNickname())
													  .tendency(account.getTendency())
													  .calendar(convertGetMyCalenderDateInfo(calender))
													  .subjects(convertGetMyCalenderSubjectInfo(calender))
													  .build());
	}

	private List<GetMyCalenderResponse.SubjectInfo> convertGetMyCalenderSubjectInfo(Calender calender) {
		return calender.getExams()
					   .stream()
					   .map(GetMyCalenderResponse.SubjectInfo::from)
					   .collect(Collectors.toList());
	}

	private List<GetMyCalenderResponse.DateInfo> convertGetMyCalenderDateInfo(Calender calender) {
		return calender.getDailyPlans()
					   .stream()
					   .map(plan -> new GetMyCalenderResponse.DateInfo(plan.getDate(),
																	   plan.getComments()
																		   .size(),
																	   convertGetMyCalenderTodo(plan)))
					   .collect(Collectors.toList());
	}

	private List<GetMyCalenderResponse.DateInfo.ToDoInfo> convertGetMyCalenderTodo(DailyPlan plan) {
		return plan.getToDos()
				   .stream()
				   .map(GetMyCalenderResponse.DateInfo.ToDoInfo::from)
				   .collect(Collectors.toList());
	}

	@Deprecated
	@GetMapping("/calender/{userId}/result")
	public GetCalenderByUserIdResponse getCalenderByUserIdResponse() {

		List<GetCalenderByUserIdResponse.DateInfoResponse> calendar = new ArrayList<>();

		List<GetCalenderByUserIdResponse.DateInfoResponse.SubjectResponse> subjects = new ArrayList<>();
		subjects.add(new GetCalenderByUserIdResponse.DateInfoResponse.SubjectResponse("데이터 분석 및 활용", 2));
		subjects.add(new GetCalenderByUserIdResponse.DateInfoResponse.SubjectResponse("컴파일러", 2));
		subjects.add(new GetCalenderByUserIdResponse.DateInfoResponse.SubjectResponse("디비", 2));
		calendar.add(new GetCalenderByUserIdResponse.DateInfoResponse(LocalDate.of(2022, 3, 24), 4, subjects));

		subjects = new ArrayList<>();
		subjects.add(new GetCalenderByUserIdResponse.DateInfoResponse.SubjectResponse("데이터 분석 및 활용", 2));
		subjects.add(new GetCalenderByUserIdResponse.DateInfoResponse.SubjectResponse("컴파일러", 2));
		subjects.add(new GetCalenderByUserIdResponse.DateInfoResponse.SubjectResponse("디비", 2));
		calendar.add(new GetCalenderByUserIdResponse.DateInfoResponse(LocalDate.of(2022, 3, 25), 5, subjects));

		return GetCalenderByUserIdResponse.builder()
										  .tendency(3)
										  .nickname("daehwan")
										  .calendar(calendar)
										  .build();
	}
}
