package com.backend.event_management.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.event_management.model.Utilisateur;
import com.backend.event_management.repository.UtilisateurRepository;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Optional<Utilisateur> getUtilisateurByEmail(String email) {

        return utilisateurRepository.findByEmail(email);
    }

    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id);
    }

    public Iterable<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public void deleteUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }

    // New method for updating user info
    public Utilisateur updateUtilisateur(Long id, Utilisateur updatedUtilisateur) {
        // Check if the user exists
        Optional<Utilisateur> existingUtilisateur = utilisateurRepository.findById(id);

        if (existingUtilisateur.isPresent()) {
            Utilisateur utilisateur = existingUtilisateur.get();

            // Update the fields you want to update
            if (updatedUtilisateur.getEmail() != null)
                utilisateur.setEmail(updatedUtilisateur.getEmail());
            if (updatedUtilisateur.getNom() != null)
                utilisateur.setNom(updatedUtilisateur.getNom());
            if (updatedUtilisateur.getPrenom() != null)
                utilisateur.setPrenom(updatedUtilisateur.getPrenom());
            // Add other fields as needed

            return utilisateurRepository.save(utilisateur); // Save the updated user
        } else {
            // User not found, return null or throw an exception as needed
            return null;
        }
    }
}
