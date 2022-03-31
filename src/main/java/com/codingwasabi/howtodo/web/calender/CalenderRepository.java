package com.codingwasabi.howtodo.web.calender;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calender.entity.Calender;

public interface CalenderRepository extends JpaRepository<Calender, Long> {
	Optional<Calender> findByAccount(Account account);
}
