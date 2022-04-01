package com.codingwasabi.howtodo.web.calendar;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calendar.entity.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
	Optional<Calendar> findByAccount(Account account);
}
