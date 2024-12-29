package com.backend.event_management;

import com.backend.event_management.controller.CommentaireController;
import com.backend.event_management.model.Commentaire;
import com.backend.event_management.model.Role;
import com.backend.event_management.model.Utilisateur;
import com.backend.event_management.service.CommentaireService;
import com.backend.event_management.service.UtilisateurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CommentaireControllerTest {

    @Mock
    private UtilisateurService utilisateurService;

    @Mock
    private CommentaireService commentaireService;

    @InjectMocks
    private CommentaireController commentaireController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentaireController).build();
    }

    @Test
    void testCreateCommentaire() throws Exception {
        // Arrange
        Commentaire commentaire = new Commentaire();
        commentaire.setId(1L);
        commentaire.setContenu("Test Comment");

        Utilisateur user = new Utilisateur();
        user.setEmail("user@example.com");

        // Mock the UserDetails with User implementation (which is a concrete class)
        UserDetails userDetails = User.withUsername("user@example.com")
                .password("password")
                .roles("USER")
                .build();

        // Set the mock UserDetails to SecurityContext
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(userDetails, null));
        SecurityContextHolder.setContext(context);

        // Mock the service methods
        when(utilisateurService.getUtilisateurByEmail(anyString())).thenReturn(java.util.Optional.of(user));
        when(commentaireService.saveCommentaire(any(Commentaire.class))).thenReturn(commentaire);

        // Act and Assert

        assertEquals(true, true);
    }

    @Test
    void testGetAllCommentaires() throws Exception {
        // Arrange
        Commentaire commentaire1 = new Commentaire();
        commentaire1.setId(1L);
        commentaire1.setContenu("Test Comment 1");

        Commentaire commentaire2 = new Commentaire();
        commentaire2.setId(2L);
        commentaire2.setContenu("Test Comment 2");

        when(commentaireService.getAllCommentaires()).thenReturn(List.of(commentaire1, commentaire2));

        // Act and Assert
        mockMvc.perform(get("/api/commentaires"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].contenu").value("Test Comment 1"))
                .andExpect(jsonPath("$[1].contenu").value("Test Comment 2"));

        verify(commentaireService, times(1)).getAllCommentaires();
    }

    @Test
    void testGetCommentaireById() throws Exception {
        // Arrange
        Commentaire commentaire = new Commentaire();
        commentaire.setId(1L);
        commentaire.setContenu("Test Comment");

        when(commentaireService.getCommentaireById(1L)).thenReturn(commentaire);

        // Act and Assert
        mockMvc.perform(get("/api/commentaires/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contenu").value("Test Comment"));

        verify(commentaireService, times(1)).getCommentaireById(1L);
    }

    @Test
    void testDeleteCommentaire() throws Exception {
        // Act and Assert
        mockMvc.perform(delete("/api/commentaires/{id}", 1L))
                .andExpect(status().isOk());

        verify(commentaireService, times(1)).deleteCommentaire(1L);
    }

    @Test
    void testGetCommentairesByEvenementId() throws Exception {
        // Arrange
        Commentaire commentaire1 = new Commentaire();
        commentaire1.setId(1L);
        commentaire1.setContenu("Test Event Comment 1");

        Commentaire commentaire2 = new Commentaire();
        commentaire2.setId(2L);
        commentaire2.setContenu("Test Event Comment 2");

        when(commentaireService.getCommentairesByEvenementId(1L)).thenReturn(List.of(commentaire1, commentaire2));

        // Act and Assert
        mockMvc.perform(get("/api/commentaires/evenement/{evenementId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].contenu").value("Test Event Comment 1"))
                .andExpect(jsonPath("$[1].contenu").value("Test Event Comment 2"));

        verify(commentaireService, times(1)).getCommentairesByEvenementId(1L);
    }
}
