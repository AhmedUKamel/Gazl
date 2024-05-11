package org.ahmedukamel.gazl.service.image;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface IImageService {
    String saveImage(MultipartFile image, Path path) throws IOException;

    byte[] readImage(Path path) throws IOException;

    void deleteImage(Path path) throws IOException;
}