package com.sujal.LinkedInProject.postService.controller;

import com.sujal.LinkedInProject.postService.services.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class PostLikesController {

    private final PostLikeService postLikeService;

    @PostMapping(path = "/{postId}")
    public ResponseEntity<Void> likePost(@PathVariable Long postId){
        postLikeService.likePost(postId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{postId}")
    public ResponseEntity<Void > unlikePost(@PathVariable Long postId){
        postLikeService.unlikePost(postId);
        return ResponseEntity.noContent().build();
    }

}
