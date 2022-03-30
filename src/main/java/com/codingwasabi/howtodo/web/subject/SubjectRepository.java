package com.codingwasabi.howtodo.web.subject;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingwasabi.howtodo.web.subject.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {}
