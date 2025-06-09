package com.sujal.LinkedInProject.ConnectionsService.auth;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {


    @Override
    public void apply(RequestTemplate requestTemplate) {
        Long userId = AuthContextHolder.getCurrentUserId(); //Ithe userId bhetat ahe because Ya Interceptor chya aadhi RequestInterceptor normal wala hi request intercept krel and userId set krel inside AuthContextHolder and mg FeignInterceptor tya request la read krel
        if (userId != null) {
            requestTemplate.header("X-User-Id", userId.toString());
        }
    }
}
