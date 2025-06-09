package com.sujal.LinkedInProject.userService.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConfig {

    @Value("${kafka.topic.user-created-topic}")
    private String USER_CREATED_TOPIC;


    @Bean
    public NewTopic userCreatedTopic(){
        return new NewTopic(USER_CREATED_TOPIC,3,(short) 1);
    }
}
