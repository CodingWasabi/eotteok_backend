package com.codingwasabi.howtodo.web.account;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AccountController {
	private final AccountService accountService;

	@GetMapping("/validation/nickname")
	ResponseEntity<Void> getValidationNickname(@RequestParam("value") String nickname) {
		if (accountService.isValidNickname(nickname)) {
			return ResponseEntity.status(OK)
								 .build();
		}

		return ResponseEntity.status(CONFLICT)
							 .build();
	}
}
