package com.codingwasabi.howtodo.web.dailyplan;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;

public interface DailyPlanRepository extends JpaRepository<DailyPlan, Long> {}
