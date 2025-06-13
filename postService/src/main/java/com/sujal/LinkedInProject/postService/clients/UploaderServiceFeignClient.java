package com.sujal.LinkedInProject.postService.clients;


import com.sujal.LinkedInProject.postService.config.AppConfig;
import com.sujal.LinkedInProject.postService.config.EncoderService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "uploader-service",path = "/uploads/file",url = "${UPLOADER_SERVICE_URI:}",configuration = EncoderService.class)
public interface UploaderServiceFeignClient {

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file);
}
