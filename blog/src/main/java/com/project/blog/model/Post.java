package com.project.blog.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
@Entity
@Table
public class Post {

@Id
@GeneratedValue ( strategy = GenerationType.IDENTITY)
private int id ;

@Column
private String title;

@Lob
@Column
@NotEmpty
private String content;

@Column
private Instant createdOn;


@Column
private Instant updatedOn;

@Column
@NotBlank
private String username; 

	
	
}
