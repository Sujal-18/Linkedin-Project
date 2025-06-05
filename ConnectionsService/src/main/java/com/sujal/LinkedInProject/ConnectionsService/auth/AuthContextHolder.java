package com.sujal.LinkedInProject.ConnectionsService.auth;
//We are creating this to store userId in contextHolder just for that specific request and not for all time for all requests
public class AuthContextHolder {

    private static final ThreadLocal<Long> currentUserId = new ThreadLocal<>();

    public static Long getCurrentUserId(){
        return currentUserId.get();
    }

    static void setCurrentUserId(Long userId){
        currentUserId.set(userId);
    }

    static void clear(){
        currentUserId.remove();
    }
}
