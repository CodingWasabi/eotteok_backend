package com.codingwasabi.howtodo.web.comment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.comment.entity.Comment;
import com.codingwasabi.howtodo.web.dailyplan.DailyPlanRepository;
import com.codingwasabi.howtodo.web.dailyplan.entity.DailyPlan;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CommentService {
	
	private final CommentRepository commentRepository;
	private final DailyPlanRepository dailyPlanRepository;
	
	public List<Comment> getCommentsByAccountIdAndDate(Long accountId, LocalDate date) {
		return commentRepository.findCommentByAccountIdAndDate(accountId, date);
	}
	
	@Transactional
	public void saveComment(Long accountId, LocalDate localDate, Comment comment) {
		DailyPlan dailyPlan = dailyPlanRepository.findByAccountIdAndDate(accountId, localDate)
												 .orElseThrow(() -> new IllegalArgumentException("account or date not found"));
		dailyPlan.addComment(comment);
	}
}
