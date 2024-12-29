package com.backend.event_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.event_management.model.Evenement;

@Repository
public interface EvenementRepository extends JpaRepository<Evenement, Long> {
}