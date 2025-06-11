package com.sujal.LinkedInProject.postService.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "uploader-service",path = "/uploads",url = "${UPLOADER_SERVICE_URI:}")
public interface UploaderServiceFeignClient {

    @PostMapping("/file")
    ResponseEntity<String> uploadFile(@RequestParam MultipartFile file);
}
