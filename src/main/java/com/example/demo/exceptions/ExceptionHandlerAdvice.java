package com.example.demo.exceptions;

import static com.example.demo.utils.Utils.CHILE_TIME_ZONE;
import static com.example.demo.utils.Utils.TIME_FORMATTER;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {

		ErrorResponse errorResponse = ErrorResponse.builder()
				.errorName(ex.getClass().getSimpleName())
				.errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.errorMessage(ex.getMessage())
				.timestamp(getDateTime())
				.build();

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
		
	@ExceptionHandler(InvalidUserException.class)
	public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidUserException ex) {

		ErrorResponse errorResponse = ErrorResponse.builder()
				.errorName(ex.getClass().getSimpleName())
				.errorCode(HttpStatus.UNAUTHORIZED.value())
				.errorMessage(ex.getMessage())
				.timestamp(getDateTime())
				.build();

		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}
	
	public String getDateTime() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(CHILE_TIME_ZONE));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMATTER);
        return  zonedDateTime.format(formatter);
	}

}
