package com.codingwasabi.howtodo.web.calendar;

import static com.codingwasabi.howtodo.web.calendar.utils.CalenderResponseConverter.*;
import static org.springframework.http.MediaType.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import com.codingwasabi.howtodo.web.exam.ExamService;
import com.codingwasabi.howtodo.web.exam.entity.Exam;
import com.codingwasabi.howtodo.web.policy.tendency.TendencyPolicy;
import com.sun.jdi.request.DuplicateRequestException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class CalendarController {
	private final CalendarService calendarService;
	private final AccountService accountService;
	private final ExamService examService;
	private final TendencyPolicy tendencyPolicy;
	@Value("${oauth.redirect-uri}")
	private String REDIRECT_URI;

	@PostMapping(value = "/calendar", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<CalendarResponse> createCalendar(@LoginAccount Account account,
														   @RequestBody CreateCalendarRequest createCalendarRequest) {
		List<Exam> exams = extractExams(account, createCalendarRequest);
		examService.insertColor(exams);

		if (!account.isAnonymous() && calendarService.alreadyExist(account)) {
			throw new DuplicateRequestException("user already has calendar date, need initialize");
		}

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
												 .accountId(account.getId())
												 .tendency(tendency)
												 .exams(convertExamInfos(calendar))
												 .calendar(convertMonthToDoInfo(calendar))
												 .build());
	}

	private List<Exam> extractExams(Account account, CreateCalendarRequest createCalendarRequest) {
		return createCalendarRequest.getExams()
									.stream()
									.map(dto -> Exam.builder()
													.dueDateTime(dto.getDate())
													.name(dto.getName())
													.studyDegree(extractStudyDegree(dto))
													.account(account)
													.build())
									.collect(Collectors.toList());
	}

	private int extractStudyDegree(CreateCalendarRequest.ExamInfo dto) {
		switch (dto.getPrepareTime()) {
			case 1:
				return 10;
			case 2:
				return 20;
			case 3:
				return 30;
			case 4:
				return 40;
			case 5:
				return 50;
			default:
				throw new IllegalArgumentException("studyDegree accept only 1~5");
		}
	}

	@GetMapping(value = "/my/calendar/result", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<CalendarResponse> getMyCalenderResponse(@LoginAccount Account account) {
		if (account.isAnonymous()) {
			throw new IllegalStateException("need authentication to retrieve self result");
		}
		Calendar calendar = calendarService.find(account);

		return ResponseEntity.ok(CalendarResponse.builder()
												 .nickname(account.getNickname())
												 .accountId(account.getId())
												 .tendency(account.getTendency())
												 .exams(convertExamInfos(calendar))
												 .calendar(convertMonthToDoInfo(calendar))
												 .build());
	}

	@GetMapping(value = "/calendar/{userId}/result", produces = APPLICATION_JSON_VALUE)
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

	@RequestMapping("/redirect")
	public ResponseEntity<Void> redirect(HttpServletRequest httpServletRequest) throws URISyntaxException {
		URI redirectUri = new URI(REDIRECT_URI);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(httpServletRequest.getCookies()[0].getName(), httpServletRequest.getCookies()[0].getValue());
		httpHeaders.setLocation(redirectUri);
		return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
	}
}
