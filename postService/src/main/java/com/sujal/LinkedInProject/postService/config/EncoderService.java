package com.sujal.LinkedInProject.postService.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncoderService {

    @Bean
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder();
    }
}
