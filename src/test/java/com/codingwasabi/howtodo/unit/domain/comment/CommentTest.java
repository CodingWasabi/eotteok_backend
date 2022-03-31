package com.codingwasabi.howtodo.unit.domain.comment;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codingwasabi.howtodo.unit.utils.EntityFactory;
import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.comment.entity.Comment;

@DisplayName("Comment, 도메인 단위 테스트")
public class CommentTest {
	@DisplayName("Comment 인스턴스 생성")
	@Test
	void Comment_생성() {
		// given
		Account account = EntityFactory.Account_생성();

		// when
		Comment comment = Comment.builder()
								 .account(account)
								 .body("테스트 댓글")
								 .profileNumber(3)
								 .build();

		// then
		assertThat(comment.getBody()).isEqualTo("테스트 댓글");
	}
}
