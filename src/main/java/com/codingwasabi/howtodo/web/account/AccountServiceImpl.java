package com.codingwasabi.howtodo.web.account;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calendar.CalendarRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
	private final AccountRepository accountRepository;
	private final CalendarRepository calendarRepository;

	@Override
	public Account findAccount(Long accountId) {
		return accountRepository.findById(accountId)
								.orElseThrow(() -> new IllegalArgumentException("not valid accountId"));
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isValidNickname(String nickname) {
		return !accountRepository.existsByNickname(nickname);
	}

	@Override
	public void reset(Account account) {
		calendarRepository.findByAccount(account)
						  .ifPresent(c -> calendarRepository.deleteById(c.getId()));
	}
}
