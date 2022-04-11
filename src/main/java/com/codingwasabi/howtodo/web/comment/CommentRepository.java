package com.codingwasabi.howtodo.web.comment;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codingwasabi.howtodo.web.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	@Query("select c from Comment c where c.dailyPlan.calendar.account.id=:accountId and c.dailyPlan.date=:date")
	List<Comment> findCommentByAccountIdAndDate(Long accountId, LocalDate date);
}

