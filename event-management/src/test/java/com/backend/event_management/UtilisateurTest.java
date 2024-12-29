package com.backend.event_management;

import org.junit.jupiter.api.Test;

import com.backend.event_management.model.Role;
import com.backend.event_management.model.Utilisateur;

import static org.junit.jupiter.api.Assertions.*;

class UtilisateurTest {

    @Test
    void testGetAuthorities() {
        // Arrange
        Utilisateur utilisateur = Utilisateur.builder()
                .role(Role.ADMIN)
                .build();

        // Act
        var authorities = utilisateur.getAuthorities();

        // Assert
        assertEquals(1, authorities.size());
        assertEquals("ADMIN", authorities.iterator().next().getAuthority());
    }

    @Test
    void testIsAccountNonExpired() {
        // Arrange
        Utilisateur utilisateur = new Utilisateur();

        // Act & Assert
        assertTrue(utilisateur.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        // Arrange
        Utilisateur utilisateur = new Utilisateur();

        // Act & Assert
        assertTrue(utilisateur.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        // Arrange
        Utilisateur utilisateur = new Utilisateur();

        // Act & Assert
        assertTrue(utilisateur.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        // Arrange
        Utilisateur utilisateur = new Utilisateur();

        // Act & Assert
        assertTrue(utilisateur.isEnabled());
    }

    @Test
    void testGetPassword() {
        // Arrange
        String password = "securePassword";
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setMotDePasse(password);

        // Act & Assert
        assertEquals(password, utilisateur.getPassword());
    }

    @Test
    void testGetUsername() {
        // Arrange
        String email = "user@example.com";
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(email);

        // Act & Assert
        assertEquals(email, utilisateur.getUsername());
    }

    @Test
    void testSetUsername() {
        // Arrange
        String newEmail = "newuser@example.com";
        Utilisateur utilisateur = new Utilisateur();

        // Act
        utilisateur.setUsername(newEmail);

        // Assert
        assertEquals(newEmail, utilisateur.getEmail());
    }

    @Test
    void testIsPresent() {
        // Arrange
        Utilisateur utilisateur = new Utilisateur();

        // Act & Assert
        assertTrue(utilisateur.isPresent());
    }
}
