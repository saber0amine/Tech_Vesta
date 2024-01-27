package com.project.blog.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog.model.Comment;
import com.project.blog.model.Forum;
import com.project.blog.model.Post;
import com.project.blog.model.User;
import com.project.blog.repository.CommentRepository;
import com.project.blog.repository.ForumRepository;
import com.project.blog.repository.UserRepository;

import io.jsonwebtoken.io.IOException;
import jakarta.transaction.Transactional;

@Service
public class ForumService {

	@Autowired
	 private AuthService authService ; 
	@Autowired
	private ForumRepository forumRepository ;
	
	@Autowired
	private UserRepository userRepository ;
	
	@Autowired
	private CommentRepository commentRepository ; 

	public void save(Forum forum) {
		  try {
		         String currentUsername = authService.getCurrentUserUsername();
		         System.out.println("from post Service *******************************************************" + currentUsername);

		        

		         User user = userRepository.findByUserName(currentUsername).orElse(null);

		         if (user != null) {
		             forum.setUser(user);
		             user.getForum().add(forum);
		             forum.setCreatedOn(Instant.now());

		             forum.setUpdatedOn(Instant.now());
		         }
		         forumRepository.save(forum) ;
	              }
	   catch (IOException e) {
	         throw new RuntimeException("Faild username ", e);
	     }

}
	

public List<Forum> findAll() {
    return forumRepository.findAll();
}


@Transactional  // ghire to ensure that the method is transactional
public void addComment(Long forumId, Comment comment) {
    Forum forum = forumRepository.findById(forumId).orElseThrow(() -> new RuntimeException("Forum not found"));

    String currentUsername = authService.getCurrentUserUsername();
    User user = userRepository.findByUserName(currentUsername).orElseThrow(() -> new RuntimeException("User not found"));
 
    comment.setUser(user);
    comment.setForum(forum);
    comment.setCreatedOn(Instant.now());

    comment.setUpdatedOn(Instant.now());

    forum.getComments().add(comment);
    forumRepository.save(forum);
}


public List<Forum> searchForums(String query) {
	        return forumRepository.findByQuestionContaining(query);
	  
}



}