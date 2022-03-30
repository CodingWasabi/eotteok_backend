package com.codingwasabi.howtodo.unit.domain.utils;

import com.codingwasabi.howtodo.web.account.entity.Account;

public class EntityFactory {
	public static Account Account_생성() {
		return Account.builder()
					  .ageRange("20~29")
					  .birthday("1997129")
					  .email("test@email.com")
					  .gender("mail")
					  .oauthId(123123L)
					  .provider("kakao")
					  .build();
	}
}
