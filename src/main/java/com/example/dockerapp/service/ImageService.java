package com.example.dockerapp.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ImageService {

    private final Path uploadFolder = Paths.get("app/src/main/resources/images");
    public String upload(MultipartFile file) throws IOException {
        file.transferTo(uploadFolder.resolve(file.getOriginalFilename()));
        return file.getOriginalFilename();
    }

    public void remove(String fileName) throws IOException {
        Path targetPath = uploadFolder.resolve(fileName);
        Files.delete(targetPath);
    }
}
