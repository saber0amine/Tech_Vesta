package com.project.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.blog.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUserName(String userName) ;
	Optional<User> findByEmail(String email) ; 
	Optional<User> findByEmailAndPassword(String email , String passsword ) ;

}
