package com.backend.event_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.event_management.model.Evenement;
import com.backend.event_management.model.Utilisateur;

@Repository
public interface EvenementRepository extends JpaRepository<Evenement, Long> {
    List<Evenement> findByCreateur(Utilisateur createur);
}