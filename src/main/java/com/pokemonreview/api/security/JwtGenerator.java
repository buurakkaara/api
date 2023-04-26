package com.pokemonreview.api.security;

import java.security.Key;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtGenerator {

	public String generateToken(Authentication authentication) {
		
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);
		
		String token = Jwts.builder()
					.setSubject(username)
					.setIssuedAt(new Date())
					.setExpiration(expireDate)
					.signWith(getSignInKey(), SignatureAlgorithm.HS256)
					.compact();
		return token;
	
	}
	
	public String getUsernameFromJwt(String token) {
		
		Claims claims = Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
		
	}
	
	public boolean validateToken(String token) {
		
		try {
			Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
			return true;
		} catch (Exception e) {
			throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
		}
		
	}
	
	
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SecurityConstants.JWT_SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
}















