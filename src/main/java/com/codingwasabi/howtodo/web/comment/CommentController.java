package com.codingwasabi.howtodo.web.comment;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codingwasabi.howtodo.security.resolver.LoginAccount;
import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calendar.entity.Calendar;
import com.codingwasabi.howtodo.web.calendar.service.CalendarService;
import com.codingwasabi.howtodo.web.comment.dto.CreateCommentsRequest;
import com.codingwasabi.howtodo.web.comment.dto.GetCommentsByUserIdResponse;
import com.codingwasabi.howtodo.web.comment.entity.Comment;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;
	private final CalendarService calendarService;

	@GetMapping("/calendar/{accountId}/result/comments")
	public GetCommentsByUserIdResponse getCommentsByUserId(@PathVariable("accountId") Long accountId,
														   @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd")
															   LocalDate date) {
		Calendar calendar = calendarService.findByAccountId(accountId);

		return new GetCommentsByUserIdResponse(commentService.getCommentsByCalendarAndDate(calendar, date)
															 .stream()
															 .map(GetCommentsByUserIdResponse.CommentResponse::new)
															 .collect(Collectors.toList()));
	}

	@PostMapping("/calendar/{accountId}/result/comments/{targetDate}")
	public ResponseEntity<Void> createComments(@LoginAccount Account account,
											   @PathVariable("accountId") Long accountId,
											   @PathVariable("targetDate") @DateTimeFormat(pattern = "yyyy-MM-dd")
												   LocalDate date,
											   @RequestBody CreateCommentsRequest createCommentsRequest) {
		if (account.isAnonymous()) {
			throw new IllegalStateException("need authenticate before write comment");
		}

		Calendar targetCalendar = calendarService.findByAccountId(accountId);

		Comment comment = Comment.builder()
								 .account(account)
								 .calendar(targetCalendar)
								 .body(createCommentsRequest.getBody())
								 .date(date)
								 .profileNumber(createCommentsRequest.getProfileImageNumber())
								 .build();

		commentService.saveComment(targetCalendar, comment);

		return ResponseEntity.ok()
							 .build();
	}

}
