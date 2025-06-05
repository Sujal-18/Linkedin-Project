package com.sujal.LinkedInProject.ConnectionsService.auth;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
//Ha interceptor each request la intercept krel ani aadhi contextHolder mde userId set krel
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception { //will  capture the request at controller
        String userId = request.getHeader("X-User-Id");
        Long usrId = Long.valueOf(userId);
        AuthContextHolder.setCurrentUserId(usrId);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception { //this method will always run no matter what like even if error or exception occured
        AuthContextHolder.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }



}
