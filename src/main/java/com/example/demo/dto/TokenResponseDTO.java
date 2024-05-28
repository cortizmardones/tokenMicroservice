package com.example.demo.dto;

import lombok.Builder;

@Builder
public record TokenResponseDTO(
		
		String subject,
		String token
		
		) {

}
