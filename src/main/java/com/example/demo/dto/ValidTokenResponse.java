package com.example.demo.dto;

import lombok.Builder;

@Builder
public record ValidTokenResponse(
		
		String tokenType,
		boolean isValid,
		String errorMessage
		
		) {

}
