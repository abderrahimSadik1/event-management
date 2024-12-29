package com.backend.event_management.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.backend.event_management.model.Utilisateur;
import com.backend.event_management.service.UtilisateurService;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    // Get user
    @GetMapping()
    public ResponseEntity<Utilisateur> getUtilisateurById(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        Utilisateur user = utilisateurService.getUtilisateurByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Optional<Utilisateur> utilisateur = utilisateurService.getUtilisateurById(user.getId());

        if (utilisateur.isPresent()) {
            return new ResponseEntity<>(utilisateur.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // User not found
        }
    }

    // Get all users
    @GetMapping("/all")
    public ResponseEntity<Iterable<Utilisateur>> getAllUtilisateurs() {
        Iterable<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
        return new ResponseEntity<>(utilisateurs, HttpStatus.OK);
    }

    // Save a new user
    @PostMapping
    public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        Utilisateur savedUtilisateur = utilisateurService.saveUtilisateur(utilisateur);
        return new ResponseEntity<>(savedUtilisateur, HttpStatus.CREATED);
    }

    // Get user ID
    @GetMapping("/id")
    public ResponseEntity<Long> getUtilisateurId(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        Utilisateur user = utilisateurService.getUtilisateurByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new ResponseEntity<>(user.getId(), HttpStatus.OK);
    }

    // Update user information
    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable Long id,
            @RequestBody Utilisateur updatedUtilisateur) {
        Utilisateur utilisateur = utilisateurService.updateUtilisateur(id, updatedUtilisateur);

        if (utilisateur != null) {
            return new ResponseEntity<>(utilisateur, HttpStatus.OK); // Successfully updated
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // User not found
        }
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Long id) {
        Optional<Utilisateur> utilisateur = utilisateurService.getUtilisateurById(id);

        if (utilisateur.isPresent()) {
            return new ResponseEntity<>(utilisateur.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // User not found
        }
    }

    // Delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        Optional<Utilisateur> utilisateur = utilisateurService.getUtilisateurById(id);

        if (utilisateur.isPresent()) {
            utilisateurService.deleteUtilisateur(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Successfully deleted
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // User not found
        }
    }
}
