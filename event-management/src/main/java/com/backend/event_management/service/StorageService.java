package com.backend.event_management.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

    private final String folderPath = System.getProperty("user.dir")
            + "/src/main/java/com/backend/event_management/image/";

    public String uploadImage(MultipartFile file) throws IOException {
        // Validate the uploaded file
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        // Ensure the folder path exists
        File directory = new File(folderPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }

        // Generate a unique filename to avoid conflicts
        String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String filePath = folderPath + uniqueFileName;

        // Convert the MultipartFile to a java.io.File and write to disk
        File convFile = new File(filePath);
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace(); // Log the exception for debugging
            throw e; // Re-throw the exception for further handling
        }

        return filePath; // Return the saved file path
    }

    public byte[] getImageBytes(String filename) throws IOException {
        File file = new File(folderPath + filename);
        if (!file.exists()) {
            throw new IOException("File not found");
        }
        return Files.readAllBytes(file.toPath());
    }
}