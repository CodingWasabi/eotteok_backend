package com.codingwasabi.howtodo.web.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingwasabi.howtodo.web.account.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findByEmail(String email);

	boolean existsByNickname(String nickname);
}
