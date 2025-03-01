package com.ricardo.scalable.ecommerce.platform.product_service.storageService;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class S3StorageService implements StorageService {

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Override
    public Optional<String> store(File file) {
        try {
            s3Client.putObject(request -> 
            request
                .bucket(bucketName)
                .key(file.getName())
                .acl("public-read")
                .ifNoneMatch("*"),
            file.toPath());

            return Optional.of("Imagen cargada correctamente");
        } catch (S3Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public String getImageUrl(String fileName) {
        String url = s3Client.utilities()
                .getUrl(builder -> 
                    builder.bucket(bucketName)
                            .key(fileName)
                ).toString();
        return url;
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
