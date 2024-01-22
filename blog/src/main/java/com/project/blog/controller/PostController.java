package com.project.blog.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.blog.dto.PostDto;
import com.project.blog.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts/")
public class PostController {
	

	    @Autowired
	    private PostService postService;

	    @PostMapping
	    public ResponseEntity createPost(@RequestBody PostDto postDto) {
	        postService.createPost(postDto);
	        return new ResponseEntity(HttpStatus.OK);
	    }


	}

