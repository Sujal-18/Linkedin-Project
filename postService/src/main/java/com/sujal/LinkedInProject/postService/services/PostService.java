package com.sujal.LinkedInProject.postService.services;

import com.sujal.LinkedInProject.postService.auth.AuthContextHolder;
import com.sujal.LinkedInProject.postService.clients.ConnectionsFeignClient;
import com.sujal.LinkedInProject.postService.clients.UploaderServiceFeignClient;
import com.sujal.LinkedInProject.postService.dto.PostCreateRequestDTO;
import com.sujal.LinkedInProject.postService.dto.PostDTO;
import com.sujal.LinkedInProject.postService.entities.Person;
import com.sujal.LinkedInProject.postService.entities.Post;
import com.sujal.LinkedInProject.postService.event.PostCreated;
import com.sujal.LinkedInProject.postService.exceptions.ResourceNotFoundException;
import com.sujal.LinkedInProject.postService.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    @Value("${kafka.topic.post-created-topic}")
    private String POST_CREATED_TOPIC;

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final ConnectionsFeignClient connectionsFeignClient;
    private final KafkaTemplate<Long, PostCreated> postCreatedKafkaTemplate;
    private final UploaderServiceFeignClient uploaderServiceFeignClient;

    public PostDTO createPost(PostCreateRequestDTO postDTO, MultipartFile file) {
        Long userId = AuthContextHolder.getCurrentUserId();
        log.info("Creating post for user with id: {}",userId);

        ResponseEntity<String> imageUrl = uploaderServiceFeignClient.uploadFile(file);
        log.info(String.valueOf(imageUrl));
        Post postEntity = modelMapper.map(postDTO,Post.class);
        postEntity.setUserId(userId);
        postEntity.setImageUrl(imageUrl.getBody());

        postEntity = postRepository.save(postEntity);
        List<Person> PersonList = connectionsFeignClient.getFirstDegreeConnections(userId);

        for(Person person: PersonList){ //send notification to each connection i.e connectionUserId
            PostCreated postCreatedEvent = PostCreated.builder()
                    .postOwnerUserId(userId)
                    .postId(postEntity.getId())
                    .connectionUserId(person.getUserId())
                    .content(postEntity.getContent())
                    .build();
            postCreatedKafkaTemplate.send(POST_CREATED_TOPIC,userId,postCreatedEvent);
        }
        return modelMapper.map(postEntity,PostDTO.class);

    }

    public PostDTO getPostById(Long postId) {
        log.info("Getting the post with ID: {}",postId);
        Long userId = AuthContextHolder.getCurrentUserId();

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
