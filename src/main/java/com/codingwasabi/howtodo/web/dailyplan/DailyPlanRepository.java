package com.codingwasabi.howtodo.web.dailyplan;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codingwasabi.howtodo.web.calendar.entity.Calendar;
import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;

public interface DailyPlanRepository extends JpaRepository<DailyPlan, Long> {
	@Query("select dp from DailyPlan dp where dp.account=:accountId and dp.date=:date")
	Optional<DailyPlan> findByAccountIdAndDate(@Param("accountId") long accountId, @Param("date") LocalDate localDate);
}
