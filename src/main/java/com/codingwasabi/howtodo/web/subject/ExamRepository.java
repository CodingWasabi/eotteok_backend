package com.codingwasabi.howtodo.web.subject;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingwasabi.howtodo.web.subject.entity.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {}
