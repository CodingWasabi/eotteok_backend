package com.codingwasabi.howtodo.web.calendar;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calendar.entity.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
	Optional<Calendar> findByAccount(Account account);

	void deleteByAccount(Account account);
}
