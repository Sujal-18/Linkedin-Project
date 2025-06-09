package com.sujal.LinkedInProject.postService.services;

import com.sujal.LinkedInProject.postService.auth.AuthContextHolder;
import com.sujal.LinkedInProject.postService.entities.Post;
import com.sujal.LinkedInProject.postService.entities.PostLikes;
import com.sujal.LinkedInProject.postService.event.PostLiked;
import com.sujal.LinkedInProject.postService.exceptions.BadRequestException;
import com.sujal.LinkedInProject.postService.exceptions.ResourceNotFoundException;
import com.sujal.LinkedInProject.postService.repositories.PostLikeRepository;
import com.sujal.LinkedInProject.postService.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {

    @Value("${kafka.topic.post-liked-topic}")
    private String POST_LIKED_TOPIC;

    private final PostLikeRepository postLikeRepository;
    private  final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<Long, PostLiked> postLikedKafkaTemplate;

    @Transactional
    public void likePost(Long postId) {
        Long userId = AuthContextHolder.getCurrentUserId();
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

        PostLiked postLikedEvent = PostLiked.builder()
                .ownerUserId(post.getUserId())
                .postId(postId)
                .likedByUserId(userId)
                .build();

        postLikedKafkaTemplate.send(POST_LIKED_TOPIC,userId,postLikedEvent);


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
