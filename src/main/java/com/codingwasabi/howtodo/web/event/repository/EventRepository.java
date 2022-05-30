package com.codingwasabi.howtodo.web.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingwasabi.howtodo.web.event.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
	boolean existsByPhoneNumber(String phoneNumber);
}
