package com.sujal.LinkedInProject.postService.controller;

import com.sujal.LinkedInProject.postService.auth.AuthContextHolder;
import com.sujal.LinkedInProject.postService.dto.PostCreateRequestDTO;
import com.sujal.LinkedInProject.postService.dto.PostDTO;
import com.sujal.LinkedInProject.postService.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class PostController {

    private final PostService postService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<PostDTO> createPost(@RequestPart("post")PostCreateRequestDTO postCreateRequestDTO, @RequestPart("file")MultipartFile file){
        PostDTO createdPostDto = postService.createPost(postCreateRequestDTO,file) ;
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
