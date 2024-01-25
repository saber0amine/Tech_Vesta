package com.project.blog.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.project.blog.dto.PostDto;
import com.project.blog.model.Post;
import com.project.blog.model.User;
import com.project.blog.repository.UserRepository;
import com.project.blog.service.PostService;

import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("/api/posts/")
public class PostController {
	
    @Autowired
    private PostService postService;
    @Autowired
    private UserRepository userRepository  ;
    
    @GetMapping("/CreationPostPage")
    public String CreationPostPage(Model model) {
        model.addAttribute("post", new Post());

    	return "addPost" ;
    	
    }
    @PostMapping("/creatPost")
    public String createPost(@ModelAttribute("post") Post post , Model model) {
        postService.createPost(post);
        model.addAttribute("postCreated" , post);
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
























