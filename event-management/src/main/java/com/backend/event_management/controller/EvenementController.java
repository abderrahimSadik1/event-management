package com.backend.event_management.controller;

import com.backend.event_management.dto.EvenementDTO;
import com.backend.event_management.model.Utilisateur;
import com.backend.event_management.service.EvenementService;
import com.backend.event_management.service.StorageService;
import com.backend.event_management.service.UtilisateurService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/evenements")
public class EvenementController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private EvenementService evenementService;

    @Autowired
    private StorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            String savedFilePath = storageService.uploadImage(file);
            return ResponseEntity.ok("Image saved at: " + savedFilePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error parsing produit JSON: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding produit: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<EvenementDTO> createEvenement(
            @RequestPart("evenement") EvenementDTO evenementDTO,
            @RequestPart("image") MultipartFile file, @AuthenticationPrincipal UserDetails userDetails)
            throws IOException {

        String email = userDetails.getUsername();
        Utilisateur user = utilisateurService.getUtilisateurByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        evenementDTO.setCreateurId(user.getId());
        evenementDTO.setParticipantIds(List.of(user.getId()));
        EvenementDTO savedEvent = evenementService.saveEvenement(evenementDTO, file);
        return ResponseEntity.ok(savedEvent);
    }

    @GetMapping
    public ResponseEntity<List<EvenementDTO>> getAllEvenements() {
        return ResponseEntity.ok(evenementService.getAllEvenements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvenementDTO> getEvenementById(@PathVariable Long id) {
        return ResponseEntity.ok(evenementService.getEvenementById(id));
    }
}
