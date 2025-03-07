package com.ricardo.scalable.ecommerce.platform.product_service.storageService;

import java.io.File;
import java.util.Optional;

public interface StorageService {

    Optional<String> store(File file);

    String getImageUrl(String fileName);

    Optional<String> delete(String filename);

}
