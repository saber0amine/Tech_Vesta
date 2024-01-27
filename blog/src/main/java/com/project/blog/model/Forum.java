package com.project.blog.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Instant createdOn;


    @Column
    private Instant updatedOn;
    
    
    @Lob
    @NotEmpty
    @Column(columnDefinition = "LONGTEXT") 
    private String question;

    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();



    @ManyToOne(cascade = CascadeType.ALL)    
    @JoinColumn(name = "user_id")
    private User user;

}
