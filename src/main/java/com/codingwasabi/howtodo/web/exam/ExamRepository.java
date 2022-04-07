package com.codingwasabi.howtodo.web.exam;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.exam.entity.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {

	List<Exam> findByAccount(Account account);
}
