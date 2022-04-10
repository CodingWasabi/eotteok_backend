package com.codingwasabi.howtodo.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
	@ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
	public ResponseEntity<String> handleIllegalException(RuntimeException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
