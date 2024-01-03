package com.project.blog.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import com.project.blog.model.User;


@Data
@Entity
@Table
public class Post {

@Id
@GeneratedValue ( strategy = GenerationType.IDENTITY)
private Long id ;

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
private String username ; 

@ManyToOne
@JoinColumn(name = "user_id")
private User user; 

	
	
}
