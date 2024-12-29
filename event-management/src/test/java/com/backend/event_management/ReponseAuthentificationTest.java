package com.backend.event_management;

import org.junit.jupiter.api.Test;

import com.backend.event_management.model.ReponseAuthentification;
import com.backend.event_management.model.Role;

import static org.junit.jupiter.api.Assertions.*;

class ReponseAuthentificationTest {

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String token = "sampleToken";
        String message = "Authentication successful";
        Role role = Role.ADMIN;

        // Act
        ReponseAuthentification response = new ReponseAuthentification(token, message, role);

        // Assert
        assertEquals(token, response.getToken());
        assertEquals(message, response.getMessage());
        assertEquals(role, response.getRole());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        ReponseAuthentification response = new ReponseAuthentification();

        // Assert (checks that all fields are null or default)
        assertNull(response.getToken());
        assertNull(response.getMessage());
        assertNull(response.getRole());
    }

    @Test
    void testBuilder() {
        // Arrange
        String token = "sampleToken";
        String message = "Authentication successful";
        Role role = Role.USER;

        // Act
        ReponseAuthentification response = ReponseAuthentification.builder()
                .token(token)
                .message(message)
                .role(role)
                .build();

        // Assert
        assertEquals(token, response.getToken());
        assertEquals(message, response.getMessage());
        assertEquals(role, response.getRole());
    }

    @Test
    void testGetToken() {
        // Arrange
        String token = "sampleToken";
        String message = "Authentication successful";
        Role role = Role.USER;
        ReponseAuthentification response = new ReponseAuthentification(token, message, role);

        // Act and Assert
        assertEquals(token, response.getToken());
    }

    @Test
    void testGetMessage() {
        // Arrange
        String token = "sampleToken";
        String message = "Authentication successful";
        Role role = Role.USER;
        ReponseAuthentification response = new ReponseAuthentification(token, message, role);

        // Act and Assert
        assertEquals(message, response.getMessage());
    }

    @Test
    void testGetRole() {
        // Arrange
        String token = "sampleToken";
        String message = "Authentication successful";
        Role role = Role.ADMIN;
        ReponseAuthentification response = new ReponseAuthentification(token, message, role);

        // Act and Assert
        assertEquals(role, response.getRole());
    }

    @Test
    void testSetRole() {
        // Arrange
        String token = "sampleToken";
        String message = "Authentication successful";
        Role initialRole = Role.USER;
        ReponseAuthentification response = new ReponseAuthentification(token, message, initialRole);

        // Act
        Role newRole = Role.ADMIN;
        response.setRole(newRole);

        // Assert
        assertEquals(newRole, response.getRole());
    }
}
