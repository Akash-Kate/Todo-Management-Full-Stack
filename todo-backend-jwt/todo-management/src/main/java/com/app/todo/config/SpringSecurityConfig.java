package com.app.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.todo.security.JwtAuthenticationEntryPoint;
import com.app.todo.security.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity // In order to enable Method Level Security annotation
public class SpringSecurityConfig 
{
	
	
	private UserDetailsService userDetailsService;   // In order to achieve loose coupling we are using an Interface
	
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	
	private JwtAuthenticationFilter authenticationFilter;
	
	
	// CTOR based dependency Injection
	public SpringSecurityConfig(UserDetailsService userDetailsService,
			JwtAuthenticationEntryPoint authenticationEntryPoint, JwtAuthenticationFilter authenticationFilter) {
		super();
		this.userDetailsService = userDetailsService;
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.authenticationFilter = authenticationFilter;
	}


	@Bean
	public static PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();   // ByCryptPasswordEncoder is the implementaion of PasswordEncoder Interface
	}


	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception  {
		
		http.csrf((csrf) -> csrf.disable().authorizeHttpRequests((authorize) -> {
			
//			authorize.requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN");  // All the Hppt POST request that starts with url /api/** should be accessible by all users who have ADMIN role
//			authorize.requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN");
//			authorize.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN");
//			
//			authorize.requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "USER");
//			
//			authorize.requestMatchers(HttpMethod.PATCH, "/api/**").hasAnyRole("ADMIN", "USER");
			
//			authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll();  // Publically exposed all the GET Todos Rest APIs
			
//         *** We can alos do Method Level Security instead of configuring security like above we can configure security at Method level ***
			
// 		   *** In Real Time Projects Most of the Developers prefer the Method Level Spring Security ***		
			
			authorize.requestMatchers("/api/auth/**").permitAll();   // All urls like registering a new user etc shall be accessible to anyone
			
			authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
			
			
			authorize.anyRequest().authenticated();  // Any request must be authenticated
		})/*.httpBasic(Customizer.withDefaults())*/);   // Commenting this httpBasic otherwise without JWT token basic auth will also work
		
		// We have configured Spring Security such a way that we have only enabled HTTP BASIC AUTH
		// When we do this we will get a pop when we try to access the REST APIs 
		// In Form based authentication we were getting a login form by default from spring security
		
		// Whenever we send a REST api request from Postman and give username and password in Headers section
		// Postman will combine the username and password convert it in Base64 and pass it in Headers section as key value pairs
		// Same goes for browsers
		
		// This Basic Auth is not recommened for production becuase anybody can decode your username and password by decoding Base64 based text from the Headers
		
		// Most Devs use JWT Token based Authentication
		
		
		http.exceptionHandling( exception -> exception.authenticationEntryPoint(authenticationEntryPoint));   
		//  Whenever un-authenticate user try to access the resourse then spring security throws authentication exception
		// Then this class -> JwtAuthenticationEntryPoint will catch that exception and return error respose to client
		
		
		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class); // This JWT Filter should run before the Spring Security Filter , so writing this line of code
		return http.build();
	}
	
	
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)  throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	
	
	
	
	
	
	
	
// Below commented method userDetailsService is for In Memory Authentication in DB based Authentication we dont write this code
	
//	@Bean // Let Spring Container to manage object of this class by making this object as spring bean
//	public UserDetailsService userDetailsService() {
//		
//		
//		UserDetails akash = User.builder()   // Here we were manually creating the user object and role
//				.username("akash")
//				.password(passwordEncoder().encode("password"))  // Spring expects you to pass the encoded passwords
//				.roles("USER")
//				.build();
//		
//		
//		UserDetails admin = User.builder()
//				.username("admin")
//				.password(passwordEncoder().encode("password"))
//				.roles("ADMIN")
//				.build();
//		
//		
//		// Store these 2 Users in Spring Security provided In Memory Objects
//		
//		// This is In Memory Authentication we are storing User and Role Details in In Memory We dont need the User and Role JPA Entites for this
//		
//		
//		return new InMemoryUserDetailsManager(akash, admin);
//		
//	}
	
	
	
	
}












































