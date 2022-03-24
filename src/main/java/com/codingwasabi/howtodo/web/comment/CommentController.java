package com.codingwasabi.howtodo.web.comment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingwasabi.howtodo.web.comment.dto.CreateCommentsResponse;
import com.codingwasabi.howtodo.web.comment.dto.GetCommentsByUserIdResponse;

@RestController
public class CommentController {
	
	@GetMapping("/survey/{userId}/result/comments/{targetDate}")
	public GetCommentsByUserIdResponse getCommentsByUserId() {
		List<GetCommentsByUserIdResponse.CommentResponse> commentResponses = new ArrayList<>();
		commentResponses.add(new GetCommentsByUserIdResponse.CommentResponse(1, "nickname1", "body1"));
		commentResponses.add(new GetCommentsByUserIdResponse.CommentResponse(2, "nickname2", "body2"));
		commentResponses.add(new GetCommentsByUserIdResponse.CommentResponse(3, "nickname3", "body3"));
		commentResponses.add(new GetCommentsByUserIdResponse.CommentResponse(4, "nickname4", "body4"));
		
		return new GetCommentsByUserIdResponse(commentResponses);
	}
	
	@PostMapping("/survey/{userId}/result/comments/{targetDate}")
	public CreateCommentsResponse createComments() {
		return new CreateCommentsResponse(1L);
	}
	
}
