package com.backend.event_management;

import com.backend.event_management.controller.EvenementController;
import com.backend.event_management.dto.EvenementDTO;
import com.backend.event_management.model.Utilisateur;
import com.backend.event_management.service.EvenementService;
import com.backend.event_management.service.StorageService;
import com.backend.event_management.service.UtilisateurService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EvenementControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UtilisateurService utilisateurService;

    @Mock
    private EvenementService evenementService;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private EvenementController evenementController;

    private ObjectMapper objectMapper;

    private final String jwtToken = "Bearer mock-jwt-token";

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(evenementController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testUploadImage() throws Exception {
        MultipartFile file = new MockMultipartFile("image", "test.jpg", "image/jpeg", new byte[1]);
        String savedFilePath = "path/to/saved/file.jpg";

        when(storageService.uploadImage(file)).thenReturn(savedFilePath);

        mockMvc.perform(multipart("/api/evenements/upload")
                .file((MockMultipartFile) file)
                .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(content().string("Image saved at: " + savedFilePath));

        verify(storageService, times(1)).uploadImage(file);
    }

    @Test
    void testJoinEvent() throws Exception {
        Utilisateur user = new Utilisateur();
        user.setId(1L);
        user.setEmail("test@example.com");
        Long evenementId = 5L;

        when(utilisateurService.getUtilisateurByEmail("test@example.com")).thenReturn(Optional.of(user));

    }

    @Test
    void testCreateEvenement() throws Exception {
        Utilisateur user = new Utilisateur();
        user.setId(1L);
        user.setEmail("test@example.com");

        EvenementDTO evenementDTO = new EvenementDTO();
        evenementDTO.setCreateurId(user.getId());
        evenementDTO.setParticipantIds(List.of(user.getId()));

        MultipartFile file = new MockMultipartFile("image", "test.jpg", "image/jpeg", new byte[1]);
        EvenementDTO savedEvent = new EvenementDTO();
        savedEvent.setCreateurId(user.getId());

        when(utilisateurService.getUtilisateurByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(evenementService.saveEvenement(any(EvenementDTO.class), eq(file))).thenReturn(savedEvent);

        mockMvc.perform(multipart("/api/evenements")
                .file((MockMultipartFile) file)
                .param("evenement", objectMapper.writeValueAsString(evenementDTO))
                .header("Authorization", jwtToken));

    }

    @Test
    void testGetAllEvenements() throws Exception {
        EvenementDTO evenementDTO = new EvenementDTO();
        List<EvenementDTO> evenementList = List.of(evenementDTO);

        when(evenementService.getAllEvenements()).thenReturn(evenementList);

        mockMvc.perform(get("/api/evenements")
                .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());

        verify(evenementService, times(1)).getAllEvenements();
    }

    @Test
    void testGetEvenementsByUserId() throws Exception {
        Utilisateur user = new Utilisateur();
        user.setId(1L);
        user.setEmail("test@example.com");

        EvenementDTO evenementDTO = new EvenementDTO();
        List<EvenementDTO> evenementList = List.of(evenementDTO);

        when(utilisateurService.getUtilisateurByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(evenementService.getEvenementsByUserId(user.getId())).thenReturn(evenementList);

    }

    @Test
    void testGetEvenementById() throws Exception {
        EvenementDTO evenementDTO = new EvenementDTO();
        when(evenementService.getEvenementById(1L)).thenReturn(evenementDTO);

        mockMvc.perform(get("/api/evenements/{id}", 1L)
                .header("Authorization", jwtToken))
                .andExpect(status().isOk());

        verify(evenementService, times(1)).getEvenementById(1L);
    }
}
