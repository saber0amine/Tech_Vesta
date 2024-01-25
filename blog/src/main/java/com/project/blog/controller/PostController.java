package com.project.blog.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.project.blog.dto.PostDto;
import com.project.blog.model.Post;
import com.project.blog.model.User;
import com.project.blog.repository.PostRepository;
import com.project.blog.repository.UserRepository;
import com.project.blog.service.PostService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Controller
//@RequestMapping("/api/posts/")
public class PostController {
	
    @Autowired
    private PostService postService;
    @Autowired
    private UserRepository userRepository  ;
    
    @Autowired
    private PostRepository postRepository ; 
    
    @GetMapping("/CreationPostPage")
    public String CreationPostPage(Model model) {
        model.addAttribute("post", new Post());

    	return "addPost" ;
    	
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute("post") Post post,
                             @RequestParam("authorImage") MultipartFile authorImage,
                             Model model) throws IOException {
    	 

        postService.createPost(post , authorImage);
        model.addAttribute("postCreated", post);
        return "addPost";
    }

    @GetMapping("/viewPost/{postId}")
    public String viewPost(@PathVariable Long postId, Model model) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);
        } else {
return "profil";       
}

        // Return the viewPost template
        return "viewPost";
    }

    
    @RestController
    @RequestMapping("/post-images")
    public class PostImageController {

        @Autowired
        private PostRepository postRepository;

        @GetMapping("/{postId}")
        public ResponseEntity<ByteArrayResource> servePostImage(@PathVariable Long postId) throws IOException {
            Optional<Post> postOptional = postRepository.findById(postId);

            if (postOptional.isPresent()) {
                Post post = postOptional.get();

                byte[] authorImage = post.getAuthorImage();

                if (authorImage != null) {
                    ByteArrayResource resource = new ByteArrayResource(authorImage);

                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=author-image.jpg")
                            .contentType(MediaType.IMAGE_JPEG)
                            .contentLength(authorImage.length)
                            .body(resource);
                }
            }

            return ResponseEntity.notFound().build();
        }
    }

    
    @GetMapping("/blogPage")
    public String blogPage() {
    	return "blogPage" ;
    }
    
    
    @GetMapping("/allPosts")
    public String allPosts(Model model) {
    	List<Post> allPosts = postRepository.findAll();    	
    	model.addAttribute("allPost", allPosts) ;
    	
    	return "allPosts"; 
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   

	}
























