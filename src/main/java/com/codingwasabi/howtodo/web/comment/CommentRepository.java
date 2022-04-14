package com.codingwasabi.howtodo.web.comment;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingwasabi.howtodo.web.calendar.entity.Calendar;
import com.codingwasabi.howtodo.web.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findCommentByCalendarAndDate(Calendar calendar, LocalDate date);
}

