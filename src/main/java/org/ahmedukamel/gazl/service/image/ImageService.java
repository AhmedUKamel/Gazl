package org.ahmedukamel.gazl.service.image;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class ImageService implements IImageService {
    @Override
    public String saveImage(MultipartFile image, Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        Supplier<String> nameSupplier = () -> "%s.%s".formatted(
                UUID.randomUUID(), FilenameUtils.getExtension(image.getOriginalFilename())
        );

        String name;
        do {
            name = nameSupplier.get();
        } while (Files.exists(path.resolve(name)));

        Files.copy(image.getInputStream(), path.resolve(name));
        return name;
    }

    @Override
    public byte[] readImage(Path path) throws IOException {
        return Files.readAllBytes(path);
    }

    @Override
    public void deleteImage(Path path) throws IOException {
        Files.delete(path);
    }
}