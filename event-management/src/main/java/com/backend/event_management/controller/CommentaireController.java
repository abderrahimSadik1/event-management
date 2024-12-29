package com.backend.event_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.event_management.model.Commentaire;
import com.backend.event_management.model.Utilisateur;
import com.backend.event_management.service.CommentaireService;
import com.backend.event_management.service.EvenementService;
import com.backend.event_management.service.UtilisateurService;

@RestController
@RequestMapping("/api/commentaires")
public class CommentaireController {
    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private CommentaireService commentaireService;

    @PostMapping
    public Commentaire createCommentaire(@RequestBody Commentaire commentaire,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        Utilisateur user = utilisateurService.getUtilisateurByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        commentaire.setUtilisateur(user);
        return commentaireService.saveCommentaire(commentaire);
    }

    @GetMapping
    public List<Commentaire> getAllCommentaires() {
        return commentaireService.getAllCommentaires();
    }

    @GetMapping("/{id}")
    public Commentaire getCommentaireById(@PathVariable Long id) {
        return commentaireService.getCommentaireById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCommentaire(@PathVariable Long id) {
        commentaireService.deleteCommentaire(id);
    }

    @GetMapping("/evenement/{evenementId}")
    public List<Commentaire> getCommentairesByEvenementId(@PathVariable Long evenementId) {
        return commentaireService.getCommentairesByEvenementId(evenementId);
    }
}