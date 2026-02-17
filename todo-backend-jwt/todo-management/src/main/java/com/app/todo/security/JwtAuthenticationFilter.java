package com.app.todo.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


// Execute before Executing Spring Security Filters
// Validate the JWT Token and provides user details to Spring Security for Authentication
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private JwtTokenProvider jwtTokenProvider;
	
	private UserDetailsService userDetailsService;
	
	
	// CTOR based Dependency Injection
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
		super();
		this.jwtTokenProvider = jwtTokenProvider;
		this.userDetailsService = userDetailsService;
	}





	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		// Get JWT Token from Http Request
		String token = getTokenFromRequest(request);
		
		// validate token
		
		if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token))  // check null or empty 
		{
			// Get the userName from the token
			String userName = jwtTokenProvider.getUserNameFromToken(token);
			
			// Once we get the username from the token we can get the user from the DB using this token
			
			UserDetails userDetails = userDetailsService.loadUserByUsername(userName);  // Got the user Details obj
			
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, 
					null,
					userDetails.getAuthorities()
				);
			
			
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // Added request obj to this Authentication token
			
			SecurityContextHolder .getContext().setAuthentication(authenticationToken);
			
		} // if end
		
		
		filterChain.doFilter(request, response);
		
		
	}
	
	private String getTokenFromRequest(HttpServletRequest request)
	{
		// HttpServletRequest obj contains a Header from that Header we will get JWT token
		String bearerToken = request.getHeader("Authorization");
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			// If token is not null and not empty and starts with 'Bearer ' we will get the token
			
			return bearerToken.substring(7,bearerToken.length());
			
		}
		
		return null;
		
	}

}

























































