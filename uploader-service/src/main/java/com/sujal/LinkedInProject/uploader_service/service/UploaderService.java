package com.sujal.LinkedInProject.uploader_service.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploaderService {

    public String upload(MultipartFile file);
}
