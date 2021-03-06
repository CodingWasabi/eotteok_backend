package com.codingwasabi.howtodo.web.dailyplan;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codingwasabi.howtodo.web.calendar.entity.Calendar;
import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;

public interface DailyPlanRepository extends JpaRepository<DailyPlan, Long> {
	@Query("select dp from DailyPlan dp where dp.account.id=:accountId and dp.date=:date")
	Optional<DailyPlan> findByAccountIdAndDate(Long accountId, LocalDate date);

	void deleteAllByCalendar(Calendar calendar);
}
