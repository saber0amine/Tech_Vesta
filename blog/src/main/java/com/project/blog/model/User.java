package com.project.blog.model;

import org.hibernate.mapping.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table
public class User {

@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id ; 

@Column
@NotBlank(message = "username cannot be blank")
private String userName ; 

@NotBlank(message = "Password cannot be blank")
@Column
private String password ; 

@Column
@NotBlank(message = "email cannot be blank")
private String email ; 


@ManyToOne(targetEntity = Post.class, fetch = FetchType.LAZY   )
@JoinColumn(name ="post_user_id")
private java.util.List<Post> post  ; 


	
	
}

