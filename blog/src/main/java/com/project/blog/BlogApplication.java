package com.project.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.project.blog.security.JwtProvider;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class BlogApplication {


	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);

	}

}
