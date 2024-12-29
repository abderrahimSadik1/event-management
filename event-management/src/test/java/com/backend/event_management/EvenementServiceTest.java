package com.backend.event_management;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.backend.event_management.dto.EvenementDTO;
import com.backend.event_management.model.Evenement;
import com.backend.event_management.model.Utilisateur;
import com.backend.event_management.repository.EvenementRepository;
import com.backend.event_management.repository.UtilisateurRepository;
import com.backend.event_management.service.EvenementService;
import com.backend.event_management.service.StorageService;

class EvenementServiceTest {

    @Mock
    private EvenementRepository evenementRepository;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private EvenementService evenementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveEvenement_shouldReturnSavedEvenementDTO() throws IOException {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(storageService.uploadImage(file)).thenReturn(System.getProperty("user.dir")
                + "/src/main/java/com/backend/event_management/image/");

        Utilisateur createur = new Utilisateur();
        createur.setId(1L);

        Utilisateur participant = new Utilisateur();
        participant.setId(2L);

        EvenementDTO evenementDTO = new EvenementDTO();
        evenementDTO.setNom("Test Event");
        evenementDTO.setDescription("Test Description");
        evenementDTO.setDate(java.sql.Date.valueOf(LocalDate.now()));
        evenementDTO.setLieu("Test Location");
        evenementDTO.setCreateurId(1L);
        evenementDTO.setParticipantIds(Arrays.asList(2L));

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(createur));
        when(utilisateurRepository.findAllById(evenementDTO.getParticipantIds()))
                .thenReturn(Arrays.asList(participant));

        Evenement savedEvenement = new Evenement();
        savedEvenement.setId(1L);
        savedEvenement.setNom("Test Event");
        savedEvenement.setDescription("Test Description");
        savedEvenement.setDate(java.sql.Date.valueOf(LocalDate.now()));
        savedEvenement.setLieu("Test Location");
        savedEvenement.setImageUrl(System.getProperty("user.dir")
                + "/src/main/java/com/backend/event_management/image/");
        savedEvenement.setCreateur(createur);
        savedEvenement.setParticipants(Arrays.asList(participant));

        when(evenementRepository.save(any(Evenement.class))).thenReturn(savedEvenement);

        // Act
        EvenementDTO result = evenementService.saveEvenement(evenementDTO, file);

        // Assert
        assertNotNull(result);
        assertEquals("Test Event", result.getNom());
        verify(storageService, times(1)).uploadImage(file);
        verify(evenementRepository, times(1)).save(any(Evenement.class));
    }

    @Test
    void getAllEvenements_shouldReturnListOfEvenementDTOs_singleEvent() {
        // Arrange
        Utilisateur createur = new Utilisateur();
        createur.setId(1L);

        Utilisateur user = new Utilisateur();
        user.setId(2L);
        List<Utilisateur> participants = new ArrayList<Utilisateur>();
        participants.add(user);
        Evenement evenement = new Evenement();
        evenement.setId(1L);
        evenement.setNom("Test Event");
        evenement.setImageUrl("image/path");
        evenement.setCreateur(createur);
        evenement.setParticipants(participants);

        when(evenementRepository.findAll()).thenReturn(Arrays.asList(evenement));

        // Act
        List<EvenementDTO> evenements = evenementService.getAllEvenements();

        // Assert
        assertNotNull(evenements);
        assertEquals(1, evenements.size());
        assertEquals("Test Event", evenements.get(0).getNom());
        verify(evenementRepository, times(1)).findAll();
    }

    @Test
    void getEvenementById_shouldReturnEvenementDTO_whenEventExists() {
        // Arrange
        Utilisateur createur = new Utilisateur();
        createur.setId(1L);

        Utilisateur user = new Utilisateur();
        user.setId(2L);
        List<Utilisateur> participants = new ArrayList<Utilisateur>();
        participants.add(user);
        Evenement evenement = new Evenement();
        evenement.setId(1L);
        evenement.setNom("Test Event");
        evenement.setImageUrl("image/path");
        evenement.setCreateur(createur);
        evenement.setParticipants(participants);

        when(evenementRepository.findById(1L)).thenReturn(Optional.of(evenement));

        // Act
        EvenementDTO result = evenementService.getEvenementById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Test Event", result.getNom());
        verify(evenementRepository, times(1)).findById(1L);
    }

    @Test
    void getEvenementById_shouldThrowException_whenEventDoesNotExist() {
        // Arrange
        when(evenementRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> evenementService.getEvenementById(1L));
        assertEquals("Event not found", exception.getMessage());
        verify(evenementRepository, times(1)).findById(1L);
    }

    @Test
    void deleteEvenement_shouldDeleteEvent_whenEventExists() {
        // Arrange
        Evenement evenement = new Evenement();
        evenement.setId(1L);

        when(evenementRepository.findById(1L)).thenReturn(Optional.of(evenement));

        // Act
        evenementService.deleteEvenement(1L);

        // Assert
        verify(evenementRepository, times(1)).delete(evenement);
    }
}
