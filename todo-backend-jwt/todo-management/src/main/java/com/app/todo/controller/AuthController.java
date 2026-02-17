package com.app.todo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.todo.dto.JwtAuthResponse;
import com.app.todo.dto.LoginDto;
import com.app.todo.dto.RegisterDto;
import com.app.todo.service.IAuthService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	
	private IAuthService authService;

	// CTOR based dependency injection
	public AuthController(IAuthService authService) {
		super();
		this.authService = authService;
	}
	
	
	// Build register REST APi
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto)
	{
		String response =  authService.register(registerDto);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
		
	}
	
	
	// Build Login REST API
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto)
	{
		JwtAuthResponse jwtAuthResponse = authService.login(loginDto);
		
		
		return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
	}
	
	
	
	
}





