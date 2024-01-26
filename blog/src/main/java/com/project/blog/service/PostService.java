package com.project.blog.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.blog.dto.PostDto;
import com.project.blog.exception.PostNotFoundException;
import com.project.blog.model.Post;
import com.project.blog.model.User;
import com.project.blog.repository.PostRepository;
import com.project.blog.repository.UserRepository;

import io.jsonwebtoken.io.IOException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class PostService {
	
	@Autowired
	 private AuthService authService ; 
	@Autowired
	private PostRepository postRepository ; 
	
	@Autowired
	private UserRepository userRepository ; 
	

	 @Autowired
	 public void PostService (PostRepository postRepository) { //injection par constructeur 
		 this.postRepository =postRepository ;
		 
	
		
	 }
	
	
	
	 @Transactional
	 public void createPost(Post post, MultipartFile authorImage) {
	     try {
	         String currentUsername = authService.getCurrentUserUsername();
	         System.out.println("from post Service *******************************************************" + currentUsername);

	         post.setCreatedOn(Instant.now());
	         post.setUsername(currentUsername);
	         post.setUpdatedOn(Instant.now());

	         User user = userRepository.findByUserName(currentUsername).orElse(null);

	         if (user != null) {
	             post.setUser(user);
	             user.getPost().add(post);
	         }

	         if (authorImage != null && !authorImage.isEmpty()) {
	             post.setAuthorImage(authorImage);
	         }

	         postRepository.save(post);
	     } catch (IOException e) {
	         throw new RuntimeException("Failed to process file", e);
	     }
	 }

	 

	 private byte[] getImageBytes(MultipartFile file) throws IOException, java.io.IOException {
	     return file.getBytes();
	 }

	 
	 
	 
	

	  public List<Post> searchPosts(String query) {
	        return postRepository.findByTitleContainingOrContentContaining(query, query);
	    }

}































