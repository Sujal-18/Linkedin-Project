package com.sujal.linkedInProject.postService.services;

import com.sujal.linkedInProject.postService.entities.Post;
import com.sujal.linkedInProject.postService.entities.PostLikes;
import com.sujal.linkedInProject.postService.exceptions.BadRequestException;
import com.sujal.linkedInProject.postService.exceptions.ResourceNotFoundException;
import com.sujal.linkedInProject.postService.repositories.PostLikeRepository;
import com.sujal.linkedInProject.postService.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private  final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void likePost(Long postId) {
        Long userId = 1L;
        log.info("User with ID: {} liking the post with ID: {}",userId,postId);

        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found with id:"+postId));

        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId,postId);
        if(hasAlreadyLiked){
            throw new BadRequestException("You cannot like the post again!");
        }

        PostLikes postLikes = new PostLikes();
        postLikes.setPostId(postId);
        postLikes.setUserId(userId);
        postLikeRepository.save(postLikes);

        //TODO: send notifications to the owner of the post
    }

    @Transactional
    public void unlikePost(Long postId) {
        Long userId = 1L;
        log.info("User with ID: {} unliking the post with ID: {}",userId,postId);
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found with id:"+postId));

        boolean exists = postLikeRepository.existsByUserIdAndPostId(userId,postId);
        if(!exists){
            throw new BadRequestException("Post is not liked before");
        }
        postLikeRepository.deleteByUserIdAndPostId(userId,postId);

    }
}
