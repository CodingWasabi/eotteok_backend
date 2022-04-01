package com.codingwasabi.howtodo.web.calender.service;

import java.util.List;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calender.entity.Calender;
import com.codingwasabi.howtodo.web.exam.entity.Exam;

public interface CalenderService {

	Calender create(Account account, int tendency, String nickname, List<Exam> exams);

	Calender findMine(Account account);
}
