package com.codingwasabi.howtodo.web.calender;

import static com.codingwasabi.howtodo.web.calender.dto.CreateCalenderResponse.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingwasabi.howtodo.web.calender.dto.CreateCalenderResponse;
import com.codingwasabi.howtodo.web.calender.dto.GetCalenderByUserIdResponse;
import com.codingwasabi.howtodo.web.calender.dto.GetMyCalenderResponse;

@RestController
public class CalenderController {

	@PostMapping("/calender")
	public CreateCalenderResponse createCalender() {

		List<CreateCalenderResponse.DateInfoResponse> calendar = new ArrayList<>();

		List<CreateCalenderResponse.DateInfoResponse.SubjectResponse> subjects = new ArrayList<>();
		subjects.add(new CreateCalenderResponse.DateInfoResponse.SubjectResponse("데이터 분석 및 활용", 2));
		subjects.add(new CreateCalenderResponse.DateInfoResponse.SubjectResponse("컴파일러", 2));
		subjects.add(new CreateCalenderResponse.DateInfoResponse.SubjectResponse("디비", 2));
		calendar.add(new DateInfoResponse(LocalDate.of(2022, 3, 24), subjects));

		subjects = new ArrayList<>();
		subjects.add(new CreateCalenderResponse.DateInfoResponse.SubjectResponse("데이터 분석 및 활용", 2));
		subjects.add(new CreateCalenderResponse.DateInfoResponse.SubjectResponse("컴파일러", 2));
		subjects.add(new CreateCalenderResponse.DateInfoResponse.SubjectResponse("디비", 2));
		calendar.add(new DateInfoResponse(LocalDate.of(2022, 3, 25), subjects));

		return CreateCalenderResponse.builder()
									 .nickname("daehwan")
									 .tendency(4)
									 .calendar(calendar)
									 .build();
	}

	@GetMapping("/my/calender/result")
	public GetMyCalenderResponse getMyCalenderResponse() {

		List<GetMyCalenderResponse.DateInfoResponse> calendar = new ArrayList<>();

		List<GetMyCalenderResponse.DateInfoResponse.SubjectResponse> subjects = new ArrayList<>();
		subjects.add(new GetMyCalenderResponse.DateInfoResponse.SubjectResponse("데이터 분석 및 활용", 2));
		subjects.add(new GetMyCalenderResponse.DateInfoResponse.SubjectResponse("컴파일러", 2));
		subjects.add(new GetMyCalenderResponse.DateInfoResponse.SubjectResponse("디비", 2));
		calendar.add(new GetMyCalenderResponse.DateInfoResponse(LocalDate.of(2022, 3, 24), 4, subjects));

		subjects = new ArrayList<>();
		subjects.add(new GetMyCalenderResponse.DateInfoResponse.SubjectResponse("데이터 분석 및 활용", 2));
		subjects.add(new GetMyCalenderResponse.DateInfoResponse.SubjectResponse("컴파일러", 2));
		subjects.add(new GetMyCalenderResponse.DateInfoResponse.SubjectResponse("디비", 2));
		calendar.add(new GetMyCalenderResponse.DateInfoResponse(LocalDate.of(2022, 3, 25), 5, subjects));

		return GetMyCalenderResponse.builder()
									.tendency(3)
									.calendar(calendar)
									.build();
	}

	@GetMapping("/calender/{userId}/result")
	public GetCalenderByUserIdResponse getCalenderByUserIdResponse() {

		List<GetCalenderByUserIdResponse.DateInfoResponse> calendar = new ArrayList<>();

		List<GetCalenderByUserIdResponse.DateInfoResponse.SubjectResponse> subjects = new ArrayList<>();
		subjects.add(new GetCalenderByUserIdResponse.DateInfoResponse.SubjectResponse("데이터 분석 및 활용", 2));
		subjects.add(new GetCalenderByUserIdResponse.DateInfoResponse.SubjectResponse("컴파일러", 2));
		subjects.add(new GetCalenderByUserIdResponse.DateInfoResponse.SubjectResponse("디비", 2));
		calendar.add(new GetCalenderByUserIdResponse.DateInfoResponse(LocalDate.of(2022, 3, 24), 4, subjects));

		subjects = new ArrayList<>();
		subjects.add(new GetCalenderByUserIdResponse.DateInfoResponse.SubjectResponse("데이터 분석 및 활용", 2));
		subjects.add(new GetCalenderByUserIdResponse.DateInfoResponse.SubjectResponse("컴파일러", 2));
		subjects.add(new GetCalenderByUserIdResponse.DateInfoResponse.SubjectResponse("디비", 2));
		calendar.add(new GetCalenderByUserIdResponse.DateInfoResponse(LocalDate.of(2022, 3, 25), 5, subjects));

		return GetCalenderByUserIdResponse.builder()
										  .tendency(3)
										  .nickname("daehwan")
										  .calendar(calendar)
										  .build();
	}
}
