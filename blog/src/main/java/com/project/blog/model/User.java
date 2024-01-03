package com.project.blog.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class User {

@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id ; 

@Column
private String userName ; 

@Column
private String password ; 

@Column
private String email ; 


	
	
}
