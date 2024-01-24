package com.project.blog.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest  ;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter { /*the method doFilterInternal will be excuted once for each http request 
 																	  and not for the same rq ofc . */

	
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailsService userDetailsService;



	@Override
	  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	            throws ServletException, IOException {
	        String jwt = getJwtFromRequest(request);

	        if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {
	            String username = jwtProvider.getUsernameFromJWT(jwt);

	            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
	                    null, userDetails.getAuthorities());
	            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

	            SecurityContextHolder.getContext().setAuthentication(authentication);
	        }

	        filterChain.doFilter(request, response);
	    }


	
	  private String getJwtFromRequest(HttpServletRequest request) {
	        Cookie[] cookies = request.getCookies();

	        if (cookies != null) {
	            for (Cookie cookie : cookies) {
	                if (cookie.getName().equals("JWT")) {
	                    return cookie.getValue();
	                }
	            }
	        }

	        return null;
	    }
	
//    private String getJwtFromRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//
//        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return bearerToken;
//    }

}