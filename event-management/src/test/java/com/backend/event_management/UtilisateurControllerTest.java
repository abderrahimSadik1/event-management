package com.backend.event_management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.backend.event_management.controller.UtilisateurController;
import com.backend.event_management.model.Role;
import com.backend.event_management.model.Utilisateur;
import com.backend.event_management.service.UtilisateurService;
import com.fasterxml.jackson.databind.ObjectMapper;

class UtilisateurControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UtilisateurService utilisateurService;

    @InjectMocks
    private UtilisateurController utilisateurController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private final String jwtToken = "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzYWRpa0BhYmRlcnJhaGltLmNvbSIsInJvbGUiOlt7ImF1dGhvcml0eSI6IlVTRVIifV0sImlhdCI6MTczNTUwNTk2OSwiZXhwIjoxNzM1NzY1MTY5fQ.X6U5XzUqUWCiuiVWF8vX_SQ_u_Wkmuvl1Gp0qmQHK-FfiQTg0g_gL9aNdAtbEfQY";

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(utilisateurController).build();
    }

    @Test
    void testCreateUtilisateur() throws Exception {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setEmail("newuser@example.com");
        utilisateur.setRole(Role.USER);
        when(utilisateurService.saveUtilisateur(any(Utilisateur.class))).thenReturn(utilisateur);

        mockMvc.perform(post("/api/utilisateurs")
                .header("Authorization", jwtToken));
    }

    @Test
    void testGetUtilisateurById() throws Exception {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setEmail("test@example.com");

        when(utilisateurService.getUtilisateurById(1L)).thenReturn(Optional.of(utilisateur));

        mockMvc.perform(get("/api/utilisateurs/1")
                .header("Authorization", jwtToken));
    }

    @Test
    void testDeleteUtilisateur() throws Exception {
        when(utilisateurService.getUtilisateurById(1L)).thenReturn(Optional.of(new Utilisateur()));

        mockMvc.perform(delete("/api/utilisateurs/1")
                .header("Authorization", jwtToken));
    }
}
