package com.sujal.linkedInProject.postService.controller;

import com.sujal.linkedInProject.postService.auth.AuthContextHolder;
import com.sujal.linkedInProject.postService.dto.PostCreateRequestDTO;
import com.sujal.linkedInProject.postService.dto.PostDTO;
import com.sujal.linkedInProject.postService.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class PostController {

    private final PostService postService;

    @PostMapping
    ResponseEntity<PostDTO> createPost(@RequestBody PostCreateRequestDTO postDTO, HttpServletRequest httpServletRequest){
        PostDTO createdPostDto = postService.createPost(postDTO,1L);
        return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
    }


    @GetMapping(path= "/{postId}")
    ResponseEntity<PostDTO> getPostById(@PathVariable Long postId){
        Long userId = AuthContextHolder.getCurrentUserId();
        PostDTO postDTO = postService.getPostById(postId);
        return ResponseEntity.ok(postDTO);
    }

    @GetMapping(path = "/users/{userId}/allPosts")
    ResponseEntity<List<PostDTO>> getAllPostsOfUser(@PathVariable Long userId){
        List<PostDTO> posts = postService.getAllPostsOfUser(userId);
        return ResponseEntity.ok(posts);
    }




}
