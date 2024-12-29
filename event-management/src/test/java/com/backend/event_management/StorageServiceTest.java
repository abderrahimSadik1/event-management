package com.backend.event_management;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;

import com.backend.event_management.service.StorageService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StorageServiceTest {

    @InjectMocks
    private StorageService storageService;

    @Mock
    private MultipartFile multipartFile;

    private String folderPath;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        folderPath = System.getProperty("user.dir") + "/src/main/java/com/backend/event_management/image/";
    }

    @Test
    void uploadImage_shouldThrowExceptionIfFileIsEmpty() {
        // Arrange
        when(multipartFile.isEmpty()).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            storageService.uploadImage(multipartFile);
        });
        assertEquals("Uploaded file is empty", exception.getMessage());
    }

    @Test
    void uploadImage_shouldCreateFileSuccessfully() throws IOException {
        // Arrange
        byte[] fileBytes = "Sample content".getBytes();
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getBytes()).thenReturn(fileBytes);
        when(multipartFile.getOriginalFilename()).thenReturn("testImage.jpg");

        // Mocking the directory creation and file creation steps
        File directory = new File(folderPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Ensure directory exists in test
        }

        // Act
        String filePath = storageService.uploadImage(multipartFile);

        // Assert
        assertNotNull(filePath);
        assertTrue(filePath.contains("testImage.jpg"));
        assertTrue(new File(filePath).exists()); // Check if the file is created in the mock directory
    }

    @Test
    void getImageBytes_shouldReturnFileBytes() throws IOException {
        // Arrange
        String filename = "testImage.jpg";
        byte[] expectedBytes = "Sample content".getBytes();
        File file = new File(folderPath + filename);
        Files.write(file.toPath(), expectedBytes); // Write mock content to file

        // Act
        byte[] result = storageService.getImageBytes(filename);

        // Assert
        assertArrayEquals(expectedBytes, result);
    }

    @Test
    void getImageBytes_shouldThrowExceptionIfFileNotFound() {
        // Arrange
        String filename = "nonExistentImage.jpg";

        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> {
            storageService.getImageBytes(filename);
        });
        assertEquals("File not found", exception.getMessage());
    }
}
