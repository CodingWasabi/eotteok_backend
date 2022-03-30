package com.codingwasabi.howtodo.web.plan;

import static com.codingwasabi.howtodo.web.plan.dto.CreatePlanResponse.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingwasabi.howtodo.web.plan.dto.CreatePlanResponse;
import com.codingwasabi.howtodo.web.plan.dto.GetMyPlanResponse;
import com.codingwasabi.howtodo.web.plan.dto.GetPlanByUserIdResponse;

@RestController
public class PlanController {

	@PostMapping("/plan")
	public CreatePlanResponse createPlan() {

		List<CreatePlanResponse.DateInfoResponse> calendar = new ArrayList<>();

		List<CreatePlanResponse.DateInfoResponse.SubjectResponse> subjects = new ArrayList<>();
		subjects.add(new CreatePlanResponse.DateInfoResponse.SubjectResponse("데이터 분석 및 활용", 2));
		subjects.add(new CreatePlanResponse.DateInfoResponse.SubjectResponse("컴파일러", 2));
		subjects.add(new CreatePlanResponse.DateInfoResponse.SubjectResponse("디비", 2));
		calendar.add(new DateInfoResponse(LocalDate.of(2022, 3, 24), subjects));

		subjects = new ArrayList<>();
		subjects.add(new CreatePlanResponse.DateInfoResponse.SubjectResponse("데이터 분석 및 활용", 2));
		subjects.add(new CreatePlanResponse.DateInfoResponse.SubjectResponse("컴파일러", 2));
		subjects.add(new CreatePlanResponse.DateInfoResponse.SubjectResponse("디비", 2));
		calendar.add(new DateInfoResponse(LocalDate.of(2022, 3, 25), subjects));

		return CreatePlanResponse.builder()
								 .nickname("daehwan")
								 .tendency(4)
								 .calendar(calendar)
								 .build();
	}

	@GetMapping("/my/plan/result")
	public GetMyPlanResponse getMyPlanResponse() {

		List<GetMyPlanResponse.DateInfoResponse> calendar = new ArrayList<>();

		List<GetMyPlanResponse.DateInfoResponse.SubjectResponse> subjects = new ArrayList<>();
		subjects.add(new GetMyPlanResponse.DateInfoResponse.SubjectResponse("데이터 분석 및 활용", 2));
		subjects.add(new GetMyPlanResponse.DateInfoResponse.SubjectResponse("컴파일러", 2));
		subjects.add(new GetMyPlanResponse.DateInfoResponse.SubjectResponse("디비", 2));
		calendar.add(new GetMyPlanResponse.DateInfoResponse(LocalDate.of(2022, 3, 24), 4, subjects));

		subjects = new ArrayList<>();
		subjects.add(new GetMyPlanResponse.DateInfoResponse.SubjectResponse("데이터 분석 및 활용", 2));
		subjects.add(new GetMyPlanResponse.DateInfoResponse.SubjectResponse("컴파일러", 2));
		subjects.add(new GetMyPlanResponse.DateInfoResponse.SubjectResponse("디비", 2));
		calendar.add(new GetMyPlanResponse.DateInfoResponse(LocalDate.of(2022, 3, 25), 5, subjects));

		return GetMyPlanResponse.builder()
								.tendency(3)
								.calendar(calendar)
								.build();
	}

	@GetMapping("/plan/{userId}/result")
	public GetPlanByUserIdResponse getPlanByUserIdResponse() {

		List<GetPlanByUserIdResponse.DateInfoResponse> calendar = new ArrayList<>();

		List<GetPlanByUserIdResponse.DateInfoResponse.SubjectResponse> subjects = new ArrayList<>();
		subjects.add(new GetPlanByUserIdResponse.DateInfoResponse.SubjectResponse("데이터 분석 및 활용", 2));
		subjects.add(new GetPlanByUserIdResponse.DateInfoResponse.SubjectResponse("컴파일러", 2));
		subjects.add(new GetPlanByUserIdResponse.DateInfoResponse.SubjectResponse("디비", 2));
		calendar.add(new GetPlanByUserIdResponse.DateInfoResponse(LocalDate.of(2022, 3, 24), 4, subjects));

		subjects = new ArrayList<>();
		subjects.add(new GetPlanByUserIdResponse.DateInfoResponse.SubjectResponse("데이터 분석 및 활용", 2));
		subjects.add(new GetPlanByUserIdResponse.DateInfoResponse.SubjectResponse("컴파일러", 2));
		subjects.add(new GetPlanByUserIdResponse.DateInfoResponse.SubjectResponse("디비", 2));
		calendar.add(new GetPlanByUserIdResponse.DateInfoResponse(LocalDate.of(2022, 3, 25), 5, subjects));

		return GetPlanByUserIdResponse.builder()
									  .tendency(3)
									  .nickname("daehwan")
									  .calendar(calendar)
									  .build();
	}
}
