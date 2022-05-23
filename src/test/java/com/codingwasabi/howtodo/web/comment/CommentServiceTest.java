package com.codingwasabi.howtodo.web.comment;

import com.codingwasabi.howtodo.unit.utils.EntityFactory;
import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.calendar.CalendarRepository;
import com.codingwasabi.howtodo.web.calendar.entity.Calendar;
import com.codingwasabi.howtodo.web.comment.entity.Comment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentService commentService;

    @Test
    void 정상_getCommentsByCalendarAndDate_하나도없을때_Empty() {
        // Given
        Account account = EntityFactory.Account_생성();
        Calendar calendar = EntityFactory.Calendar_생성(account);
        LocalDate localDate = LocalDate.now();

        when(commentRepository.findCommentByCalendarAndDate(calendar, localDate))
                        .thenReturn(List.of());

        // When
        List<Comment> result = commentService.getCommentsByCalendarAndDate(calendar, localDate);

        // Then
        assertThat(result).hasSize(0);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10})
    void 정상_getCommentsByCalendarAndDate_여러개존재(int count) {
        // Given
        Account account = EntityFactory.Account_생성();
        Calendar calendar = EntityFactory.Calendar_생성(account);
        LocalDate localDate = LocalDate.now();

        List<Comment> list = new ArrayList<>();
        for(int i=0;i<count;i++) {
            list.add(new Comment());
        }

        when(commentRepository.findCommentByCalendarAndDate(calendar, localDate))
                        .thenReturn(list);

        // When
        List<Comment> result = commentService.getCommentsByCalendarAndDate(calendar, localDate);

        // Then
        assertThat(result).hasSize(count);
    }

    @Test
    void 정상_saveComment_comment저장() {
        // Given
        Account account = EntityFactory.Account_생성();
        Calendar calendar = EntityFactory.Calendar_생성(account);
        Comment comment = new Comment();

        // When
        commentService.saveComment(calendar, comment);

        // Then
        assertThat(calendar.getComments()).containsExactly(comment);
    }


}