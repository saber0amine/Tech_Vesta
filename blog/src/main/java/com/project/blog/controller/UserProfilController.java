package com.project.blog.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.blog.model.Comment;
import com.project.blog.model.Forum;
import com.project.blog.model.Post;
import com.project.blog.model.User;
import com.project.blog.repository.PostRepository;
import com.project.blog.repository.UserRepository;
import com.project.blog.service.PostService;

import io.jsonwebtoken.io.IOException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Controller
public class UserProfilController {
    @Autowired
    private PostService postService;
    
    @Autowired
    private PostRepository postRepository ; 
	 @Autowired
	    private UserRepository userRepository  ;
	 
	 
    @GetMapping("/profilPage")
    public String profilPage(Authentication authentication, Model model) {
        if (authentication != null) {
            Optional<User> userWithPosts = userRepository.findUserWithPostsByUserName(authentication.getName());
            model.addAttribute("post", new Post());

            if (userWithPosts.isPresent()) {
                User user = userWithPosts.get();
                List<Post> userPosts = user.getPost();
                Collections.sort(userPosts, (post1, post2) -> post2.getCreatedOn().compareTo(post1.getCreatedOn()));

                model.addAttribute("user", user);
                model.addAttribute("authentication", authentication);
                model.addAttribute("userPosts", userPosts);
            }
        }
        return "profil";
    }
    @GetMapping("/searchUserPost")
    public String searchUserPos(@RequestParam("query") String query, Model model , Authentication authentication) {
        if (authentication != null) {
            Optional<User> userWithPosts = userRepository.findUserWithPostsByUserName(authentication.getName());
            model.addAttribute("post", new Post());

            if (userWithPosts.isPresent()) {
                User user = userWithPosts.get();
                List<Post> userPosts =  postService.searchUserPosts(query , user.getId());
                model.addAttribute("user", user);
                model.addAttribute("authentication", authentication);
                model.addAttribute("userPosts", userPosts);
            }
            
            
        }
       
        return "profil";
    }
    
    
    @Transactional 
    @GetMapping("/deletePost/{postId}")
    public String deletePost(@PathVariable Long postId, Authentication authentication, Model model) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Optional<User> userFromAuth = userRepository.findUserWithPostsByUserName(authentication.getName());

        if (optionalPost.isPresent() && userFromAuth.isPresent()) {
            User user = userFromAuth.get();
            model.addAttribute("user", user);

            postRepository.deleteById(postId);
            return "redirect:/profilPage";
        } else {
            return "profil";
        }
    }


    @Transactional
    @PostMapping("/editPost/{postId}")
    public String editPost(@PathVariable Long postId, @ModelAttribute Post updatedPost) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            // Update post content
            post.setContent(updatedPost.getContent());
            postRepository.save(post);

            return "redirect:/profilPage";
        } else {
            return "redirect:/profilPage";
        }
    }


    
    @GetMapping("/editBio")
    public String editBio(Authentication authentication, Model model) {
        if (authentication != null) {
            Optional<User> userWithPosts = userRepository.findUserWithPostsByUserName(authentication.getName());

            if (userWithPosts.isPresent()) {
                User user = userWithPosts.get();
                model.addAttribute("user", user);
            }
        }
        return "editBio";
    }

    @PostMapping("/editBio")
    public String saveBio(@ModelAttribute("user") User updatedUser, Authentication authentication, Model model) {
        if (authentication != null) {
            Optional<User> userWithPosts = userRepository.findUserWithPostsByUserName(authentication.getName());

            if (userWithPosts.isPresent()) {
                User user = userWithPosts.get();
                // Update only the bio field
                user.setBio(updatedUser.getBio());
                userRepository.save(user);
                model.addAttribute("user", user);
            }
        }
        return "redirect:/profilPage";
    }
    
    
    @PostMapping("/uploadProfilePicture")
    public String handleFileUpload(
            @RequestParam("file") MultipartFile file,
            Authentication authentication,
            Model model
    ) throws java.io.IOException {
        if (authentication != null && !file.isEmpty()) {
            try {
                Optional<User> userWithPosts = userRepository.findUserWithPostsByUserName(authentication.getName());

                if (userWithPosts.isPresent()) {
                    User user = userWithPosts.get();

                    // Convert the MultipartFile to a byte array
                    byte[] bytes = file.getBytes();

                    // Set the byte array as the user's profile picture ( deja image s fourm d'un vd)
                    user.setProfilePicture(bytes);

                    userRepository.save(user);

                    return "redirect:/profilPage";
                }
            } catch (IOException e) {
System.out.println("EROOOOOOOOOOR PIC *****************************" +e);            }
        }

        // If there's an issue with the authentication or file, I can handle it accordingly
        return "redirect:/addPost"; // Redirect to an error apres ondirha 
    }

    
    @GetMapping("/profile-picture/{userId}")
    public ResponseEntity<ByteArrayResource> serveProfilePicture(@PathVariable Long userId) throws IOException {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            byte[] profilePicture = user.getProfilePicture();

            if (profilePicture != null) {
                ByteArrayResource resource = new ByteArrayResource(profilePicture);

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=profile-picture.jpg")
                        .contentType(MediaType.IMAGE_JPEG)
                        .contentLength(profilePicture.length)
                        .body(resource);
            }
        }

        // If the user or profile picture is not found, you can return a default image or handle it accordingly.
        return ResponseEntity.notFound().build();
    }

    
    
    
    
    
    
    
    
    @GetMapping("/userStat/{userId}")
    public String getUserStatistics(@PathVariable Long userId, Model model) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Post> userPosts = user.getPost();
            List<Comment> userComments = user.getComments();
            List<Forum> userForums = user.getForum();

            model.addAttribute("user", user);
            model.addAttribute("userPosts",userPosts);
            model.addAttribute("userComments",userComments); 
            model.addAttribute("userForums",userForums); 



            return "userStat";
        } else {
            return "askFrorum";
        }
    }

    
    
    
    

    @GetMapping("/logOut")
    public String logOut(HttpServletRequest request, HttpServletResponse response) {
        Cookie JWT = new Cookie("JWT", null);
        JWT.setPath("/");
        JWT.setMaxAge(0);
        response.addCookie(JWT);

        return "redirect:/loginPage?logout"; // Redirect to your login page with a logout parameter
    }


    
    
    
    
    
    

}
