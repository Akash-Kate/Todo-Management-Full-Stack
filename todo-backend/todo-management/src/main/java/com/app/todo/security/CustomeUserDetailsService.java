package com.app.todo.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.todo.entity.User;
import com.app.todo.exception.ResourceNotFoundException;
import com.app.todo.repository.IUserRepository;


@Service     // We will configure this CustomeUserDetailsService in Spring security, Spring security will call this method to get user object from the DB
public class CustomeUserDetailsService implements UserDetailsService {

	
	private IUserRepository userRepo;
	
	
	
	// CTOR based depdency injection , this CTOR will be called by spring IOC container
	public CustomeUserDetailsService(IUserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}



	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		
		// Retriving user from DB by using userName or Email 
		User user = userRepo.findByUserNameOrEmail(usernameOrEmail, usernameOrEmail)
				.orElseThrow(() -> new UsernameNotFoundException("User not Exist by username or Email"));
		
		
		// user has set or roles , convert set of roles into set of granted authorities
		// Spring security basically expects this Set of GrantedAuthority, thats why we have converted Set of roles into Set of Granted Authorities
		Set<GrantedAuthority> authorities = user.getRoles().stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
		
		return new org.springframework.security.core.userdetails.User(
				
				usernameOrEmail,
				user.getPassword(),
				authorities
				
				);
	}

}















