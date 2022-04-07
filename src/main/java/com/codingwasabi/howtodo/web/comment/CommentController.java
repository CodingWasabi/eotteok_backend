package com.codingwasabi.howtodo.web.comment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codingwasabi.howtodo.security.oauth2.OAuth2UserAdapter;
import com.codingwasabi.howtodo.security.resolver.LoginAccount;
import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.comment.dto.CreateCommentsRequest;
import com.codingwasabi.howtodo.web.comment.dto.CreateCommentsResponse;
import com.codingwasabi.howtodo.web.comment.dto.GetCommentsByUserIdResponse;
import com.codingwasabi.howtodo.web.comment.entity.Comment;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CommentController {
	
	private final CommentService commentService;
	
	@GetMapping("/survey/{userId}/result/comments/{targetDate}")
	public GetCommentsByUserIdResponse getCommentsByUserId(@PathVariable("accountId") Long accountId,
														   @PathVariable("targetDate") LocalDate targetDate) {
		
		return new GetCommentsByUserIdResponse(commentService.getCommentsByAccountIdAndDate(accountId, targetDate)
															 .stream()
															 .map(GetCommentsByUserIdResponse.CommentResponse::new)
															 .collect(Collectors.toList()));
	}
	
	@PostMapping("/survey/{accountId}/result/comments/{targetDate}")
	public void createComments(@LoginAccount Account account,
							   @PathVariable("accountId") Long accountId,
							   @PathVariable("targetDate") LocalDate targetDate,
							   @RequestBody CreateCommentsRequest createCommentsRequest) {
		Comment comment = Comment.builder()
								 .account(account)
								 .body(createCommentsRequest.getBody())
								 .profileNumber(createCommentsRequest.getProfileImageNumber())
								 .build();
		
		commentService.saveComment(accountId, targetDate, comment);
	}
	
}
