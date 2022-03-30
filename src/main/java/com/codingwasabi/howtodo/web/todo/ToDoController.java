package com.codingwasabi.howtodo.web.todo;

import static com.codingwasabi.howtodo.web.todo.dto.GetMyToDoResponse.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingwasabi.howtodo.web.todo.dto.GetMyToDoResponse;
import com.codingwasabi.howtodo.web.todo.dto.PutMyToDoResponse;

@RestController
public class ToDoController {

	@GetMapping("/my/exams")
	public GetMyToDoResponse getMyExam() {
		List<SubjectResponse> subjectResponses = new ArrayList<>();
		subjectResponses.add(new SubjectResponse("인간컴퓨터상호작용", LocalDateTime.of(2022, 3, 24, 0, 0, 0), 3));
		subjectResponses.add(new SubjectResponse("데이터 분석 및 활용", LocalDateTime.of(2022, 3, 25, 0, 0, 0), 4));

		return new GetMyToDoResponse(subjectResponses);
	}

	@PutMapping("/my/exams")
	public PutMyToDoResponse putMyExam() {

		return new PutMyToDoResponse(4);
	}

}
