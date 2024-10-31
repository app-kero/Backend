package com.appkero.backend_kero.services;

import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;

@Service
public class MinioService {
    
    @Value("${minio.url}")
    private String minioUrl;
    @Value("${minio.access.name}")
    private String accessKey;
    @Value("${minio.access.secret}")
    private String secretKey;
    @Value("${minio.bucket.name}")
    private String bucketName;

    private final MinioClient minioClient;

    public MinioService() {
        this.minioClient = MinioClient.builder()
            .endpoint("http://localhost:9000")
            .credentials("fbENmFn08ABIzhD8590W", "9DeMGjiXUaYjwi5qfN3qQjWQ9VrGWuHVb5EeXL3L")
            .build();
    }

    public String uploadFile(MultipartFile file) throws Exception {
        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
        minioClient.putObject(
            PutObjectArgs.builder().bucket("kero").object(filename)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build()
        );
        return filename;   
    }

    public InputStream downloadFile(String fileName) throws Exception {
        return minioClient.getObject(GetObjectArgs.builder()
            .bucket("kero")
            .object(fileName)
            .build()
        );   
    }
    
}
