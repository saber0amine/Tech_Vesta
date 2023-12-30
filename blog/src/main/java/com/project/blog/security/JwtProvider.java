package com.project.blog.security;

import java.security.Key;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;


@Service
public class JwtProvider {
	
	private Key key ;

	@PostConstruct
	public void init() {
		key = Keys.secretKeyFor(SignatureAlgorithm.ES512) ;
	}
	
	public String generateToken(Authentication authentication) {
		User pricipal = (User)authentication.getPrincipal() ;
		return Jwts.builder().setSubject(pricipal.getUsername()).signWith(key).compact();
	}
	
}
