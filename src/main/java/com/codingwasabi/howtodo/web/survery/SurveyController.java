package com.codingwasabi.howtodo.web.survery;

import static com.codingwasabi.howtodo.web.survery.dto.CreateSurveyResponse.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingwasabi.howtodo.web.survery.dto.CreateSurveyResponse;
import com.codingwasabi.howtodo.web.survery.dto.GetMySurveyResponse;
import com.codingwasabi.howtodo.web.survery.dto.GetSurveyByUserIdResponse;

@RestController
public class SurveyController {
	
	@PostMapping("/survey")
	public CreateSurveyResponse createSurvey() {
		
		List<CreateSurveyResponse.DateInfoResponse> calendar = new ArrayList<>();
		
		List<CreateSurveyResponse.DateInfoResponse.SubjectResponse> subjects = new ArrayList<>();
		subjects.add(new CreateSurveyResponse.DateInfoResponse.SubjectResponse("데이터 분석 및 활용", 2));
		subjects.add(new CreateSurveyResponse.DateInfoResponse.SubjectResponse("컴파일러", 2));
		subjects.add(new CreateSurveyResponse.DateInfoResponse.SubjectResponse("디비", 2));
		calendar.add(new DateInfoResponse(LocalDate.of(2022, 3, 24), subjects));
		
		subjects = new ArrayList<>();
		subjects.add(new CreateSurveyResponse.DateInfoResponse.SubjectResponse("데이터 분석 및 활용", 2));
		subjects.add(new CreateSurveyResponse.DateInfoResponse.SubjectResponse("컴파일러", 2));
		subjects.add(new CreateSurveyResponse.DateInfoResponse.SubjectResponse("디비", 2));
		calendar.add(new DateInfoResponse(LocalDate.of(2022, 3, 25), subjects));
		
		return CreateSurveyResponse.builder()
								   .nickname("daehwan")
								   .tendency(4)
								   .calendar(calendar)
								   .build();
	}
	
	@GetMapping("/my/survey/result")
	public GetMySurveyResponse getMySurveyResponse() {
		
		List<GetMySurveyResponse.DateInfoResponse> calendar = new ArrayList<>();
		
		List<GetMySurveyResponse.DateInfoResponse.SubjectResponse> subjects = new ArrayList<>();
		subjects.add(new GetMySurveyResponse.DateInfoResponse.SubjectResponse("데이터 분석 및 활용", 2));
		subjects.add(new GetMySurveyResponse.DateInfoResponse.SubjectResponse("컴파일러", 2));
		subjects.add(new GetMySurveyResponse.DateInfoResponse.SubjectResponse("디비", 2));
		calendar.add(new GetMySurveyResponse.DateInfoResponse(LocalDate.of(2022, 3, 24), 4, subjects));
		
		subjects = new ArrayList<>();
		subjects.add(new GetMySurveyResponse.DateInfoResponse.SubjectResponse("데이터 분석 및 활용", 2));
		subjects.add(new GetMySurveyResponse.DateInfoResponse.SubjectResponse("컴파일러", 2));
		subjects.add(new GetMySurveyResponse.DateInfoResponse.SubjectResponse("디비", 2));
		calendar.add(new GetMySurveyResponse.DateInfoResponse(LocalDate.of(2022, 3, 25), 5, subjects));
		
		return GetMySurveyResponse.builder()
								  .tendency(3)
								  .calendar(calendar)
								  .build();
	}
	
	@GetMapping("/survey/{userId}/result")
	public GetSurveyByUserIdResponse getSurveyByUserIdResponse() {
		
		List<GetSurveyByUserIdResponse.DateInfoResponse> calendar = new ArrayList<>();
		
		List<GetSurveyByUserIdResponse.DateInfoResponse.SubjectResponse> subjects = new ArrayList<>();
		subjects.add(new GetSurveyByUserIdResponse.DateInfoResponse.SubjectResponse("데이터 분석 및 활용", 2));
		subjects.add(new GetSurveyByUserIdResponse.DateInfoResponse.SubjectResponse("컴파일러", 2));
		subjects.add(new GetSurveyByUserIdResponse.DateInfoResponse.SubjectResponse("디비", 2));
		calendar.add(new GetSurveyByUserIdResponse.DateInfoResponse(LocalDate.of(2022, 3, 24), 4, subjects));
		
		subjects = new ArrayList<>();
		subjects.add(new GetSurveyByUserIdResponse.DateInfoResponse.SubjectResponse("데이터 분석 및 활용", 2));
		subjects.add(new GetSurveyByUserIdResponse.DateInfoResponse.SubjectResponse("컴파일러", 2));
		subjects.add(new GetSurveyByUserIdResponse.DateInfoResponse.SubjectResponse("디비", 2));
		calendar.add(new GetSurveyByUserIdResponse.DateInfoResponse(LocalDate.of(2022, 3, 25), 5, subjects));
		
		return GetSurveyByUserIdResponse.builder()
										.tendency(3)
										.nickname("daehwan")
										.calendar(calendar)
										.build();
	}
}
