package com.backend.event_management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.backend.event_management.config.JwtService;
import com.backend.event_management.model.ReponseAuthentification;
import com.backend.event_management.model.Role;
import com.backend.event_management.model.Utilisateur;
import com.backend.event_management.repository.UtilisateurRepository;
import com.backend.event_management.service.AuthenticationService;

class AuthenticationServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_shouldReturnSuccessResponse_whenUserDoesNotExist() {
        // Arrange
        Utilisateur user = new Utilisateur();
        user.setUsername("test@example.com");
        user.setMotDePasse("password");
        user.setRole(Role.USER); // This line should set the role correctly.

        when(utilisateurRepository.findByEmail(user.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        // Act
        ReponseAuthentification response = authenticationService.register(user);

        // Assert
        assertEquals("User registration was successful", response.getMessage());
        assertEquals("jwtToken", response.getToken());
        assertEquals(Role.USER, response.getRole());
        verify(utilisateurRepository, times(1)).save(any(Utilisateur.class));
    }

    @Test
    void register_shouldReturnErrorResponse_whenUserAlreadyExists() {
        // Arrange
        Utilisateur user = new Utilisateur();
        user.setUsername("test@example.com");
        // This line should set the role correctly.

        when(utilisateurRepository.findByEmail(user.getUsername())).thenReturn(Optional.of(user));

        // Act
        ReponseAuthentification response = authenticationService.register(user);

        // Assert
        assertEquals("User already exist", response.getMessage());
        assertEquals(null, response.getToken());
        verify(utilisateurRepository, never()).save(any(Utilisateur.class));
    }

    @Test
    void authenticate_shouldReturnSuccessResponse_whenCredentialsAreValid() {
        // Arrange
        Utilisateur user = new Utilisateur();
        user.setUsername("test@example.com");
        user.setMotDePasse("password");

        when(utilisateurRepository.findByEmail(user.getUsername())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        // Act
        ReponseAuthentification response = authenticationService.authenticate(user);

        // Assert
        assertEquals("User login was successful", response.getMessage());
        assertEquals("jwtToken", response.getToken());
        verify(authenticationManager, times(1)).authenticate(any());
    }
}
