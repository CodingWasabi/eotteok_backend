package com.codingwasabi.howtodo.web.account;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingwasabi.howtodo.web.account.entity.Account;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
	private final AccountRepository accountRepository;

	@Override
	public Account findAccount(Long userId) {
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isValidNickname(String nickname) {
		return !accountRepository.existsByNickname(nickname);
	}
}
