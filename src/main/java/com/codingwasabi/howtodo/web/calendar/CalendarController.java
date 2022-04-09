package com.codingwasabi.howtodo.web.calendar;

import static com.codingwasabi.howtodo.web.calendar.utils.CalenderResponseConverter.*;
import static org.springframework.http.MediaType.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingwasabi.howtodo.security.resolver.LoginAccount;
import com.codingwasabi.howtodo.web.account.AccountService;
import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calendar.dto.CalendarResponse;
import com.codingwasabi.howtodo.web.calendar.dto.CreateCalendarRequest;
import com.codingwasabi.howtodo.web.calendar.entity.Calendar;
import com.codingwasabi.howtodo.web.calendar.service.CalendarService;
import com.codingwasabi.howtodo.web.exam.entity.Exam;
import com.codingwasabi.howtodo.web.policy.tendency.TendencyPolicy;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class CalendarController {
	private final CalendarService calendarService;
	private final AccountService accountService;
	private final TendencyPolicy tendencyPolicy;

	@PostMapping(value = "/calendar", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<CalendarResponse> createCalendar(@LoginAccount Account account,
														   @RequestBody CreateCalendarRequest createCalendarRequest) {

		List<Exam> exams = extractExams(account, createCalendarRequest);

		int tendency = tendencyPolicy.setUp(createCalendarRequest.getAnswers(),
											createCalendarRequest.getDailyQuota(),
											exams,
											LocalDate.now());

		Calendar calendar = calendarService.create(account,
												   tendency,
												   createCalendarRequest.getNickname(),
												   createCalendarRequest.getDailyQuota(),
												   exams);

		return ResponseEntity.ok(CalendarResponse.builder()
												 .nickname(createCalendarRequest.getNickname())
												 .tendency(tendency)
												 .exams(convertExamInfos(calendar))
												 .calendar(convertMonthToDoInfo(calendar))
												 .build());
	}

	private List<Exam> extractExams(Account account, CreateCalendarRequest createCalendarRequest) {
		return createCalendarRequest.getExams()
									.stream()
									.map(dto -> Exam.builder()
													.dueDateTime(dto.getLocalDateTime())
													.name(dto.getName())
													.studyDegree(dto.getPrepareTime())
													.account(account)
													.build())
									.collect(Collectors.toList());
	}

	@GetMapping(value = "/my/calendar/result", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<CalendarResponse> getMyCalenderResponse(@LoginAccount Account account) {
		Calendar calendar = calendarService.find(account);

		return ResponseEntity.ok(CalendarResponse.builder()
												 .nickname(account.getNickname())
												 .tendency(account.getTendency())
												 .exams(convertExamInfos(calendar))
												 .calendar(convertMonthToDoInfo(calendar))
												 .build());
	}

	@GetMapping(value = "/calendar/{userId}/comments", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<CalendarResponse> getOtherCalendarResponse(@PathVariable("userId") Long userId) {
		Account account = accountService.findAccount(userId);
		Calendar calendar = calendarService.find(account);

		return ResponseEntity.ok(CalendarResponse.builder()
												 .nickname(account.getNickname())
												 .tendency(account.getTendency())
												 .exams(convertExamInfos(calendar))
												 .calendar(convertMonthToDoInfo(calendar))
												 .build());
	}
}
