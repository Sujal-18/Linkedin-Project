package com.sujal.linkedInProject.postService.repositories;

import com.sujal.linkedInProject.postService.dto.PostDTO;
import com.sujal.linkedInProject.postService.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {


    List<Post> findByUserId(Long userId);
}
