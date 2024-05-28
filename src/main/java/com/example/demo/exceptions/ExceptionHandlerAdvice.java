package com.example.demo.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {

		ErrorResponse errorResponse = ErrorResponse.builder()
				.errorName("Exception")
				.errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.errorMessage(ex.getMessage())
				.timestamp(LocalDateTime.now())
				.build();

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
		
	@ExceptionHandler(InvalidUserException.class)
	public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidUserException ex) {

		ErrorResponse errorResponse = ErrorResponse.builder()
				.errorName("InvalidUserException")
				.errorCode(HttpStatus.UNAUTHORIZED.value())
				.errorMessage(ex.getMessage())
				.timestamp(LocalDateTime.now())
				.build();

		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}

}
