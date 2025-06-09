package com.sujal.LinkedInProject.postService.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.TopicForRetryable;

@Configuration
public class KafkaConfig {

    @Value("${kafka.topic.post-created-topic}")
    private String POST_CREATED_TOPIC;

    @Value("${kafka.topic.post-liked-topic}")
    private String POST_LIKED_TOPIC;

    @Bean
    public NewTopic postCreated(){
        return new NewTopic(POST_CREATED_TOPIC,3,(short) 1);
    }

    @Bean
    public NewTopic postLiked(){
        return new NewTopic(POST_LIKED_TOPIC,3,(short) 1);
    }
}
