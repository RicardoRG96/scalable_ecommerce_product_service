package com.ricardo.scalable.ecommerce.platform.product_service.storageService;

import java.io.File;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class S3StorageService implements StorageService {

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Override
    public String store(File file) {
        try {
            s3Client.putObject(request -> 
            request
                .bucket(bucketName)
                .key(file.getName())
                .ifNoneMatch("*"),
            file.toPath());

            return "https://" + bucketName + ".s3.amazonaws.com/" + file.getName();
        } catch (S3Exception e) {
            e.printStackTrace();
            return "Error uploading file";
        }
    }

    @Override
    public byte[] download(String filename, Path downloadPath) {
        return null;
    }

    @Override
    public String delete(String filename) {
        return null;
    }

    @Override
    public String deleteAll() {
        return null;
    }

}
