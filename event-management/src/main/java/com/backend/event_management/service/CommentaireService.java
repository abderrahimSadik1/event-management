package com.backend.event_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.event_management.model.Commentaire;
import com.backend.event_management.repository.CommentaireRepository;

@Service
public class CommentaireService {

    @Autowired
    private CommentaireRepository commentaireRepository;

    public Commentaire saveCommentaire(Commentaire commentaire) {
        return commentaireRepository.save(commentaire);
    }

    public List<Commentaire> getAllCommentaires() {
        return commentaireRepository.findAll();
    }

    public Commentaire getCommentaireById(Long id) {
        return commentaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commentaire not found"));
    }

    public void deleteCommentaire(Long id) {
        commentaireRepository.deleteById(id);
    }

    public List<Commentaire> getCommentairesByEvenementId(Long evenementId) {
        return commentaireRepository.findByEvenementId(evenementId);
    }
}
