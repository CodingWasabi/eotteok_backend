package com.codingwasabi.howtodo.web.account;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codingwasabi.howtodo.security.resolver.LoginAccount;
import com.codingwasabi.howtodo.web.account.entity.Account;

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

	// 배포 테스트 용 API
	@GetMapping("/me")
	ResponseEntity<Long> get(@LoginAccount Account account) {
		if (account.isAnonymous()) {
			return ResponseEntity.status(UNAUTHORIZED)
								 .build();
		}
		return ResponseEntity.ok(account.getId());
	}

	@DeleteMapping("/me")
	ResponseEntity<Void> reset(@LoginAccount Account account) {
		accountService.reset(account);
		return ResponseEntity.noContent()
							 .build();
	}
}
