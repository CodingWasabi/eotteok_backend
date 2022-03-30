package com.codingwasabi.howtodo.web.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Authority {
	USER("ROLE_USER"), ADMIN("ROLE_ADMIN"), ANONYMOUS("ROLE_ANONYMOUS");

	private String role;
}
