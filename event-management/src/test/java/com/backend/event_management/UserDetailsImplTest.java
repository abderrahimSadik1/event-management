package com.backend.event_management;

import com.backend.event_management.model.Utilisateur;
import com.backend.event_management.repository.UtilisateurRepository;
import com.backend.event_management.service.impl.UserDetailsImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailsImplTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private UserDetailsImpl userDetailsImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_UserFound() {
        // Arrange
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("test@example.com");
        when(utilisateurRepository.findByEmail("test@example.com")).thenReturn(Optional.of(utilisateur));

        // Act
        UserDetails userDetails = userDetailsImpl.loadUserByUsername("test@example.com");

        // Assert
        assertNotNull(userDetails);
        assertEquals(utilisateur, userDetails);
        verify(utilisateurRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Arrange
        when(utilisateurRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> userDetailsImpl.loadUserByUsername("notfound@example.com"));

        assertEquals("email not found", exception.getMessage());
        verify(utilisateurRepository, times(1)).findByEmail("notfound@example.com");
    }
}
