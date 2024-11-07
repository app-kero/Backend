package com.appkero.backend_kero.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import com.appkero.backend_kero.utils.CommonsUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;

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

    private final String defaultFolder = "uploads";

    private MinioClient minioClient;

    // Configura o MinioClient após a injeção dos valores @Value
    @PostConstruct
    private void initializeMinioClient() {
        this.minioClient = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey, secretKey)
                .build();
    }

    public String uploadFile(MultipartFile file) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        var now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        var folderPath = Path.of(bucketName, defaultFolder, now);
        String filename = UUID.randomUUID().toString() + "_" + CommonsUtils.normalizeFileName(file.getOriginalFilename());
        var path = "S3:" + folderPath.resolve(filename);
        var filePath = splitPathInBucketInKeyName(path).get(1);
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(filePath)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );
        return path;
    }

    public InputStream downloadFile(String filePath) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        var data = splitPathInBucketInKeyName(filePath);
        var bucket = data.get(0);
        var path = data.get(1);
        return minioClient.getObject(
                GetObjectArgs.builder().bucket(bucket)
                        .object(path)
                        .build()
        );
    }

    /**
     *
     * @param s3FilePath String
     * @return List<String> [0]  bucket [1] file path
     */
    public List<String> splitPathInBucketInKeyName(String s3FilePath) {
        var path = s3FilePath.split(":")[1];
        var bucket = path.split("/")[0];
        path = path.substring(path.indexOf("/") + 1);
        return List.of(bucket, path);
    }
}
