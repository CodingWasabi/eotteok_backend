package com.codingwasabi.howtodo.web.calender.service;

import java.util.List;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calender.entity.Calender;
import com.codingwasabi.howtodo.web.subject.entity.Subject;

public interface CalenderService {

	Calender create(Account account, int tendency, String nickname, List<Subject> subjects);

	Calender findMine(Account account);
}
