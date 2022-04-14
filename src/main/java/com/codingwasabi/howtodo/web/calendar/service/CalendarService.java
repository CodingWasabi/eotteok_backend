package com.codingwasabi.howtodo.web.calendar.service;

import java.util.List;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calendar.entity.Calendar;
import com.codingwasabi.howtodo.web.exam.entity.Exam;

public interface CalendarService {

	Calendar create(Account account, int tendency, String nickname, int dailyQuota,List<Exam> exams);

	Calendar find(Account account);

	boolean alreadyExist(Account account);

	Calendar findByAccountId(Long accountId);
}
