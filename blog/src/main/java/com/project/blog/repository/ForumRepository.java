package com.project.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.blog.model.Forum;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long>{

}
