package com.project.blog.security;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;


@Service
public class JwtProvider {
	
	private Key key ;

	public static final long EXPIRATION_TIME = 864_000_000; // 10 days

   
     
    
	@PostConstruct
	public void init() {
		key = Keys.secretKeyFor(SignatureAlgorithm.HS256) ;
	}
	
	public String generateToken(Authentication authentication) {
		User pricipal = (User)authentication.getPrincipal() ;

		return Jwts.builder().setSubject(pricipal.getUsername()).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
	}
	
	
	public boolean validateToken(String jwt) {
	    try {
	        Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);
	        return true;
	    } catch (io.jsonwebtoken.ExpiredJwtException e) {
	        System.out.println("JWT Token has expired.");
	    } catch (io.jsonwebtoken.MalformedJwtException e) {
	        System.out.println("Malformed JWT Token.");
	    } catch (io.jsonwebtoken.SignatureException e) {
	        System.out.println("Invalid JWT signature.");
	    } catch (Exception e) {
	        System.out.println("Error validating JWT Token.");
	    }
	    return false;
	}




    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
	
}
