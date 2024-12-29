package com.backend.event_management;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import com.backend.event_management.model.Utilisateur;
import com.backend.event_management.repository.UtilisateurRepository;
import com.backend.event_management.service.UtilisateurService;

public class UtilisateurServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private UtilisateurService utilisateurService;

    private Utilisateur utilisateur;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setEmail("user@example.com");
        utilisateur.setNom("John");
        utilisateur.setPrenom("Doe");
    }

    @Test
    void getUtilisateurByEmail_shouldReturnUtilisateur() {
        // Arrange
        when(utilisateurRepository.findByEmail("user@example.com")).thenReturn(Optional.of(utilisateur));

        // Act
        Optional<Utilisateur> result = utilisateurService.getUtilisateurByEmail("user@example.com");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("user@example.com", result.get().getEmail());
    }

    @Test
    void getUtilisateurByEmail_shouldReturnEmptyWhenNotFound() {
        // Arrange
        when(utilisateurRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // Act
        Optional<Utilisateur> result = utilisateurService.getUtilisateurByEmail("nonexistent@example.com");

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void getUtilisateurById_shouldReturnUtilisateur() {
        // Arrange
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));

        // Act
        Optional<Utilisateur> result = utilisateurService.getUtilisateurById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void getUtilisateurById_shouldReturnEmptyWhenNotFound() {
        // Arrange
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Utilisateur> result = utilisateurService.getUtilisateurById(1L);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void getAllUtilisateurs_shouldReturnListOfUtilisateurs() {
        // Arrange
        when(utilisateurRepository.findAll()).thenReturn(List.of(utilisateur));

        // Act
        Iterable<Utilisateur> result = utilisateurService.getAllUtilisateurs();

        // Assert
        assertNotNull(result);
        assertTrue(result.iterator().hasNext()); // Ensure the list is not empty
    }

    @Test
    void saveUtilisateur_shouldSaveUtilisateur() {
        // Arrange
        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(utilisateur);

        // Act
        Utilisateur result = utilisateurService.saveUtilisateur(utilisateur);

        // Assert
        assertNotNull(result);
        assertEquals("user@example.com", result.getEmail());
        verify(utilisateurRepository, times(1)).save(utilisateur);
    }

    @Test
    void deleteUtilisateur_shouldDeleteUtilisateur() {
        // Arrange
        doNothing().when(utilisateurRepository).deleteById(1L);

        // Act
        utilisateurService.deleteUtilisateur(1L);

        // Assert
        verify(utilisateurRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateUtilisateur_shouldUpdateUtilisateur() {
        // Arrange
        Utilisateur updatedUtilisateur = new Utilisateur();
        updatedUtilisateur.setEmail("updated@example.com");
        updatedUtilisateur.setNom("John Updated");
        updatedUtilisateur.setPrenom("Doe Updated");

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));
        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(updatedUtilisateur);

        // Act
        Utilisateur result = utilisateurService.updateUtilisateur(1L, updatedUtilisateur);

        // Assert
        assertNotNull(result);
        assertEquals("updated@example.com", result.getEmail());
        assertEquals("John Updated", result.getNom());
        assertEquals("Doe Updated", result.getPrenom());
    }

    @Test
    void updateUtilisateur_shouldReturnNullWhenUserNotFound() {
        // Arrange
        Utilisateur updatedUtilisateur = new Utilisateur();
        updatedUtilisateur.setEmail("updated@example.com");

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Utilisateur result = utilisateurService.updateUtilisateur(1L, updatedUtilisateur);

        // Assert
        assertNull(result); // User not found, should return null
    }
}
