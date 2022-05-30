package com.codingwasabi.howtodo.web.event.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String phoneNumber;
	private String img;

	public Event(String name, String phoneNumber, String img) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.img = img;
	}
}
