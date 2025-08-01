package com.sujal.LinkedInProject.postService.repositories;

import com.sujal.LinkedInProject.postService.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {


    List<Post> findByUserId(Long userId);
}
