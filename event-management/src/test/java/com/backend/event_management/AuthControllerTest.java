package com.backend.event_management;

import com.backend.event_management.controller.AuthController;
import com.backend.event_management.model.ReponseAuthentification;
import com.backend.event_management.model.Utilisateur;
import com.backend.event_management.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class AuthControllerTest {

    @Mock
    private AuthenticationService authService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testRegister() throws Exception {
        // Arrange
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("testuser@example.com");
        utilisateur.setMotDePasse("password");

        ReponseAuthentification reponse = new ReponseAuthentification("token123", "Registration successful", null);

        when(authService.register(any(Utilisateur.class))).thenReturn(reponse);

        // Act and Assert
        mockMvc.perform(post("/register")
                .contentType("application/json")
                .content("{\"email\":\"testuser@example.com\",\"motDePasse\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Registration successful"))
                .andExpect(jsonPath("$.token").value("token123"));

        verify(authService, times(1)).register(any(Utilisateur.class));
    }

    @Test
    void testLogin() throws Exception {
        // Arrange
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("testuser@example.com");
        utilisateur.setMotDePasse("password");

        ReponseAuthentification reponse = new ReponseAuthentification("token123", "Login successful", null);

        when(authService.authenticate(any(Utilisateur.class))).thenReturn(reponse);

        // Act and Assert
        mockMvc.perform(post("/login")
                .contentType("application/json")
                .content("{\"email\":\"testuser@example.com\",\"motDePasse\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.token").value("token123"));

        verify(authService, times(1)).authenticate(any(Utilisateur.class));
    }
}
