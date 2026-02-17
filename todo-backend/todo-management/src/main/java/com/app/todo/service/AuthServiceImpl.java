package com.app.todo.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.todo.dto.LoginDto;
import com.app.todo.dto.RegisterDto;
import com.app.todo.entity.Role;
import com.app.todo.entity.User;
import com.app.todo.exception.TodoAPIException;
import com.app.todo.repository.IRoleRepository;
import com.app.todo.repository.IUserRepository;


@Service
public class AuthServiceImpl implements IAuthService {

	
	private IUserRepository userRepo;
	
	private IRoleRepository roleRepo;
	
	private PasswordEncoder passwordEncoder;
	
	private AuthenticationManager authmanager;
	
	
	
	// CTOR based dependency Injection
	// We dont need to use @Autowired annotation because Spring will Automatically inject the dependency whenever it will find
	// the @Service Bean with only 1 CTOR
	public AuthServiceImpl(IUserRepository userRepo, IRoleRepository roleRepo, PasswordEncoder passwordEncoder,
			AuthenticationManager authmanager) {
		super();
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
		this.passwordEncoder = passwordEncoder;
		this.authmanager = authmanager;
	}


	@Override
	public String register(RegisterDto registerDto) {
		
		
		// check if user with userName alreayd exist in DB, if exist throw excpetion
		if(userRepo.existsByUserName(registerDto.getUserName()))
		{
			throw new TodoAPIException(HttpStatus.BAD_REQUEST, "UserName Alreay Exists in DB");
		}
		
		
		// check if user with given email alreayd exist in DB
		if(userRepo.existsByEmail(registerDto.getEmail()))
		{
			throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Email Alreay Exists in DB");
		}
		
		User user = new User();
		
		user.setName(registerDto.getName());
		user.setEmail(registerDto.getEmail());
		user.setUserName(registerDto.getUserName());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		
		
		Set<Role> roles = new HashSet<>();   // Even if there is only 1 role we still need a Set as the field in the User Pojo is for role is Set
		
		Role userRole = roleRepo.findByName("ROLE_USER");
		roles.add(userRole);
		
		
		user.setRoles(roles);
		
		userRepo.save(user);
		
		return "User Registered Successfully !";
	}




	@Override
	public String login(LoginDto loginDto) {
		
		Authentication authentication =  authmanager.authenticate(new UsernamePasswordAuthenticationToken(
				
				loginDto.getUserNameOrEmail(),
				loginDto.getPassword()
				
				));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
		return "User Logged In Successfully !";
	}
	
	

}


































