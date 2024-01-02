package com.project.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.project.blog.service.AuthService;
import com.project.blog.dto.LoginRequest;
import com.project.blog.dto.RegisterRequest;
@RestController
@RequestMapping("/api/auth/")
public class AuthContoller {
	
	@Autowired
	private AuthService authService ;

	@PostMapping("/signup")
	public ResponseEntity signup(@RequestBody RegisterRequest registerRequest ) {
		authService.signup(registerRequest);

		return new ResponseEntity(HttpStatus.OK);
		
	}
	
	 @PostMapping("/login")
	    public String login(@RequestBody LoginRequest loginRequest) {
	        return authService.login(loginRequest);
	    }
	
}
