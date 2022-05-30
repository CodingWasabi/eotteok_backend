package com.codingwasabi.howtodo.web.calendar;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calendar.entity.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
	Optional<Calendar> findByAccount(Account account);

	void deleteByAccount(Account account);

	boolean existsByAccount(Account account);

	@Query("select c from Calendar c where c.account.id=:accountId")
	Optional<Calendar> findByAccountId(Long accountId);
}
