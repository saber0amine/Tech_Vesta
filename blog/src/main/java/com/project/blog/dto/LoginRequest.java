package com.project.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoginRequest {
    @JsonProperty("username")
	private String userName ; 
	private String password ; 
}
