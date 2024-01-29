package com.project.blog.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.blog.service.ForumService;
import com.project.blog.model.Comment;
import com.project.blog.model.Forum;
import com.project.blog.model.Post;
import com.project.blog.service.CommentService;

@Controller
public class ForumController {
	
	    private final ForumService forumService;
	    private final CommentService commentService;

	    @Autowired
	    public ForumController(ForumService questionService, CommentService commentService) {
	        this.forumService = questionService;
	        this.commentService = commentService;
	    }
	    
	    
	  

	    @GetMapping("/askforum")
	    public String askforumPage(Model model) {
	        model.addAttribute("forum", new Forum()); 
	        return "askforum"; 
	    }

    
	    @PostMapping("/saveForum")
	    public String saveForum(@ModelAttribute Forum forum) {
	    	System.out.println("the question is **********************************************************" +forum.getQuestion() );
	        forumService.save(forum);
	        return "redirect:/askforum";
	    }

	

	    
	    @GetMapping("/forum")
	    public String showAllForums(Model model) {
	        List<Forum> forums = forumService.findAll();
	        Collections.sort(forums ,(firstquestion , secondquestion) ->  secondquestion.getCreatedOn().compareTo(firstquestion.getCreatedOn())) ;
	        model.addAttribute("forums", forums);
	        model.addAttribute("comment", new Comment());  // Add an empty comment object to the model
	        return "forum";
	    }


	    @PostMapping("/addComment/{forumId}")
	    public String addComment(@PathVariable Long forumId, @ModelAttribute Comment comment) {
	        forumService.addComment(forumId, comment);
	        return "redirect:/forum";
	    }

	
	    
	    @GetMapping("/searchForum")
	    public String searchForum(@RequestParam("query") String query, Model model) {
	    	System.out.println("this is ur query from Search Forum ********************" +query);
	        List<Forum> forums = forumService.searchForums(query);
	    	System.out.println("this is ur forums from Search Forum by searching********************" +forums.size());

	        model.addAttribute("forums", forums);
	        model.addAttribute("comment", new Comment());  

	        return "forum";
	    }
	
	
	
	
}
