package com.codingwasabi.howtodo.web.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentsRequest {
	private int profileImageNumber;
	private String body;
}
