package com.project.blog.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.blog.dto.PostDto;
import com.project.blog.exception.PostNotFoundException;
import com.project.blog.model.Post;
import com.project.blog.model.User;
import com.project.blog.repository.PostRepository;
import com.project.blog.repository.UserRepository;

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
	public void createPost(Post post) {
	    String currentUsername = authService.getCurrentUserUsername();
      System.out.println("from post Service *******************************************************" +currentUsername);
		post.setCreatedOn(Instant.now());
		post.setUsername(currentUsername);
		post.setUpdatedOn(Instant.now());
		User user =userRepository.findByUserName(currentUsername).orElse(null) ;
		if(user !=null) {
			post.setUser(user);	
			user.getPost().add(post); 
			
			}
	    
      postRepository.save(post);
       
	}
	
	private PostDto mapFromPostToDto(Post post) {
		 PostDto postDto = new PostDto() ; 
		 postDto.setId(post.getId());
		 postDto.setTitle(post.getTitle());
		 postDto.setContent(post.getContent());
			
		 /*String username = authService
					.getCurrentUser() .orElseThrow(() -> new IllegalArgumentException("no user logged ") ) */ ;
	       post.setUsername(post.getUsername());
	       postRepository.save(post) ;
		
		return postDto ;
	}

//	private Post mapFromDtoToPost(PostDto postDto) {
//		Post post = new Post() ; 
//		post.setTitle(postDto.getTitle());
//		post.setContent(postDto.getContent());
//		String username = authService.getCurrentUser(); /*.orElseThrow(() -> new IllegalArgumentException("no user logged ")) ;  it will return the username */
//		post.setCreatedOn(Instant.now());
//		post.setUsername(username);
//		post.setUpdatedOn(Instant.now());
//				
//				return post ; 
//	}
	public List<PostDto> showAllPosts() {
		List<Post> posts = postRepository.findAll();
		return posts.stream().map(this::mapFromPostToDto).collect(Collectors.toList()); // looping the list of posts with stream (java 8)
	}

	public PostDto readSinglePost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(()-> new PostNotFoundException("For id " + id)  )  ; 
		return mapFromPostToDto(post);
	}

}































