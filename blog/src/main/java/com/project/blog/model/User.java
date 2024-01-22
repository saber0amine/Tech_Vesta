package com.project.blog.model;

import org.hibernate.mapping.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class User {

@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id ; 

@Column
private String userName ; 

@Column
private String password ; 

@Column
private String email ; 


@ManyToOne(targetEntity = Post.class, fetch = FetchType.LAZY   )
@JoinColumn(name ="post_user_id")
private java.util.List<Post> post  ; 


	
	
}

