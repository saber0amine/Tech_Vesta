package com.project.blog.service;

import java.util.Collection;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.blog.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
@Autowired
private UserRepository userRepository ; 
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = (User) userRepository.findByUserName(userName).orElseThrow( () -> new UsernameNotFoundException("No user found" + userName ) ) ; 
		   return new org.springframework.security.core.userdetails.User(user.getUsername(),
	                user.getPassword(),
	                true, true, true, true,
	                getAuthorities("ROLE_USER"));	}

	private Collection<? extends GrantedAuthority> getAuthorities(String string) {
		return null;
	}

}
