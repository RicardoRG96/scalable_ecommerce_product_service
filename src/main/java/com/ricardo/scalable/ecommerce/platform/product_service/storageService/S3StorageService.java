package com.ricardo.scalable.ecommerce.platform.product_service.storageService;

import java.io.File;
import java.nio.file.Path;

public class S3StorageService implements StorageService {

    @Override
    public void store(File file) {
        
    }

    @Override
    public byte[] download(String filename, Path downloadPath) {
        return null;
    }

    @Override
    public void delete(String filename) {
        
    }

    @Override
    public void deleteAll() {
        
    }

}
