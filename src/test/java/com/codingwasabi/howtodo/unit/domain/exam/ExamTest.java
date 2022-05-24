package com.codingwasabi.howtodo.unit.domain.exam;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codingwasabi.howtodo.unit.utils.EntityFactory;
import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.web.exam.entity.Exam;

@DisplayName("Exam : Entity 단위 테스트")
public class ExamTest {

	private Account account;

	@BeforeEach
	void init() {
		account = EntityFactory.Account_생성();
	}

	@Test
	void 정상_builder_인스턴스생성() {
		// Given
		String name = "과목";
		LocalDateTime dueDateTime = LocalDateTime.now();
		int studyDegree = 3;

		// When
		Exam exam = Exam.builder()
						.name(name)
						.account(account)
						.dueDateTime(dueDateTime)
						.studyDegree(studyDegree)
						.build();

		// then
		assertThat(exam.getName()).isEqualTo(name);
		assertThat(exam.getAccount()).isEqualTo(account);
		assertThat(exam.getDueDateTime()).isEqualTo(dueDateTime);
		assertThat(exam.getStudyDegree()).isEqualTo(studyDegree);
	}

	@Test
	void 정상_getDDay_같은date_0() {
		// given
		LocalDateTime dueDateTime = LocalDateTime.of(2022, 4, 20, 14, 30);

		Exam exam = Exam.builder()
						.name("과목")
						.account(account)
						.dueDateTime(dueDateTime)
						.studyDegree(3)
						.build();

		// when
		int result = exam.getDDay(dueDateTime.toLocalDate());

		// then
		assertThat(result).isEqualTo(0);
	}

	@Test
	void 정상_getDDay_하루빠른date_1() {
		// given
		LocalDateTime dueDateTime = LocalDateTime.of(2022, 4, 20, 14, 30);

		Exam exam = Exam.builder()
						.name("과목")
						.account(account)
						.dueDateTime(dueDateTime)
						.studyDegree(3)
						.build();

		// when
		int result = exam.getDDay(dueDateTime.plus(-1, ChronoUnit.DAYS).toLocalDate());

		// then
		assertThat(result).isEqualTo(1);
	}

	@Test
	void 정상_getDDay_하루늦은date_minus1() {
		// given
		LocalDateTime dueDateTime = LocalDateTime.of(2022, 4, 20, 14, 30);

		Exam exam = Exam.builder()
						.name("과목")
						.account(account)
						.dueDateTime(dueDateTime)
						.studyDegree(3)
						.build();

		// when
		int result = exam.getDDay(dueDateTime.plus(1, ChronoUnit.DAYS).toLocalDate());

		// then
		assertThat(result).isEqualTo(-1);
	}

	@Test
	void 정상_isMid_dueDateTime의day가10인경우_true() {
		// given
		LocalDateTime dueDateTime = LocalDateTime.of(2022, 4, 10, 14, 30);

		Exam exam = Exam.builder()
						.name("과목")
						.account(account)
						.dueDateTime(dueDateTime)
						.studyDegree(3)
						.build();

		// when
		boolean result = exam.isMid();

		// then
		assertThat(result).isTrue();
	}

	@Test
	void 정상_isMid_dueDateTime의day가20인경우_true() {
		// given
		LocalDateTime dueDateTime = LocalDateTime.of(2022, 4, 20, 14, 30);

		Exam exam = Exam.builder()
						.name("과목")
						.account(account)
						.dueDateTime(dueDateTime)
						.studyDegree(3)
						.build();

		// when
		boolean result = exam.isMid();

		// then
		assertThat(result).isTrue();
	}

	@Test
	void 정상_isMid_dueDateTime의day가9인경우_false() {
		// given
		LocalDateTime dueDateTime = LocalDateTime.of(2022, 4, 9, 14, 30);

		Exam exam = Exam.builder()
						.name("과목")
						.account(account)
						.dueDateTime(dueDateTime)
						.studyDegree(3)
						.build();

		// when
		boolean result = exam.isMid();

		// then
		assertThat(result).isFalse();
	}

	@Test
	void 정상_isMid_dueDateTime의day가21인경우_false() {
		// given
		LocalDateTime dueDateTime = LocalDateTime.of(2022, 4, 21, 14, 30);

		Exam exam = Exam.builder()
						.name("과목")
						.account(account)
						.dueDateTime(dueDateTime)
						.studyDegree(3)
						.build();

		// when
		boolean result = exam.isMid();

		// then
		assertThat(result).isFalse();
	}

	@Test
	void 정상_isFront_dueDateTime의day가10인경우_false() {
		// given
		LocalDateTime dueDateTime = LocalDateTime.of(2022, 4, 10, 14, 30);

		Exam exam = Exam.builder()
						.name("과목")
						.account(account)
						.dueDateTime(dueDateTime)
						.studyDegree(3)
						.build();

		// when
		boolean result = exam.isFront();

		// then
		assertThat(result).isFalse();
	}

	@Test
	void 정상_isFront_dueDateTime의day가9인경우_true() {
		// given
		LocalDateTime dueDateTime = LocalDateTime.of(2022, 4, 9, 14, 30);

		Exam exam = Exam.builder()
						.name("과목")
						.account(account)
						.dueDateTime(dueDateTime)
						.studyDegree(3)
						.build();

		// when
		boolean result = exam.isFront();

		// then
		assertThat(result).isTrue();
	}

	@Test
	void 정상_isBack_dueDateTime의day가20인경우_false() {
		// given
		LocalDateTime dueDateTime = LocalDateTime.of(2022, 4, 20, 14, 30);

		Exam exam = Exam.builder()
						.name("과목")
						.account(account)
						.dueDateTime(dueDateTime)
						.studyDegree(3)
						.build();

		// when
		boolean result = exam.isBack();

		// then
		assertThat(result).isFalse();
	}

	@Test
	void 정상_isBack_dueDateTime의day가21인경우_true() {
		// given
		LocalDateTime dueDateTime = LocalDateTime.of(2022, 4, 21, 14, 30);

		Exam exam = Exam.builder()
						.name("과목")
						.account(account)
						.dueDateTime(dueDateTime)
						.studyDegree(3)
						.build();

		// when
		boolean result = exam.isBack();

		// then
		assertThat(result).isTrue();
	}
}
