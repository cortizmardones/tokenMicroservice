package com.example.demo.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TokenResponseDTO;
import com.example.demo.dto.ValidTokenResponse;
import com.example.demo.service.TokenService;

@RestController
@RequestMapping("/v1/token")
public class TokenController {
	
	@Autowired
	private TokenService tokenService;

	@GetMapping("/getToken")
	public TokenResponseDTO getToken(@RequestParam String user, @RequestParam String pass) throws InterruptedException, ExecutionException {
		return tokenService.getToken(user, pass);
	}
	
	@GetMapping("/validToken")
	public ValidTokenResponse validToken(@RequestHeader("Authorization") String token) {
		return tokenService.validToken(token);
	}
	

}
