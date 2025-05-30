package com.board.notice.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

public class FileUtil {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            if (fileName.contains("..")) {
                throw new IllegalArgumentException("Filename contains invalid path sequence " + fileName);
            }

            String storedFileName = UUID.randomUUID().toString() + "_" + fileName;
            Path targetLocation = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(storedFileName);
            Files.createDirectories(targetLocation.getParent());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return storedFileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public void deleteFile(String storedFileName) {
        try {
            Path filePath = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(storedFileName);
            Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            System.err.println("Failed to delete file: " + storedFileName + " - " + ex.getMessage());
        }
    }

    public String getFileUrl(String storedFileName) {
        return "/files/" + storedFileName;
    }

}
