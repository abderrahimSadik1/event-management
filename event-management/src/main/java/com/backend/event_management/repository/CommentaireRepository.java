package com.backend.event_management.repository;

import com.backend.event_management.model.Commentaire;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
    List<Commentaire> findByEvenementId(Long id);
}