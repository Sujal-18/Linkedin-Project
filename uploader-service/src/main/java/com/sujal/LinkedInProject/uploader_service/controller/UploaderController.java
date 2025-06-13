package com.sujal.LinkedInProject.uploader_service.controller;


import com.cloudinary.Uploader;
import com.sujal.LinkedInProject.uploader_service.service.GoogleCloudStorageUploaderService;
import com.sujal.LinkedInProject.uploader_service.service.UploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/file")
public class UploaderController {


    private final GoogleCloudStorageUploaderService googleCloudStorageUploaderService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file){

        String url = googleCloudStorageUploaderService.upload(file);
         return ResponseEntity.ok(url);

    }
}
