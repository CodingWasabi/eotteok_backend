package com.codingwasabi.howtodo.web.calender;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingwasabi.howtodo.web.calender.entity.Calender;

public interface CalenderRepository extends JpaRepository<Calender, Long> {}
