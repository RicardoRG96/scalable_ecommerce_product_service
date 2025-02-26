package com.ricardo.scalable.ecommerce.platform.product_service.storageService;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public interface StorageService {

    Optional<String> store(File file);

    // Optional<String> store(MultipartFile file);

    byte[] download(String filename, Path downloadPath);

    String delete(String filename);

    String deleteAll();

}
