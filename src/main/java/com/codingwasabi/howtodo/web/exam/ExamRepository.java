package com.codingwasabi.howtodo.web.exam;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingwasabi.howtodo.web.exam.entity.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {}
