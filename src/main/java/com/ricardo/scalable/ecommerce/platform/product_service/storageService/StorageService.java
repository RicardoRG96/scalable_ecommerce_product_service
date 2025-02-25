package com.ricardo.scalable.ecommerce.platform.product_service.storageService;

import java.io.File;
import java.nio.file.Path;

public interface StorageService {

    void store(File file);

    byte[] download(String filename, Path downloadPath);

    void delete(String filename);

    void deleteAll();

}
