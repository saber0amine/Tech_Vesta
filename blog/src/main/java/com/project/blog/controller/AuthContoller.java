package com.project.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.project.blog.service.AuthService;
import com.project.blog.dto.LoginRequest;
import com.project.blog.dto.RegisterRequest;
import com.project.blog.exception.UserNotFoundExecption;
import com.project.blog.model.User;
import com.project.blog.repository.UserRepository;
@Controller
//@RequestMapping("/api/auth/")
public class AuthContoller {
	
	@Autowired
	private AuthService authService ;
	@Autowired
	private UserRepository userRepository ; 

	
@GetMapping("/")
public String index() {
	return "index" ; 
}
@GetMapping("/register")
public String signup(Model model) {
    model.addAttribute("user", new User()); 

	return "register" ;
	
}

@GetMapping("/loginPage")
public String loginPage(Model model) {
    model.addAttribute("user", new User());

	return "login" ;
	
}

@GetMapping("/contactPage")
public String contactPage(Model model) {
    model.addAttribute("user", new User());

	return "contact" ;
	
}



//@GetMapping("/aboutPage")
//public String aboutPage(Model model) {
//    model.addAttribute("user", new User());
//
//	return "addPost" ;
//	
//}


@PostMapping("/saveUser")
public String saveUser(Model model , @ModelAttribute("user") User user , BindingResult bindingResult) {
	
	if (bindingResult.hasErrors()) {
        System.out.println("Binding errors: " + bindingResult.getAllErrors());
        return "register"; 
        }
    try {
        User userExist = userRepository.findByEmail(user.getEmail() ).orElseThrow(() -> new UserNotFoundExecption("⛔ You're already registred !! just login " ));
        model.addAttribute("error", "⛔ You're already registered!! Just login.");
        return "login";
    } catch (UserNotFoundExecption e) {
    	authService.signup(user) ; 
    	
    	return "login" ;
     }
	
}

	
	 @PostMapping("/login")
	 public String login(@ModelAttribute("user") User user, Model model) {
	     try {
	         User userExist = userRepository.findByEmail(user.getEmail())
	                 .orElseThrow(() -> new UserNotFoundExecption("we are sorry !! but can you try again  " ));

	         user.setUserName(userExist.getUserName());
    		 authService.login(user) ;
	         return "BlogPage";
	     } catch (UserNotFoundExecption e) {
	         model.addAttribute("error", e.getMessage());
	         return "login";
	     }
	 }

	
}

