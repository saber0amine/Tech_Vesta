package com.project.blog.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.blog.dto.LoginRequest;
import com.project.blog.dto.RegisterRequest;
import com.project.blog.model.User;
import com.project.blog.repository.UserRepository;
import com.project.blog.security.JwtProvider;

@Service
public class AuthService {
	
@Autowired
private UserRepository userRepository ; 

@Autowired
private PasswordEncoder passwordEncoder ; 

@Autowired
private AuthenticationManager authenticationManager ;

@Autowired
private JwtProvider jwtProvider ; 

   
public void signup(User user ) {
	    
 user.setPassword(encodePassword(user.getPassword() ));
 userRepository.save(user);
} 

	    private String encodePassword(String password) {
	        if (password != null) {
	            return passwordEncoder.encode(password);
	        } else {
	            throw new IllegalArgumentException("Password cannot be null");
	        }
	    }
	   
	    public String login(User user) {
	        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),
	                user.getPassword()));
	        SecurityContextHolder.getContext().setAuthentication(authenticate);
	        System.out.println("form service ******************************************************* " + jwtProvider.generateToken(authenticate));
	        System.out.println("form service ******************************************************* " + authenticate.getDetails() + "****" 
	        + authenticate.getPrincipal() + "from security "
	        +SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass() );
	        return  jwtProvider.generateToken(authenticate); 
	        }
	    
	    
//	    public String login(User user) {
//	        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),
//	                user.getPassword()));
//	        SecurityContextHolder.getContext().setAuthentication(authenticate);
//
//	        UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
//
//	        System.out.println("form service ******************************************************* " + jwtProvider.generateToken(authenticate));
//	        System.out.println("form service ******************************************************* UserDetails: " + userDetails.getUsername() + " **** " +
//	                userDetails.getPassword() + " **** " +
//	                userDetails.getAuthorities() + " from security " +
//	                SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//
//	        return jwtProvider.generateToken(authenticate);
//	    }



//			public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() { // I will import user like this cz , we have already user entity imported .. that make confilct between them . 
//	        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
//	                getContext().getAuthentication().getPrincipal();
//			return Optional.of(principal) ; //principal.getUsername();
//	    }
//	    
//			public String getCurrentUserUsername() {
//			    org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder
//			            .getContext().getAuthentication().getPrincipal();
//			      System.out.println("from auth Service ******************************************************* " +principal.getUsername( ) );
//
//			    return principal.getUsername();
//			}

	   

	    public String getCurrentUserUsername() {
	        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	        if (principal instanceof UserDetails) {
	            return ((UserDetails) principal).getUsername();
	        } else {
	            return "Unable to retrieve the username. Please check your authentication.";
	        }
	    }

}

















