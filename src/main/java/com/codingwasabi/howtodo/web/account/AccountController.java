package com.codingwasabi.howtodo.web.account;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingwasabi.howtodo.web.account.dto.GetMyProfileResponse;
import com.codingwasabi.howtodo.web.account.dto.PutMyProfileResponse;

@RestController
public class AccountController {

	@GetMapping("/my/profile")
	public GetMyProfileResponse getMyProfile() {
		return new GetMyProfileResponse(1L, "nickname");
	}
	
	@PutMapping("/my/profile")
	public PutMyProfileResponse putMyProfile() {
		return new PutMyProfileResponse(1L);
	}

}
