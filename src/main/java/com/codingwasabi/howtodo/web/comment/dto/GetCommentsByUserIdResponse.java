package com.codingwasabi.howtodo.web.comment.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentsByUserIdResponse {

	List<CommentResponse> comments;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class CommentResponse {
		private int profileNumber;
		private String nickname;
		private String body;
	}

}