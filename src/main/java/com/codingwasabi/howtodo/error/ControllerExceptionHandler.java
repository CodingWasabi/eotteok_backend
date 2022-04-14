package com.codingwasabi.howtodo.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sun.jdi.request.DuplicateRequestException;

@RestControllerAdvice
public class ControllerExceptionHandler {
	@ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
	public ResponseEntity<String> handleIllegalException(RuntimeException e) {
		return ResponseEntity.badRequest()
							 .body(e.getMessage());
	}

	@ExceptionHandler(DuplicateRequestException.class)
	public ResponseEntity<String> handleDuplicateRequestException(DuplicateRequestException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
							 .body(e.getMessage());
	}
}
