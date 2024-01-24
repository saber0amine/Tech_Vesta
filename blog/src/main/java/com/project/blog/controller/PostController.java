package com.project.blog.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.project.blog.dto.PostDto;
import com.project.blog.model.Post;
import com.project.blog.model.User;
import com.project.blog.service.PostService;

import java.util.List;

@Controller
//@RequestMapping("/api/posts/")
public class PostController {
	
    @Autowired
    private PostService postService;
    
    @GetMapping("/aboutPage")
    public String aboutPage(Model model) {
        model.addAttribute("post", new Post());

    	return "addPost" ;
    	
    }
    @PostMapping("/creatPost")
    public String createPost(@ModelAttribute("post") Post post) {
        postService.createPost(post);
        return "addPost" ; 
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> showAllPosts() {
        return new ResponseEntity<>(postService.showAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable @RequestBody Long id) {
        return new ResponseEntity<>(postService.readSinglePost(id), HttpStatus.OK);
    }

	}
























