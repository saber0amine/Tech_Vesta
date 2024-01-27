package com.project.blog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.blog.model.Post;
import com.project.blog.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUserName(String userName) ;
	Optional<User> findByEmail(String email) ; 
	Optional<User> findByEmailAndPassword(String email , String passsword ) ;
	 @Query("SELECT u FROM User u LEFT JOIN FETCH u.post WHERE u.userName = :userName")
	    Optional<User> findUserWithPostsByUserName(@Param("userName") String userName);
 	void deleteByPostId(Long postId);
 	@Query("SELECT p FROM User u JOIN u.post p WHERE u.id = :userId AND (p.title LIKE %:query% OR p.content LIKE %:query2%)")
 	List<Post> findPostByUserAndTitleContainingOrContentContaining(@Param("userId") Long userId, @Param("query") String query, @Param("query2") String query2);


}

























