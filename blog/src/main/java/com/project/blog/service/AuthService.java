package com.project.blog.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog.dto.RegisterRequest;
import com.project.blog.model.User;
import com.project.blog.repository.UserRepository;

@Service
public class AuthService {
	
@Autowired
private UserRepository userRepository ; 

	   public void signup(RegisterRequest registerRequest) {
	        User user = new User();
	        user.setUserName(registerRequest.getUsername());
	        user.setEmail(registerRequest.getEmail());
	        user.setPassword(registerRequest.getPassword());

	        userRepository.save(user);
	    } 

}
