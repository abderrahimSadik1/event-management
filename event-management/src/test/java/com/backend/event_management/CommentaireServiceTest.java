package com.backend.event_management;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.backend.event_management.model.Commentaire;
import com.backend.event_management.model.Evenement;
import com.backend.event_management.repository.CommentaireRepository;
import com.backend.event_management.service.CommentaireService;

class CommentaireServiceTest {

    @Mock
    private CommentaireRepository commentaireRepository;

    @InjectMocks
    private CommentaireService commentaireService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCommentaire_shouldReturnSavedCommentaire() {
        // Arrange
        Commentaire commentaire = new Commentaire();
        commentaire.setId(1L);
        commentaire.setContenu("Test message");

        when(commentaireRepository.save(commentaire)).thenReturn(commentaire);

        // Act
        Commentaire savedCommentaire = commentaireService.saveCommentaire(commentaire);

        // Assert
        assertNotNull(savedCommentaire);
        assertEquals(1L, savedCommentaire.getId());
        assertEquals("Test message", savedCommentaire.getContenu());
        verify(commentaireRepository, times(1)).save(commentaire);
    }

    @Test
    void getAllCommentaires_shouldReturnListOfCommentaires() {
        // Arrange
        Commentaire commentaire1 = new Commentaire();
        commentaire1.setId(1L);
        commentaire1.setContenu("Message 1");

        Commentaire commentaire2 = new Commentaire();
        commentaire2.setId(2L);
        commentaire2.setContenu("Message 2");

        when(commentaireRepository.findAll()).thenReturn(Arrays.asList(commentaire1, commentaire2));

        // Act
        List<Commentaire> commentaires = commentaireService.getAllCommentaires();

        // Assert
        assertNotNull(commentaires);
        assertEquals(2, commentaires.size());
        verify(commentaireRepository, times(1)).findAll();
    }

    @Test
    void getCommentaireById_shouldReturnCommentaire_whenIdExists() {
        // Arrange
        Commentaire commentaire = new Commentaire();
        commentaire.setId(1L);
        commentaire.setContenu("Test message");

        when(commentaireRepository.findById(1L)).thenReturn(Optional.of(commentaire));

        // Act
        Commentaire foundCommentaire = commentaireService.getCommentaireById(1L);

        // Assert
        assertNotNull(foundCommentaire);
        assertEquals(1L, foundCommentaire.getId());
        assertEquals("Test message", foundCommentaire.getContenu());
        verify(commentaireRepository, times(1)).findById(1L);
    }

    @Test
    void getCommentaireById_shouldThrowException_whenIdDoesNotExist() {
        // Arrange
        when(commentaireRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> commentaireService.getCommentaireById(1L));
        assertEquals("Commentaire not found", exception.getMessage());
        verify(commentaireRepository, times(1)).findById(1L);
    }

    @Test
    void deleteCommentaire_shouldCallRepositoryDeleteById() {
        // Act
        commentaireService.deleteCommentaire(1L);

        // Assert
        verify(commentaireRepository, times(1)).deleteById(1L);
    }

    @Test
    void getCommentairesByEvenementId_shouldReturnCommentaires() {
        // Arrange
        Evenement evenement = new Evenement();
        evenement.setId(10L);
        Commentaire commentaire1 = new Commentaire();
        commentaire1.setId(1L);
        commentaire1.setContenu("Message 1");
        commentaire1.setEvenement(evenement);

        Commentaire commentaire2 = new Commentaire();
        commentaire2.setId(2L);
        commentaire2.setContenu("Message 2");
        commentaire2.setEvenement(evenement);

        when(commentaireRepository.findByEvenementId(10L)).thenReturn(Arrays.asList(commentaire1, commentaire2));

        // Act
        List<Commentaire> commentaires = commentaireService.getCommentairesByEvenementId(10L);

        // Assert
        assertNotNull(commentaires);
        assertEquals(2, commentaires.size());
        verify(commentaireRepository, times(1)).findByEvenementId(10L);
    }
}
