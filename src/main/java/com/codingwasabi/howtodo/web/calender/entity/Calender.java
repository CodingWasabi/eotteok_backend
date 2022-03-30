package com.codingwasabi.howtodo.web.calender.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Calender {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "calender", cascade = CascadeType.ALL)
	private List<DailyPlan> dailyPlans = new ArrayList<>();
}
