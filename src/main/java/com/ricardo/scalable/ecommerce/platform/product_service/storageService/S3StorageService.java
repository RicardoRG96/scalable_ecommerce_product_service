package com.ricardo.scalable.ecommerce.platform.product_service.storageService;

import java.io.File;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.exception.SdkException;
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
    public Optional<String> getImageUrl(String fileName) {
        try {
            String url = s3Client.utilities()
                .getUrl(builder -> 
                    builder.bucket(bucketName)
                            .key(fileName)
                ).toString();
            return Optional.of(url);
        } catch (SdkException e) {
            e.printStackTrace();
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> delete(String filename) {
        try {
            s3Client.deleteObject(request -> 
                request
                    .bucket(bucketName)
                    .key(filename)
            );
            return Optional.of("Imagen eliminada correctamente");
        } catch (S3Exception e) {
            e.printStackTrace();
            return Optional.empty();
        } catch (SdkClientException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}
