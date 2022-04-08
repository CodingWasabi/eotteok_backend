package com.codingwasabi.howtodo.web.exam;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.exam.entity.Exam;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ExamService {
	
	private final ExamRepository examRepository;
	
	public List<Exam> getMyExam(Account account) {
		return examRepository.findByAccount(account);
	}
	
	public void putExam(Account account, List<Exam> exam) {
		examRepository.deleteAll(examRepository.findByAccount(account));
		examRepository.saveAll(exam);
	}
}