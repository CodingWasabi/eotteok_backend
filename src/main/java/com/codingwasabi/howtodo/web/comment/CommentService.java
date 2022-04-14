package com.codingwasabi.howtodo.web.comment;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingwasabi.howtodo.web.calendar.entity.Calendar;
import com.codingwasabi.howtodo.web.comment.entity.Comment;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;

	public List<Comment> getCommentsByCalendarAndDate(Calendar calendar, LocalDate date) {
		return commentRepository.findCommentByCalendarAndDate(calendar, date);
	}

	@Transactional
	public void saveComment(Calendar calendar, Comment comment) {
		calendar.addComment(comment);
	}
}
