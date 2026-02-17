package com.app.todo.service;

import com.app.todo.dto.JwtAuthResponse;
import com.app.todo.dto.LoginDto;
import com.app.todo.dto.RegisterDto;

public interface IAuthService 
{
	String register(RegisterDto registerDto);
	
	
	JwtAuthResponse login(LoginDto loginDto);
}
