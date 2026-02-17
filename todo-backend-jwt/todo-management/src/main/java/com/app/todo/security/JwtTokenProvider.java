package com.app.todo.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider 
{
	@Value("${app.jwt-secret}") // Retrive the value of the property
	private String jwtSecret;
	
	@Value("${app.jwt-expiration-milliseconds}")
	private long jwtExpirationMilliseconds;
	
	
	// Create generate token utility method
	public String generateToken(Authentication authentication) {
			
		String userName  = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + jwtExpirationMilliseconds);
		
		// Create a JWT token
		Jwts.builder()
					 .subject(userName)
					 .issuedAt(new Date())
					 .expiration(expireDate)   // These method expect the parameters in Date format thats why we have converted the time in Date
					 .signWith(key())   // Sign the JWT token using secret key
					 .compact();
		
			
			return userName;
			
	}
	
	
	
	private Key key()
	{
		// Whenever we extract the userName from this JWT token we are going to use the same secret key
		// And anywhere we validate this JWT Token we are going to use the same secret key, so writing this code here and will reuse wherever required
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)); 
	}
	
	
	// get userName from Token
	public String getUserNameFromToken(String token) {
		
		return Jwts.parser()
					.verifyWith((SecretKey) key())
					.build()
					.parseSignedClaims(token)
					.getPayload()
					.getSubject();    // This gets the userName from the payload
	}
	
	
	
	
	// validate token
	public boolean validateToken(String token)
	{
		 Jwts.parser()
				.verifyWith((SecretKey) key())
				.build()
				.parse(token);  // This parse() method throws exception internally if the token is invalid ! if expired or invalid
		 
		 return true;
	}
}











































