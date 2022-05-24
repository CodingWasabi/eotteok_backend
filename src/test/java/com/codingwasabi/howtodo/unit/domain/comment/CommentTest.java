package com.codingwasabi.howtodo.unit.domain.comment;

import static com.codingwasabi.howtodo.unit.utils.EntityFactory.*;
import static org.assertj.core.api.Assertions.*;

import com.codingwasabi.howtodo.web.calendar.entity.Calendar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codingwasabi.howtodo.unit.utils.EntityFactory;
import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.comment.entity.Comment;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@DisplayName("Comment : Entity 단위 테스트")
public class CommentTest {

    @Test
    void 정상_builder_인스턴스생성() {
        // given
        Account account = Account_생성();
        Calendar calendar = new Calendar(account);
        String body = "테스트 댓글";
		LocalDate date = LocalDate.of(2022, 5, 20);
        int profileNumber = 3;

        // when
        Comment comment = Comment.builder()
                .account(account)
                .calendar(calendar)
                .body(body)
				.date(date)
                .profileNumber(profileNumber)
                .build();

        // then
        assertThat(comment.getAccount()).isEqualTo(account);
		assertThat(comment.getCalendar()).isEqualTo(calendar);
		assertThat(comment.getDate()).isEqualTo(date);
        assertThat(comment.getBody()).isEqualTo(body);
        assertThat(comment.getProfileNumber()).isEqualTo(profileNumber);
    }

    @Test
    void 정상_isDate_같은date_true() {
        // Given
        Account account = Account_생성();
        Calendar calendar = new Calendar(account);
        String body = "테스트 댓글";
        LocalDate date = LocalDate.of(2022, 5, 20);
        int profileNumber = 3;

        Comment comment = Comment.builder()
                .account(account)
                .calendar(calendar)
                .body(body)
                .date(date)
                .profileNumber(profileNumber)
                .build();

        // When
        boolean result = comment.isDate(date);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void 정상_isDate_같은date_false() {
        // Given
        Account account = Account_생성();
        Calendar calendar = new Calendar(account);
        String body = "테스트 댓글";
        LocalDate date = LocalDate.of(2022, 5, 20);
        int profileNumber = 3;

        Comment comment = Comment.builder()
                .account(account)
                .calendar(calendar)
                .body(body)
                .date(date)
                .profileNumber(profileNumber)
                .build();

        // When
        boolean result = comment.isDate(date.plus(1, ChronoUnit.DAYS));

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void 정상_isMonth_같은month_true() {
        // Given
        Account account = Account_생성();
        Calendar calendar = new Calendar(account);
        String body = "테스트 댓글";
        LocalDate date = LocalDate.of(2022, 5, 20);
        int profileNumber = 3;

        Comment comment = Comment.builder()
                .account(account)
                .calendar(calendar)
                .body(body)
                .date(date)
                .profileNumber(profileNumber)
                .build();

        // When
        boolean result = comment.isMonth(date.getMonthValue());

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void 정상_isMonth_다른month_false() {
        // Given
        Account account = Account_생성();
        Calendar calendar = new Calendar(account);
        String body = "테스트 댓글";
        LocalDate date = LocalDate.of(2022, 5, 20);
        int profileNumber = 3;

        Comment comment = Comment.builder()
                .account(account)
                .calendar(calendar)
                .body(body)
                .date(date)
                .profileNumber(profileNumber)
                .build();

        // When
        boolean result = comment.isMonth(date.plus(1, ChronoUnit.MONTHS).getMonthValue());

        // Then
        assertThat(result).isFalse();
    }
}
