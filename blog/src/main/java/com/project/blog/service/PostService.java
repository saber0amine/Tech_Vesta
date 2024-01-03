package com.project.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.project.blog.dto.PostDto;
import com.project.blog.model.Post;
import com.project.blog.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	 AuthService authService ; 
	
	private PostRepository postRepository ; 

	 @Autowired
	 public void PostService (PostRepository postRepository) { //injection par constructeur 
		 this.postRepository =postRepository ;
		 
	
		
	 }
	public void createPost(PostDto postDto) {
       Post post = new Post() ; 
       post.setTitle(postDto.getTitle());
       post.setContent(postDto.getContent());
		String username = authService
				.getCurrentUser()/* .orElseThrow(() -> new IllegalArgumentException("no user logged ") ) */ ;
       post.setUsername(username);
       postRepository.save(post) ;
       
	}

	public Object showAllPosts() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object readSinglePost(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
