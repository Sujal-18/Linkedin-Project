package com.sujal.LinkedInProject.uploader_service.service;


import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleCloudStorageUploaderService implements UploaderService{

    @Value("${gcloud.storage-bucket-name}")
    public String bucketName;

    private final Storage storage;

    @Override
    public String upload(MultipartFile file) {
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName,fileName).build();
        try {
            storage.create(blobInfo,file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return String.format("https://storage.googleapis.com/%s/%s",bucketName,fileName);
    }
}
