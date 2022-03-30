package com.codingwasabi.howtodo.web.calender;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.codingwasabi.howtodo.web.subject.entity.Subject;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CalenderController {
	private final CalenderService calenderService;
	private final TendencyPolicy tendencyPolicy;

	@PostMapping("/calender")
	public CreateCalenderResponse createCalender(@LoginAccount Account account,
												 @RequestBody CreateCalenderRequest createCalenderRequest) {

		Calender calender = calenderService.create(account,
												   createCalenderRequest.getNickname(),
												   extractSubjects(account, createCalenderRequest));

		int tendency = tendencyPolicy.setUp(createCalenderRequest.getAnswers());

		return CreateCalenderResponse.builder()
									 .nickname(createCalenderRequest.getNickname())
									 .tendency(tendency)
									 .subjects(ConvertSubjectDto(calender))
									 .calendar(ConvertCalenderDto(calender))
									 .build();
	}

	private List<Subject> extractSubjects(Account account, CreateCalenderRequest createCalenderRequest) {
		return createCalenderRequest.getSubjects()
									.stream()
									.map(dto -> Subject.builder()
													   .dateTime(dto.getLocalDateTime())
													   .name(dto.getName())
													   .studyDegree(dto.getPrepareTime())
													   .account(account)
													   .build())
									.collect(Collectors.toList());
	}

	private List<CreateCalenderResponse.SubjectInfo> ConvertSubjectDto(Calender calender) {
		return calender.getSubjects()
					   .stream()
					   .map(CreateCalenderResponse.SubjectInfo::from)
					   .collect(Collectors.toList());
	}

	private List<CreateCalenderResponse.DateInfo> ConvertCalenderDto(Calender calender) {
		return calender.getDailyPlans()
					   .stream()
					   .map(plan -> CreateCalenderResponse.DateInfo.of(plan.getDate(), convertTodoDto(plan)))
					   .collect(Collectors.toList());
	}

	private List<CreateCalenderResponse.DateInfo.TodoInfo> convertTodoDto(DailyPlan plan) {
		return plan.getToDos()
				   .stream()
				   .map(CreateCalenderResponse.DateInfo.TodoInfo::from)
				   .collect(Collectors.toList());
	}

	@GetMapping("/my/calender/result")
	public GetMyCalenderResponse getMyCalenderResponse() {

		List<GetMyCalenderResponse.DateInfoResponse> calendar = new ArrayList<>();

		List<GetMyCalenderResponse.DateInfoResponse.SubjectResponse> subjects = new ArrayList<>();
		subjects.add(new GetMyCalenderResponse.DateInfoResponse.SubjectResponse("데이터 분석 및 활용", 2));
		subjects.add(new GetMyCalenderResponse.DateInfoResponse.SubjectResponse("컴파일러", 2));
		subjects.add(new GetMyCalenderResponse.DateInfoResponse.SubjectResponse("디비", 2));
		calendar.add(new GetMyCalenderResponse.DateInfoResponse(LocalDate.of(2022, 3, 24), 4, subjects));

		subjects = new ArrayList<>();
		subjects.add(new GetMyCalenderResponse.DateInfoResponse.SubjectResponse("데이터 분석 및 활용", 2));
		subjects.add(new GetMyCalenderResponse.DateInfoResponse.SubjectResponse("컴파일러", 2));
		subjects.add(new GetMyCalenderResponse.DateInfoResponse.SubjectResponse("디비", 2));
		calendar.add(new GetMyCalenderResponse.DateInfoResponse(LocalDate.of(2022, 3, 25), 5, subjects));

		return GetMyCalenderResponse.builder()
									.tendency(3)
									.calendar(calendar)
									.build();
	}

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
