package com.sujal.LinkedInProject.postService.advices;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor

@Builder
public class ApiError {

    private LocalDateTime timeStamp;
    private String error;
    private HttpStatus statusCode;

    public ApiError(){
        this.timeStamp = LocalDateTime.now();

    }
    public ApiError(String error,HttpStatus statusCode){
        this();
        this.error = error;
        this.statusCode =statusCode;

    }
}
