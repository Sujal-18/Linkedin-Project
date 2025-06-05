package com.sujal.linkedInProject.postService.services;

import com.sujal.linkedInProject.postService.auth.AuthContextHolder;
import com.sujal.linkedInProject.postService.clients.ConnectionsFeignClient;
import com.sujal.linkedInProject.postService.dto.PostCreateRequestDTO;
import com.sujal.linkedInProject.postService.dto.PostDTO;
import com.sujal.linkedInProject.postService.entities.Person;
import com.sujal.linkedInProject.postService.entities.Post;
import com.sujal.linkedInProject.postService.exceptions.ResourceNotFoundException;
import com.sujal.linkedInProject.postService.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final ConnectionsFeignClient connectionsFeignClient;

    public PostDTO createPost(PostCreateRequestDTO postDTO,Long userId) {
        Post postEntity = modelMapper.map(postDTO,Post.class);
        postEntity.setUserId(userId);

        postEntity = postRepository.save(postEntity);
        return modelMapper.map(postEntity,PostDTO.class);
    }

    public PostDTO getPostById(Long postId) {
        log.info("Getting the post with ID: {}",postId);
        Long userId = AuthContextHolder.getCurrentUserId();


        List<Person> PersonList = connectionsFeignClient.getFirstDegreeConnections(userId);

        Post post  =  postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found with ID: "+postId));
        return modelMapper.map(post,PostDTO.class);


    }

    public List<PostDTO> getAllPostsOfUser(Long userId) {
        log.info("Getting all posts of user");
        List<Post> postList = postRepository.findByUserId(userId);
        return postList.stream()
                .map(post ->
                    modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());

    }
}
