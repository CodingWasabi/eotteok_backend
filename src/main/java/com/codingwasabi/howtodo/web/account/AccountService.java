package com.codingwasabi.howtodo.web.account;

import com.codingwasabi.howtodo.web.account.entity.Account;

public interface AccountService {
	Account findAccount(Long userId);

	boolean isValidNickname(String nickname);

	void reset(Account account);
}
