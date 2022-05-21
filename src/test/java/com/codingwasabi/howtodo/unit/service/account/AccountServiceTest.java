package com.codingwasabi.howtodo.unit.service.account;

import static com.codingwasabi.howtodo.unit.utils.EntityFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codingwasabi.howtodo.unit.service.ServiceUnitTest;
import com.codingwasabi.howtodo.web.account.AccountService;
import com.codingwasabi.howtodo.web.account.AccountServiceImpl;
import com.codingwasabi.howtodo.web.account.entity.Account;

@DisplayName("Account, 비즈니스 로직 단위 테스트")
public class AccountServiceTest extends ServiceUnitTest {
	private AccountService accountService;

	@BeforeEach
	void init() {
		accountService = new AccountServiceImpl(accountRepository, calendarRepository);
	}

	@DisplayName("id로 account 찾기")
	@Test
	void findAccount_accountId_True() {
		// given
		willReturn(Optional.ofNullable(Account_생성())).given(accountRepository)
													 .findById(any());

		// when
		Account account = accountService.findAccount(1L);

		// then
		assertThat(account.getEmail()).isEqualTo("test@email.com");
	}

	@DisplayName("존재하지 않는 ID 로 account 찾기")
	@Test
	void findAccount_accountId_False() {
		// given
		willReturn(Optional.ofNullable(null)).given(accountRepository)
											 .findById(any());

		// when/then
		assertThatIllegalArgumentException().isThrownBy(() -> accountService.findAccount(1L))
											.withMessage("not valid accountId");

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
