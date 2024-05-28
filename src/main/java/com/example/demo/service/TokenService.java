package com.example.demo.service;

import java.util.concurrent.ExecutionException;

import com.example.demo.dto.TokenResponseDTO;
import com.example.demo.dto.ValidTokenResponse;

public interface TokenService {
	
	TokenResponseDTO getToken(String user, String pass) throws InterruptedException, ExecutionException;
	ValidTokenResponse validToken(String token);

}
