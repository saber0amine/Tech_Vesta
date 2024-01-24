package com.project.blog.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table
public class User {

@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id ; 

@Column(unique=true)
@NotBlank(message = "username cannot be blank")
private String userName ; 

@NotBlank(message = "Password cannot be blank")
@Column
private String password ; 

@Column(unique=true)
@NotBlank(message = "email cannot be blank")
private String email ; 


@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,mappedBy = "user" ,fetch = FetchType.LAZY   )
private List<Post> post  = new ArrayList<>() ; 


	
	
}

