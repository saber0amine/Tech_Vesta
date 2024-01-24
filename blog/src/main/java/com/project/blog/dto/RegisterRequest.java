package com.project.blog.dto;

import lombok.Data;

@Data
public class RegisterRequest {
	public RegisterRequest(String username2, String email2, String password2) {
		// TODO Auto-generated constructor stub
	}
	private String username ; 
	private String email ;
	private String password ; 

}
