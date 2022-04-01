package com.codingwasabi.howtodo.unit.service.account;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codingwasabi.howtodo.unit.service.ServiceUnitTest;
import com.codingwasabi.howtodo.web.account.AccountService;
import com.codingwasabi.howtodo.web.account.AccountServiceImpl;

@DisplayName("Account, 비즈니스 로직 단위 테스트")
public class AccountServiceTest extends ServiceUnitTest {
	private AccountService accountService;

	@BeforeEach
	void init() {
		accountService = new AccountServiceImpl(accountRepository);
	}

	@DisplayName("중복없는 닉네임 검증")
	@Test
	void isValidNickname_nickname_True() {
		// given
		willReturn(false).given(accountRepository)
						 .existsByNickname(any());
		
		// when
		boolean validateResult = accountService.isValidNickname("daehwan2yo");

		// then
		assertThat(validateResult).isTrue();
	}

	@DisplayName("중복있는 닉네임 검증")
	@Test
	void isValidNickname_nickname_False() {
		// given
		willReturn(true).given(accountRepository)
						.existsByNickname(any());

		// when
		boolean validateResult = accountService.isValidNickname("daehwan2yo");

		// then
		assertThat(validateResult).isFalse();
	}
}
