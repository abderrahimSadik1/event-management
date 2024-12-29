package com.backend.event_management.service;

import com.backend.event_management.dto.EvenementDTO;
import com.backend.event_management.model.Evenement;
import com.backend.event_management.model.Utilisateur;
import com.backend.event_management.repository.EvenementRepository;
import com.backend.event_management.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvenementService {

    @Autowired
    private EvenementRepository evenementRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private StorageService storageService;

    // Save an event with an image
    public EvenementDTO saveEvenement(EvenementDTO evenementDTO, MultipartFile file) throws IOException {
        // Upload the image and get the file path
        String imagePath = null;

        if (file != null && !file.isEmpty()) {
            // Handle the image upload
            imagePath = storageService.uploadImage(file);
        }

        // Find the creator by ID
        Utilisateur createur = utilisateurRepository.findById(evenementDTO.getCreateurId())
                .orElseThrow(() -> new RuntimeException("Createur not found"));

        // Find participants by IDs
        List<Utilisateur> participants = utilisateurRepository.findAllById(evenementDTO.getParticipantIds());

        // Map DTO to Entity
        Evenement evenement = new Evenement();
        evenement.setNom(evenementDTO.getNom());
        evenement.setDescription(evenementDTO.getDescription());
        evenement.setDate(evenementDTO.getDate());
        evenement.setLieu(evenementDTO.getLieu());
        evenement.setImageUrl(imagePath);
        evenement.setCreateur(createur);
        evenement.setParticipants(participants);

        // Save the event to the database
        evenement = evenementRepository.save(evenement);

        // Map Entity back to DTO
        return mapToDTO(evenement);
    }

    // Retrieve all events
    public List<EvenementDTO> getAllEvenements() {
        List<Evenement> evenements = evenementRepository.findAll();
        return evenements.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Retrieve an event by ID
    public EvenementDTO getEvenementById(Long id) {
        Evenement evenement = evenementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return mapToDTO(evenement);
    }

    // Register a user to an event
    public EvenementDTO inscrireUtilisateur(Long evenementId, Long utilisateurId) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!evenement.getParticipants().contains(utilisateur)) {
            evenement.getParticipants().add(utilisateur);
            evenement = evenementRepository.save(evenement);
        }

        return mapToDTO(evenement);
    }

    // Add a participant to an event
    public EvenementDTO addParticipant(Long evenementId, Utilisateur utilisateur) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (!evenement.getParticipants().contains(utilisateur)) {
            evenement.getParticipants().add(utilisateur);
            evenement = evenementRepository.save(evenement);
        }

        return mapToDTO(evenement);
    }

    // Retrieve events by user ID
    public List<EvenementDTO> getEvenementsByUserId(Long utilisateurId) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Evenement> evenements = evenementRepository.findByCreateur(utilisateur);
        return evenements.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Delete an event by ID
    public void deleteEvenement(Long evenementId) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        evenementRepository.delete(evenement);
    }

    // Utility method to map Entity to DTO
    private EvenementDTO mapToDTO(Evenement evenement) {
        EvenementDTO dto = new EvenementDTO();
        dto.setId(evenement.getId());
        dto.setNom(evenement.getNom());
        dto.setDescription(evenement.getDescription());
        dto.setDate(evenement.getDate());
        dto.setLieu(evenement.getLieu());
        // Convert image URL to Base64
        try {
            byte[] imageBytes = Files.readAllBytes(Paths.get(evenement.getImageUrl())); // Assuming it's a file path
            String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
            dto.setImageBase64(imageBase64);
        } catch (IOException e) {
            // Handle exception if image can't be read
            dto.setImageBase64(null);
        }
        dto.setCreateurId(evenement.getCreateur().getId());
        dto.setParticipantIds(evenement.getParticipants()
                .stream()
                .map(Utilisateur::getId)
                .collect(Collectors.toList()));
        return dto;
    }
}
