package com.codingwasabi.howtodo.web.comment;

import com.codingwasabi.howtodo.unit.utils.EntityFactory;
import com.codingwasabi.howtodo.web.account.AccountRepository;
import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calendar.CalendarRepository;
import com.codingwasabi.howtodo.web.calendar.entity.Calendar;
import com.codingwasabi.howtodo.web.comment.entity.Comment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Comment : Persistence 단위 테스트")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CalendarRepository calendarRepository;

    Account account;
    Calendar calendar;

    @BeforeAll
    void beforeAll() {
        account = accountRepository.save(EntityFactory.Account_생성());
        calendar = calendarRepository.save(EntityFactory.Calendar_생성(account));
    }

    @Test
    void 정상_findCommentByCalendarAndDate_같은Calendar와Date로조회_1개조회() {
        // Given
        LocalDate date = LocalDate.of(2022, 5, 20);

        Comment comment = commentRepository.save(Comment.builder()
                .account(account)
                .calendar(calendar)
                .body("테스트 댓글")
                .date(date)
                .profileNumber(3)
                .build());

        // When
        List<Comment> result = commentRepository.findCommentByCalendarAndDate(calendar, date);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result).containsExactly(comment);
    }

    @Test
    void 정상_findCommentByCalendarAndDate_없는Calendar와Date로조회_빈리스트() {
        // Given
        LocalDate date = LocalDate.of(2022, 5, 20);

        // When
        List<Comment> result = commentRepository.findCommentByCalendarAndDate(calendar, date);

        // Then
        assertThat(result).isEmpty();
    }

}